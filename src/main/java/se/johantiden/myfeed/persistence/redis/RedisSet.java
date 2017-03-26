package se.johantiden.myfeed.persistence.redis;


import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
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
     * scard
     */
    public int scard() {
        throw new RuntimeException("Not implemneted");
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

    public Optional<T> find(Predicate<T> predicate, Type type) {
        return openJedis(j -> scanUntilFound(predicate, j, "0", type));
    }

    private Optional<T> scanUntilFound(Predicate<T> predicate, Jedis j, String cursor, Type type) {
        ScanResult<String> scan = j.sscan(myKey, cursor);
        List<String> currentPage = scan.getResult();

        if (currentPage.isEmpty()) {
            return Optional.empty();
        }


        for (String json : currentPage) {
            T t = fromJson(json, type);
            if (predicate.test(t)) {
                return Optional.of(t);
            }
        }

        // Still not found.
        // Beware of tail recursion. It might get stackoverflow.
        String stringCursor = scan.getStringCursor();
        if ("0".equals(stringCursor)) {
            return Optional.empty();
        }
        return scanUntilFound(predicate, j, stringCursor, type);
    }

    private <T> T openJedis(Function<Jedis, T> function) {
        try (Jedis jedis = jedisPool.getResource()){
            return function.apply(jedis);
        }
    }

    public boolean removeIf(Predicate<T> predicate, Type type) {
        Optional<T> t = find(predicate, type);
        if (t.isPresent()) {
            remove(t.get());
            removeIf(predicate, type); // Find more and remove them too. Beware of stack overflow :D
            return true;
        }
        return false;
    }

    public void put(T member, Function<T, Object> equatableProperty, Type type) {
        if (isMember(member)) {
            return; // no change needed
        }
        removeIf(t -> equatableProperty.apply(t).equals(equatableProperty.apply(member)), type);

        add(member);
    }

    private T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
