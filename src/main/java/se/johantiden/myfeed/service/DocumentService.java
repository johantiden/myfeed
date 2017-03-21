package se.johantiden.myfeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.model.User;
import se.johantiden.myfeed.persistence.model.UserDocument;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public List<UserDocument> getUnreadDocumentsFor(User user) {

        List<UserDocument> documents = documentRepository.getUnreadDocuments(user);

        Comparator<UserDocument> comparator = Comparator.comparing(ud -> ud.getDocument().getPublishedDate());
        Comparator<UserDocument> reversed = comparator.reversed();
        Collections.sort(documents, reversed);
        return documents;
    }
}
