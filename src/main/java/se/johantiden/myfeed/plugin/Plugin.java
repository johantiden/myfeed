package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

import java.time.Duration;
import java.util.Map;
import java.util.function.Predicate;

public interface Plugin {

    Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Predicate<Document> filter);

    FeedReader createFeedReader(Feed feed);

}
