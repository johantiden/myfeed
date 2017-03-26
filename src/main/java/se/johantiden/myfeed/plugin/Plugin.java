package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.Filter;

import java.time.temporal.TemporalUnit;
import java.util.Map;

public interface Plugin {

    Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, long invalidationPeriod, TemporalUnit invalidationPeriodUnit, Filter filter);

    FeedReader createFeedReader(Feed feed);

}
