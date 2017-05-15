package se.johantiden.myfeed.classification;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.util.DocumentPredicates;

import java.util.Objects;

public class DocumentMatcher {
    private final Document document;

    public DocumentMatcher(Document document) {
        this.document = Objects.requireNonNull(document);
    }

    public boolean has(String... strings) {
        return DocumentPredicates.has(strings).test(document);
    }

    public boolean hasCaseSensitive(String... strings) {
        return DocumentPredicates.hasCaseSensitive(strings).test(document);
    }

    public boolean isFromFeed(String feedName) {
        return DocumentPredicates.isFromFeed(feedName).test(document);
    }

    public boolean startsWith(String string) {
        return DocumentPredicates.startsWith(string).test(document);
    }

    public boolean startsWithCaseSensitive(String string) {
        return DocumentPredicates.startsWithCaseSensitive(string).test(document);
    }

    public boolean anyCategoryEquals(String category) {
        return DocumentPredicates.anyCategoryEquals(category).test(document);
    }

    public boolean anySubjectEquals(String subject) {
        return DocumentPredicates.anySubjectEquals(subject).test(document);
    }

    public boolean authorEquals(String author) {
        return DocumentPredicates.authorEquals(author).test(document);
    }
}
