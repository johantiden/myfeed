package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.UserDocumentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
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
    public List<DocumentBean> index(
            @PathVariable("username") String username,
            HttpServletRequest req  ) {

        if (username == null || "null".equals(username)) {
            username = "johan";
        }

        log.info("User: " + username);
        Key<User> user = Keys.user(username);
        List<UserDocument> userDocuments = userDocumentService.getUnreadDocumentsFor(user);

        return userDocuments.stream().map(ud -> documentService.find(ud.getDocumentKey()).map(d -> new DocumentBean(ud, d)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
