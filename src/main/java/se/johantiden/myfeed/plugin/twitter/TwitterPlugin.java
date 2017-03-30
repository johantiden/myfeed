package se.johantiden.myfeed.plugin.twitter;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.persistence.Filter;
import se.johantiden.myfeed.persistence.PluginType;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssFeedReader;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static se.johantiden.myfeed.util.JCollections.map;

public class TwitterPlugin implements Plugin{

    @Override
    public Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Filter filter) {
        return new FeedImpl(PluginType.TWITTER, feedName, webUrl, "twitter", readerParameters, ttl, filter);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        String twitterUsername = feed.getFeedReaderParameters().get("username");
        return new TwitterAsRssFeedReader(twitterUsername, feed);
    }

    private static class TwitterAsRssFeedReader extends RssFeedReader {
        private final String username;

        TwitterAsRssFeedReader(String username, Feed feed) {
            super("https://twitrss.me/twitter_user_to_rss/?user=" + username, "twitter", "Twitter", "https://twitter.com/" + username, feed);
            this.username = username;
        }

        @Override
        public List<Document> readAllAvailable() {
            return map(super.readAllAvailable(), createEntryMapper(username));
        }


        private static Function<Document, Document> createEntryMapper(String username) {
            return entry ->  {
                entry.title = null;
                entry.authorName = "@" + username;
                entry.authorUrl = entry.feedUrl;
                entry.feedUrl = "https://www.twitter.com";
                return entry;
            };
        }

    }
}
