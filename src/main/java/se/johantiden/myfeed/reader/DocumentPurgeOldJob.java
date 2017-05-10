package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.UserDocumentService;
import se.johantiden.myfeed.settings.GlobalSettings;

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

    @Scheduled(fixedRate = 3600*1000)
    public void purgeOldByPublishDate() {
        log.info("Purging oldest documents!");

        long removedUserDocuments = userDocumentService.purgeOlderThan(GlobalSettings.DOCUMENT_MAX_AGE);
        log.info("Removed {} UserDocuments", removedUserDocuments);

        long removedDocuments = documentService.purgeOlderThan(GlobalSettings.DOCUMENT_MAX_AGE);
        log.info("Removed {} Documents.", removedDocuments);
    }
}
