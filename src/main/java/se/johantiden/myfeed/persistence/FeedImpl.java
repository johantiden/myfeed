package se.johantiden.myfeed.persistence;

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
    private Instant lastRead = Instant.EPOCH;

    public FeedImpl(
            PluginType type,
            String name,
            String webUrl,
            Map<String, String> feedReaderParameters,
            String cssClass,
            long invalidationPeriod,
            TemporalUnit invalidationPeriodUnit) {
        this.name = name;
        this.webUrl = webUrl;
        this.type = type;
        this.feedReaderParameters = feedReaderParameters;
        this.cssClass = cssClass;
        this.invalidationPeriod = invalidationPeriod;
        this.invalidationPeriodUnit = invalidationPeriodUnit;
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
}
