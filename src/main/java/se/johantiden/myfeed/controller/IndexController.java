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
import se.johantiden.myfeed.persistence.Account;
import se.johantiden.myfeed.persistence.AccountDocument;
import se.johantiden.myfeed.persistence.AccountService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.AccountDocumentService;
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
    private AccountDocumentService accountDocumentService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private AccountService accountService;

    @RequestMapping("/rest/index/{accountname}")
    public Collection<Long> index(
            @PathVariable("accountname") String accountname) {

        Optional<Account> accountOptional = accountService.find(accountname);

        Account account = accountOptional.orElseGet(() -> accountService.create(accountname));

        Set<Long> accountDocumentIds = accountDocumentService.getReadyAccountDocumentIdsFor(account.getId());

        log.info("index Account:{}, keys:{}", accountname, accountDocumentIds.size());

        return accountDocumentIds;
    }

    @RequestMapping("/rest/accountdocument/{accountDocumentId}")
    public DocumentBean accountDocument(@PathVariable("accountDocumentId") Long accountDocumentId) {

        Optional<DocumentBean> documentBean = tryFindAccountDocument(accountDocumentId);

        if (!documentBean.isPresent()) {
            throw new NotFound404("Not found");
        }

        return documentBean.get();
    }

    private Optional<DocumentBean> tryFindAccountDocument(Long accountDocumentId) {
        Optional<AccountDocument> accountDocumentOptional = accountDocumentService.find(accountDocumentId);

        Optional<Document> document = accountDocumentOptional.map(AccountDocument::getDocument);

        return document.filter(JPredicates.not(Document::isHidden)).map(d -> new DocumentBean(accountDocumentOptional.get(), d));
    }

    @RequestMapping("/rest/accountdocuments")
    public List<DocumentBean> accountDocumentsMulti(@RequestParam("keys") List<Long> accountDocumentIds) {
        List<DocumentBean> documentBeans = accountDocumentIds.stream()
                .map(this::tryFindAccountDocument)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return documentBeans;
    }

}
