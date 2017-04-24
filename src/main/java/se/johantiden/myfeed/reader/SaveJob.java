package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.UserDocumentRepository;
import se.johantiden.myfeed.persistence.file.DocumentRepositorySaver;
import se.johantiden.myfeed.persistence.file.UserDocumentRepositorySaver;

@Component
public class SaveJob {

    private static final Logger log = LoggerFactory.getLogger(SaveJob.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Autowired
    private UserDocumentRepositorySaver userDocumentRepositorySaver;

    @Autowired
    private DocumentRepositorySaver documentRepositorySaver;

    @Scheduled(fixedRate = 20*1000, initialDelay = 5*1000)
    public void saveToDisk() {
        log.info("Saving all...");
        documentRepositorySaver.save(documentRepository);
        userDocumentRepositorySaver.save(userDocumentRepository);
        log.info("Saved All!");

    }
}
