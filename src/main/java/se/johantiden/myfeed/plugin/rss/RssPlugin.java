package se.johantiden.myfeed.plugin.rss;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.persistence.PluginType;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;

import java.time.Duration;
import java.util.Map;
import java.util.function.Predicate;

public class RssPlugin implements Plugin {

    @Override
    public Feed createFeed(
            String feedName,
            String cssClass, String webUrl,
            Map<String, String> readerParameters,
            Duration ttl, Predicate<Document> filter) {
        return new FeedImpl(PluginType.RSS, feedName, webUrl, cssClass, readerParameters, ttl, filter);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        String rssUrlKey = "rssUrl";
        String rssUrl = feed.getFeedReaderParameters().get(rssUrlKey);
        return new RssFeedReader(rssUrl, feed.getCssClass(), feed.getName(), feed.getWebUrl(), feed);
    }
}
