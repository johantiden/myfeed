package se.johantiden.myfeed.plugin.svt;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SVTPlugin implements Plugin {

    public static final String SVT_NYHETER = "SVT Nyheter";
    private final Duration invalidationPeriod;

    public SVTPlugin(Duration invalidationPeriod) {
        this.invalidationPeriod = invalidationPeriod;
    }

    private static Predicate<Document> notIsLokalaNyheter() {

        return d -> {
            boolean lokalt = d.pageUrl.contains("nyheter/lokalt/");
            if(lokalt) {
                return false;
            }
            return true;
        };

    }

    @Override
    public Feed createFeed() {
        return new FeedImpl(SVT_NYHETER, invalidationPeriod, this, notIsLokalaNyheter());
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin(SVT_NYHETER, "https://www.svt.se/nyheter", "https://www.svt.se/nyheter/rss.xml", invalidationPeriod, notIsLokalaNyheter())
                                       .createFeedReader(createFeed()).readAllAvailable();
            return documents.stream()
                   .filter(notIsLokalaNyheter())
                   .collect(Collectors.toList());
        };
    }


}
