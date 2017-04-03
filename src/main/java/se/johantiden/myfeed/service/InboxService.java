package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.InboxRepository;
import se.johantiden.myfeed.persistence.redis.Key;

import java.util.Optional;
import java.util.function.Predicate;

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
        boolean isAlreadyInDocuments = documentService.hasDocument(document.getKey());
        boolean isAlreadyInInbox = inboxRepository.hasDocument(document.getKey());
        if (!isAlreadyInDocuments && !isAlreadyInInbox) {
            log.info("Adding new document to inbox: {}", document.pageUrl);
            put(document);
        }
    }

    public Optional<Document> find(Key<Document> documentKey) {
        return inboxRepository.find(documentKey);
    }

    public boolean hasDocument(Key<Document> documentKey) {
        Optional<Document> document = inboxRepository.find(documentKey);
        return document.isPresent();
    }

    public Optional<Document> pop() {
        return inboxRepository.pop();
    }
}
