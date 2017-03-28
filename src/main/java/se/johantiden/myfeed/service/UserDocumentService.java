package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.UserDocumentRepository;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserDocumentService {

    private static final Logger log = LoggerFactory.getLogger(UserDocumentService.class);
    @Autowired
    private UserDocumentRepository userDocumentRepository;

    public List<UserDocument> getUnreadDocumentsFor(Key<User> user) {

        List<UserDocument> documents = userDocumentRepository.getUnreadDocuments(user);

        Comparator<UserDocument> comparator = Comparator.comparing(UserDocument::getPublishedDate);
        Comparator<UserDocument> reversed = comparator.reversed();
        Collections.sort(documents, reversed);
        return documents;
    }

    public void put(UserDocument userDocument) {
        userDocumentRepository.put(userDocument);
    }

    public void setRead(Key<User> user, Key<Document> document, boolean read) {

        Optional<UserDocument> documentOptional = userDocumentRepository.find(user, document);

        UserDocument doc = documentOptional.orElseThrow(() -> new IllegalStateException("Could not find " + Keys.userDocument(user, document)));

        doc.setRead(read);
        put(doc);

    }

    public void putIfNew(UserDocument userDocument) {
        Optional<UserDocument> optional = userDocumentRepository.find(userDocument.getUserKey(), userDocument.getDocumentKey());
        if (optional.isPresent()) {
            log.warn("putIfNew but was not new. (This can probably be optimized)");
        } else {
            put(userDocument);
        }
    }
}
