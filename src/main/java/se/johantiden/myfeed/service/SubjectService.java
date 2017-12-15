package se.johantiden.myfeed.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.classification.DocumentMatcher;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.SubjectClassifier;
import se.johantiden.myfeed.persistence.SubjectRule;
import se.johantiden.myfeed.persistence.SubjectRuleRepository;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

        Collection<SubjectRule> defaultSubjectRules = SubjectClassifier.getDefaultSubjectRules();

        defaultSubjectRules.forEach(this::put);
    }

    public void put(SubjectRule subjectRule) {
        Pattern pattern = Pattern.compile(subjectRule.getExpression());

        Optional<SubjectRule> existing = subjectRuleRepository.findOneByNameAndExpression(subjectRule.getName(), subjectRule.getExpression());
        if (existing.isPresent() && !Objects.equals(subjectRule.getId(), existing.get().getId())) {
            throw new IllegalStateException("Subject Rule already present! " + subjectRule + " vs " + existing.get());
        }

        subjectRuleRepository.save(subjectRule);

        documentService.invalidateSubjects();
    }

    public void parseSubjectsFor(Document document) {

        document.setSubjectsParsed(true);

        List<SubjectRule> subjectRules = getAllSubjectRules();

        Set<SubjectRule> matchingRules = subjectRules.stream()
                .filter(r -> {
                    boolean match = r.isMatch(document);
                    if (match) {
                        r.setLatestMatch(Timestamp.from(Instant.now()));
                    }
                    return match;
                })
                .collect(Collectors.toSet());
        subjectRuleRepository.save(matchingRules);

        Set<String> matchingSubjects = matchingRules.stream()
                .map(SubjectRule::getName)
                .collect(Collectors.toSet());

        hackAddSubredditAsSubject(matchingSubjects, document);


        document.getSubjects().clear();
        document.getSubjects().addAll(matchingSubjects);
        documentService.put(document);
    }

    private static void hackAddSubredditAsSubject(Set<String> matchingSubjects, Document document) {

        if (new DocumentMatcher(document).has("Reddit")) {
            if (!document.getSourceCategories().isEmpty()) {
                String cat = document.getSourceCategories().get(0);
                cat = StringUtils.capitalize(cat);
                matchingSubjects.add(cat);
            }
        }

    }

    public List<SubjectRule> getAllSubjectRules() {
        return Lists.newArrayList(subjectRuleRepository.findAll());
    }

    public Optional<SubjectRule> findSubjectRule(Long id) {
        SubjectRule subjectRule = subjectRuleRepository.findOne(id);
        return Optional.ofNullable(subjectRule);
    }

    public void deleteSubjectRule(long id) {
        subjectRuleRepository.delete(id);
        documentService.invalidateSubjects();
    }
}
