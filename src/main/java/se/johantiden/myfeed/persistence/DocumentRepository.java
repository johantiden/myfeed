package se.johantiden.myfeed.persistence;

import com.google.common.collect.EvictingQueue;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DocumentRepository {

    private static final int DOCUMENTS_PER_USER_LIMIT = 10_000;
    private static final int UNFANNED_DOCUMENTS_LIMIT = 10_000;

    // hack solution while prototyping :)
    private final EvictingQueue<Document> unfannedDocumentsQueue = EvictingQueue.create(UNFANNED_DOCUMENTS_LIMIT);
    private final Map<User, EvictingQueue<UserDocument>> allUserDocumentsInMemory = new HashMap<>();

    public List<UserDocument> getUnreadDocuments(User user) {
        return getUserDocuments(user)
                .stream()
                .collect(Collectors.toList());

    }

    private EvictingQueue<UserDocument> getUserDocuments(User user) {
        if (!allUserDocumentsInMemory.containsKey(user)) {
            allUserDocumentsInMemory.put(user, EvictingQueue.create(DOCUMENTS_PER_USER_LIMIT));
        }
        return allUserDocumentsInMemory.get(user);
    }

    public Optional<Document> findByPageUrl(String pageUrl) {
        Predicate<Document> documentPredicate = d -> Objects.equals(d.pageUrl, pageUrl);

        Optional<Document> unfanned = unfannedDocumentsQueue.stream()
                .filter(documentPredicate)
                .findAny();

        if (unfanned.isPresent()) {
            return unfanned;
        }

        Optional<Document> userDocument = allUserDocumentsInMemory.values().stream()
                .flatMap(Collection::stream)
                .map(UserDocument::getDocument)
                .filter(documentPredicate)
                .findAny();

        return userDocument;
    }

    public void add(Document document) {
        // TODO: Thread safe collection (or external queue e.g. rabbit)
        unfannedDocumentsQueue.add(document);
    }


    public Optional<Document> getNextUnfanned() {
        return unfannedDocumentsQueue.isEmpty() ?
                Optional.empty() :
                Optional.of(unfannedDocumentsQueue.poll());
    }

    public void add(UserDocument userDocument) {
        getUserDocuments(userDocument.getUser()).add(userDocument);
    }
}
