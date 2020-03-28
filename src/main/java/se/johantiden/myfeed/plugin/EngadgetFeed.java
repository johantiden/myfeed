package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Item;
import se.johantiden.myfeed.util.Chrono;

import java.time.Instant;


public class EngadgetFeed extends Feed {

    public static final String URL = "https://www.engadget.com";
    public static final String URL_RSS = "https://www.engadget.com/rss.xml";
    public static final String NAME = "Engadget";

    public EngadgetFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> new MyRss2FeedReader().readAllAvailable();
    }

    private static class MyRss2FeedReader extends Rss2FeedReader {
        MyRss2FeedReader() {super(URL_RSS);}

        @Override
        public Document toDocument(Item item) {
            String title = item.title;
            String text = FeedReader.html2text(item.description);
            String pageUrl = item.link;
            String imageUrl = findImage(item.description);
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            String html = null;
            return new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
        }

        private String findImage(String description) {
            org.jsoup.nodes.Document parse = Jsoup.parse(description);
            Elements imgs = parse.select("img");
            String src = imgs.attr("src");
            return src;
        }
    }
}
