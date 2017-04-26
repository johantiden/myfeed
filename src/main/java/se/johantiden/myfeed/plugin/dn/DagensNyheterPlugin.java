package se.johantiden.myfeed.plugin.dn;

import se.johantiden.myfeed.controller.NameAndUrl;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.function.Predicate;

import static se.johantiden.myfeed.util.JCollections.map;

public class DagensNyheterPlugin implements Plugin {

    @Override
    public Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Predicate<Document> filter) {
        return new FeedImpl(feedName, webUrl, cssClass, readerParameters, ttl, filter, this);
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
            entry.category = new NameAndUrl(category, "https://www.dn.se/"+category);
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
