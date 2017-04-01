package se.johantiden.myfeed.persistence.redis;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Objects.*;

public class RedisMap<T> {
    private static final Void VOID = null;

    private final JedisPool jedisPool;
    private final String myKey;
    private final String indexSetKey;
    private final Gson gson;
    private final Function<T, Key<T>> keyMapper;

    public RedisMap(Key<RedisMap<T>> key, JedisPool jedisPool, Gson gson, Function<T, Key<T>> keyMapper) {
        this.myKey = requireNonNull(key).toString();
        this.indexSetKey = myKey + ":index";
        this.jedisPool = requireNonNull(jedisPool);
        this.gson = requireNonNull(gson);
        this.keyMapper = requireNonNull(keyMapper);
    }

    public long add(T t, double score) {
        return openJedis(j -> add(j, t, score));
    }

    public long add(Jedis j, T t, double score) {
        Key<T> key = keyMapper.apply(t);
        Long added = j.zadd(indexSetKey, score, key.toString());
        j.set(key.toString(), gson.toJson(t));
        return added;
    }

    public void put(T member, double score, Key<T> key) {
        openJedis(j -> {
            put(j, member, score, key);
            return VOID;
        });
    }

    public void put(Jedis j, T t, double score, Key<T> key) {
        if (!isMember(j, key)) {
            Long added = j.zadd(indexSetKey, score, key.toString());
        }
        j.set(key.toString(), gson.toJson(t));
    }

    private boolean isMember(Jedis j, Key<T> key) {
        String s = j.get(key.toString());
        return s != null;
//        Long rank = j.zrank(indexSetKey, key.toString());
//        return rank != null;
    }


    private <R> R openJedis(Function<Jedis, R> function) {
        try (Jedis jedis = jedisPool.getResource()){
            return function.apply(jedis);
        }
    }


    public long removeByScore(String start, String end) {
        return openJedis(j -> removeByScore(j, start, end));
    }

    public long removeByScore(Jedis jedis, String start, String end) {
        Set<String> keysByScore = jedis.zrangeByScore(indexSetKey, start, end);
        Long numRemoved = jedis.zremrangeByScore(indexSetKey, start, end);
        long numRemovedP = numRemoved.longValue();
        String[] keysToDelete = keysByScore.toArray(new String[]{});
        keysByScore.forEach(k -> jedis.del(keysToDelete));
        return numRemovedP;
    }

    public Optional<T> find(Key<T> key, Type type) {
        return openJedis(j -> find(j, key, type));
    }
    public Optional<T> find(Jedis jedis, Key<T> key, Type type) {
        String jsonOrNull = jedis.get(key.toString());
        return Optional
                .ofNullable(jsonOrNull)
                .map(s -> gson.fromJson(s, type));
    }

    public void remove(Key<T> key) {
        openJedis(j -> {
            remove(j, key);
            return VOID;
        });
    }
    public void remove(Jedis jedis, Key<T> key) {
        jedis.del(key.toString());
    }

    public Collection<String> getAllKeys() {
        return openJedis(this::getAllKeys);
    }
    public Collection<String> getAllKeys(Jedis jedis) {
        Set<String> allKeys = jedis.zrangeByScore(indexSetKey, "-inf", "inf");
        return allKeys;
    }
}
