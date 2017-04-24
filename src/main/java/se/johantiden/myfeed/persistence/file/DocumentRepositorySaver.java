package se.johantiden.myfeed.persistence.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.DocumentRepository;

import java.util.Optional;

public class DocumentRepositorySaver implements Saver<DocumentRepository> {

    private static final Logger log = LoggerFactory.getLogger(DocumentRepositorySaver.class);

    @Autowired
    private BaseSaver baseSaver;

    @Override
    public void save(DocumentRepository documentRepository) {
        log.info("Saving {} documents...", documentRepository.size());
        BaseSaver.save(BaseSaver.DOCUMENTS, documentRepository);
        log.info("Documents saved!");
    }

    @Override
    public Optional<DocumentRepository> load() {
        return baseSaver.load(BaseSaver.DOCUMENTS);
    }
}
