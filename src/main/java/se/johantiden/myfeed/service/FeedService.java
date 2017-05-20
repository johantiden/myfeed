package se.johantiden.myfeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedRepository;
import se.johantiden.myfeed.persistence.redis.Key;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FeedService {

    @Autowired
    private FeedRepository feedRepository;

    public Optional<Feed> popOldestInvalidatedFeed() {
        Comparator<Feed> comparator = Comparator.nullsFirst(Comparator.comparing(Feed::getLastRead))
                .thenComparing(Feed::getName);

        List<Feed> feeds = feedRepository.invalidatedFeeds();

        if (feeds.isEmpty()) {
            return Optional.empty();
        }

        Feed feed = feeds.stream()
                .sorted(comparator)
                .findFirst()
                .get();
        feed.setLastRead(Instant.now());
        return Optional.of(feed);
    }

    public Feed getFeed(Key<Feed> feed) {
        return feedRepository.get(feed);
    }
}
