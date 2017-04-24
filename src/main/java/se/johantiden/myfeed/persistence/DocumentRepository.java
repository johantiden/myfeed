package se.johantiden.myfeed.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.util.Chrono;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DocumentRepository implements Serializable {

    private static final long serialVersionUID = -4911338149956359732L;
    private static final Logger log = LoggerFactory.getLogger(DocumentRepository.class);

    private final Map<Key<Document>, Document> map;

    public DocumentRepository(Map<Key<Document>, Document> map) {
        this.map = Objects.requireNonNull(map);
    }

    public final void put(Document document) {
        map.put(document.getKey(), document);
    }

    public final Optional<Document> find(Key<Document> documentKey) {
        return Optional.ofNullable(map.get(documentKey));
    }

    public final long purgeOlderThan(Duration duration) {

        List<Map.Entry<Key<Document>, Document>> toBeRemoved = map.entrySet().stream()
                .filter(isOlderThan(duration))
                .collect(Collectors.toList());

        toBeRemoved.forEach(e -> map.remove(e.getKey()));

        return toBeRemoved.size();
    }

    private Predicate<? super Map.Entry<Key<Document>, Document>> isOlderThan(Duration duration) {
        return e -> {
            Instant publishDate = e.getValue().getPublishDate();
            return Chrono.isOlderThan(duration, publishDate);
        };
    }

    public void purge(Key<Document> documentKey) {
        map.remove(documentKey);
    }

    public final int size() {
        return map.size();
    }
}
