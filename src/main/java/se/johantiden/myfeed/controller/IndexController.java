package se.johantiden.myfeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.User;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.service.UserDocumentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class IndexController {

    @Autowired
    private UserDocumentService userDocumentService;

    @RequestMapping("/rest/index")
    public List<DocumentBean> index(HttpServletRequest req  ) {

        Key<User> johan = Keys.user("johan");
        List<UserDocument> userDocuments = userDocumentService.getUnreadDocumentsFor(johan);

        return userDocuments.stream().map(DocumentBean::new).collect(Collectors.toList());
    }

}
