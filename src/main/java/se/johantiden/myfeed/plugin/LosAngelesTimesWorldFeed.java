package se.johantiden.myfeed.plugin;

import com.google.common.base.Strings;
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

public class LosAngelesTimesWorldFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(LosAngelesTimesWorldFeed.class);
    public static final String NAME = "Los Angeles Times - World";
    public static final String URL = "http://www.latimes.com";
    public static final String URL_RSS = "http://www.latimes.com/world/rss2.0.xml";

    public LosAngelesTimesWorldFeed() {
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

        Elements figureImg = doc.select("figure.trb_em_ic_figure > img");
        if(!figureImg.isEmpty()) {
            String src = figureImg.attr("srcset");
            if (Strings.isNullOrEmpty(src)) {
                src = figureImg.attr("data-baseurl");
            }
            if (!Strings.isNullOrEmpty(src)) {
                String s = src.split(" ")[0];
                if (s.startsWith("//")) {
                    s = "https:" + s;
                }
                return s;
            }
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
