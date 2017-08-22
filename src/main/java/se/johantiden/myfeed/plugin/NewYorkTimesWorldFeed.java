package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NewYorkTimesWorldFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(NewYorkTimesWorldFeed.class);
    public static final String URL = "https://www.nytimes.com/section/world";
    public static final String NAME = "New York Times - World";
    public static final String URL_RSS = "https://rss.nytimes.com/services/xml/rss/nyt/World.xml";

    public NewYorkTimesWorldFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new RssFeedReader(NAME, URL, URL_RSS).readAllAvailable();
            return documents.stream()
                   .map(docMapper())
                   .collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> docMapper() {

        return document -> {
            document.imageUrl = findImage(document);
            return document;
        };

    }

    private static String findImage(Document document) {
        URL url = getUrl(document.getPageUrl());
        org.jsoup.nodes.Document doc = getJsoupDocument(url);

        Elements img = doc.select("img.media-viewer-candidate");
        if(!img.isEmpty()) {
            String src = img.attr("src");
            return src;
        }

        return null;
    }

    private static org.jsoup.nodes.Document getJsoupDocument(URL url) {
        try {
            return Jsoup.parse(url, 10_000);
        } catch (IOException | RuntimeException e) {
            log.error("Could not jsoup-parse " + url, e);
            return null;
        }
    }

    private static URL getUrl(String pageUrl) {
        try {
            return new URL(pageUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
