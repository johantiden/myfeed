package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;

import java.util.Optional;
import java.util.function.Predicate;

public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);
    @Autowired
    private DocumentRepository documentRepository;

    public void put(Iterable<Document> documents) {
        documents.forEach(this::put);
    }

    public void put(Document document) {
        documentRepository.put(document);
    }

    public void putIfNew(Iterable<Document> documents) {
        documents.forEach(this::putIfNew);
    }

    public void putIfNew(Document document) {
        Optional<Document> optional = documentRepository.find(document);
        if (optional.isPresent()) {
//            log.warn("putIfNew but was not new. (This can probably be optimized)");
        } else {
            put(document);
        }
    }

    public Optional<Document> find(Predicate<Document> predicate) {
        return documentRepository.find(predicate);
    }
}
