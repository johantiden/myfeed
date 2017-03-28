package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.service.UserDocumentService;


@RestController
@EnableAutoConfiguration
public class UserDocumentController {

    private static final Logger log = LoggerFactory.getLogger(UserDocumentController.class);
    @Autowired
    private UserDocumentService userDocumentService;

    @RequestMapping(value = "/rest/documents", method = RequestMethod.PUT)
    public void putDocument(@RequestBody UserDocumentPutBean userDocumentPutBean) {


        log.info("Received PUT document: {}", userDocumentPutBean);

        Key<User> userKey = Keys.user(userDocumentPutBean.getUsername());
        userDocumentService.setRead(userKey, Keys.document(userDocumentPutBean.pageUrl), userDocumentPutBean.read);


    }
}
