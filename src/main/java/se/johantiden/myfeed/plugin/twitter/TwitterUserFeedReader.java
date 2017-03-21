package se.johantiden.myfeed.plugin.twitter;

import se.johantiden.myfeed.persistence.model.Document;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.rss.RssFeedReader;

import java.util.List;
import java.util.function.Function;

import static se.johantiden.myfeed.util.JCollections.map;

public class TwitterUserFeedReader implements FeedReader {


    private final Function<Document, Document> mapper;
    private final RssFeedReader feed;

    public TwitterUserFeedReader(String username) {
        feed = createFeed(username);
        mapper = createEntryMapper(username);
    }

    private static RssFeedReader createFeed(String username) {
        return new RssFeedReader("https://twitrss.me/twitter_user_to_rss/?user="+username, "twitter", "Twitter", "https://twitter.com/"+username);
    }

    private static Function<Document, Document> createEntryMapper(String username) {
        return entry ->  {
            entry.author = "@" + username;
            entry.authorUrl = entry.feedUrl;
            entry.feedUrl = "https://www.twitter.com";

            return entry;
        };
    }

    @Override
    public List<Document> readAllAvailable() {
        return map(feed.readAllAvailable(), mapper);
    }
}
