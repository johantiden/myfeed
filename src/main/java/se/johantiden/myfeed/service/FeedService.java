package se.johantiden.myfeed.service;

import se.johantiden.myfeed.persistence.Feed;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FeedService {

    private static final AtomicLong SEQUENCE = new AtomicLong(0);
    private final Set<Feed> feeds = new HashSet<>();

    public Optional<Feed> popOldestInvalidatedFeed() {

        List<Feed> feeds = findAllInvalidatedFeeds();

        if (feeds.isEmpty()) {
            return Optional.empty();
        }

        Feed feed = feeds.stream()
                .min(Feed.COMPARATOR_OLDEST_INVALIDATED)
                .get();

        feed.setLastRead(Instant.now());
        return Optional.of(feed);
    }

    private List<Feed> findAllInvalidatedFeeds() {

        return feeds.stream()
                .filter(Feed::isInvalidated)
                .collect(Collectors.toList());
    }

    public void put(Feed feed) {
        feed.setId(SEQUENCE.incrementAndGet());
        feeds.add(feed);
    }
}
