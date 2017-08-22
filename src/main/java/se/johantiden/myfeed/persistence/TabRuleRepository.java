package se.johantiden.myfeed.persistence;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface TabRuleRepository extends CrudRepository<TabRule, Long> {

    Optional<TabRule> findOneByNameAndExpression(String subject, String expression);
}
