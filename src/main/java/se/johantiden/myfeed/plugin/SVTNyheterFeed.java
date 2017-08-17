package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SVTNyheterFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(SVTNyheterFeed.class);
    public static final String NAME = "SVT Nyheter";
    public static final String URL = "https://www.svt.se/nyheter";
    public static final String URL_RSS = "https://www.svt.se/nyheter/rss.xml";

    public SVTNyheterFeed(Duration ttl) {
        super(NAME, URL, ttl, createFeedReader(ttl));
    }

    private static Predicate<Document> notIsLokalaNyheter() {

        return d -> {
            boolean lokalt = d.pageUrl.contains("nyheter/lokalt/");
            return !lokalt;
        };

    }

    public static FeedReader createFeedReader(Duration ttl) {
        return () -> {
            List<Document> documents = new RssFeed(NAME, URL, URL_RSS, ttl).getFeedReader().readAllAvailable();
            return documents.stream()
                    .map(docMapper())
                    .filter(notIsLokalaNyheter())
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
        org.jsoup.nodes.Document doc = getJsoupDocument(document.pageUrl);

        Elements img = doc.select(".lp_track_artikelbild").select(".pic__img--wide");
        if(!img.isEmpty()) {
            String src = img.attr("src");
            return src;
        }

        Elements video = doc.select("video.svp_video");
        if(!video.isEmpty()) {
            String src = video.attr("poster");
            return src;
        }


        return null;
    }

    private static org.jsoup.nodes.Document getJsoupDocument(String pageUrl) {
        try {
            return Jsoup.parse(new URL(pageUrl), 10_000);
        } catch (IOException e) {
            log.error("Could not jsoup-parse " + pageUrl, e);
            return null;
        }
    }

}
