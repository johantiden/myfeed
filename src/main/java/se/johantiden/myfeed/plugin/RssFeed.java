package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Feed;

import java.time.Duration;

public class RssFeed implements Plugin {

    private final String rssUrl;
    private final String feedName;
    private final String webUrl;
    private final Duration ttl;

    public RssFeed(String feedName, String webUrl, String rssUrl, Duration ttl) {
        this.rssUrl = rssUrl;
        this.feedName = feedName;
        this.webUrl = webUrl;
        this.ttl = ttl;
    }

    @Override
    public Feed createFeed() {
        return new Feed(feedName, ttl, this, webUrl);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return new RssFeedReader(rssUrl, feedName, feed);
    }
}
