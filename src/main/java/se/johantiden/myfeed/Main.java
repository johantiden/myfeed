package se.johantiden.myfeed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import se.johantiden.myfeed.persistence.DB;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.FeedRepository;
import se.johantiden.myfeed.persistence.InboxRepository;
import se.johantiden.myfeed.persistence.UserDocumentRepository;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.persistence.file.BaseSaver;
import se.johantiden.myfeed.persistence.user.UserRepository;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.service.InboxService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
@Configuration
public class Main implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Bean
    public DocumentRepository documentRepository() {
        DocumentRepository documentRepository = new DocumentRepository(db());
        return documentRepository;
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
    public UserDocumentRepository userDocumentRepository() {
        return new UserDocumentRepository();
    }

    @Bean
    public DB db() {
        Optional<DB> loaded = BaseSaver.load(BaseSaver.DB);
        if (loaded.isPresent()) {
            log.info("Loaded database");
            DB db = loaded.get();
            return db;
        }
        log.info("No database file found. Creating fresh.");
        return new DB();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(executor());
    }

    @Bean(destroyMethod="shutdown")
    public Executor executor() {
        return Executors.newScheduledThreadPool(10);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
