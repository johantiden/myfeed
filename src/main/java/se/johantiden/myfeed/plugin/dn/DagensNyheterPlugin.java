package se.johantiden.myfeed.plugin.dn;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.persistence.Filter;
import se.johantiden.myfeed.persistence.PluginType;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;

import static se.johantiden.myfeed.util.JCollections.map;

public class DagensNyheterPlugin implements Plugin {

    @Override
    public Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Filter filter) {
        return new FeedImpl(PluginType.DAGENS_NYHETER, feedName, webUrl, cssClass, readerParameters, ttl, filter);
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
            String category = parseFirstFolder(entry.pageUrl);
            entry.categoryName = category;
            entry.categoryUrl = "https://www.dn.se/"+category;
            return entry;
        };
    }

    private static String parseFirstFolder(String pageUrl) {
        StringTokenizer stringTokenizer = new StringTokenizer(pageUrl);
        String first = stringTokenizer.nextToken("/"); // http:
        String second = stringTokenizer.nextToken("/"); // www.dn.se
        String third = stringTokenizer.nextToken("/"); // sport
        return third;
    }

}
