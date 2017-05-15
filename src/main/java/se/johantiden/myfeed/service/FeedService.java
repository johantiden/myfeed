package se.johantiden.myfeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.persistence.FeedRepository;
import se.johantiden.myfeed.persistence.redis.Key;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

public class FeedService {

    @Autowired
    private FeedRepository feedRepository;

    public Feed popOldestInvalidatedFeed() {
        Comparator<Feed> comparator = Comparator.nullsFirst(Comparator.comparing(Feed::getLastRead))
                .thenComparing(Feed::getName);

        List<Feed> feeds = feedRepository.invalidatedFeeds();
        Feed feed = feeds.stream()
                .sorted(comparator)
                .findFirst()
                .orElse(FeedImpl.EMPTY);
        feed.setLastRead(Instant.now());
        return feed;
    }

    public Feed getFeed(Key<Feed> feed) {
        return feedRepository.get(feed);
    }
}
