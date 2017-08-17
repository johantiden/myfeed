package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.util.DateConverter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

public class Feed {

    private final String name;
    private final FeedReader feedReader;
    private final String url;
    private Timestamp lastRead;

    public Feed(
            String name,
            String url,
            FeedReader feedReader) {
        this.name = name;
        this.url = url;
        this.feedReader = Objects.requireNonNull(feedReader);
    }

    public FeedReader getFeedReader() {
        return feedReader;
    }

    public Instant getLastRead() {
        return DateConverter.toInstant(lastRead);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setLastRead(Instant lastRead) {
        this.lastRead = DateConverter.toSqlTimestamp(lastRead);
    }

    public boolean isInvalidated() {
        return lastRead == null || lastRead.before(java.util.Date.from(Instant.now().minus(FeedPopulator.INVALIDATION_PERIOD)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Feed feed = (Feed) o;

        if (name != null ? !name.equals(feed.name) : feed.name != null) { return false; }
        if (feedReader != null ? !feedReader.equals(feed.feedReader) : feed.feedReader != null) { return false; }
        if (url != null ? !url.equals(feed.url) : feed.url != null) { return false; }
//        if (ttl != null ? !ttl.equals(feed.ttl) : feed.ttl != null) { return false; }
        return !(lastRead != null ? !lastRead.equals(feed.lastRead) : feed.lastRead != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (feedReader != null ? feedReader.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
//        result = 31 * result + (ttl != null ? ttl.hashCode() : 0);
        result = 31 * result + (lastRead != null ? lastRead.hashCode() : 0);
        return result;
    }
}
