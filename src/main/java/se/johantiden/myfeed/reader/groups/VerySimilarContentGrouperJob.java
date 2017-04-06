package se.johantiden.myfeed.reader.groups;

import com.google.gson.Gson;
import info.debatty.java.stringsimilarity.Cosine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentGroupService;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisMap;
import se.johantiden.myfeed.service.DocumentService;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class VerySimilarContentGrouperJob  {

    private static final Logger log = LoggerFactory.getLogger(VerySimilarContentGrouperJob.class);
    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private Gson gson;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentGroupService groupService;

    @Scheduled(fixedRate = 1000)
    public void consumeOne() {
        Collection<Document> allDocuments = documentService.getAll();

        Optional<Document> documentOptional = findUnconsumedDocument(allDocuments);

        documentOptional.ifPresent(document -> {
            log.info("Consuming document: {}", document);
            setConsumed(document);
            consume(document, allDocuments);
        });
    }

    private void consume(Document document, Collection<Document> allDocuments) {

        List<Document> verySimilarDocuments = allDocuments.stream()
                .filter(d -> isVerySimilar(d, document))
                .collect(Collectors.toList());

        // The document itself is in the list, so check if more than 1
        if (verySimilarDocuments.size() > 1) {
            createGroup(verySimilarDocuments);
        }
    }

    private void createGroup(List<Document> verySimilarDocuments) {
        log.info("Group: {}", verySimilarDocuments);
        groupService.putGrouping(verySimilarDocuments);
    }

    private boolean isVerySimilar(Document a, Document b) {
        if (Objects.equals(a, b)) {
            return true;
        }

        boolean titleEquals = Objects.equals(a.title, b.title);
        if (!titleEquals) {
            return false;

        }
        return true;
//        Cosine cosine = new Cosine();
//        double distance = cosine.distance(a.text, b.text);
//        return distance < 0.1;
    }

    private void setConsumed(Document document) {
        Key<Document> key = document.getKey();
        getProxy().put(key, key);
    }

    private Optional<Document> findUnconsumedDocument(Collection<Document> allDocuments) {


        try (Jedis jedis = jedisPool.getResource();) {
            return allDocuments.stream()
                    .filter(d -> {
                        Key<Document> key = d.getKey();
                        Optional<Key<Document>> alreadyConsumed = getProxy().get(jedis, key);
                        return !alreadyConsumed.isPresent();
                    }).findAny();
        }
    }

    private RedisMap<Key<Document>, Key<Document>> getProxy() {
        Type keyType = new Key<Document>().getClass();
        Key<RedisMap<Key<Document>, Key<Document>>> redisSetKey = Keys.verySimilarGrouper();
        return new RedisMap<>(redisSetKey, jedisPool, gson, keyType);
    }


}
