package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.settings.GlobalSettings;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FeedReaderJob {

    private static final Logger log = LoggerFactory.getLogger(FeedReaderJob.class);

    private final FeedService feedService;
    private final DocumentService documentService;

    public FeedReaderJob(FeedService feedService, DocumentService documentService) {
        this.feedService = Objects.requireNonNull(feedService);
        this.documentService = Objects.requireNonNull(documentService);
    }

    @Scheduled(fixedRate = GlobalSettings.FEED_READER_INTERVAL)
    public void myRunnable() {
//        log.info("ENTER FeedReaderJob");
        Optional<Feed> feed = feedService.popOldestInvalidatedFeed();
        feed.ifPresent(this::consume);
//        log.info("EXIT  FeedReaderJob");
    }

    @Async
    protected void consume(Feed feed) {
//        log.info("  ENTER FeedReaderJob.consume - {}", feed.getName());

        List<Document> documents = FeedReaderService.readAll(feed);

        List<Document> filtered = documents.stream()
                .filter(GlobalSettings.DOCUMENT_MAX_AGE_PREDICATE)
                .collect(Collectors.toList());


        if (!filtered.isEmpty()) {
            log.debug("Done reading feed '{}'. Merging a total of {} documents. {} removed by filter. Oldest: {}",
                    feed.getName(), filtered.size(), documents.size()-filtered.size(), oldestInstantDebug(filtered));
        }
        documentService.put(filtered);

//        log.info("  EXIT  FeedReaderJob.consume");

    }

    private static String oldestInstantDebug(List<Document> filtered) {

        Optional<Document> max = filtered.stream().max(Comparator.comparing(Document::getPublishDate).reversed());

        return max.map(Document::getPublishedShortString).orElse("null");

    }

}
