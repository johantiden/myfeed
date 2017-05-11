package se.johantiden.myfeed.plugin.svd;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.controller.NameAndUrl;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;
import se.johantiden.myfeed.util.DocumentPredicates;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static se.johantiden.myfeed.util.DocumentPredicates.*;


public class SvenskaDagbladetPlugin implements Plugin {

    private static final Logger log = LoggerFactory.getLogger(SvenskaDagbladetPlugin.class);

    @Override
    public final Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Predicate<Document> filter) {
        return new FeedImpl(feedName, webUrl, cssClass, readerParameters, ttl, filter, this);
    }

    @Override
    public final FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin().createFeedReader(feed).readAllAvailable();
            return documents.parallelStream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return document -> {
            document.isPaywalled = isPaywalled(document);

            document.categories = document.categories.stream()
                    .filter(c -> c.url == null)
                    .map(c -> new NameAndUrl(c.name, document.feed.url + "/" + c.name))
                    .collect(Collectors.toList());

            if (hasEscapeCharacters().test(document)) {
                boolean a = true;
            }
            return document;
        };
    }

    private static boolean isPaywalled(Document document) {
        try {
            org.jsoup.nodes.Document parse = Jsoup.parse(new URL(document.pageUrl), 10_000);

            Elements select = parse.select(".paywall-loader");
            if (!select.isEmpty()) {
                log.debug("SVD Paywall: {}", document.pageUrl);
                return true;
            }

            return false;

        } catch (IOException e) {
            log.error("Could not determine paywall", e);
            return false;
        }

    }

}
