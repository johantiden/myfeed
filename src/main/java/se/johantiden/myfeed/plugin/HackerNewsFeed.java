package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HackerNewsFeed extends Feed {

    public static final String NAME = "HackerNews";
    public static final String URL = "https://news.ycombinator.com/news";
    public static final String URL_RSS = "https://news.ycombinator.com/rss";

    public HackerNewsFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new RssFeedReader(NAME, URL, URL_RSS).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> document;
    }
}
