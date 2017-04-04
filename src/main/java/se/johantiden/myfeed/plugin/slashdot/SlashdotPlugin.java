package se.johantiden.myfeed.plugin.slashdot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.persistence.PluginType;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class SlashdotPlugin implements Plugin {

    private static final Logger log = LoggerFactory.getLogger(SlashdotPlugin.class);

    @Override
    public Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Predicate<Document> filter) {
        return new FeedImpl(PluginType.SLASHDOT, feedName, webUrl, cssClass, readerParameters, ttl, filter);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin().createFeedReader(feed).readAllAvailable();
            return documents.parallelStream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return entry -> {
            entry.html = prune(entry.html);
            return entry;
        };
    }

    public static String prune(String html) {
        int i = html.indexOf("<p>");
        return html.substring(0, i);
    }
}
