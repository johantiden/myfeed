package se.johantiden.myfeed.plugin.hackenews;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HackerNewsPlugin implements Plugin {

    public static final String HACKER_NEWS = "Hacker News";
    private final Duration ttl;

    public HackerNewsPlugin(Duration ttl) {this.ttl = ttl;}

    @Override
    public Feed createFeed() {
        return new FeedImpl(HACKER_NEWS, ttl, this);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin(HACKER_NEWS, "hackernews", "https://news.ycombinator.com/news", "https://news.ycombinator.com/rss", ttl).createFeedReader(feed).readAllAvailable();
            return documents.parallelStream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> document;
    }
}
