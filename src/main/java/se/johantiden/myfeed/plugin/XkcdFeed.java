package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss1AtomFeedReader;
import se.johantiden.myfeed.plugin.rss.v1.atom.RssV1AtomDoc.Entry;
import se.johantiden.myfeed.util.Chrono;

import java.time.Instant;
import java.util.List;


public class XkcdFeed extends Feed {

    public static final String URL = "https://xkcd.com";
    public static final String URL_RSS = "https://xkcd.com/atom.xml";
    public static final String NAME = "xkcd";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public XkcdFeed() {
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

        static String getText(String html) {
            org.jsoup.nodes.Document doc = Jsoup.parse(html);
            Elements img = doc.select("img");
            if(!img.isEmpty()) {
                return img.get(0).attr("title");
            }
            return null;
        }

        static String getImageUrl(String html) {
            org.jsoup.nodes.Document doc = Jsoup.parse(html);
            Elements img = doc.select("img");
            if(!img.isEmpty()) {
                return img.get(0).attr("src");
            }
            return null;
        }

        @Override
        public Document toDocument(Entry item) {

            String title = item.title;
            String bodyRaw = item.summary.body;
            String text = getText(bodyRaw);
            String imageUrl = getImageUrl(bodyRaw);
            String pageUrl = item.id;
            Instant publishedDate = Chrono.parse(item.updated, DATE_FORMAT);
            String html = null;
            return new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
        }

    }
}
