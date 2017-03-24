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
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.Optional;

@Component
public class DocumentFanJob {

    private static final Logger log = LoggerFactory.getLogger(DocumentFanJob.class);

    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserDocumentService userDocumentService;


    @Scheduled(fixedRate = 50)
    public void consumeOne() {
        Optional<Document> document = documentService.popNewestUnfanned();
        document.ifPresent(this::consume);
    }

    private void consume(Document document) {
        log.info("DocumentFanJob consuming '{}'", document.pageUrl);
        Feed feed = document.getFeed();
        feed.getFeedUsers().stream()
                .map(FeedUser::getUser)
                .filter(u -> u.getUserGlobalFilter().test(document))
                .forEach(user -> {
                    userDocumentService.put(new UserDocument(user, document));
                });
    }
}
