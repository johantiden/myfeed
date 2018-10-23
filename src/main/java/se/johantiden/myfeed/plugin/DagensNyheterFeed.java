package se.johantiden.myfeed.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
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

public class DagensNyheterFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(DagensNyheterFeed.class);
    private static final String NAME = "Dagens Nyheter";
    public static final String URL = "https://www.dn.se";
    public static final String RSS_URL = "https://www.dn.se/nyheter/rss/";

    public DagensNyheterFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            RssFeedReader rssFeedReader = new RssFeedReader(NAME, URL, RSS_URL, Rss2Doc.class);
            List<Pair<Item, Document>> documents = rssFeedReader.readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Pair<Item, Document>, Document> createEntryMapper() {
        return pair -> {
            if (pair.right.imageUrl == null) {
                pair.right.imageUrl = findImage(pair);
            }
            return pair.right;
        };
    }


    private static String findImage(Pair<Item, Document> document) {
        org.jsoup.nodes.Document doc = getJsoupDocument(document.right.getPageUrl());

        Elements articleHeaderImg = doc.select(".image-box--article > .image-box__container > * > img.image-box__img--fallback");
        if(!articleHeaderImg.isEmpty()) {
            Element first = articleHeaderImg.first();
            String src = first.attr("src");

            if (src != null) {
                return src;
            }
        }

        log.warn("DN - no image");
        return null;
    }

    private static JsonNode getJsonNode(String attr) {
        JsonNode jsonNode;
        try {
            jsonNode = new ObjectMapper().readTree(attr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonNode;
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
