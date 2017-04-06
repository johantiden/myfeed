package se.johantiden.myfeed.persistence.redis;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.*;

public class RedisIndexedMap<K, V> {
    private static final Void VOID = null;


    private final JedisPool jedisPool;
    private final String myKey;
    private final String indexSetKey;
    private final Gson gson;
    private final Function<V, K> keyMapper;
    private final Type valueType;

    public RedisIndexedMap(Key<RedisIndexedMap<K, V>> key, Type valueType, JedisPool jedisPool, Gson gson, Function<V, K> keyMapper) {
        this.myKey = requireNonNull(key).toString();
        this.valueType = requireNonNull(valueType);
        this.indexSetKey = myKey + ":index";
        this.jedisPool = requireNonNull(jedisPool);
        this.gson = requireNonNull(gson);
        this.keyMapper = requireNonNull(keyMapper);
    }

    public long add(V v, double score) {
        return openJedis(j -> add(j, v, score));
    }

    public long add(Jedis j, V v, double score) {
        K key = keyMapper.apply(v);
        Long added = j.zadd(indexSetKey, score, key.toString());
        j.set(key.toString(), gson.toJson(v));
        return added;
    }

    public void put(V member, double score, K key) {
        openJedis(j -> {
            put(j, member, score, key);
            return VOID;
        });
    }

    public void put(Jedis j, V v, double score, K key) {
        if (!isMember(j, key)) {
            Long added = j.zadd(indexSetKey, score, key.toString());
        }
        j.set(key.toString(), gson.toJson(v));
    }

    private boolean isMember(Jedis j, K key) {
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

    public Optional<V> find(K key) {
        return openJedis(j -> find(j, key));
    }

    public Optional<V> find(Jedis jedis, K key) {
        return find(jedis, key.toString());
    }

    public Optional<V> find(Jedis jedis, String key) {
        String jsonOrNull = jedis.get(key);
        return Optional
                .ofNullable(jsonOrNull)
                .map(s -> gson.fromJson(s, valueType));
    }

    public void remove(K key) {
        openJedis(j -> {
            remove(j, key);
            return VOID;
        });
    }

    public void remove(Jedis jedis, K key) {
        jedis.del(key.toString());
    }

    public Collection<String> getAllKeys() {
        return openJedis(this::getAllKeys);
    }

    public Collection<String> getAllKeys(Jedis jedis) {
        return jedis.zrangeByScore(indexSetKey, "-inf", "inf");
    }

    public Optional<V> findAnySlow(Predicate<V> predicate) {
        return openJedis(j -> findAnySlow(j, predicate));
    }

    public Optional<V> findAnySlow(Jedis jedis, Predicate<V> predicate) {
        return scanUntilFound(jedis, predicate, "0");
    }

    private Optional<V> scanUntilFound(Jedis j, Predicate<V> predicate, String cursor) {
        ScanResult<Tuple> scan = j.zscan(indexSetKey, cursor);
        List<Tuple> currentPageKeys = scan.getResult();

        Optional<V> any = currentPageKeys.stream()
                .map(k -> find(j, k.getElement()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(predicate)
                .findAny();
        
        if (any.isPresent()) {
            return any;
        }

        // Still not found.
        // Beware of tail recursion. It might get stackoverflow.
        String stringCursor = scan.getStringCursor();
        if ("0".equals(stringCursor)) {
            return Optional.empty();
        }
        return scanUntilFound(j, predicate, stringCursor);
    }

    public List<V> findAllSlow(Predicate<V> predicate) {
        return openJedis(j -> findAllSlow(j, predicate));
    }

    public List<V> findAllSlow(Jedis jedis, Predicate<V> predicate) {
        return scanUntilEnd(jedis, predicate, "0");
    }

    private List<V> scanUntilEnd(Jedis j, Predicate<V> predicate, String cursor) {
        ScanResult<Tuple> scan = j.zscan(indexSetKey, cursor);
        List<Tuple> currentPage = scan.getResult();

        List<V> matches = currentPage.stream()
                .map(k -> find(j, k.getElement()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(predicate)
                .collect(Collectors.toList());



        // Beware of tail recursion. It might get stackoverflow.
        String stringCursor = scan.getStringCursor();
        if ("0".equals(stringCursor)) {
            return matches;
        }
        List<V> rest = scanUntilEnd(j, predicate, stringCursor);
        matches.addAll(rest);
        return matches;
    }

    public Collection<V> values() {
        return openJedis(this::values);
    }

    public Collection<V> values(Jedis jedis) {
        return getAllKeys(jedis).stream()
                .map(k -> find(jedis, k))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
