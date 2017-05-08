package se.johantiden.myfeed.controller;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Subject implements Predicate<Document> {

    public static final Comparator<Subject> COMPARATOR = Comparator.comparing(Subject::getTitle);

    private final String title;
    private final Predicate<Document> hasDocument;
    private final String tab;

    public Subject(String title, String tab, Predicate<Document> hasDocument) {
        this.title = Objects.requireNonNull(title);
        this.tab = Objects.requireNonNull(tab);
        this.hasDocument = Objects.requireNonNull(hasDocument);
    }

    @Override
    public boolean test(Document document) {
        return hasDocument.test(document);
    }

    public String getTitle() {
        return title;
    }

    public Key<Subject> getKey() {
        return Keys.subject(this);
    }

    public String getTab() {
        return tab;
    }
}
