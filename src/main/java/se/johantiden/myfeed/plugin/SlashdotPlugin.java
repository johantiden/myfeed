package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class SlashdotPlugin implements Plugin {

    public static final String URL = "https://slashdot.org";
    private final Duration ttl;

    public SlashdotPlugin(Duration ttl) {this.ttl = ttl;}

    @Override
    public Feed createFeed() {
        return new Feed("Slashdot", ttl, this, URL);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin("Slashdot", URL, "http://rss.slashdot.org/Slashdot/slashdotMainatom", ttl).createFeedReader(feed).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return entry -> {
            entry.html = prune(entry.html);
            if(entry.html.toLowerCase().contains("google-analytics")) {
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
