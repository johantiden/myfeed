package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss1AtomFeedReader;
import se.johantiden.myfeed.plugin.rss.v1.atom.RssV1AtomDoc.Entry;
import se.johantiden.myfeed.util.Chrono;

import java.time.Instant;
import java.util.List;

public class SlashdotFeed extends Feed {

    public static final String URL = "https://slashdot.org";
    public static final String NAME = "Slashdot";
    public static final String URL_RSS = "http://rss.slashdot.org/Slashdot/slashdotMainatom";

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    public SlashdotFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new MyRss1AtomFeedReader().readAllAvailable();
            return documents;
        };
    }


    private static class MyRss1AtomFeedReader extends Rss1AtomFeedReader {
        MyRss1AtomFeedReader() {super(URL_RSS);}

        @Override
        public Document toDocument(Entry item) {
            String title = item.title;
            String text = FeedReader.unescape(item.summary.body);
            text = FeedReader.pruneUntrustedHtml(text);
            String html = null;
            String pageUrl = item.link.href;
            String imageUrl = null;
            Instant publishedDate = Chrono.parse(item.updated, DATE_FORMAT);
            return new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
        }

    }
}
