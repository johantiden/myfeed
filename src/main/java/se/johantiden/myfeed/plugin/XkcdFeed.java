package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Item;
import se.johantiden.myfeed.plugin.rss.Rss1Doc;
import se.johantiden.myfeed.plugin.rss.RssFeedReader;
import se.johantiden.myfeed.util.Pair;

import java.time.Instant;
import java.util.List;

import static se.johantiden.myfeed.util.JCollections.map;


public class XkcdFeed extends Feed {

    public static final String URL = "https://xkcd.com";
    public static final String URL_RSS = "https://xkcd.com/atom.xml";
    public static final String NAME = "xkcd";

    public XkcdFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Pair<Item, Document>> documents = new RssFeedReader(NAME, URL, URL_RSS, Rss1Doc.class) {
                @Override
                protected Document createDocument(String title, String pageUrl, String imageUrl, Instant publishedDate, String text, String html, String feedName, String feedUrl) {

                    imageUrl = XkcdFeed.getImageUrl(html);
                    return new Document(title, text, pageUrl, imageUrl, publishedDate, feedName, feedUrl);

                }
            }.readAllAvailable();
            return map(Pair::getRight, documents);
        };
    }


    static String getImageUrl(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Elements img = doc.select("img");
        if(!img.isEmpty()) {
            return img.get(0).attr("src");
        }
        return null;
    }
}
