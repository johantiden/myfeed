package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.util.DocumentPredicates;

import java.util.ArrayList;
import java.util.List;

public class FeedReaderService {

    private static final Logger log = LoggerFactory.getLogger(FeedReaderService.class);

    private FeedReaderService() {
    }

    public static List<Document> readAll(Feed feed) {
        List<Document> documents = getDocuments(feed);

        documents.stream()
                .filter(DocumentPredicates.hasEscapeCharacters())
                .findAny()
                .ifPresent(d -> {
                    throw new RuntimeException("Escape characters must be handled! "+feed.getName()+" Document: " + d);
                });

        return documents;
    }

    private static List<Document> getDocuments(Feed feed) {
        FeedReader reader = feed.getFeedReader();
        try {
            return reader.readAllAvailable();
        } catch (RuntimeException e) {
            log.error("Failed to read feed {}", feed.getName(), e);
            log.debug("Exception:", e);
            return new ArrayList<>();
        }
    }

}
