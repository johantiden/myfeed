package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);
    @Autowired
    private DocumentRepository documentRepository;

    public void put(Iterable<Document> documents) {
        documents.forEach(this::put);
    }

    public Document put(Document document) {
        if (document.getId() == null) {
            Optional<Document> existing = find(document);
            if (existing.isPresent()) {
                return merge(existing.get(), document);
            }
        }
        return documentRepository.save(document);
    }

    private Document merge(Document existing, Document newDocument) {
        existing.setScore(newDocument.getScore());
        Document saved = documentRepository.save(existing);
        return saved;
    }

    public boolean hasDocument(Document document) {
        return find(document).isPresent();
    }

    private Optional<Document> find(Document document) {

        Optional<Document> existing = Optional.empty();

        if (document.getId() != null) {
            existing = Optional.ofNullable(documentRepository.findOne(document.getId()));
        }

        if (!existing.isPresent()) {
            existing = documentRepository.findOneByPageUrl(document.getPageUrl());
        }

        return existing;
    }

    public Optional<Document> find(long documentId) {
        return Optional.ofNullable(documentRepository.findOne(documentId));
    }

    public List<Document> findDocumentsNotParsedSubjects() {
        return documentRepository.findDocumentsNotParsedSubjects();
    }

    public List<Document> findDocumentsNotParsedTabs() {
        return documentRepository.findDocumentsNotParsedTabs();
    }

    public void invalidateSubjects() {
        documentRepository.findAll().forEach(d -> {
            d.setSubjectsParsed(false);
            d.setTabsParsed(false);
            this.put(d);
        });
    }

    public void invalidateTabs() {
        documentRepository.findAll().forEach(d -> {
            d.setTabsParsed(false);
            this.put(d);
        });
    }

    public Set<Long> getReadyDocuments() {
        return documentRepository.getReadyDocumentIds();
    }

    public void setRead(long documentId, boolean read) {

        Optional<Document> documentOptional = Optional.ofNullable(documentRepository.findOne(documentId));

        Document doc = documentOptional.orElseThrow(() -> new IllegalStateException("Could not find document " + documentId));

        doc.setRead(read);
        put(doc);

    }

    public long purgeReadDocuments() {
        Set<Document> allReadDocuments = documentRepository.findAllRead();
        int size = allReadDocuments.size();
        documentRepository.delete(allReadDocuments);
        return size;
    }
}
