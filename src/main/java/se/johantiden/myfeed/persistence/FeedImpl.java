package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.plugin.Plugin;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class FeedImpl implements Feed {

    public static final Feed EMPTY = new EmptyFeed();
    private final String name;
    private final Plugin plugin;
    private final List<FeedUser> feedUsers;
    private final Duration ttl;
    private Instant lastRead = Instant.EPOCH;
    private final Predicate<Document> filter;

    public FeedImpl(
            String name,
            Duration ttl,
            Plugin plugin,
            Predicate<Document> filter) {
        this.name = name;
        this.ttl = Objects.requireNonNull(ttl);
        this.filter = Objects.requireNonNull(filter);
        this.plugin = Objects.requireNonNull(plugin);
        this.feedUsers = new ArrayList<>();
    }

    public FeedImpl(
            String name,
            Duration ttl,
            Plugin plugin) {
        this(name, ttl, plugin, d -> true);
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public List<FeedUser> getFeedUsers() {
        return feedUsers;
    }

    @Override
    public Instant getLastRead() {
        return lastRead;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setLastRead(Instant lastRead) {
        this.lastRead = lastRead;
    }

    @Override
    public boolean isInvalidated() {
        return lastRead == null || lastRead.plus(ttl).isBefore(Instant.now());
    }

    @Override
    public Predicate<Document> getFilter() {
        return filter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeedImpl feed = (FeedImpl) o;

        if (name != null ? !name.equals(feed.name) : feed.name != null) {
            return false;
        }
        if (plugin != null ? !plugin.equals(feed.plugin) : feed.plugin != null) {
            return false;
        }
        if (feedUsers != null ? !feedUsers.equals(feed.feedUsers) : feed.feedUsers != null) {
            return false;
        }
        if (ttl != null ? !ttl.equals(feed.ttl) : feed.ttl != null) {
            return false;
        }
        return !(lastRead != null ? !lastRead.equals(feed.lastRead) : feed.lastRead != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (plugin != null ? plugin.hashCode() : 0);
        result = 31 * result + (feedUsers != null ? feedUsers.hashCode() : 0);
        result = 31 * result + (ttl != null ? ttl.hashCode() : 0);
        result = 31 * result + (lastRead != null ? lastRead.hashCode() : 0);
        return result;
    }

    @Override
    public Key<Feed> getKey() {
        return Keys.feed(this);
    }
}
