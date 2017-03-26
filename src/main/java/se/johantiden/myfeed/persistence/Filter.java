package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.util.JCollections;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Filter implements Predicate<Document> {

    public static final Filter TRUE = new Filter(d -> true);

    private final Predicate<Document> predicate;

    public Filter(Predicate<Document> predicate) {
        this.predicate = Objects.requireNonNull(predicate);
    }

    public Filter(List<Predicate<Document>> predicates) {
        Objects.requireNonNull(predicates);
        this.predicate = JCollections.and(predicates);
    }

    @Override
    public boolean test(Document document) {
        return predicate.test(document);
    }
}
