package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Inbox;

import java.util.Optional;

public class InboxService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    private Inbox inbox;
    @Autowired
    private DocumentService documentService;

    public void put(Iterable<Document> documents) {
        documents.forEach(this::put);
    }

    public void put(Document document) {
        inbox.put(document);
    }

    public void putIfNew(Iterable<Document> documents) {
        documents.forEach(this::putIfNew);
    }

    public void putIfNew(Document document) {
        boolean isAlreadyInDocuments = documentService.hasDocument(document.getId());
        boolean isAlreadyInInbox = inbox.hasDocument(document.getId());
        if (!isAlreadyInInbox && !isAlreadyInDocuments) {
            log.info("Adding new document to inbox: {}", document.pageUrl);
            inbox.put(document);
        } else if (isAlreadyInDocuments) {
            documentService.put(document);
        }
    }

    public Optional<Document> pop() {
        return inbox.pop();
    }
}
