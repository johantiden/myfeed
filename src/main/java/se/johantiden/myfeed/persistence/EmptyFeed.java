package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.redis.Key;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class EmptyFeed implements Feed {
    @Override
    public PluginType getType() {
        return PluginType.NONE;
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
