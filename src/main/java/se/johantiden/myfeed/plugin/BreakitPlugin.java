package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class BreakitPlugin implements Plugin {

    private final Duration ttl;

    public BreakitPlugin(Duration ttl) {this.ttl = ttl;}

    @Override
    public Feed createFeed() {
        return new FeedImpl("Slashdot", ttl, this);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin("Breakit", "http://www.breakit.se", "http://www.breakit.se/feed/artiklar", ttl).createFeedReader(feed).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> {
            document.imageUrl = getImageUrl(document.html);
            document.html = prune(document.html);
            return document;
        };
    }

    private static String getImageUrl(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Elements img = doc.select("img");
        if(!img.isEmpty()) {
            return img.get(0).attr("src");
        }
        return null;
    }

    public static String prune(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.select(".feedflare").remove();
        doc.select("img").remove();
        String pruned = doc.body().html();
        return pruned;
    }
}
