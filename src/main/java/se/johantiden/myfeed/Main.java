package se.johantiden.myfeed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.FeedRepository;
import se.johantiden.myfeed.persistence.InboxRepository;
import se.johantiden.myfeed.persistence.UserDocumentRepository;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.persistence.file.BaseSaver;
import se.johantiden.myfeed.persistence.file.DocumentRepositorySaver;
import se.johantiden.myfeed.persistence.file.UserDocumentRepositorySaver;
import se.johantiden.myfeed.persistence.user.UserRepository;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.service.InboxService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Bean
    public DocumentRepository documentRepository() {
        Optional<DocumentRepository> loaded = documentRepositorySaver().load();
        if (loaded.isPresent()) {
            log.info("Loaded documents {}", loaded.get().size());
        }

        return loaded.orElse(new DocumentRepository());
    }

    @Bean
    public DocumentRepositorySaver documentRepositorySaver() {
        return new DocumentRepositorySaver();
    }

    @Bean
    public UserDocumentRepositorySaver userDocumentRepositorySaver() {
        return new UserDocumentRepositorySaver(baseSaver());
    }

    @Bean
    public DocumentService documentService() {
        return new DocumentService();
    }

    @Bean
    public FeedService feedService() {
        return new FeedService();
    }

    @Bean
    public FeedRepository feedRepository() {
        return new FeedRepository();
    }

    @Bean
    public UserDocumentService userDocumentService() {
        return new UserDocumentService();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public InboxService inboxService() {
        return new InboxService();
    }

    @Bean
    public InboxRepository inboxRepository() {
        return new InboxRepository();
    }

    @Bean
    public BaseSaver baseSaver() {
        return new BaseSaver();
    }

    @Bean
    public UserDocumentRepository userDocumentRepository() {


        Optional<UserDocumentRepository> loaded = userDocumentRepositorySaver().load();
        if (loaded.isPresent()) {
            log.info("Loaded userdocuments {}", loaded.get());
        }
        return loaded.orElse(new UserDocumentRepository());
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
