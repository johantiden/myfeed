package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.UserDocumentRepository;
import se.johantiden.myfeed.persistence.redis.Key;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

public class UserDocumentService {

    private static final Logger log = LoggerFactory.getLogger(UserDocumentService.class);
    @Autowired
    private UserDocumentRepository userDocumentRepository;

    public Collection<String> getAllDocumentsFor(Key<User> user) {
        return userDocumentRepository.getAllKeys(user);
    }

    public void put(UserDocument userDocument) {
        userDocumentRepository.put(userDocument);
    }

    public void setRead(Key<User> user, Key<UserDocument> userDocumentKey, boolean read) {

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
            log.warn("putIfNew but was not new. (This can probably be optimized)");
        } else {
            put(userDocument);
        }
    }

    public long purgeOlderThan(Key<User> user, Duration duration) {
        return userDocumentRepository.purgeOlderThan(user, duration);
    }

    public Optional<UserDocument> get(Key<User> userKey, Key<UserDocument> userDocumentKey) {
        return userDocumentRepository.find(userKey, userDocumentKey);
    }
}
