package se.johantiden.myfeed.plugin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DagensNyheterFeed implements Plugin {

    private static final Logger log = LoggerFactory.getLogger(DagensNyheterFeed.class);
    private static final String DAGENS_NYHETER = "Dagens Nyheter";
    public static final String URL = "https://www.dn.se";

    private final Duration ttl;

    public DagensNyheterFeed(Duration ttl) {
        this.ttl = Objects.requireNonNull(ttl);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssFeed(feed.getName(), "http://www.dn.se/nyheter/rss/", ttl, URL).createFeedReader(feed).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> {
            document.imageUrl = findImage(document);
            return document;
        };
    }


    private static String findImage(Document document) {
        org.jsoup.nodes.Document doc = getJsoupDocument(document.pageUrl);

        Elements articleHeaderImg = doc.select(".article__header-img");
        if(!articleHeaderImg.isEmpty()) {
            String attr = articleHeaderImg.attr("data-srcset");
            JsonNode jsonNode = getJsonNode(attr);

            String src = jsonNode.get("mobile").asText();
            return src;
        }

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
