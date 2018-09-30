package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.service.DocumentService;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@EnableAutoConfiguration
public class ErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);
    private static final boolean REMOVE_BAD = true;
    private static final boolean REMOVE_SPORT = true;

    private final DocumentService documentService;

    public ErrorController(DocumentService documentService) {this.documentService = Objects.requireNonNull(documentService);}

    @RequestMapping(value = "/error", method = GET)
    public Collection<Long> index() {

        Set<Long> documentIds = documentService.getReadyDocuments();

        log.info("index keys:{}", documentIds.size());

        return documentIds;
    }

}
