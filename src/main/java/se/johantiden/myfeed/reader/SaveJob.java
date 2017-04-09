package se.johantiden.myfeed.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.Saver;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.UserDocumentRepository;

@Component
public class SaveJob {

    @Autowired
    private Saver saver;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserDocumentRepository userDocumentRepository;

    @Scheduled(fixedRate = 60*1000, initialDelay = 10*1000)
    public void saveToDisk() {
        saver.saveDocuments(documentRepository.unwrapMap());
        saver.saveUserDocuments(userDocumentRepository.unwrapMap());
    }
}
