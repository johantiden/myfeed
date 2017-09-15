package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;

    public Collection<Account> getAllAccounts() {
        return Lists.newArrayList(accountRepository.findAll());
    }

    public Optional<Account> find(String accountname) {
        return Optional.ofNullable(accountRepository.findOneByName(accountname));
    }

    public Account create(String accountname) {
        log.info("Creating account '{}'", accountname);
        Account account = new Account(accountname);
        accountRepository.save(account);
        return account;
    }
}
