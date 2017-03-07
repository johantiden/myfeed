package se.johantiden.myfeed.plugin.twitter;

import se.johantiden.myfeed.plugin.Entry;
import se.johantiden.myfeed.plugin.Feed;
import se.johantiden.myfeed.plugin.rss.RssFeed;

import java.util.List;
import java.util.function.Function;

import static se.johantiden.myfeed.util.JCollections.map;

public class TwitterUserFeed implements Feed {


    private final Function<Entry, Entry> mapper;
    private final RssFeed feed;

    public TwitterUserFeed(String username) {
        feed = createFeed(username);
        mapper = createEntryMapper(username);
    }

    private static RssFeed createFeed(String username) {
        return new RssFeed("https://twitrss.me/twitter_user_to_rss/?user="+username, ".twitter", "Twitter", "https://twitter.com/"+username);
    }

    private static Function<Entry, Entry> createEntryMapper(String username) {
        return entry ->  {
            entry.author = "@" + username;
            entry.authorUrl = entry.feedUrl;
            entry.feedUrl = "https://www.twitter.com";

            return entry;
        };
    }

    @Override
    public List<Entry> readAllAvailable() {
        return map(feed.readAllAvailable(), mapper);
    }
}
