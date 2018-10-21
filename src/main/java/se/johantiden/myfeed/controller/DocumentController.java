package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.service.DocumentService;

import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@EnableAutoConfiguration
public class DocumentController {

    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {this.documentService = Objects.requireNonNull(documentService);}

    @PostMapping("/rest/documents/hide")
    public String putDocument(@RequestParam("id") List<Long> ids) {

        log.info("Received hide request: {}", ids);

        ids.forEach(documentService::setRead);


        return "{\"status:\":\"OK\"}";

    }
}
