package se.johantiden.myfeed.reader;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.util.DocumentPredicates;

import java.util.List;

public final class FeedReaderService {

    private FeedReaderService() {
    }

    public static List<Document> readAll(Feed feed) {
        FeedReader reader = feed.getFeedReader();
        List<Document> documents = reader.readAllAvailable();

        documents.stream()
                .filter(DocumentPredicates.hasEscapeCharacters())
                .findAny()
                .ifPresent(d -> {
                    throw new RuntimeException("Escape characters must be handled! Document: " + d);
                });

        return documents;
    }

}
