package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Item;
import se.johantiden.myfeed.plugin.rss.Rss2Doc;
import se.johantiden.myfeed.plugin.rss.RssFeedReader;
import se.johantiden.myfeed.util.Pair;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AlJazeeraFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(AlJazeeraFeed.class);
    public static final String URL = "http://www.aljazeera.com";
    public static final String NAME = "Al Jazeera";
    public static final String URL_RSS = "https://www.aljazeera.com/xml/rss/all.xml";

    public AlJazeeraFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Pair<Item, Document>> documents = new RssFeedReader(NAME, URL, URL_RSS, Rss2Doc.class).readAllAvailable();
            return documents.stream()
                   .map(docMapper())
                   .collect(Collectors.toList());
        };
    }

    private static Function<Pair<Item, Document>, Document> docMapper() {


        return pair -> {

            pair.right.imageUrl = findImage(pair);
            return pair.right;
        };

    }

    private static String findImage(Pair<Item, Document> document) {
        URL url = getUrl(document.right.getPageUrl());
        org.jsoup.nodes.Document doc = getJsoupDocument(url);

        Elements img = doc.select(".article-main-img");
        if(!img.isEmpty()) {
            String srcRelative = img.attr("src");
            String host = url.getHost();

            String protocol = url.getProtocol();
            String src = protocol + "://" + host + srcRelative;
            return src;
        }

        Elements videoElement = doc.select("video.vjs-tech");
        if(!videoElement.isEmpty()) {
            String src = videoElement.get(0).attr("poster");
            if (src.startsWith("//")) {
                src = "https:" + src;
            }
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
