package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

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
            List<Document> documents = new RssFeedReader(NAME, URL, URL_RSS).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> {

            document.imageUrl = getImageUrl(document.html);
            document.html = null;
            return document;
        };
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
