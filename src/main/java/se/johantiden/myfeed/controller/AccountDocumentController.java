package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.service.AccountDocumentService;


@RestController
@EnableAutoConfiguration
public class AccountDocumentController {

    private static final Logger log = LoggerFactory.getLogger(AccountDocumentController.class);
    @Autowired
    private AccountDocumentService accountDocumentService;

    @RequestMapping(value = "/rest/documents", method = RequestMethod.PUT)
    public void putDocument(@RequestBody AccountDocumentPutBean accountDocumentPutBean) {


        if (accountDocumentPutBean.getAccountName() == null) {
            log.warn("Not 'logged in'. Can't check documents as read.");
            return;
        }

        log.info("Received PUT document: {}", accountDocumentPutBean);

        accountDocumentService.setRead(accountDocumentPutBean.accountDocumentId, accountDocumentPutBean.read);



    }
}
