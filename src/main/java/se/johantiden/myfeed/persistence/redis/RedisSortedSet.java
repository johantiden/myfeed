package se.johantiden.myfeed.persistence.redis;


import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
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

    public void add(T member) {
        openJedis(j -> {
            double score = scoreMapper.apply(member);
            Long added = j.zadd(myKey, score, gson.toJson(member));
            return VOID;
        });
    }

    public void put(T member, Key<T> key, Type type) {
        if (isMember(member)) {
            return; // no change needed
        }
        removeIfPresent(key, type);

        add(member);
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

    public void remove(T member) {
        openJedis(j -> j.zrem(myKey, gson.toJson(member)));
    }

    public List<T> getAll(Type type) {
        return openJedis(j -> {
            Set<String> allMembers = j.zrangeByScore(myKey, "-inf", "+inf");
            return allMembers.stream().map(json -> fromJson(json, type)).collect(Collectors.toList());
        });
    }

    public Optional<T> find(Key<T> key, Type type) {
        return openJedis(j -> scanUntilFound(j, key, type));
    }

    private Optional<T> scanUntilFound(Jedis j, Key<T> key, Type type) {
        String match = toMatch(key);
        return scanUntilFound(j, match, "0", type);
    }

    private Optional<T> scanUntilFound(Jedis j, String match, String cursor, Type type) {
        ScanResult<Tuple> zscan = j.zscan(myKey, cursor, new ScanParams().match(match));
        List<Tuple> currentPage = zscan.getResult();

        if (!currentPage.isEmpty()) {
            T t = fromJson(currentPage.get(0).getElement(), type);
            return Optional.of(t);
        }

        // Still not found.
        // Beware of tail recursion. It might get stackoverflow.
        String stringCursor = zscan.getStringCursor();
        if ("0".equals(stringCursor)) {
            return Optional.empty();
        }
        return scanUntilFound(j, match, stringCursor, type);
    }

    private String toMatch(Key<T> key) {
        String json = gson.toJson(key);
        json = json.replaceAll("\\\"", "\\\\\"");
        return json;
    }

    private boolean isMember(T member) {
        return openJedis(j -> {
            String json = gson.toJson(member);
            Long rank = j.zrank(myKey, json);
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
