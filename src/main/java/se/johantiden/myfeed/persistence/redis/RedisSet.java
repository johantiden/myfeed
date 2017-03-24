package se.johantiden.myfeed.persistence.redis;


import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

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
    public Optional<T> popRandomElement() {
        return withJedis(j -> {
            String spop = j.spop(myKey);
            return Optional.ofNullable(spop).map(s -> {
                Type typeOfT = this.getClass().getGenericInterfaces()[0];
                return gson.fromJson(s, typeOfT);
            });
        });
    }

    /**
     * sadd
     */
    public void add(T member) {
        withJedis(j -> {
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
        throw new RuntimeException("Not implemneted");
    }

    /**
     * smembers
     */
    public Set<T> getAll() {
        throw new RuntimeException("Not implemneted");
    }

    /**
     * srem
     */
    public void remove(T member) {
        throw new RuntimeException("Not implemneted");
    }

    public Optional<T> find(Predicate<T> predicate) {
        throw new RuntimeException("Not implemneted");
    }

    private <T> T withJedis(Function<Jedis, T> function) {
        try (Jedis jedis = jedisPool.getResource()){
            return function.apply(jedis);
        }
    }

    public boolean removeIf(Predicate<T> predicate) {
        Optional<T> t = find(predicate);
        if (t.isPresent()) {
            remove(t.get());
            removeIf(predicate); // Find more and remove them too. Beware of stack overflow :D
            return true;
        }
        return false;
    }

    public void put(T member, Function<T, Object> equatableProperty) {
        if (isMember(member)) {
            return; // no change needed
        }
        removeIf(t -> equatableProperty.apply(t).equals(equatableProperty.apply(member)));

        add(member);
    }
}
