package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Item;
import se.johantiden.myfeed.plugin.rss.v2.Item.MrssContent;
import se.johantiden.myfeed.util.Chrono;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.List;

public class NewYorkTimesWorldFeed extends Feed {

    static final Logger log = LoggerFactory.getLogger(NewYorkTimesWorldFeed.class);
    public static final String URL = "https://www.nytimes.com/section/world";
    public static final String NAME = "New York Times - World";
    public static final String URL_RSS = "https://rss.nytimes.com/services/xml/rss/nyt/World.xml";

    public NewYorkTimesWorldFeed() {
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
            URL url = getUrl(pageUrl);
            org.jsoup.nodes.Document doc = getJsoupDocument(url);

            Elements img = doc.select("img.media-viewer-candidate");
            if(!img.isEmpty()) {
                return img.attr("src");
            }

            return null;
        }

        static URL getUrl(String pageUrl) {
            try {
                return new URL(pageUrl);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        static org.jsoup.nodes.Document getJsoupDocument(URL url) {
            try {
                return Jsoup.parse(url, 10_000);
            } catch (IOException | RuntimeException e) {
                log.error("Could not jsoup-parse {}", url, e);
                return null;
            }
        }

        @Override
        public Document toDocument(Item item) {
            String title = item.title;
            String text = item.description;
            String pageUrl = item.link;
            String imageUrl = findImage(pageUrl);
            if (imageUrl == null) {
                imageUrl = findImage(item.mrssContent);
            }
            String html = null;
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            Document document = new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
            document.extra = item.category + ':' + item.mrssDescription;
            return document;
        }

        private String findImage(MrssContent mrssContent) {
            if (mrssContent != null) {
                return mrssContent.url;
            }
            return null;
        }
    }
}
