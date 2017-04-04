package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.reader.EmptyReader;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class EmptyFeed implements Feed {

    @Override
    public Plugin getPlugin() {
        return new Plugin() {
            @Override
            public Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Predicate<Document> filter) {
                return EmptyFeed.this;
            }

            @Override
            public FeedReader createFeedReader(Feed feed) {
                return new EmptyReader();
            }
        };
    }

    @Override
    public Map<String, String> getFeedReaderParameters() {
        return new HashMap<>();
    }

    @Override
    public List<FeedUser> getFeedUsers() {
        return Lists.newArrayList();
    }

    @Override
    public String getCssClass() {
        return "";
    }

    @Override
    public Instant getLastRead() {
        return Instant.MAX;
    }

    @Override
    public String getName() {
        return "EMPTY FEED!";
    }

    @Override
    public String getWebUrl() {
        return "";
    }

    @Override
    public void setLastRead(Instant lastRead) {
    }

    @Override
    public boolean isInvalidated() {
        return false;
    }

    @Override
    public Key<Feed> getKey() {
        return null;
    }

    @Override
    public Predicate<Document> getFilter() {
        return d -> true;
    }
}
