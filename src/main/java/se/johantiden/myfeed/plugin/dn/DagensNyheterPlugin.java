package se.johantiden.myfeed.plugin.dn;

import se.johantiden.myfeed.controller.NameAndUrl;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.Function;

import static se.johantiden.myfeed.util.JCollections.map;

public class DagensNyheterPlugin implements Plugin {

    private static final String DAGENS_NYHETER = "Dagens Nyheter";

    private final Duration ttl;

    public DagensNyheterPlugin(Duration ttl) {
        this.ttl = Objects.requireNonNull(ttl);
    }

    @Override
    public Feed createFeed() {
        return new FeedImpl(DAGENS_NYHETER, ttl, this);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin(feed.getName(), "dn", "https://www.dn.se", "http://www.dn.se/nyheter/rss/", ttl).createFeedReader(feed).readAllAvailable();
            return map(documents, createEntryMapper());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return entry -> {
            String category = parseFirstFolder(entry.pageUrl);
            entry.categories = Collections.singletonList(new NameAndUrl(category, "https://www.dn.se/"+category));
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
