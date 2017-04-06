package se.johantiden.myfeed.persistence;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisIndexedMap;
import se.johantiden.myfeed.persistence.user.User;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public class UserDocumentRepository {


    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Gson gson;

    public Collection<String> getAllKeys(Key<User> user) {
        return getProxy(user).getAllKeys();
    }

    public void put(UserDocument userDocument) {
        Double score = youngestFirst().apply(userDocument);
        getProxy(userDocument.getUserKey()).put(userDocument, score, userDocument.getKey());
    }

    public Optional<UserDocument> find(Key<User> userKey, Key<UserDocument> userDocumentKey) {
        return getProxy(userKey).find(userDocumentKey);

    }

    private RedisIndexedMap<Key<UserDocument>, UserDocument> getProxy(Key<User> userKey) {
        Key<RedisIndexedMap<Key<UserDocument>, UserDocument>> redisIndexedMapKey = Keys.userDocuments(userKey);
        return new RedisIndexedMap<>(redisIndexedMapKey, UserDocument.class, jedisPool, gson, UserDocument::getKey);
    }

    private static Function<UserDocument, Double> youngestFirst() {
        return d -> youngestFirstInstant().apply(d.getPublishDate());
    }

    private static Function<Instant, Double> youngestFirstInstant() {
        return i -> (double) -i.toEpochMilli();
    }

    public long purgeOlderThan(Key<User> user, Duration duration) {
        // Note that score is calculated as MINUS epochMillis. The youngest elements are the most negative,
        // We want to remove older that the youngest i.e. larger values.

        Instant minus = Instant.now().minus(duration);
        String min = youngestFirstInstant().apply(minus).toString();
        String maxValue = "inf";
        return getProxy(user).removeByScore(min, maxValue);
    }

    public void remove(UserDocument userDocument) {
        getProxy(userDocument.getUserKey()).remove(userDocument.getKey());
    }
}
