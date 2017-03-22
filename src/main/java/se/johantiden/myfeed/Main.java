package se.johantiden.myfeed;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.persistence.FeedRepository;
import se.johantiden.myfeed.reader.DocumentFanJob;
import se.johantiden.myfeed.reader.FeedReaderJob;
import se.johantiden.myfeed.reader.FeedReaderService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.FeedService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@SpringBootApplication
@EnableScheduling
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Bean
    public DocumentRepository documentRepository() {
        return new DocumentRepository();
    }

    @Bean
    public DocumentService documentService() {
        return new DocumentService();
    }
//
//    @Bean
//    public FeedReaderJob feedReaderJob() {
//        log.info("constructing bean for FeedReaderJob");
//        return new FeedReaderJob();
//    }
//
//    @Bean
//    public DocumentFanJob documentFanJob() {
//        log.info("constructing bean for DocumentFanJob");
//        return new DocumentFanJob();
//    }

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

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
