package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.plugin.FeedReader;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.Comparator;
import java.util.Objects;

public class Feed {

    public static final Comparator<Feed> COMPARATOR_OLDEST_INVALIDATED = ((Comparator<Feed>) (o1, o2) -> {

        int lastReadCompare = Comparator.comparing(Feed::getLastRead).compare(o1, o2);
        if (lastReadCompare != 0) {
            return lastReadCompare;
        }
        int idCompare = Comparator.comparing(Feed::getId).compare(o1, o2);
        return idCompare;
    })

    .thenComparing(Feed::getId);

    private Long id;
    private final String name;
    private final FeedReader feedReader;
    private final String url;
    @Nonnull
    private Instant lastRead;

    public Feed(
            String name,
            String url,
            FeedReader feedReader) {
        this.name = name;
        this.url = url;
        this.feedReader = Objects.requireNonNull(feedReader);
        this.lastRead = Instant.EPOCH;
    }

    public long getId() {
        Objects.requireNonNull(id);
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FeedReader getFeedReader() {
        return feedReader;
    }

    public Instant getLastRead() {
        Objects.requireNonNull(lastRead, "ASSERTION FAILED: lastRead cannot be null!");
        return lastRead;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setLastRead(Instant lastRead) {
        this.lastRead = Objects.requireNonNull(lastRead);
    }

    public boolean isInvalidated() {
        return lastRead == null || lastRead.isBefore(Instant.now().minus(FeedPopulator.INVALIDATION_PERIOD));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Feed feed = (Feed) o;
        return Objects.equals(id, feed.id) &&
                Objects.equals(name, feed.name) &&
                Objects.equals(feedReader, feed.feedReader) &&
                Objects.equals(url, feed.url) &&
                Objects.equals(lastRead, feed.lastRead);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, feedReader, url, lastRead);
    }
}
