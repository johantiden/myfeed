package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.service.InboxService;
import se.johantiden.myfeed.settings.GlobalSettings;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FeedReaderJob {

    private static final Logger log = LoggerFactory.getLogger(FeedReaderJob.class);

    @Autowired
    private FeedService feedService;
    @Autowired
    private FeedReaderService feedReaderService;
    @Autowired
    private InboxService inboxService;

    @Scheduled(fixedRate = 4_000)
    public void myRunnable() {
        consume(feedService.popOldestInvalidatedFeed());
    }

    private void consume(Feed feed) {

        List<Document> documents = FeedReaderService.readAll(feed);

        List<Document> filtered = documents.stream()
                .filter(feed.getFilter())
                .filter(GlobalSettings.DOCUMENT_MAX_AGE_PREDICATE)
                .collect(Collectors.toList());


        if (!filtered.isEmpty()) {
            log.debug("Done reading feed '{}'. Merging a total of {} documents. {} removed by filter. Oldest: {}",
                    feed.getName(), filtered.size(), documents.size()-filtered.size(), oldestInstant(filtered));
        }
        inboxService.putIfNew(filtered);
    }

    private String oldestInstant(List<Document> filtered) {

        Optional<Document> max = filtered.stream().max(Comparator.comparing(Document::getPublishDate).reversed());

        return max.map(Document::getPublishedShortString).orElse("null");

    }

}
