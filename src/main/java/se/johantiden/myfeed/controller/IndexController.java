package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.User;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.UserDocumentService;
import se.johantiden.myfeed.util.JPredicates;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private static final boolean REMOVE_BAD = true;
    private static final boolean REMOVE_SPORT = true;
    @Autowired
    private UserDocumentService userDocumentService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserService userService;

    @RequestMapping("/rest/index/{username}")
    public Collection<Long> index(
            @PathVariable("username") String username) {

        log.info("User: " + username);
        Optional<User> userOptional = userService.find(username);

        User user = userOptional.orElseGet(() -> userService.create(username));

        Set<Long> userDocumentIds = userDocumentService.getReadyUserDocumentIdsFor(user.getId());

        log.info("index User:{}, keys:{}", username, userDocumentIds.size());

        return userDocumentIds;
    }

    @RequestMapping("/rest/userdocument/{userDocumentId}")
    public DocumentBean userDocument(@PathVariable("userDocumentId") Long userDocumentId) {

        Optional<DocumentBean> documentBean = tryFindUserDocument(userDocumentId);

        if (!documentBean.isPresent()) {
            throw new NotFound404("Not found");
        }

        return documentBean.get();
    }

    private Optional<DocumentBean> tryFindUserDocument(Long userDocumentId) {
        Optional<UserDocument> userDocumentOptional = userDocumentService.find(userDocumentId);

        Optional<Document> document = userDocumentOptional.map(UserDocument::getDocument);

        return document.filter(JPredicates.not(Document::isHidden)).map(d -> new DocumentBean(userDocumentOptional.get(), d));
    }

    @RequestMapping("/rest/userdocuments")
    public List<DocumentBean> userDocumentsMulti(@RequestParam("keys") List<Long> userDocumentIds) {
        List<DocumentBean> documentBeans = userDocumentIds.stream()
                .map(this::tryFindUserDocument)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return documentBeans;
    }

}
