package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.InboxRepository;

import java.util.Optional;

public class InboxService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    @Autowired
    private InboxRepository inboxRepository;
    @Autowired
    private DocumentService documentService;

    public void put(Iterable<Document> documents) {
        documents.forEach(this::put);
    }

    public void put(Document document) {
        inboxRepository.put(document);
    }

    public void putIfNew(Iterable<Document> documents) {
        documents.forEach(this::putIfNew);
    }

    public void putIfNew(Document document) {
        boolean isAlreadyInInbox = inboxRepository.hasDocument(document.getKey());
        if (!isAlreadyInInbox) {
            log.info("Adding new document to inbox: {}", document.pageUrl);
            put(document);
        } else {
            if (documentService.hasDocument(document.getKey())) {
                documentService.purge(document.getKey());
                documentService.put(document);
            }
        }
    }

    public Optional<Document> pop() {
        return inboxRepository.pop();
    }
}
