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

public class RedisSet<T> {

    private static final Void VOID = null;
    private final String myKey;
    private final JedisPool jedisPool;
    private final Gson gson;

    public RedisSet(Key<RedisSet<T>> key, JedisPool jedisPool, Gson gson) {
        this.myKey = Objects.requireNonNull(key).toString();
        this.jedisPool = Objects.requireNonNull(jedisPool);
        this.gson = Objects.requireNonNull(gson);
    }

    /**
     * spop
     */
    public Optional<T> popAnyElement(Type type) {
        return openJedis(j -> {
            String spop = j.spop(myKey);
            return Optional.ofNullable(spop).map(s -> fromJson(s, type));
        });
    }

    /**
     * sadd
     */
    public void add(T member) {
        openJedis(j -> {
            j.sadd(myKey, gson.toJson(member));
            return VOID;
        });
    }

    /**
     * sismember
     */
    public boolean isMember(T member) {
        return openJedis(j -> j.sismember(myKey, gson.toJson(member)));
    }

    /**
     * smembers
     * @param type
     */
    public Set<T> getAll(Type type) {
        return openJedis(j -> {
            Set<String> smembers = j.smembers(myKey);
            return smembers.stream().map(json -> fromJson(json, type)).collect(Collectors.toSet());
        });
    }

    /**
     * srem
     */
    public void remove(T member) {
        openJedis(j -> j.srem(myKey, gson.toJson(member)));
    }

    private Optional<T> scanUntilFound(Jedis j, Key<T> key, Type type) {
        String matcher = toMatcher(key);
        return scanUntilFound(j, matcher, "0", type);
    }

    private Optional<T> scanUntilFound(Jedis j, String match, String cursor, Type type) {
        ScanResult<String> scan = j.sscan(myKey, cursor, new ScanParams().match(match));
        List<String> currentPage = scan.getResult();

        if (!currentPage.isEmpty()) {
            T t = fromJson(currentPage.get(0), type);
            return Optional.of(t);
        }

        // Still not found.
        // Beware of tail recursion. It might get stackoverflow.
        String stringCursor = scan.getStringCursor();
        if ("0".equals(stringCursor)) {
            return Optional.empty();
        }
        return scanUntilFound(j, match, stringCursor, type);
    }

    private String toMatcher(Key<T> key) {
        String json = gson.toJson(key);
        json = json.replaceAll("\\\"", "\\\\\"");
        return json;
    }

    private <T> T openJedis(Function<Jedis, T> function) {
        try (Jedis jedis = jedisPool.getResource()){
            return function.apply(jedis);
        }
    }

    public boolean removeIfPresent(Key<T> key, Type type) {
        Optional<T> t = find(key, type);
        if (t.isPresent()) {
            remove(t.get());
            removeIfPresent(key, type); // Find more and remove them too. Beware of stack overflow :D
            return true;
        }
        return false;
    }

    public Optional<T> find(Key<T> key, Type type) {
        return openJedis(j -> {
            Optional<T> t = scanUntilFound(j, key, type);
            return t;
        });
    }

    public void put(T member, Key<T> key, Type type) {
        if (isMember(member)) {
            return; // no change needed
        }
        removeIfPresent(key, type);

        add(member);
    }

    private T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
