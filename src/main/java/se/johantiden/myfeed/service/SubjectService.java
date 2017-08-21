package se.johantiden.myfeed.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentClassifier;
import se.johantiden.myfeed.persistence.SubjectRule;
import se.johantiden.myfeed.persistence.SubjectRuleRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SubjectService {

    @Autowired
    SubjectRuleRepository subjectRuleRepository;

    @Autowired
    DocumentService documentService;

    @PostConstruct
    public void postConstruct() {
        boolean isEmpty = !subjectRuleRepository.findAll().iterator().hasNext();
        if (isEmpty) {
            hackCreateDefaultSubjectRules();
        }
    }

    private void hackCreateDefaultSubjectRules() {

        List<SubjectRule> defaultSubjectRules = DocumentClassifier.getDefaultSubjectRules();

        defaultSubjectRules.forEach(this::put);
    }

    public void put(SubjectRule subjectRule) {
        Pattern pattern = Pattern.compile(subjectRule.getExpression());
        subjectRuleRepository.save(subjectRule);
    }

    public void parseSubjectsFor(Document document) {

        document.setSubjectsParsed(true);

        List<SubjectRule> subjectRules = getAllSubectRules();

        Set<String> matchingSubjects = subjectRules.stream()
                .filter(r -> {
                    boolean match = r.isMatch(document);
                    return match;
                })
                .map(SubjectRule::getSubject)
                .collect(Collectors.toSet());

        document.getSubjects().clear();
        document.getSubjects().addAll(matchingSubjects);
        documentService.put(document);
    }

    public List<SubjectRule> getAllSubectRules() {
        return Lists.newArrayList(subjectRuleRepository.findAll());
    }
}
