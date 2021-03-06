package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = Objects.requireNonNull(documentRepository);
    }

    public void put(Iterable<Document> documents) {
        documents.forEach(this::put);
    }

    public Document put(Document document) {
        if (!document.hasId()) {
            Optional<Document> existing = find(document);
            if (existing.isPresent()) {
                return documentRepository.merge(document, existing.get());
            } else {
                log.info("Creating new document {}", document);
            }
        }
        return documentRepository.save(document);
    }

    public boolean hasDocumentWithUrl(String pageUrl) {
        return findOneByPageUrl(pageUrl).isPresent();
    }

    private Optional<Document> find(Document document) {

        Optional<Document> existing = Optional.empty();

        if (document.hasId()) {
            existing = Optional.of(documentRepository.findOne(document.getId()));
        }

        if (!existing.isPresent()) {
            existing = findOneByPageUrl(document.getPageUrl());
        }

        return existing;
    }

    private Optional<Document> findOneByPageUrl(String pageUrl) {
        return documentRepository.findOneByPageUrl(pageUrl);
    }

    public Optional<Document> find(long documentId) {
        return Optional.of(documentRepository.findOne(documentId));
    }

    public Set<Document> findDocumentsNotParsedSubjects() {
        return documentRepository.findDocumentsNotParsedSubjects();
    }

    public Set<Document> getReadyDocuments() {
        return documentRepository.getReadyDocuments();
    }

    public void setRead(long documentId) {
        setRead(documentId, true);
    }

    public void setRead(long documentId, boolean read) {

        Optional<Document> documentOptional = Optional.of(documentRepository.findOne(documentId));

        Document doc = documentOptional.orElseThrow(() -> new IllegalStateException("Could not find document " + documentId));

        doc.setRead(read);
        put(doc);

    }
}
