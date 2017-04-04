package se.johantiden.myfeed.reader;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.FeedReader;

import java.util.List;

public class FeedReaderService {

    public static List<Document> readAll(Feed feed) {
        FeedReader reader = findFeedReader(feed);
        return reader.readAllAvailable();
    }

    private static FeedReader findFeedReader(Feed feed) {
        return feed.getPlugin().createFeedReader(feed);
    }
}
