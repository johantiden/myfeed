package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.plugin.Plugin;

import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;

public interface Feed extends Persistable<Feed> {

    Plugin getPlugin();

    List<FeedUser> getFeedUsers();

    Instant getLastRead();

    String getName();

    void setLastRead(Instant lastRead);

    boolean isInvalidated();

    Predicate<Document> getFilter();
}
