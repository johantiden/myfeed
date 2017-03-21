package se.johantiden.myfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import se.johantiden.myfeed.persistence.DocumentRepository;
import se.johantiden.myfeed.service.DocumentService;

@SpringBootApplication
public class Main {

    @Bean
    public DocumentRepository documentRepository() {
        return new DocumentRepository();
    }

    @Bean
    public DocumentService documentService() {
        return new DocumentService();
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
