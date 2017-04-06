package se.johantiden.myfeed.persistence;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisMap;

import java.util.Optional;

public class InboxRepository {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Gson gson;

    private RedisMap<Key<Document>, Document> getProxy() {
        return new RedisMap<>(Keys.inbox(), jedisPool, gson, Document.class);
    }

    public Optional<Document> pop() {
        return getProxy().popAnyElement();
    }

    public void put(Document document) {
        getProxy().put(document, document.getKey());
    }

    public Optional<Document> find(Document document) {
        return find(document.getKey());
    }

    public Optional<Document> find(Key<Document> documentKey) {
        return getProxy().get(documentKey);
    }

    public boolean hasDocument(Key<Document> key) {
        return find(key).isPresent();
    }
}
