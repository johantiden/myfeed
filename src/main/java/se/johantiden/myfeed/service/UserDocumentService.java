package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.UserDocumentRepository;
import se.johantiden.myfeed.persistence.Username;
import se.johantiden.myfeed.persistence.redis.Key;

import java.time.Duration;
import java.util.Optional;
import java.util.SortedSet;

public class UserDocumentService {

    private static final Logger log = LoggerFactory.getLogger(UserDocumentService.class);

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    public SortedSet<UserDocument> getAllDocumentsFor(Username user) {
        return userDocumentRepository.getAllKeys(user);
    }

    public void put(UserDocument userDocument) {
        userDocumentRepository.put(userDocument);
    }

    public void setRead(Username user, Key<UserDocument> userDocumentKey, boolean read) {

        if (user == null) {
            log.info("No user: No check");
            return;
        }

        Optional<UserDocument> documentOptional = userDocumentRepository.find(user, userDocumentKey);

        UserDocument doc = documentOptional.orElseThrow(() -> new IllegalStateException("Could not find " + userDocumentKey));

        doc.setRead(read);
        put(doc);

    }

    public void putIfNew(UserDocument userDocument) {
        Optional<UserDocument> optional = userDocumentRepository.find(userDocument.getUserKey(), userDocument.getKey());
        if (optional.isPresent()) {
            log.debug("putIfNew but was not new. (This can probably be optimized)");
        } else {
            put(userDocument);
        }
    }

    public long purgeOlderThan(Duration duration) {
        return userDocumentRepository.purgeOlderThan(duration);
    }

    public Optional<UserDocument> get(Username userKey, Key<UserDocument> userDocumentKey) {
        return userDocumentRepository.find(userKey, userDocumentKey);
    }

    public long purgeReadDocuments(Username userKey) {
        SortedSet<UserDocument> allUserDocuments = getAllDocumentsFor(userKey);

        int sizeBefore = allUserDocuments.size();
        allUserDocuments.removeIf(UserDocument::isRead);
        int sizeAfter = allUserDocuments.size();

        return sizeBefore-sizeAfter;
    }

}
