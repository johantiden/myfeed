package se.johantiden.myfeed.settings;

import se.johantiden.myfeed.persistence.Document;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Predicate;

public class GlobalSettings {

    public static final Duration DOCUMENT_MAX_AGE = Duration.ofDays(7);

    public static final Predicate<? super Document> DOCUMENT_MAX_AGE_PREDICATE = d -> {
        Instant publishDate = d.getPublishDate();
        Instant now = Instant.now();

        return publishDate.plus(DOCUMENT_MAX_AGE).isAfter(now);
    };
}
