package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.service.UserDocumentService;


@RestController
@EnableAutoConfiguration
public class DocumentGroupController {

    private static final Logger log = LoggerFactory.getLogger(DocumentGroupController.class);
    @Autowired
    private UserDocumentService userDocumentService;

    @RequestMapping(value = "/rest/documents/groups/{documentGroupKey}")
    public void putDocument(@PathVariable("documentGroupKey") String documentGroupKey) {

        throw new RuntimeException("Not implemented");

    }
}
