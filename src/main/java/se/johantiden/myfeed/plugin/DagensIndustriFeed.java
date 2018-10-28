package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Rss2Doc.Item;
import se.johantiden.myfeed.util.Chrono;
import se.johantiden.myfeed.util.JCollections;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.regex.Pattern;


public class DagensIndustriFeed extends Feed {

    static final Logger log = LoggerFactory.getLogger(DagensIndustriFeed.class);
    public static final String NAME = "Dagens Industri";
    public static final String URL = "https://www.di.se/rss";
    public static final String RSS_URL = "https://www.svd.se/?service=rss";

    public DagensIndustriFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new ItemRssFeedReader().readAllAvailable();
            return JCollections.filter(d -> !isPaywalled(d), documents);
        };
    }


    private static boolean isPaywalled(Document document) {
        try {
            org.jsoup.nodes.Document parse = Jsoup.parse(new URL(document.getPageUrl()), 10_000);

            Elements select = parse.select(".paywall-loader");
            if (!select.isEmpty()) {
                log.debug("SVD Paywall: {}", document.getPageUrl());
                return true;
            }

            return false;

        } catch (IOException e) {
            log.error("Could not determine paywall", e);
            return false;
        }

    }


    private static class ItemRssFeedReader extends Rss2FeedReader {
        ItemRssFeedReader() {super(RSS_URL);}

        static String handleCapitalizedSubject(String text) {

            Pattern pattern = Pattern.compile("^[A-ZÅÄÖ]+\\. ");
            if (pattern.matcher(text).find()) {
                String[] splitText = pattern.split(text);
                return splitText[1];
            } else {
                return text;
            }
        }

        static String findImage(String pageUrl) {
            org.jsoup.nodes.Document doc = getJsoupDocument(pageUrl);

            Elements figureImg = doc.select(".di_article-figure__picture > img");
            if (!figureImg.isEmpty()) {
                String src = figureImg.attr("srcset");
                String s = src.split(" ")[0];
                if (s.startsWith("//")) {
                    s = "https:" + s;
                }
                return s;
            }

            return null;
        }

        static org.jsoup.nodes.Document getJsoupDocument(String pageUrl) {
            try {
                return Jsoup.parse(new URL(pageUrl), 10_000);
            } catch (IOException e) {
                log.error("Could not jsoup-parse {}", pageUrl, e);
                return null;
            }
        }

        @Override
        public Document toDocument(Item item) {
            String title = item.title;
            String pageUrl = item.link;
            String text = FeedReader.html2text(item.description);
//            text = handleCapitalizedSubject(text);
            String image = findImage(pageUrl);
            String html = null;
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            Document document = new Document(title, text, html, pageUrl, image, publishedDate, NAME, URL);
            document.extra = item.category;
            return document;
        }
    }
}