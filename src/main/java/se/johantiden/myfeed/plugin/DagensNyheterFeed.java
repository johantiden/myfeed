package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Rss2Doc.Item;
import se.johantiden.myfeed.util.Chrono;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;

public class DagensNyheterFeed extends Feed {

    static final Logger log = LoggerFactory.getLogger(DagensNyheterFeed.class);
    private static final String NAME = "Dagens Nyheter";
    public static final String URL = "https://www.dn.se";
    public static final String RSS_URL = "https://www.dn.se/nyheter/rss/";

    public DagensNyheterFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> new MyRss2FeedReader().readAllAvailable();
    }

    private static class MyRss2FeedReader extends Rss2FeedReader {
        MyRss2FeedReader() {super(RSS_URL);}

        static String findImage(String pageUrl) {
            org.jsoup.nodes.Document doc = getJsoupDocument(pageUrl);

            Elements articleHeaderImg = doc.select(".image-box--article > .image-box__container > * > img.image-box__img--fallback");
            if(!articleHeaderImg.isEmpty()) {
                Element first = articleHeaderImg.first();
                String src = first.attr("src");

                if (src != null) {
                    return src;
                }
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
            String text = item.description;
            String pageUrl = item.link;
            String imageUrl = findImage(item);
            Instant publishedDate = Chrono.parse(item.pubDate, Rss.PUB_DATE_FORMAT);
            String html = null;
            return new Document(title, text, html, pageUrl, imageUrl, publishedDate, NAME, URL);
        }

        private String findImage(Item item) {
            if (item.mrssContent != null) {
                if (item.mrssContent.type != null && item.mrssContent.type.startsWith("image")) {
                    return item.mrssContent.url;
                    // TODO: maybe add image description?
                }
            }

            return findImage(item.link);
        }
    }
}
