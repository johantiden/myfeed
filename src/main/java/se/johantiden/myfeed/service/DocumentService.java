package se.johantiden.myfeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.User;
import se.johantiden.myfeed.persistence.UserDocument;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public void put(Iterable<Document> documents) {
        documents.forEach(this::put);
    }

    public void put(Document document) {
        String pageUrl = document.getPageUrl();
        Optional<Document> oldDocument = documentRepository.findByPageUrl(pageUrl);
        if (!oldDocument.isPresent()) {
            documentRepository.add(document);
        }
    }

    public Optional<Document> popNewestUnfanned() {
        return documentRepository.getNextUnfanned();
    }

    public void put(UserDocument userDocument) {
        documentRepository.add(userDocument);
    }
}
