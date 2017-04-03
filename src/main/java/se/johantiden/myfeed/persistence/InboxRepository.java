package se.johantiden.myfeed.persistence;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisSet;

import java.util.Optional;

public class InboxRepository {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Gson gson;

    private RedisSet<Document> getProxy() {
        return new RedisSet<>(Keys.inbox(), jedisPool, gson);
    }

    public Optional<Document> pop() {
        return getProxy().popAnyElement(Document.class);
    }

    public void put(Document document) {
        getProxy().put(document, document.getKey(), Document.class);
    }

    public Optional<Document> find(Document document) {
        return find(document.getKey());
    }

    public Optional<Document> find(Key<Document> documentKey) {
        return getProxy().find(documentKey, Document.class);
    }

    public boolean hasDocument(Key<Document> key) {
        return find(key).isPresent();
    }
}
