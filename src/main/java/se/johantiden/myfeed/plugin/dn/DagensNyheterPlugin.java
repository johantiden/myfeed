package se.johantiden.myfeed.plugin.dn;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class DagensNyheterPlugin implements Plugin {

    private static final String DAGENS_NYHETER = "Dagens Nyheter";

    private final Duration ttl;

    public DagensNyheterPlugin(Duration ttl) {
        this.ttl = Objects.requireNonNull(ttl);
    }

    @Override
    public Feed createFeed() {
        return new FeedImpl(DAGENS_NYHETER, ttl, this);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin(feed.getName(), "https://www.dn.se", "http://www.dn.se/nyheter/rss/", ttl).createFeedReader(feed).readAllAvailable();
            return documents;
        };
    }


}
