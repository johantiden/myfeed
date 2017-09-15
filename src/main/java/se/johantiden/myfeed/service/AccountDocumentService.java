package se.johantiden.myfeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.AccountDocument;
import se.johantiden.myfeed.persistence.AccountDocumentRepository;

import java.util.Optional;
import java.util.Set;

public class AccountDocumentService {

    @Autowired
    private AccountDocumentRepository accountDocumentRepository;

    public Set<Long> getReadyAccountDocumentIdsFor(long accountId) {
        return accountDocumentRepository.getReadyAccountDocumentIdsForAccount(accountId);
    }

    public void put(AccountDocument accountDocument) {
        accountDocumentRepository.save(accountDocument);
    }

    public void setRead(long accountDocumentId, boolean read) {

        Optional<AccountDocument> documentOptional = Optional.ofNullable(accountDocumentRepository.findOne(accountDocumentId));

        AccountDocument doc = documentOptional.orElseThrow(() -> new IllegalStateException("Could not find accountDocument " + accountDocumentId));

        doc.setRead(read);
        put(doc);

    }

    public Optional<AccountDocument> find(long accountDocumentId) {
        return Optional.ofNullable(accountDocumentRepository.findOne(accountDocumentId));
    }

    public long purgeReadDocuments() {
        Set<AccountDocument> allReadDocuments = accountDocumentRepository.findAllRead();
        int size = allReadDocuments.size();
        accountDocumentRepository.delete(allReadDocuments);
        return size;
    }
}
