package se.johantiden.myfeed.settings;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.util.Chrono;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Predicate;

public class GlobalSettings {

    public static final Duration DOCUMENT_MAX_AGE = Duration.ofDays(14);

    public static final Predicate<? super Document> DOCUMENT_MAX_AGE_PREDICATE = d -> {
        Instant publishDate = d.getPublishDate();
        return !Chrono.isOlderThan(DOCUMENT_MAX_AGE, publishDate);
    };
    public static final int FANOUT_INTERVAL = 1000;
    public static final int FEED_READER_INTERVAL = 10_000;
}
