package se.johantiden.myfeed.service;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.UserDocumentRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserDocumentService {

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    public Set<Long> getReadyUserDocumentIdsFor(long userId) {
        return userDocumentRepository.getReadyUserDocumentIdsForUser(userId);
    }

    public void put(UserDocument userDocument) {
        userDocumentRepository.save(userDocument);
    }

    public void setRead(long userDocumentId, boolean read) {

        Optional<UserDocument> documentOptional = Optional.ofNullable(userDocumentRepository.findOne(userDocumentId));

        UserDocument doc = documentOptional.orElseThrow(() -> new IllegalStateException("Could not find userDocument " + userDocumentId));

        doc.setRead(read);
        put(doc);

    }

//    public void putIfNew(UserDocument userDocument) {
//        Optional<UserDocument> optional = userDocumentRepository.findOne(userDocument.getId());
//        if (optional.isPresent()) {
//            log.debug("putIfNew but was not new. (This can probably be optimized)");
//        } else {
//            put(userDocument);
//        }
//    }

//    public long purgeOlderThan(Duration duration) {
//        return userDocumentRepository.purgeOlderThan(duration);
//    }

    public Optional<UserDocument> find(long userDocumentId) {
        return Optional.ofNullable(userDocumentRepository.findOne(userDocumentId));
    }

    public long purgeReadDocuments() {
        Set<UserDocument> allReadDocuments = userDocumentRepository.findAllRead();
        int size = allReadDocuments.size();
        userDocumentRepository.delete(allReadDocuments);
        return size;
    }
}
