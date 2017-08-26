package se.johantiden.myfeed.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.TabRule;
import se.johantiden.myfeed.persistence.TabClassifier;
import se.johantiden.myfeed.persistence.TabRule;
import se.johantiden.myfeed.persistence.TabRuleRepository;

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

import static se.johantiden.myfeed.persistence.DocumentClassifier.*;

public class TabService {

    @Autowired
    TabRuleRepository tabRuleRepository;

    @Autowired
    DocumentService documentService;

    @PostConstruct
    public void postConstruct() {
        boolean isEmpty = !tabRuleRepository.findAll().iterator().hasNext();
        if (isEmpty) {
            hackCreateDefaultTabRules();
        }
    }

    private void hackCreateDefaultTabRules() {

        Collection<TabRule> defaultRules = TabClassifier.getDefaultTabRules();

        defaultRules.forEach(this::put);
    }

    public void put(TabRule tabRule) {
        Pattern pattern = Pattern.compile(tabRule.getExpression());

        Optional<TabRule> existing = tabRuleRepository.findOneByNameAndExpression(tabRule.getName(), tabRule.getExpression());
        if (existing.isPresent() && !Objects.equals(tabRule.getId(), existing.get().getId())) {
            throw new IllegalStateException("Tab Rule already present! " + tabRule + " vs " + existing.get());
        }

        tabRuleRepository.save(tabRule);
        documentService.invalidateTabs();
    }

    public void parseTabsFor(Document document) {

        document.setTabsParsed(true);
        Collection<TabRule> tabRules = getAllTabRules();

        List<TabRule> matchingRules = tabRules.stream()
                .filter(r -> {
                    boolean match = r.isMatch(document);
                    if (match) {
                        r.setLatestMatch(Timestamp.from(Instant.now()));
                    }
                    return match;
                })
                .collect(Collectors.toList());
        tabRuleRepository.save(matchingRules);

        Set<TabRule> distinctMatchingRules = matchingRules.stream()
                .collect(Collectors.toSet());

        if (distinctMatchingRules.isEmpty()) {
            distinctMatchingRules.add(new TabRule(UNMATCHED_TAB, "dummy", false));
        }

        document.setHidden(distinctMatchingRules.stream().anyMatch(TabRule::isHideDocument));
        document.getTabs().clear();
        document.getTabs().addAll(distinctMatchingRules.stream().map(TabRule::getName).collect(Collectors.toSet()));
        documentService.put(document);
    }

    public List<TabRule> getAllTabRules() {
        return Lists.newArrayList(tabRuleRepository.findAll());
    }

    public Optional<TabRule> findTabRule(Long id) {
        TabRule tabRule = tabRuleRepository.findOne(id);
        return Optional.ofNullable(tabRule);
    }

    public void deleteTabRule(long id) {
        tabRuleRepository.delete(id);
    }
}
