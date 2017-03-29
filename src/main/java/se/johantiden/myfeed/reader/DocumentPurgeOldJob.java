package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.service.UserDocumentService;
import se.johantiden.myfeed.settings.GlobalSettings;

import java.time.Duration;
import java.util.Collection;

@Component
public class DocumentPurgeOldJob {

    private static final Logger log = LoggerFactory.getLogger(DocumentPurgeOldJob.class);

    @Autowired
    private UserDocumentService userDocumentService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 10_000)
    public void consumeOne() {
        log.info("Purging documents!");

        Collection<User> users = userService.getAllUsers();

        for (User user : users) {
            long removed = userDocumentService.purgeOlderThan(user.getKey(), GlobalSettings.DOCUMENT_MAX_AGE);
            log.info("Removed {} UserDocuments for {}", removed, user.getUsername());
        }

        long removed = documentService.purgeOlderThan(GlobalSettings.DOCUMENT_MAX_AGE);
        log.info("Removed {} Documents.", removed);
    }
}
