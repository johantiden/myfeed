package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import se.johantiden.myfeed.controller.NameAndUrl;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class XkcdFeed extends Feed {

    public static final String URL = "https://xkcd.com";
    public static final String URL_RSS = "https://xkcd.com/atom.xml";
    public static final String NAME = "xkcd";

    public XkcdFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new RssFeedReader(NAME, URL, URL_RSS){
                @Override
                protected Document createDocument(String title, String pageUrl, String imageUrl, Instant publishedDate, String text, String html, NameAndUrl author, String feedName, String feedUrl) {

                    imageUrl = XkcdFeed.getImageUrl(html);
                    return new Document(title, text, author, pageUrl, imageUrl, publishedDate, feedName, feedUrl);

                }
            }.readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> document;
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
