package se.johantiden.myfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.FeedRepository;
import se.johantiden.myfeed.persistence.InboxRepository;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.UserDocumentRepository;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.user.UserRepository;
import se.johantiden.myfeed.reader.FeedReaderService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.service.InboxService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;

@SpringBootApplication
@EnableScheduling
public class Main {

    @Bean
    public DocumentRepository documentRepository() {

        Optional<HashMap<Key<Document>, Document>> loadedMap = saver().loadDocuments();

        HashMap<Key<Document>, Document> map = loadedMap.orElse(new HashMap<>());
        return new DocumentRepository(map);
    }

    @Bean
    public Saver saver() {
        return new Saver();
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
    public FeedReaderService feedReaderService() {
        return new FeedReaderService();
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
    public UserDocumentRepository userDocumentRepository() {

        Optional<Map<Key<User>, SortedSet<UserDocument>>> loadedMap = saver().loadUserDocuments();

        Map<Key<User>, SortedSet<UserDocument>> map = loadedMap.orElse(new HashMap<>());

        return new UserDocumentRepository(map);
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
