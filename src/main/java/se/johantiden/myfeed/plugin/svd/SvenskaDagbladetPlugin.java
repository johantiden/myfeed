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
    public static final String SVENSKA_DAGBLADET = "Svenska Dagbladet";
    private static final Predicate<Document> FILTER = d -> !d.isPaywalled;
    private final Duration ttl;

    public SvenskaDagbladetPlugin(Duration ttl) {this.ttl = ttl;}

    @Override
    public final Feed createFeed() {
        return new FeedImpl(SVENSKA_DAGBLADET, ttl, this, FILTER);
    }

    @Override
    public final FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin(SVENSKA_DAGBLADET, "https://www.svd.se", "https://www.svd.se/?service=rss", ttl, FILTER).createFeedReader(feed).readAllAvailable();
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
