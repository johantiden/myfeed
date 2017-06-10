package se.johantiden.myfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import se.johantiden.myfeed.persistence.FeedPopulator;
import se.johantiden.myfeed.persistence.Inbox;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;
import se.johantiden.myfeed.service.InboxService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
@Configuration
public class Main implements SchedulingConfigurer {

    @Bean
    public DocumentService documentService() {
        DocumentService documentService = new DocumentService();
        documentService.resetSubjects();
        return documentService;
    }

    @Bean
    public FeedService feedService() {
        return new FeedService();
    }

    @Bean
    public FeedPopulator feedRepository() {
        return new FeedPopulator();
    }

    @Bean
    public UserDocumentService userDocumentService() {
        return new UserDocumentService();
    }

    @Bean
    public InboxService inboxService() {
        return new InboxService();
    }

    @Bean
    public Inbox inboxRepository() {
        return new Inbox();
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
        return Executors.newScheduledThreadPool(5);
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
