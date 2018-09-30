package se.johantiden.myfeed.classification;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.util.DocumentPredicates;

import java.util.Objects;
import java.util.regex.Pattern;

public class DocumentMatcher {
    private final Document document;

    public DocumentMatcher(Document document) {
        this.document = Objects.requireNonNull(document);
    }

    public boolean has(String... strings) {
        return DocumentPredicates.has(strings).test(document);
    }

    public boolean isFromFeed(String feedName) {
        return DocumentPredicates.isFromFeed(feedName).test(document);
    }

    public boolean anyCategoryEquals(String... categories) {
        return DocumentPredicates.anyCategoryEquals(categories).test(document);
    }

    public boolean matches(Pattern pattern) {
        return DocumentPredicates.matches(pattern).test(document);
    }
}
