package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Rss2Doc.Item;
import se.johantiden.myfeed.util.Chrono;

import java.time.Instant;
import java.util.List;

public class HackerNewsFeed extends Feed {

    public static final String NAME = "HackerNews";
    public static final String URL = "https://news.ycombinator.com/news";
    public static final String URL_RSS = "https://news.ycombinator.com/rss";

    public HackerNewsFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new MyRss2AtomFeedReader().readAllAvailable();
            return documents;
        };
    }

    private static class MyRss2AtomFeedReader extends Rss2FeedReader {
        MyRss2AtomFeedReader() {super(URL_RSS);}

        @Override
        public Document toDocument(Item item) {
            String text = null;
            String pageUrl = item.comments;
            String imageUrl = null;
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            String html = item.description;
            return new Document(item.title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
        }
    }
}
