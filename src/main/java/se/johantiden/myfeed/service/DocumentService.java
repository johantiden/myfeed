package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.redis.Key;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
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
        Optional<Document> optional = documentRepository.find(document.getKey());
        if (optional.isPresent()) {
//            log.warn("put but was not new. You must remove the old document first! Not putting.");
        } else {
            log.info("Adding new document: {}", document.pageUrl);
            documentRepository.put(document);
        }
    }

    public boolean hasDocument(Key<Document> documentKey) {
        return find(documentKey).isPresent();
    }

    public Optional<Document> find(Key<Document> documentKey) {
        return documentRepository.find(documentKey);
    }

    public Optional<Document> findAnySlow(Predicate<Document> predicate) {
        return documentRepository.findAnySlow(predicate);
    }

    public List<Document> findAllSlow(Predicate<Document> predicate) {
        return documentRepository.findAllSlow(predicate);
    }

    public long purgeOlderThan(Duration duration) {
        return documentRepository.purgeOlderThan(duration);
    }

    public Collection<Document> getAll() {
        return documentRepository.getAll();
    }
}
