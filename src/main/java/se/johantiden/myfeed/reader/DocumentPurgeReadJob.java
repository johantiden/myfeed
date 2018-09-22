package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.service.DocumentService;

@Component
public class DocumentPurgeReadJob {

    private static final Logger log = LoggerFactory.getLogger(DocumentPurgeReadJob.class);

    @Autowired
    private DocumentService documentService;

    @Scheduled(fixedRate = 10*1000)
    public void purgeRead() {
        log.debug("Purging read documents!");
        long removed = documentService.purgeReadDocuments();
        log.debug("Removed {} read Documents", removed);
    }
}
