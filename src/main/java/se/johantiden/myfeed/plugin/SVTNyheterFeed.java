package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Rss2Doc.Item;
import se.johantiden.myfeed.util.Chrono;
import se.johantiden.myfeed.util.JCollections;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

public class SVTNyheterFeed extends Feed {

    static final Logger log = LoggerFactory.getLogger(SVTNyheterFeed.class);
    public static final String NAME = "SVT Nyheter";
    public static final String URL = "https://www.svt.se/nyheter";
    public static final String URL_RSS = "https://www.svt.se/nyheter/rss.xml";

    public SVTNyheterFeed() {
        super(NAME, URL, createFeedReader());
    }

    private static Predicate<Document> notIsLokalaNyheter() {
        return d -> !d.getPageUrl().contains("nyheter/lokalt/");

    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new MyRss2FeedReader().readAllAvailable();
            return JCollections.filter(notIsLokalaNyheter(), documents);
        };
    }

    private static class MyRss2FeedReader extends Rss2FeedReader {
        MyRss2FeedReader() {super(URL_RSS);}

        static String findImage(String pageUrl) {
            org.jsoup.nodes.Document doc = getJsoupDocument(pageUrl);

            Elements img = doc.select(".lp_track_artikelbild").select(".pic__img--wide");
            if(!img.isEmpty()) {
                String src = img.attr("src");
                return src;
            }

            Elements video = doc.select("video.svp_video");
            if(!video.isEmpty()) {
                String src = video.attr("poster");
                return src;
            }


            return null;
        }

        static org.jsoup.nodes.Document getJsoupDocument(String pageUrl) {
            try {
                return Jsoup.parse(new URL(pageUrl), 10_000);
            } catch (IOException e) {
                log.error("Could not jsoup-parse {}", pageUrl, e);
                return null;
            }
        }

        @Override
        public Document toDocument(Item item) {
            String title = item.title;
            String text = item.description;
            String pageUrl = item.link;
            String imageUrl = findImage(pageUrl);
            String html = null;
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            return new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
        }
    }
}
