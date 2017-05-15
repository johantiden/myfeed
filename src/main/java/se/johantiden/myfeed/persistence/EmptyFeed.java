package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.reader.EmptyReader;

import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

public class EmptyFeed implements Feed {

    @Override
    public Plugin getPlugin() {
        return new Plugin() {
            @Override
            public Feed createFeed() {
                return EmptyFeed.this;
            }

            @Override
            public FeedReader createFeedReader(Feed feed) {
                return new EmptyReader();
            }
        };
    }

    @Override
    public List<FeedUser> getFeedUsers() {
        return Lists.newArrayList();
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
