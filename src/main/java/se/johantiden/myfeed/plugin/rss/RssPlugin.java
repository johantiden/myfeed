package se.johantiden.myfeed.plugin.rss;

import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.PluginType;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;

import java.util.Map;

public class RssPlugin implements Plugin {

    @Override
    public Feed createFeed(String feedName, String webUrl, String cssClass, Map<String, String> readerParameters) {
        return new Feed(PluginType.RSS, feedName, webUrl, readerParameters, cssClass);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        String rssUrlKey = "rssUrl";
        String rssUrl = feed.getFeedReaderParameters().get(rssUrlKey);
        return new RssFeedReader(rssUrl, feed.getCssClass(), feed.getName(), feed.getWebUrl(), feed);
    }
}
