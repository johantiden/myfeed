package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.AccountService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.AccountDocumentService;

@Component
public class DocumentPurgeOldJob {

    private static final Logger log = LoggerFactory.getLogger(DocumentPurgeOldJob.class);

    @Autowired
    private AccountDocumentService accountDocumentService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private AccountService accountService;

//    @Scheduled(fixedRate = 3600*1000)
    public void purgeOldByPublishDate() {
        log.info("Purging oldest documents!");

        throw new RuntimeException("Not implemented");
//        long removedAccountDocuments = accountDocumentService.purgeOlderThan(GlobalSettings.DOCUMENT_MAX_AGE);
//        log.info("Removed {} AccountDocuments", removedAccountDocuments);

//        long removedDocuments = documentService.purgeOlderThan(GlobalSettings.DOCUMENT_MAX_AGE);
//        log.info("Removed {} Documents.", removedDocuments);
    }
}
