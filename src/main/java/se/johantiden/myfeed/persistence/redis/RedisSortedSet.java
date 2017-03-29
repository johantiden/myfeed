package se.johantiden.myfeed.persistence.redis;


import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RedisSortedSet<T> {

    private static final Void VOID = null;
    private final String myKey;
    private final JedisPool jedisPool;
    private final Gson gson;
    private final Function<T, Double> scoreMapper;

    public RedisSortedSet(Key<RedisSortedSet<T>> key, JedisPool jedisPool, Gson gson, Function<T, Double> scoreMapper) {
        this.myKey = Objects.requireNonNull(key).toString();
        this.jedisPool = Objects.requireNonNull(jedisPool);
        this.gson = Objects.requireNonNull(gson);
        this.scoreMapper = Objects.requireNonNull(scoreMapper);
    }

    public void put(T member) {
        openJedis(j -> {
            double score = scoreMapper.apply(member);
            j.zadd(myKey, score, gson.toJson(member));
            return VOID;
        });
    }

    public List<T> getAll(Type type) {
        return openJedis(j -> {
            Set<String> allMembers = j.zrangeByScore(myKey, "-inf", "+inf");
            return allMembers.stream().map(json -> fromJson(json, type)).collect(Collectors.toList());
        });
    }

    public Optional<T> find(Predicate<T> predicate, Type type) {
        return openJedis(j -> scanUntilFound(predicate, j, "0", type));
    }

    private Optional<T> scanUntilFound(Predicate<T> predicate, Jedis j, String cursor, Type type) {
        ScanResult<Tuple> zscan = j.zscan(myKey, cursor);
        List<Tuple> currentPage = zscan.getResult();

        if (currentPage.isEmpty()) {
            return Optional.empty();
        }


        for (Tuple tuple : currentPage) {
            T t = fromJson(tuple.getElement(), type);
            if (predicate.test(t)) {
                return Optional.of(t);
            }
        }

        // Still not found.
        // Beware of tail recursion. It might get stackoverflow.
        String stringCursor = zscan.getStringCursor();
        if ("0".equals(stringCursor)) {
            return Optional.empty();
        }
        return scanUntilFound(predicate, j, stringCursor, type);
    }

    private boolean isMember(T member) {
        return openJedis(j -> {
            Long rank = j.zrank(myKey, gson.toJson(member));
            return rank != null;
        });
    }

    public long removeByScore(double start, double end) {
        return openJedis(j -> {
            Long numRemoved = j.zremrangeByScore(myKey, start, end);
            long numRemovedP = numRemoved.longValue();
            return numRemovedP;
        });
    }


    private <R> R openJedis(Function<Jedis, R> function) {
        try (Jedis jedis = jedisPool.getResource()){
            return function.apply(jedis);
        }
    }


    private T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

}
