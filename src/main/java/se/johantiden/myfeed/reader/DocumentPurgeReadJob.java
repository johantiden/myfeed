package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.persistence.Username;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.Collection;

@Component
public class DocumentPurgeReadJob {

    private static final Logger log = LoggerFactory.getLogger(DocumentPurgeReadJob.class);

    @Autowired
    private UserDocumentService userDocumentService;
    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 10*1000)
    public void purgeRead() {
        log.debug("Purging read documents!");

        Collection<Username> users = userService.getAllUsers();

        for (Username user : users) {
            long removed = userDocumentService.purgeReadDocuments(user);
            log.debug("Removed {} UserDocuments for {}", removed, user);
        }
    }
}
