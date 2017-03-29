package se.johantiden.myfeed.persistence;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisSortedSet;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class DocumentRepository {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Gson gson;


    public void put(Document document) {
        getProxy().put(document, Document::getKey, Document.class);
    }

    public Optional<Document> find(Document document) {
        return find(document.getKey());
    }

    public Optional<Document> find(Predicate<Document> predicate) {
        return getProxy().find(predicate, Document.class);
    }

    public Optional<Document> find(Key<Document> documentKey) {
        return find(doc -> doc.getKey().equals(documentKey));
    }





    private RedisSortedSet<Document> getProxy() {
        return new RedisSortedSet<>(Keys.documents(), jedisPool, gson, youngestFirst());
    }

    private static Function<Document, Double> youngestFirst() {
        return d -> youngestFirstInstant().apply(d.publishedDate);
    }

    private static Function<Instant, Double> youngestFirstInstant() {
        return i -> (double) -i.toEpochMilli();
    }

    public long purgeOlderThan(Duration duration) {
        // Note that score is calculated as MINUS epochMillis. The youngest elements are the most negative,
        // We want to remove older that the youngest i.e. larger values.

        Instant minus = Instant.now().minus(duration);
        Double min = youngestFirstInstant().apply(minus);
        double maxValue = Double.MAX_VALUE;
        return getProxy().removeByScore(min, maxValue);
    }
}
