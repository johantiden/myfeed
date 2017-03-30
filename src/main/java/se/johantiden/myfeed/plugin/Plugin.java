package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.Filter;

import java.time.Duration;
import java.util.Map;

public interface Plugin {

    Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Filter filter);

    FeedReader createFeedReader(Feed feed);

}
