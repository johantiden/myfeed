package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;

import java.util.List;

@Component
public class FeedReaderJob {

    private static final Logger log = LoggerFactory.getLogger(FeedReaderJob.class);

    @Autowired
    private FeedService feedService;
    @Autowired
    private FeedReaderService feedReaderService;
    @Autowired
    private DocumentService documentService;

    @Scheduled(fixedRate = 100)
    public void myRunnable() {
        consume(feedService.popOldestInvalidatedFeed());
    }

    private void consume(Feed feed) {

        List<Document> documents = feedReaderService.readAll(feed);
        documentService.put(documents);
        if (!documents.isEmpty()) {
            log.info("Done reading feed '{}'. Found a total of {} documents.", feed.getName(), documents.size());
        }
    }

}
