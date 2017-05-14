package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.Username;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserDocumentService userDocumentService;
    @Autowired
    private DocumentService documentService;

    @RequestMapping("/rest/index/{username}")
    public Collection<String> index(
            @PathVariable("username") String username) {

        log.info("User: " + username);
        Username user = Keys.user(username);

        SortedSet<UserDocument> allUserDocuments = userDocumentService.getAllDocumentsFor(user);


        List<String> keys = allUserDocuments.stream()
                            .filter(UserDocument::isUnread)
                            .filter(ud -> documentService.find(ud.getDocumentKey()).flatMap(d -> Optional.ofNullable(d.tab)).isPresent())
                            .map(UserDocument::getKey)
                            .map(Object::toString)
                            .collect(Collectors.toList());

        log.info("index User:{}, keys:{}", username, keys.size());

        return keys;
    }

    @RequestMapping("/rest/userdocument/{userDocumentKey}")
    public DocumentBean userDocument(@PathVariable("userDocumentKey") String userDocumentKey) {

        String user = userDocumentKey.split(":")[0];
        Username userKey = Keys.user(user);
        Optional<UserDocument> documentOptional = userDocumentService.get(userKey, Key.<UserDocument>create(userDocumentKey));

        Optional<Document> document = documentOptional.flatMap(ud -> documentService.find(ud.getDocumentKey()));

        Optional<DocumentBean> documentBean = document
                                              .map(d -> new DocumentBean(documentOptional.get(), d));

        if (!documentBean.isPresent()) {
            throw new NotFound404("Not found");
        }

        return documentBean.get();
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class NotFound404 extends RuntimeException {
        public NotFound404(String message) {
            super(message);
        }
    }
}
