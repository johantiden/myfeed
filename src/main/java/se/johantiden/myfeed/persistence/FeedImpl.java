package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedImpl implements Feed {

    public static final Feed EMPTY = new EmptyFeed();
    private final String name;
    private final String webUrl;
    private final PluginType type;
    private final Map<String, String> feedReaderParameters;
    private final List<FeedUser> feedUsers;
    private final String cssClass;
    private final long invalidationPeriod;
    private final TemporalUnit invalidationPeriodUnit;
    private final Filter filter;
    private Instant lastRead = Instant.EPOCH;

    public FeedImpl(
            PluginType type,
            String name,
            String webUrl,
            String cssClass, Map<String, String> feedReaderParameters,
            long invalidationPeriod,
            TemporalUnit invalidationPeriodUnit,
            Filter filter) {
        this.name = name;
        this.webUrl = webUrl;
        this.type = type;
        this.feedReaderParameters = feedReaderParameters;
        this.cssClass = cssClass;
        this.invalidationPeriod = invalidationPeriod;
        this.invalidationPeriodUnit = invalidationPeriodUnit;
        this.filter = filter  == null ? Filter.TRUE : filter;
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
        return lastRead == null || lastRead.plus(invalidationPeriod, invalidationPeriodUnit).isBefore(Instant.now());
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

        if (invalidationPeriod != feed.invalidationPeriod) {
            return false;
        }
        if (!name.equals(feed.name)) {
            return false;
        }
        if (!webUrl.equals(feed.webUrl)) {
            return false;
        }
        if (type != feed.type) {
            return false;
        }
        if (!feedReaderParameters.equals(feed.feedReaderParameters)) {
            return false;
        }
        if (!feedUsers.equals(feed.feedUsers)) {
            return false;
        }
        if (cssClass != null ? !cssClass.equals(feed.cssClass) : feed.cssClass != null) {
            return false;
        }
        if (!invalidationPeriodUnit.equals(feed.invalidationPeriodUnit)) {
            return false;
        }
        return lastRead.equals(feed.lastRead);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + webUrl.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + feedReaderParameters.hashCode();
        result = 31 * result + feedUsers.hashCode();
        result = 31 * result + (cssClass != null ? cssClass.hashCode() : 0);
        result = 31 * result + (int) (invalidationPeriod ^ invalidationPeriod >>> 32);
        result = 31 * result + invalidationPeriodUnit.hashCode();
        result = 31 * result + lastRead.hashCode();
        return result;
    }

    @Override
    public Key<Feed> getKey() {
        return Keys.feed(this);
    }
    
    @Override
    public Filter getFilter() {
        return filter;
    }
}
