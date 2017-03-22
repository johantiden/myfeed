package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Feed;

import java.util.Map;

public interface Plugin {

    Feed createFeed(String feedName, String webUrl, String cssClass, Map<String, String> readerParameters);

    FeedReader createFeedReader(Feed feed);

}
