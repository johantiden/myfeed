package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.settings.GlobalSettings;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FeedReaderJob {

    private static final Logger log = LoggerFactory.getLogger(FeedReaderJob.class);

    private final FeedService feedService;
    private final DocumentService documentService;
    private final ScheduledExecutorService executor;

    public FeedReaderJob(FeedService feedService, DocumentService documentService, ScheduledExecutorService executor) {
        this.feedService = Objects.requireNonNull(feedService);
        this.documentService = Objects.requireNonNull(documentService);
        this.executor = Objects.requireNonNull(executor);
    }

    @PostConstruct
    public void start() {
        executor.scheduleWithFixedDelay(this::myRunnable, 0, 1, TimeUnit.SECONDS);
        log.info("Started FeedReaderJob");
    }

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
