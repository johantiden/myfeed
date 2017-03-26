package se.johantiden.myfeed.plugin.reddit;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.persistence.Filter;
import se.johantiden.myfeed.persistence.PluginType;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static se.johantiden.myfeed.util.JCollections.map;

public class RedditPlugin implements Plugin {

    @Override
    public Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, long invalidationPeriod, TemporalUnit invalidationPeriodUnit, Filter filter) {
        return new FeedImpl(PluginType.REDDIT, feedName, webUrl, cssClass, readerParameters, invalidationPeriod, invalidationPeriodUnit, filter);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin().createFeedReader(feed).readAllAvailable();
            return map(documents, createEntryMapper());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return entry -> {
            entry.author = null;
            entry.authorUrl = null;
            return entry;
        };
    }

}
