package se.johantiden.myfeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedRepository;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

public class FeedService {

    @Autowired
    private FeedRepository feedRepository;

    public Feed popOldestFeed() {
        Comparator<Feed> comparator = Comparator.nullsFirst(Comparator.comparing(Feed::getLastRead))
                .thenComparing(Feed::getWebUrl);

        List<Feed> feeds = feedRepository.allFeeds();
        Feed feed = feeds.stream()
                .sorted(comparator)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No feeds to pop!"));
        feed.setLastRead(Instant.now());
        return feed;
    }
}
