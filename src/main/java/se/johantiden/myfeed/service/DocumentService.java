package se.johantiden.myfeed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DocumentService {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);
    @Autowired
    private DocumentRepository documentRepository;

    @PostConstruct
    public void postConstruct() {
//
        resetSubjectsAndTabs();
    }

    private void resetSubjectsAndTabs() {
//        Objects.requireNonNull(documentRepository);
//        documentRepository.findAll().forEach(d -> {
//            d.getSubjects().clear();
//            d.getTabs().clear();
//            d.setTabsParsed(false);
//            d.setSubjectsParsed(false);
//        });
    }

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


//    public long purgeOlderThan(Duration duration) {
//
//        List<Map.Entry<Key<Document>, Document>> toBeRemoved = db.documents.entrySet().stream()
//                                                               .filter(isOlderThan(duration))
//                                                               .collect(Collectors.toList());
//
//        toBeRemoved.forEach(e -> db.documents.remove(e.getKey()));
//
//        return toBeRemoved.size();
//    }

//    private static Predicate<? super Map.Entry<Key<Document>, Document>> isOlderThan(Duration duration) {
//        return e -> {
//            Instant publishDate = e.getValue().getPublishDate();
//            return Chrono.isOlderThan(duration, publishDate);
//        };
//    }
}
