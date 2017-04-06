package se.johantiden.myfeed.persistence.redis;


import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RedisMap<K, V> {

    private static final Void VOID = null;
    private final String myKey;
    private final JedisPool jedisPool;
    private final Gson gson;
    private final Type valueType;

    public RedisMap(Key<RedisMap<K, V>> key, JedisPool jedisPool, Gson gson, Type valueType) {
        this.myKey = Objects.requireNonNull(key).toString();
        this.jedisPool = Objects.requireNonNull(jedisPool);
        this.gson = Objects.requireNonNull(gson);
        this.valueType = Objects.requireNonNull(valueType);
    }

    /**
     * spop
     */
    public Optional<V> popAnyElement() {
        return openJedis(j -> {
            String spop = j.spop(myKey);
            return Optional.ofNullable(spop).map(s -> gson.fromJson(s, valueType));
        });
    }

    /**
     * sadd
     */
    public void add(V member) {
        openJedis(j -> {
            add(j, member);
            return VOID;
        });
    }

    public void add(Jedis jedis, V member) {
        jedis.sadd(myKey, gson.toJson(member));
    }


    /**
     * sismember
     */
    public boolean isMember(V member) {
        return openJedis(j -> isMember(j, member));
    }

    public boolean isMember(Jedis jedis, V member) {
        return jedis.sismember(myKey, gson.toJson(member));
    }

    /**
     * smembers
     */
    public Set<V> getAll() {
        return openJedis(j -> {
            Set<String> smembers = j.smembers(myKey);
            return smembers.stream()
                    .<V>map(json -> gson.fromJson(json, valueType)).collect(Collectors.toSet());
        });
    }

    /**
     * srem
     */
    public void remove(V member) {
        openJedis(j -> {
            remove(j, member);
            return VOID;
        });
    }

    public void remove(Jedis jedis, V member) {
        jedis.srem(myKey, gson.toJson(member));
    }


    private Optional<V> scanUntilFound(Jedis j, K key) {
        String matcher = toMatcher(key);
        return scanUntilFound(j, matcher, "0");
    }

    private Optional<V> scanUntilFound(Jedis j, String match, String cursor) {
        ScanResult<String> scan = j.sscan(myKey, cursor, new ScanParams().match(match));
        List<String> currentPage = scan.getResult();

        if (!currentPage.isEmpty()) {
            V v = gson.fromJson(currentPage.get(0), valueType);
            return Optional.of(v);
        }

        // Still not found.
        // Beware of tail recursion. It might get stackoverflow.
        String stringCursor = scan.getStringCursor();
        if ("0".equals(stringCursor)) {
            return Optional.empty();
        }
        return scanUntilFound(j, match, stringCursor);
    }

    private String toMatcher(K key) {
        String json = gson.toJson(key);
        json = json.replaceAll("\\\"", "\\\\\"");
        return json;
    }

    private <T> T openJedis(Function<Jedis, T> function) {
        try (Jedis jedis = jedisPool.getResource()){
            return function.apply(jedis);
        }
    }

    public boolean removeIfPresent(K key) {
        return openJedis(j -> removeIfPresent(j, key));
    }

    public boolean removeIfPresent(Jedis jedis, K key) {
        Optional<V> t = scanUntilFound(jedis, key);
        if (t.isPresent()) {
            remove(jedis, t.get());
            removeIfPresent(jedis, key); // Find more and remove them too. Beware of stack overflow :D
            return true;
        }
        return false;
    }


    public Optional<V> get(K key) {
        return openJedis(j -> scanUntilFound(j, key));
    }

    public Optional<V> get(Jedis jedis, K key) {
        return scanUntilFound(jedis, key);
    }

    public void put(V member, K key) {
        openJedis(j -> {
            put(j, member, key);
            return VOID;
        });
    }

    public void put(Jedis jedis, V member, K key) {
        if (isMember(jedis, member)) {
            return; // no change needed
        }
        removeIfPresent(jedis, key);

        add(jedis, member);
    }
}
