package se.johantiden.myfeed.persistence;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisIndexedMap;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class DocumentRepository {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Gson gson;


    public void put(Document document) {
        Double score = youngestFirst().apply(document);
        getProxy().put(document, score, document.getKey());
    }

    public Optional<Document> find(Key<Document> documentKey) {
        return getProxy().find(documentKey);
    }

    public Optional<Document> findAnySlow(Predicate<Document> predicate) {
        return getProxy().findAnySlow(predicate);
    }

    public List<Document> findAllSlow(Predicate<Document> predicate) {
        return getProxy().findAllSlow(predicate);
    }

    private RedisIndexedMap<Key<Document>, Document> getProxy() {
        return new RedisIndexedMap<>(Keys.documents(), Document.class, jedisPool, gson, Document::getKey);
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
        String min = youngestFirstInstant().apply(minus).toString();
        String maxValue = "inf";
        return getProxy().removeByScore(min, maxValue);
    }

    public Collection<Document> getAll() {
        return getProxy().values();
    }
}
