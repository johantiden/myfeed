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

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;


public class ReutersFeed extends Feed {

    static final Logger log = LoggerFactory.getLogger(ReutersFeed.class);
    public static final String URL = "http://www.reuters.com/news/world";
    public static final String NAME = "Reuters - World";
    public static final String URL_RSS = "http://feeds.reuters.com/Reuters/worldNews";

    public ReutersFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new MyRss2FeedReader().readAllAvailable();
            return documents;
        };
    }


    private static class MyRss2FeedReader extends Rss2FeedReader {
        MyRss2FeedReader() {super(URL_RSS);}

        static String findImage(String pageUrl) {
            org.jsoup.nodes.Document doc = getJsoupDocument(pageUrl);

            Elements relatedImg = doc.select(".related-photo-container > img");
            if(!relatedImg.isEmpty()) {
                String src = relatedImg.get(0).attr("src");
                return src;
            }

            Elements slideImgs = doc.select(".module-slide-media > img");
            if(!slideImgs.isEmpty()) {
                String src = slideImgs.get(0).attr("data-lazy");
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
            String text = FeedReader.pruneUntrustedHtml(item.description);
            String pageUrl = item.feedBurnerOrigLink;
            String imageUrl = findImage(pageUrl);
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            String html = null;
            return new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
        }
    }
}
