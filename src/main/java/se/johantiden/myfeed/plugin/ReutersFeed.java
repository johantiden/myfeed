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
import java.net.URL;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ReutersFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(ReutersFeed.class);
    public static final String URL = "http://www.reuters.com/news/world";
    public static final String NAME = "Reuters - World";
    public static final String URL_RSS = "http://feeds.reuters.com/Reuters/worldNews";

    public ReutersFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Pair<Item, Document>> documents = new RssFeedReader(NAME, URL, URL_RSS, Rss2Doc.class).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Pair<Item, Document>, Document> createEntryMapper() {
        return pair -> {
            pair.right.imageUrl = findImage(pair);
            return pair.right;
        };
    }


    private static String findImage(Pair<Item, Document> document) {
        org.jsoup.nodes.Document doc = getJsoupDocument(document.right.getPageUrl());

        Elements relatedImg = doc.select(".related-photo-container > img");
        if(!relatedImg.isEmpty()) {
            String src = relatedImg.get(0).attr("src");
            return src;
        }

        Elements slideImgs = doc.select(".module-slide-media > img");
        if(!slideImgs.isEmpty()) {
            String src = slideImgs.get(0).attr("data-lazy");
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

    public static String prune(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.select(".feedflare").remove();
        doc.select("img").remove();
        String pruned = doc.body().html();
        return pruned;
    }
}
