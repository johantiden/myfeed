package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.johantiden.myfeed.persistence.SubjectRule;
import se.johantiden.myfeed.persistence.UserService;
import se.johantiden.myfeed.service.DocumentService;
import se.johantiden.myfeed.service.SubjectService;
import se.johantiden.myfeed.service.UserDocumentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class SettingsController {

    private static final Logger log = LoggerFactory.getLogger(SettingsController.class);
    private static final boolean REMOVE_BAD = true;
    private static final boolean REMOVE_SPORT = true;
    @Autowired
    private UserDocumentService userDocumentService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/rest/settings/subjectRules")
    public Collection<SubjectRuleBean> getSubjectRules() {

        List<SubjectRule> subjectRules = subjectService.getAllSubectRules();

        return subjectRules.stream().map(SettingsController::toBean).collect(Collectors.toList());

    }
    private static SubjectRuleBean toBean(SubjectRule subjectRule) {
        return new SubjectRuleBean(subjectRule.getSubject(), subjectRule.getExpression());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private class NotFound404 extends RuntimeException {
        public NotFound404(String message) {
            super(message);
        }
    }
}
