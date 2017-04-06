package se.johantiden.myfeed.persistence;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.redis.RedisMap;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DocumentGroupService {


    private static final Logger log = LoggerFactory.getLogger(DocumentGroupService.class);
    private JedisPool jedisPool;
    private Gson gson;

    public void putGrouping(Collection<Key<Document>> documentKeys) {
        List<DocumentGroup> existingGroups = documentKeys.stream()
                .map(this::findGroup)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());


        if (existingGroups.isEmpty()) {
            createGroup(documentKeys);
        } else if (existingGroups.size() == 1) {
            putAllDocumentsIntoGroup(documentKeys, existingGroups.get(0));
        } else {
            mergeAndAdd(documentKeys, existingGroups);
        }
    }

    private void mergeAndAdd(Collection<Key<Document>> documentKeys, List<DocumentGroup> existingGroups) {
         
    }

    private void putAllDocumentsIntoGroup(Collection<Key<Document>> documentKeys, DocumentGroup documentGroup) {
        not implemented
    }

    private void createGroup(Collection<Key<Document>> documentKeys) {
        not implemented
    }

    public Optional<DocumentGroup> findGroup(Key<Document> documentKey) {
        Optional<Key<DocumentGroup>> documentGroupKey = getProxyFindGroupByDocument().get(documentKey);
        return documentGroupKey.map(this::findGroup);
    }

    private DocumentGroup findGroup(Key<DocumentGroup> documentGroupKey) {
        return openJedis(j -> findGroup(j, documentGroupKey));
    }

    private DocumentGroup findGroup(Jedis j, Key<DocumentGroup> documentGroupKey) {
        String s = j.get(documentGroupKey.toString());
        DocumentGroup documentGroup = gson.fromJson(s, DocumentGroup.class);
        return documentGroup;
    }


    private <T> T openJedis(Function<Jedis, T> function) {
        try (Jedis jedis = jedisPool.getResource()){
            return function.apply(jedis);
        }
    }

    public void putGrouping(List<Document> documentKeys) {
        putGrouping(documentKeys.stream().map(Document::getKey).collect(Collectors.toList()));
    }

    private RedisMap<Key<Document>, Key<DocumentGroup>> getProxyFindGroupByDocument() {
        Type valueType = new Key<DocumentGroup>().getClass();
        return new RedisMap<>(Keys.findGroupByDocument(), jedisPool, gson, valueType);
    }
}
