package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.util.DateConverter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Feed extends BaseEntity {

    private final String name;
    private final Plugin plugin;
    private final String url;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "feed_user", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "feed_id",
                    nullable = false, updatable = false) })
    private final List<User> feedUsers;

    private final Duration ttl;
    private Timestamp lastRead;

    public Feed(
            String name,
            Duration ttl,
            Plugin plugin,
            String url) {
        this.name = name;
        this.url = url;
        this.ttl = Objects.requireNonNull(ttl);
        this.plugin = Objects.requireNonNull(plugin);
        this.feedUsers = new ArrayList<>();
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public List<User> getFeedUsers() {
        return feedUsers;
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
        return lastRead == null || lastRead.before(java.util.Date.from(Instant.now().minus(ttl)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Feed feed = (Feed) o;

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
}
