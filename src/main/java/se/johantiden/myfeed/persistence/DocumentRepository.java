package se.johantiden.myfeed.persistence;

import com.google.common.collect.EvictingQueue;
import se.johantiden.myfeed.persistence.model.User;
import se.johantiden.myfeed.persistence.model.UserDocument;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentRepository {

    private static final int DOCUMENTS_PER_USER = 10_000;

    // hack solution while prototyping :)
    private final Map<User, EvictingQueue<UserDocument>> allUserDocumentsInMemory = new HashMap<>();

    public List<UserDocument> getUnreadDocuments(User user) {
        return getUserDocuments(user)
                .stream()
                .collect(Collectors.toList());

    }

    private Collection<UserDocument> getUserDocuments(User user) {
        if (!allUserDocumentsInMemory.containsKey(user)) {
            allUserDocumentsInMemory.put(user, EvictingQueue.create(DOCUMENTS_PER_USER));
        }
        return allUserDocumentsInMemory.get(user);
    }
}
