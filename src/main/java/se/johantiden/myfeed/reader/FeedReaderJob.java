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
import java.util.stream.Collectors;

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

        List<Document> filtered = documents.stream().filter(feed.getFilter()).collect(Collectors.toList());

        documentService.putIfNew(filtered);
        if (!filtered.isEmpty()) {
            log.info("Done reading feed '{}'. Merging a total of {} documents. {} removed by filter",
                    feed.getName(), filtered.size(), documents.size()-filtered.size());
        }
    }

}
