package se.johantiden.myfeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;

import java.util.Optional;

public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public void put(Iterable<Document> documents) {
        documents.forEach(this::put);
    }

    public void put(Document document) {
        documentRepository.put(document);
    }

    public Optional<Document> popNewestUnfanned() {
        return documentRepository.getNextUnfanned();
    }
}
