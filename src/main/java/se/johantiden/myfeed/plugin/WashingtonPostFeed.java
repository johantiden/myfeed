package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
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

public class WashingtonPostFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(WashingtonPostFeed.class);
    public static final String NAME = "Washington Post - The Fact Checker";
    public static final String URL = "https://www.washingtonpost.com/news/fact-checker/";
    public static final String URL_RSS = "http://feeds.washingtonpost.com/rss/rss_fact-checker";
//    private final Duration invalidationPeriod;
//    private final String feedName;
//    private final String webUrl;
//    private final String rssUrl;

    public WashingtonPostFeed() {
        super(NAME, URL, createFeedReader());
    }


//    public WashingtonPostFeed(String feedName, String webUrl, String rssUrl, Duration invalidationPeriod) {
//        this.invalidationPeriod = invalidationPeriod;
//        this.feedName = feedName;
//        this.webUrl = webUrl;
//        this.rssUrl = rssUrl;
//    }

    private static FeedReader createFeedReader() {
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

    private static String parseImgFromHtml(String html) {

        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        Elements imgs = doc.select("img");
        if (!imgs.isEmpty()) {
            Element img = imgs.get(0);
            String src = img.attr("src");
            return src;
        }
        return null;
    }

    private static String findImage(Document document) {
        URL url = getUrl(document.getPageUrl());
        org.jsoup.nodes.Document doc = getJsoupDocument(url);

        Elements img = doc.select(".article-main-img");
        if(!img.isEmpty()) {
            String srcRelative = img.attr("src");
            String host = url.getHost();

            String protocol = url.getProtocol();
            String src = protocol + "://" + host + srcRelative;
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
