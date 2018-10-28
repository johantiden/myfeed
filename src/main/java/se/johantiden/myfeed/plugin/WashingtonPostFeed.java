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
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.List;

public class WashingtonPostFeed extends Feed {

    static final Logger log = LoggerFactory.getLogger(WashingtonPostFeed.class);
    public static final String NAME = "Washington Post - The Fact Checker";
    public static final String URL = "https://www.washingtonpost.com/news/fact-checker/";
    public static final String URL_RSS = "http://feeds.washingtonpost.com/rss/rss_fact-checker";

    public WashingtonPostFeed() {
        super(NAME, URL, createFeedReader());
    }


    private static FeedReader createFeedReader() {
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

            Elements img = doc.select(".article-main-img");
            if(!img.isEmpty()) {
                String srcRelative = img.attr("src");
                String host = url.getHost();

                String protocol = url.getProtocol();
                String src = protocol + "://" + host + srcRelative;
                return src;
            }

            Elements divPowaShowImage = doc.select("div.powa-shot-image");
            if (!divPowaShowImage.isEmpty()) {
                String style = divPowaShowImage.attr("style");
                if (style != null) {
                    String[] split = style.split("'");
                    String imgUrl = split[1];
                    return imgUrl;
                }
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
            String pageUrl = item.link;
            String imageUrl = findImage(pageUrl);
            String text = item.description;
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            String html = null;
            return new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
        }
    }
}
