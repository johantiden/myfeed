package se.johantiden.myfeed.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.util.Chrono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DocumentRepository {

    @Autowired
    public DB db;

    public DocumentRepository(DB db) {
        this.db = db;
        resetSubjects();
    }

    public final void put(Document document) {
        db.documents.put(document.getKey(), document);
    }

    public final Optional<Document> find(Key<Document> documentKey) {
        return Optional.ofNullable(db.documents.get(documentKey));
    }

    public List<Document> find(Predicate<Document> predicate) {
        return db.documents.values().stream().filter(predicate).collect(Collectors.toList());
    }

    public final long purgeOlderThan(Duration duration) {

        List<Map.Entry<Key<Document>, Document>> toBeRemoved = db.documents.entrySet().stream()
                .filter(isOlderThan(duration))
                .collect(Collectors.toList());

        toBeRemoved.forEach(e -> db.documents.remove(e.getKey()));

        return toBeRemoved.size();
    }

    private static Predicate<? super Map.Entry<Key<Document>, Document>> isOlderThan(Duration duration) {
        return e -> {
            Instant publishDate = e.getValue().getPublishDate();
            return Chrono.isOlderThan(duration, publishDate);
        };
    }

    public final void purge(Key<Document> documentKey) {
        db.documents.remove(documentKey);
    }

    public final void resetSubjects() {
        db.documents.values().stream()
                .forEach(d -> {
                    d.subjects.clear();
                    d.tab = null;
                });
    }
}
