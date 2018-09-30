package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.util.JPredicates;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@EnableAutoConfiguration
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final DocumentService documentService;

    public IndexController(DocumentService documentService) {this.documentService = Objects.requireNonNull(documentService);}

    @RequestMapping(value = "/rest/index", method = GET)
    public Collection<Long> index() {

        Set<Long> documentIds = documentService.getReadyDocuments();

        log.info("index keys:{}", documentIds.size());

        return documentIds;
    }

    @RequestMapping(value = "/rest/document/{documentId}", method = GET)
    public DocumentBean document(@PathVariable("documentId") Long documentId) {

        Optional<DocumentBean> documentBean = tryFindDocument(documentId);

        if (!documentBean.isPresent()) {
            throw new NotFound404("Not found");
        }

        return documentBean.get();
    }

    private Optional<DocumentBean> tryFindDocument(Long documentId) {
        Optional<Document> documentOptional = documentService.find(documentId);

        return documentOptional.filter(JPredicates.not(Document::isHidden)).map(document -> new DocumentBean(document));
    }

    @RequestMapping(value = "/rest/documents", method = GET)
    public List<DocumentBean> documentsMulti(@RequestParam("keys") List<Long> documentIds) {
        List<DocumentBean> documentBeans = documentIds.stream()
                .map(this::tryFindDocument)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return documentBeans;
    }

}
