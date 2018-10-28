package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.plugin.rss.Rss;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Rss2Doc.Item;
import se.johantiden.myfeed.util.Chrono;

import java.time.Instant;

public class TheLocalFeed extends se.johantiden.myfeed.persistence.Feed {
    public static final String NAME = "TheLocal";
    public static final String URL = "https://www.thelocal.se/";
    public static final String RSS_URL = "https://www.thelocal.se/feeds/rss.php";

    public TheLocalFeed() {
        super(NAME, URL, createFeedReader());
    }

    private static FeedReader createFeedReader() {
        return new MyRss2FeedReader();
    }

    private static class MyRss2FeedReader extends Rss2FeedReader {
        MyRss2FeedReader() {super(RSS_URL);}

        @Override
        public Document toDocument(Item item) {
            String title = item.title;
            String text = item.description;
            String html = null;
            String pageUrl = item.link;
            String imageUrl = item.enclosure.url;
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            return new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
        }
    }
}
