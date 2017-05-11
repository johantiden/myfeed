package se.johantiden.myfeed.controller;

import se.johantiden.myfeed.persistence.Document;

import java.util.Objects;
import java.util.function.Predicate;

public class Subject2 implements Predicate<Document> {

    private final Predicate<Document> hasDocument;
    private final Subject subject;

    public Subject2(String title, Predicate<Document> hasDocument) {
        this.subject = new Subject(title);
        this.hasDocument = Objects.requireNonNull(hasDocument);
    }

    @Override
    public boolean test(Document document) {
        return hasDocument.test(document);
    }

    public Subject getSubject() {
        return subject;
    }
}
