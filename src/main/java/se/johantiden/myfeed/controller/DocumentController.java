package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.service.DocumentService;


@RestController
@EnableAutoConfiguration
public class DocumentController {

    private static final Logger log = LoggerFactory.getLogger(DocumentController.class);
    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = "/rest/documents", method = RequestMethod.PUT)
    public void putDocument(@RequestBody DocumentPutBean documentPutBean) {

        log.info("Received PUT document: {}", documentPutBean);

        documentService.setRead(documentPutBean.documentId, documentPutBean.read);



    }
}
