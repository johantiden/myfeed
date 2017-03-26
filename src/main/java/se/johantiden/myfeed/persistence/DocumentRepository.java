package se.johantiden.myfeed.persistence;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisSet;

import java.util.Optional;

public class DocumentRepository {

    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private Gson gson;

    private RedisSet<Document> getDocuments() {
        return new RedisSet<>(Keys.documents(), jedisPool, gson);
    }

    public Optional<Document> getNextUnfanned() {
        return getDocuments().popRandomElement(Document.class);
    }

    public void put(Document document) {
        getDocuments().put(document, Document::getKey, Document.class);
    }
}
