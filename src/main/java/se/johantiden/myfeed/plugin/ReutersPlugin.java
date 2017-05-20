package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ReutersPlugin implements Plugin {

    private static final Logger log = LoggerFactory.getLogger(ReutersPlugin.class);
    private final Duration ttl;

    public ReutersPlugin(Duration ttl) {this.ttl = ttl;}

    @Override
    public Feed createFeed() {
        return new FeedImpl("Slashdot", ttl, this);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin("Reuters - World", "http://www.reuters.com/news/world", "http://feeds.reuters.com/Reuters/worldNews", ttl).createFeedReader(feed).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> {
            document.html = prune(document.html);
            if (document.html.toLowerCase().contains("google-analytics")) {
                throw new IllegalArgumentException("Google? Maybe there is a google analytics link?");
            }

            document.imageUrl = findImage(document);
            return document;
        };
    }


    private static String findImage(Document document) {
        org.jsoup.nodes.Document doc = getJsoupDocument(document.pageUrl);

        Elements video = doc.select("#video_el");
        if (!video.isEmpty()) {
            String src = video.attr("poster");
            return src;
        }


        return null;
    }


    private static org.jsoup.nodes.Document getJsoupDocument(String pageUrl) {
        try {
            return Jsoup.parse(new URL(pageUrl), 10_000);
        } catch (IOException e) {
            log.error("Could not jsoup-parse " + pageUrl, e);
            return null;
        }
    }

    public static String prune(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.select(".feedflare").remove();
        doc.select("img").remove();
        String pruned = doc.body().html();
        return pruned;
    }
}
