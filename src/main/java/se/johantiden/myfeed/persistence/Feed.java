package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.plugin.Plugin;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface Feed extends Persistable<Feed> {

    Plugin getPlugin();

    Map<String, String> getFeedReaderParameters();

    List<FeedUser> getFeedUsers();

    String getCssClass();

    Instant getLastRead();

    String getName();

    String getWebUrl();

    void setLastRead(Instant lastRead);

    boolean isInvalidated();

    Predicate<Document> getFilter();
}
