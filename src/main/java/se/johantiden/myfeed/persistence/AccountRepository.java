package se.johantiden.myfeed.persistence;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findOneByName(String accountname);
}
