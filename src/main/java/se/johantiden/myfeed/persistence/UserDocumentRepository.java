package se.johantiden.myfeed.persistence;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisSortedSet;
import se.johantiden.myfeed.persistence.user.User;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserDocumentRepository {


    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Gson gson;

    public List<UserDocument> getUnreadDocuments(Key<User> user) {
        List<UserDocument> userDocuments = getProxy(user).getAll(UserDocument.class);
        userDocuments.removeIf(UserDocument::isRead);
        return userDocuments;
    }

    public void put(UserDocument userDocument) {
        getProxy(userDocument.getUserKey()).put(userDocument, userDocument.getKey(), UserDocument.class);
    }

    public Optional<UserDocument> find(Key<User> user, Key<Document> documentKey) {
       return getProxy(user).find(Keys.userDocument(user, documentKey), UserDocument.class);
    }

    private RedisSortedSet<UserDocument> getProxy(Key<User> user) {
        return new RedisSortedSet<>(Keys.userDocuments(user), jedisPool, gson, youngestFirst());
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
        Double min = youngestFirstInstant().apply(minus);
        double maxValue = Double.MAX_VALUE;
        return getProxy(user).removeByScore(min, maxValue);
    }
}
