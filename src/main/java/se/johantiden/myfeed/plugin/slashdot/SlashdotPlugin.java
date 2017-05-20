package se.johantiden.myfeed.plugin.slashdot;

import org.jsoup.Jsoup;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class SlashdotPlugin implements Plugin {

    private final Duration ttl;

    public SlashdotPlugin(Duration ttl) {this.ttl = ttl;}

    @Override
    public Feed createFeed() {
        return new FeedImpl("Slashdot", ttl, this);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin("Slashdot", "https://slashdot.org", "http://rss.slashdot.org/Slashdot/slashdotMainatom", ttl).createFeedReader(feed).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return entry -> {
            entry.html = prune(entry.html);
            if (entry.html.toLowerCase().contains("google-analytics")) {
                throw new IllegalArgumentException("Google? Maybe there is a google analytics link?");
            }
            return entry;
        };
    }

    public static String prune(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.select("p").remove();
        doc.select(".share_submission").remove();
        doc.select("head").remove();
        String pruned2 = doc.body().html();
        return pruned2;
    }
}
