package se.johantiden.myfeed.persistence.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.UserDocumentRepository;

import java.util.Optional;

public class UserDocumentRepositorySaver implements Saver<UserDocumentRepository> {

    private static final Logger log = LoggerFactory.getLogger(UserDocumentRepositorySaver.class);

    private final BaseSaver baseSaver;

    public UserDocumentRepositorySaver(BaseSaver baseSaver) {
        this.baseSaver = baseSaver;
    }

    @Override
    public void save(UserDocumentRepository userDocumentRepository) {
        log.info("Saving user documents: " + userDocumentRepository);
        BaseSaver.save(BaseSaver.USER_DOCUMENTS, userDocumentRepository);
        log.info("User documents saved!");
    }

    @Override
    public Optional<UserDocumentRepository> load() {
        return baseSaver.load(BaseSaver.USER_DOCUMENTS);
    }
}
