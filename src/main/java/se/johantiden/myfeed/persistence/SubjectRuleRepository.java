package se.johantiden.myfeed.persistence;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface SubjectRuleRepository extends CrudRepository<SubjectRule, Long> {

}
