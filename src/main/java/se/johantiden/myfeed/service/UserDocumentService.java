package se.johantiden.myfeed.service;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.controller.Subject;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.SubjectRepository;
import se.johantiden.myfeed.persistence.UserSubject;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.UserDocumentRepository;
import se.johantiden.myfeed.persistence.Username;
import se.johantiden.myfeed.persistence.redis.Key;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UserDocumentService {

    private static final Logger log = LoggerFactory.getLogger(UserDocumentService.class);

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private DocumentService documentService;

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
            log.warn("putIfNew but was not new. (This can probably be optimized)");
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

    public TreeSet<UserSubject> getUnreadUserSubjects(Username user) {
        List<Subject> allSubjects = subjectRepository.getAllSubjects();

        SortedSet<UserDocument> allUserDocuments = getAllDocumentsFor(user);
        List<Pair<UserDocument, Document>> allDocuments = map(allUserDocuments);
        TreeSet<UserSubject> subjects = allSubjects.stream()
                .map(s -> new UserSubject(s, allDocuments.stream()
                                                     .filter(p -> s.test(p.getValue()))
                                                     .map(Pair::getKey)
                                                     .collect(Collectors.toSet())))
                .collect(Collectors.toCollection(getTreeSetSupplier()));

        subjects.add(createUnmatchedSubject(allDocuments));

        return subjects;
    }

    private static UserSubject createUnmatchedSubject(List<Pair<UserDocument, Document>> allUserDocuments) {

        Set<UserDocument> unmatched = allUserDocuments.stream().filter(p -> p.getRight().getSubjects().isEmpty())
                                             .map(Pair::getLeft)
                                             .collect(Collectors.toSet());
        return new UserSubject(new Subject("Unmatched", "Unmatched", "Unmatched",
                                                  d -> false), unmatched);


    }

    private static Supplier<TreeSet<UserSubject>> getTreeSetSupplier() {
        return () -> new TreeSet<>(UserSubject.COMPARATOR);
    }

    private List<Pair<UserDocument, Document>> map(Collection<UserDocument> allUserDocuments) {
        return allUserDocuments.stream()
                       .map(ud -> {
                           Optional<Document> documentOptional = documentService.find(ud.getDocumentKey());
                           Document document = documentOptional.orElse(null);
                           return Pair.of(ud, document);
                       })
                       .filter(p -> p.getRight() != null)
                       .collect(Collectors.toList());
    }
}
