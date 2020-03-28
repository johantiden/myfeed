package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Item;
import se.johantiden.myfeed.util.Chrono;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class SvenskaDagbladetFeed extends Feed {

    static final Logger log = LoggerFactory.getLogger(SvenskaDagbladetFeed.class);
    public static final String NAME = "Svenska Dagbladet";
    private static final Predicate<Document> FILTER = d -> !d.isPaywalled;
    public static final String URL = "https://www.svd.se";
    public static final String RSS_URL = "https://www.svd.se/?service=rss";

    public SvenskaDagbladetFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new MyRss2FeedReader().readAllAvailable();
            return documents.stream()
                    .filter(FILTER)
                    .collect(Collectors.toList());
        };
    }


    private static class MyRss2FeedReader extends Rss2FeedReader {
        MyRss2FeedReader() {super(RSS_URL);}

        static boolean isPaywalled(String pageUrl) {
            try {
                org.jsoup.nodes.Document parse = Jsoup.parse(new URL(pageUrl), 10_000);

                Elements select = parse.select(".paywall-loader");
                if(!select.isEmpty()) {
                    log.debug("SVD Paywall: {}", pageUrl);
                    return true;
                }

                return false;

            } catch (IOException e) {
                log.error("Could not determine paywall", e);
                return false;
            }

        }

        static String findImage(String pageUrl) {
            Objects.requireNonNull(pageUrl);
            org.jsoup.nodes.Document doc = getJsoupDocument(pageUrl);

            Elements figureImg = doc.select("img.Figure-image");
            if(!figureImg.isEmpty()) {
                String src = figureImg.attr("srcset");
                String s = src.split(" ")[0];
                return s;
            }

            Elements flexEmbedImg = doc.select("img.FlexEmbed-item");
            if(!flexEmbedImg.isEmpty()) {
                String src = flexEmbedImg.attr("srcset");
                String s = src.split(" ")[0];
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
            String title = FeedReader.unescape(item.title);
            String pageUrl = item.link;
            boolean paywalled = isPaywalled(pageUrl);
            String text = FeedReader.html2text(item.description);
            String imageUrl = paywalled ? null : findImage(pageUrl);
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            String html = null;
            Document document = new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
            document.isPaywalled = paywalled;
            document.extra = item.category;
            return document;
        }
    }
}
