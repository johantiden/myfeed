package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DocumentRepository {

    public static final Comparator<Document> YOUNGEST_FIRST =
            Comparator.comparing(Document::getPublishDate);

    Map<Key<Document>, Document> map = new HashMap<>();


    public void put(Document document) {
        map.put(document.getKey(), document);
    }

    public Optional<Document> find(Key<Document> documentKey) {
        return Optional.ofNullable(map.get(documentKey));
    }

    public long purgeOlderThan(Duration duration) {

        int removed = 0;
        List<Map.Entry<Key<Document>, Document>> toBeRemoved = map.entrySet().stream()
                .filter(isOlderThan(duration))
                .collect(Collectors.toList());

        toBeRemoved.forEach(e -> map.remove(e.getKey()));

        return toBeRemoved.size();
    }

    private Predicate<? super Map.Entry<Key<Document>, Document>> isOlderThan(Duration duration) {
        return e -> e.getValue().getPublishDate().plus(duration).isAfter(Instant.now());
    }
}
