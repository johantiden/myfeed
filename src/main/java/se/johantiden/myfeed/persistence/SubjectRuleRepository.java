package se.johantiden.myfeed.persistence;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface SubjectRuleRepository extends CrudRepository<SubjectRule, Long> {

    Optional<SubjectRule> findOneByNameAndExpression(String subject, String expression);
}
