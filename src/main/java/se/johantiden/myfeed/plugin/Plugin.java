package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Feed;

import java.time.temporal.TemporalUnit;
import java.util.Map;

public interface Plugin {

    Feed createFeed(String feedName, String webUrl, String cssClass, Map<String, String> readerParameters, long invalidationPeriod, TemporalUnit invalidationPeriodUnit);

    FeedReader createFeedReader(Feed feed);

}
