package se.johantiden.myfeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;

import java.util.List;
import java.util.Optional;

public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public void resetSubjects() {
        throw new RuntimeException("Not implemented");
    }

    public void put(Iterable<Document> documents) {
        documents.forEach(this::put);
    }

    public void put(Document document) {
        Optional<Document> optional = Optional.ofNullable(documentRepository.findOne(document.getId()));
        if(optional.isPresent()) {
            merge(optional.get(), document);
        }
        documentRepository.save(document);
    }

    private void merge(Document existing, Document newDocument) {
        throw new RuntimeException("Not implemented");
    }

    public boolean hasDocument(long documentId) {
        return find(documentId).isPresent();
    }

    public Optional<Document> find(long documentId) {
        return Optional.ofNullable(documentRepository.findOne(documentId));
    }

    public List<Document> findDocumentsNotUnparsedSubjects() {
        return documentRepository.findDocumentsNotUnparsedSubjects();
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
