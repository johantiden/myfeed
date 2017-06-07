package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentClassifier;
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
    private static final boolean REMOVE_BAD = true;
    private static final boolean REMOVE_SPORT = true;
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
                            .filter(ud -> {
                                Optional<String> tab = documentService.find(ud.getDocumentKey()).flatMap(d -> Optional.ofNullable(d.tab));

                                return tab
                                        .map(t -> {
                                            boolean isBad = DocumentClassifier.BAD.equals(t);
                                            return !isBad;
//                                            boolean isSport = DocumentClassifier.SPORT.equals(t);
//                                            boolean isKultur = DocumentClassifier.CULTURE.equals(t);
//                                            return !isBad && !isSport && !isKultur;
                                        })
                                        .orElse(false);

                            })
                            .map(UserDocument::getKey)
                            .map(Object::toString)
                            .collect(Collectors.toList());

        log.info("index User:{}, keys:{}", username, keys.size());

        return keys;
    }

    @RequestMapping("/rest/userdocument/{userDocumentKey}")
    public DocumentBean userDocument(@PathVariable("userDocumentKey") String userDocumentKey) {

        Optional<DocumentBean> documentBean = tryFindUserDocument(userDocumentKey);

        if (!documentBean.isPresent()) {
            throw new NotFound404("Not found");
        }

        return documentBean.get();
    }

    private Optional<DocumentBean> tryFindUserDocument(@PathVariable("userDocumentKey") String userDocumentKey) {
        String user = userDocumentKey.split(":")[0];
        Username userKey = Keys.user(user);
        Optional<UserDocument> documentOptional = userDocumentService.get(userKey, Key.<UserDocument>create(userDocumentKey));

        Optional<Document> document = documentOptional.flatMap(ud -> documentService.find(ud.getDocumentKey()));

        return document.map(d -> new DocumentBean(documentOptional.get(), d));
    }

    @RequestMapping("/rest/userdocuments")
    public List<DocumentBean> userDocumentsMulti(@RequestParam("keys") List<String> keys) {


        return keys.stream()
                .map(this::tryFindUserDocument)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());



    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class NotFound404 extends RuntimeException {
        public NotFound404(String message) {
            super(message);
        }
    }
}
