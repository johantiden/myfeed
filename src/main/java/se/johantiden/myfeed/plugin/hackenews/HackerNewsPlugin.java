package se.johantiden.myfeed.plugin.hackenews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HackerNewsPlugin implements Plugin {

    private static final Logger log = LoggerFactory.getLogger(HackerNewsPlugin.class);

    @Override
    public Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Predicate<Document> filter) {
        return new FeedImpl(feedName, webUrl, cssClass, readerParameters, ttl, filter, this);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin().createFeedReader(feed).readAllAvailable();
            return documents.parallelStream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> {
//            Double votes = findVotes(document);
//            document.score = votes;
            return document;
        };
    }

    private static Double findVotes(Document document) {
        org.jsoup.nodes.Document rssDocument = Jsoup.parse(document.html);
        String commentsUrl = rssDocument.select("a").get(0).attr("href");

        org.jsoup.nodes.Document jsoupDocument = getJsoupDocument(commentsUrl);

        Elements select = jsoupDocument.select(".score");
        if (select.size() == 1) {
            Element element = select.get(0);
            String html = element.html();
            html = html.replace(" points", "");

            int i = Integer.parseInt(html);
            return (double) i;
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
