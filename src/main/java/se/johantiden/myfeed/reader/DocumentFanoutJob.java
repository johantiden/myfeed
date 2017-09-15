package se.johantiden.myfeed.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Account;
import se.johantiden.myfeed.persistence.AccountDocument;
import se.johantiden.myfeed.persistence.AccountService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.InboxService;
import se.johantiden.myfeed.service.AccountDocumentService;
import se.johantiden.myfeed.settings.GlobalSettings;

import java.util.Optional;

@Component
public class DocumentFanoutJob {

    private static final Logger log = LoggerFactory.getLogger(DocumentFanoutJob.class);

    @Autowired
    private InboxService inboxService;
    @Autowired
    private AccountDocumentService accountDocumentService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DocumentService documentService;

    @Scheduled(fixedRate = GlobalSettings.FANOUT_INTERVAL)
    public void consumeOne() {
        Optional<Document> documentOptional = inboxService.pop();

        documentOptional.ifPresent(document -> {
            documentService.put(document);
            consume(document);
        });
    }

    private void consume(Document document) {
        log.debug("DocumentFanJob consuming '{}'", document.getPageUrl());

        hack();

        accountService.getAllAccounts().stream()
                .forEach(account -> {
                    log.debug("  -> {}", account);
                    accountDocumentService.put(new AccountDocument(account, document));
                });

    }

    private Account hack() {return accountService.find("johan").orElseGet(() -> accountService.create("johan"));}
}
