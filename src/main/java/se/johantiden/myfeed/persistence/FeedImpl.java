package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class FeedImpl implements Feed {

    public static final Feed EMPTY = new EmptyFeed();
    private final String name;
    private final String webUrl;
    private final PluginType type;
    private final Map<String, String> feedReaderParameters;
    private final List<FeedUser> feedUsers;
    private final String cssClass;
    private final Duration ttl;
    private final Predicate<Document> filter;
    private Instant lastRead = Instant.EPOCH;

    public FeedImpl(
            PluginType type,
            String name,
            String webUrl,
            String cssClass, Map<String, String> feedReaderParameters,
            Duration ttl,
            Predicate<Document> filter) {
        this.name = name;
        this.webUrl = webUrl;
        this.type = type;
        this.feedReaderParameters = feedReaderParameters;
        this.cssClass = cssClass;
        this.ttl = ttl;
        this.filter = filter  == null ? d -> true : filter;
        this.feedUsers = new ArrayList<>();
    }


    @Override
    public PluginType getType() {
        return type;
    }

    @Override
    public Map<String, String> getFeedReaderParameters() {
        return feedReaderParameters;
    }

    @Override
    public List<FeedUser> getFeedUsers() {
        return feedUsers;
    }

    @Override
    public String getCssClass() {
        return cssClass;
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
    public String getWebUrl() {
        return webUrl;
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
        if (webUrl != null ? !webUrl.equals(feed.webUrl) : feed.webUrl != null) {
            return false;
        }
        if (type != feed.type) {
            return false;
        }
        if (feedReaderParameters != null ? !feedReaderParameters.equals(feed.feedReaderParameters) : feed.feedReaderParameters != null) {
            return false;
        }
        if (feedUsers != null ? !feedUsers.equals(feed.feedUsers) : feed.feedUsers != null) {
            return false;
        }
        if (cssClass != null ? !cssClass.equals(feed.cssClass) : feed.cssClass != null) {
            return false;
        }
        if (ttl != null ? !ttl.equals(feed.ttl) : feed.ttl != null) {
            return false;
        }
        if (filter != null ? !filter.equals(feed.filter) : feed.filter != null) {
            return false;
        }
        return !(lastRead != null ? !lastRead.equals(feed.lastRead) : feed.lastRead != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (webUrl != null ? webUrl.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (feedReaderParameters != null ? feedReaderParameters.hashCode() : 0);
        result = 31 * result + (feedUsers != null ? feedUsers.hashCode() : 0);
        result = 31 * result + (cssClass != null ? cssClass.hashCode() : 0);
        result = 31 * result + (ttl != null ? ttl.hashCode() : 0);
        result = 31 * result + (filter != null ? filter.hashCode() : 0);
        result = 31 * result + (lastRead != null ? lastRead.hashCode() : 0);
        return result;
    }

    @Override
    public Key<Feed> getKey() {
        return Keys.feed(this);
    }
    
    @Override
    public Predicate<Document> getFilter() {
        return filter;
    }
}
