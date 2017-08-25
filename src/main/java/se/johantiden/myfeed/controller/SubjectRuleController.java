package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.SubjectRule;
import se.johantiden.myfeed.service.SubjectService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class SubjectRuleController {

    private static final Logger log = LoggerFactory.getLogger(SubjectRuleController.class);
    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/rest/subjectRules")
    public Collection<SubjectRuleBean> getSubjectRules() {

        List<SubjectRule> subjectRules = subjectService.getAllSubjectRules();

        return subjectRules.stream().map(SubjectRuleController::toBean).collect(Collectors.toList());
    }

    @RequestMapping(value = "/rest/subjectRules/{subjectRuleId}", method = RequestMethod.DELETE)
    public void deleteSubjectRule(@PathVariable("subjectRuleId") Long id) {

        Optional<SubjectRule> subjectRuleOptional = subjectService.findSubjectRule(id);

        if (!subjectRuleOptional.isPresent()) {
            throw new NotFound404("Could not find SubjectRule with id " + id);
        }

        log.info("Deleting " + subjectRuleOptional.get());
        subjectService.deleteSubjectRule(id);
    }

    @RequestMapping(value = "/rest/subjectRules", method = RequestMethod.PUT)
    public SubjectRuleBean putSubjectRule(@RequestBody SubjectRulePutBean subjectRuleBean) {


        String name = subjectRuleBean.getName();
        String expression = subjectRuleBean.getExpression();
        long id = subjectRuleBean.getId();

        SubjectRule subjectRule;
        if (id > 0) {
            Optional<SubjectRule> subjectRuleOptional = Optional.ofNullable(id)
                    .flatMap(subjectService::findSubjectRule);

            if (!subjectRuleOptional.isPresent()) {
                throw new NotFound404("Could not find SubjectRule with id " + id);
            }

            subjectRule = subjectRuleOptional.get();
            subjectRule.setName(name);
            subjectRule.setExpression(expression);
        } else {
            log.info("New subject rule: {}, {}", name, expression);
            subjectRule = new SubjectRule(name, expression);
        }

        subjectService.put(subjectRule);
        SubjectRuleBean response = toBean(subjectRule);
        return response;

    }

    private static SubjectRuleBean toBean(SubjectRule subjectRule) {
        Objects.requireNonNull(subjectRule);
        Objects.requireNonNull(subjectRule.getId());
        return new SubjectRuleBean(subjectRule.getId(), subjectRule.getName(), subjectRule.getExpression(), subjectRule.getCreated().toInstant().toEpochMilli());
    }
}
