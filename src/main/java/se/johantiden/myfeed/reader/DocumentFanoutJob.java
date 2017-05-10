package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedUser;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.service.InboxService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.Optional;

@Component
public class DocumentFanoutJob {

    private static final Logger log = LoggerFactory.getLogger(DocumentFanoutJob.class);

    @Autowired
    private InboxService inboxService;
    @Autowired
    private UserDocumentService userDocumentService;
    @Autowired
    private FeedService feedService;
    @Autowired
    private DocumentService documentService;

    @Scheduled(fixedRate = 100)
    public void consumeOne() {
        Optional<Document> documentOptional = inboxService.pop();

        documentOptional.ifPresent(document -> {
            documentService.put(document);
            consume(document);
        });
    }

    private void consume(Document document) {
        log.debug("DocumentFanJob consuming '{}'", document.pageUrl);
        Feed feed = feedService.getFeed(document.getFeedKey());
        feed.getFeedUsers().stream()
                .map(FeedUser::getUser)
                .forEach(user -> {
                    log.debug("  -> {}", user);
                    userDocumentService.putIfNew(new UserDocument(user, document.getKey(), document.publishedDate));
                });
    }
}
