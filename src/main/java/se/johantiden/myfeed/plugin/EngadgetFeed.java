package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Item;
import se.johantiden.myfeed.plugin.rss.Rss2Doc;
import se.johantiden.myfeed.plugin.rss.RssFeedReader;
import se.johantiden.myfeed.util.Pair;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class EngadgetFeed extends Feed {

    public static final String URL = "https://www.engadget.com";
    public static final String URL_RSS = "https://www.engadget.com/rss.xml";
    public static final String NAME = "Engadget";

    public EngadgetFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Pair<Item, Document>> documents = new RssFeedReader(NAME, URL, URL_RSS, Rss2Doc.class).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Pair<Item, Document>, Document> createEntryMapper() {
        return pair -> pair.right;
    }

    private static String getImageUrl(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Elements img = doc.select("img");
        if(!img.isEmpty()) {
            return img.get(0).attr("src");
        }
        return null;
    }
}
