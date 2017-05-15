package se.johantiden.myfeed.plugin.rss;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Predicate;

public class RssPlugin implements Plugin {

    private final String rssUrl;
    private final String feedName;
    private final String webUrl;
    private final String cssClass;
    private final Duration ttl;
    private final Predicate<Document> filter;

    public RssPlugin(String feedName, String cssClass, String webUrl, String rssUrl, Duration ttl, Predicate<Document> filter) {
        this.rssUrl = rssUrl;
        this.feedName = feedName;
        this.webUrl = webUrl;
        this.cssClass = cssClass;
        this.ttl = ttl;
        this.filter = Objects.requireNonNull(filter);
    }

    public RssPlugin(String feedName, String cssClass, String webUrl, String rssUrl, Duration ttl) {
        this(feedName, cssClass, webUrl, rssUrl, ttl, d -> true);
    }

    @Override
    public Feed createFeed() {
        return new FeedImpl(feedName, ttl, this, filter);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return new RssFeedReader(rssUrl, cssClass, feedName, webUrl, feed);
    }
}
