package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.plugin.HackerNewsFeed;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.util.JCollections;
import se.johantiden.myfeed.util.JPredicates;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@CrossOrigin
@RestController
@EnableAutoConfiguration
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    private final DocumentService documentService;

    public IndexController(DocumentService documentService) {this.documentService = Objects.requireNonNull(documentService);}

    @GetMapping(value = "/rest/index/keys")
    public Collection<Long> indexKeys() {

        Set<Document> documents = documentService.getReadyDocuments();

        List<Long> ids = documents.stream()
                .filter(lastFilter())
                .map(Document::getId)
                .collect(Collectors.toList());

        log.info("index size:{}", ids.size());

        return ids;
    }

    @GetMapping(value = "/rest/index/documents")
    public List<DocumentBean> indexDocuments() {

        Set<Document> documents = documentService.getReadyDocuments();


        List<DocumentBean> beans = documents.stream()
                .filter(lastFilter())
                .map(DocumentBean::new)
                .collect(Collectors.toList());

        log.info("index size:{}", beans.size());

        return beans;
    }

    private Predicate<Document> lastFilter() {
        return document -> true;
//        return document ->
//                !(Objects.equals(document.getFeedName(), HackerNewsFeed.NAME) && document.getSubjects().isEmpty());
    }

    @RequestMapping(value = "/rest/document/{documentId}", method = GET)
    public DocumentBean document(@PathVariable("documentId") long documentId) {
        Optional<DocumentBean> documentBean = tryFindDocument(documentId);

        if (!documentBean.isPresent()) {
            throw new NotFound404("Not found");
        }

        return documentBean.get();
    }

    private Optional<DocumentBean> tryFindDocument(long documentId) {
        Optional<Document> documentOptional = documentService.find(documentId);

        return documentOptional.filter(JPredicates.not(Document::isHidden)).map(DocumentBean::new);
    }

    @RequestMapping(value = "/rest/documents", method = GET)
    public List<DocumentBean> documentsMulti(@RequestParam("keys") Collection<Long> documentIds) {
        return documentIds.stream()
                .map(this::tryFindDocument)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
