package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.Collection;

//@Component
public class DocumentPurgeReadJob {

    private static final Logger log = LoggerFactory.getLogger(DocumentPurgeReadJob.class);

    @Autowired
    private UserDocumentService userDocumentService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserService userService;

//    @Scheduled(fixedRate = 3600*1000)
    public void purgeRead() {
        log.info("Purging read documents!");

        Collection<User> users = userService.getAllUsers();

        for (User user : users) {
            long removed = userDocumentService.purgeReadDocuments(user.getKey());
            log.info("Removed {} UserDocuments for {}", removed, user.getUsername());
        }
    }
}
