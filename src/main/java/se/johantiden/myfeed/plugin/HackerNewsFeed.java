package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HackerNewsFeed {

    public static final String HACKER_NEWS = "HackerNews";
    public static final String URL = "https://news.ycombinator.com/news";
    private final Duration ttl;

    public HackerNewsFeed(Duration ttl) {this.ttl = ttl;}

    @Override
    public Feed createFeed() {
        return new Feed(HACKER_NEWS, ttl, createFeedReader(), URL);
    }

    public FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new RssFeed(HACKER_NEWS, URL, "https://news.ycombinator.com/rss", ttl).createFeedReader(feed).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> document;
    }
}
