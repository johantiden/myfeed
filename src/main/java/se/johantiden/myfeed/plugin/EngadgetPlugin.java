package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class EngadgetPlugin implements Plugin {

    private final Duration ttl;

    public EngadgetPlugin(Duration ttl) {this.ttl = ttl;}

    @Override
    public Feed createFeed() {
        return new FeedImpl("Engadget", ttl, this);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin("Engadget", "https://www.engadget.com", "https://www.engadget.com/rss.xml", ttl).createFeedReader(feed).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> {

            document.imageUrl = getImageUrl(document.html);
            document.html = null;
            document.categories.removeIf(c -> c.name.startsWith("apple"));
            return document;
        };
    }

    private static String getImageUrl(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Elements img = doc.select("img");
        if (!img.isEmpty()) {
            return img.get(0).attr("src");
        }
        return null;
    }
}
