package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.SubjectRule;
import se.johantiden.myfeed.service.SubjectService;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class SettingsController {

    private static final Logger log = LoggerFactory.getLogger(SettingsController.class);
    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/rest/settings/subjectRules")
    public Collection<SubjectRuleBean> getSubjectRules() {

        List<SubjectRule> subjectRules = subjectService.getAllSubectRules();

        return subjectRules.stream().map(SettingsController::toBean).collect(Collectors.toList());
    }

    @RequestMapping(value = "/rest/settings/subjectRules/edit", method = RequestMethod.POST)
    public void postSubjectRule(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("expression") String expression) {

        Optional<SubjectRule> subjectRuleOptional = subjectService.findSubjectRule(id);

        if (!subjectRuleOptional.isPresent()) {
            throw new IllegalArgumentException("Could not find SubjectRule with id " + id);
        }
        putSubjectRule(id, name, expression);
    }
@RequestMapping(value = "/rest/settings/subjectRules", method = RequestMethod.DELETE)
    public void postSubjectRule(
            @RequestParam("id") Long id) {

        Optional<SubjectRule> subjectRuleOptional = subjectService.findSubjectRule(id);

        if (!subjectRuleOptional.isPresent()) {
            throw new IllegalArgumentException("Could not find SubjectRule with id " + id);
        }
        subjectService.deleteSubjectRule(id);
    }

    @RequestMapping(value = "/rest/settings/subjectRules", method = RequestMethod.PUT)
    public void putSubjectRule(
            @RequestParam(value = "id", required = false) @Nullable Long id,
            @RequestParam("name") String name,
            @RequestParam("expression") String expression) {

        Optional<SubjectRule> subjectRuleOptional = Optional.ofNullable(id)
                .flatMap(subjectService::findSubjectRule);

        if (!subjectRuleOptional.isPresent()) {
            log.info("New subject rule: {}, {}", name, expression);
            SubjectRule subjectRule = new SubjectRule(name, expression);
            subjectService.put(subjectRule);
        } else {
            SubjectRule subjectRule = subjectRuleOptional.get();
            subjectRule.setName(name);
            subjectRule.setExpression(expression);
            subjectService.put(subjectRule);
        }
    }

    private static SubjectRuleBean toBean(SubjectRule subjectRule) {
        Objects.requireNonNull(subjectRule);
        Objects.requireNonNull(subjectRule.getId());
        return new SubjectRuleBean(subjectRule.getId(), subjectRule.getName(), subjectRule.getExpression());
    }
}
