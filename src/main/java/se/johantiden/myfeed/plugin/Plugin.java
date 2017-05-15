package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Feed;

public interface Plugin {

    Feed createFeed();

    FeedReader createFeedReader(Feed feed);

}
