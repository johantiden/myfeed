package se.johantiden.myfeed.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentClassifier;
import se.johantiden.myfeed.persistence.TabRule;
import se.johantiden.myfeed.persistence.TabRuleRepository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TabService {

    @Autowired
    TabRuleRepository tabRuleRepository;

    @Autowired
    DocumentService documentService;

    public static Set<TabRule> getDefaultTabRules() {
        Set<TabRule> tabs = new HashSet<>();

        tabs.add(new TabRule(DocumentClassifier.ERROR, "&#", false));
        tabs.add(new TabRule(DocumentClassifier.ERROR, ";", false));

        tabs.add(new TabRule(DocumentClassifier.VÄDER, DocumentClassifier.VÄDER, false));

        tabs.add(new TabRule(DocumentClassifier.SPORT, DocumentClassifier.SPORT, false));

        tabs.add(new TabRule(DocumentClassifier.CULTURE, "kultur", false));
        tabs.add(new TabRule(DocumentClassifier.CULTURE, "kultur-noje", false));
        tabs.add(new TabRule(DocumentClassifier.CULTURE, "film", false));
        tabs.add(new TabRule(DocumentClassifier.CULTURE, "movies", false));
        tabs.add(new TabRule(DocumentClassifier.CULTURE, "dnbok", false));

        tabs.add(new TabRule(DocumentClassifier.NEWS, "Al Jazeera.*news", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Al Jazeera.*insidestory", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Al Jazeera.*opinion", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Al Jazeera.*features", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Al Jazeera.*indepth", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Al Jazeera.*programmes", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "SVT Nyheter.*nyheter", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Dagens Nyheter.*nyheter", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "TheLocal", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Los Angeles Times - World", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Washington Post - The Fact Checker", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Washington Post - WorldViews", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "worldnews", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "New York Times - World", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Reddit.*politics", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Reddit.*news", false));
        tabs.add(new TabRule(DocumentClassifier.NEWS, "Reddit.*worldnews", false));

        tabs.add(new TabRule(DocumentClassifier.TECH, "Slashdot", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "HackerNews", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Engadget", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "science", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "ProgrammerHumor", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Ars Technica.*tech-policy", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Ars Technica.*gadgets", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Ars Technica.*gaming", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Ars Technica.*facebook", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Ars Technica.*opposable thumbs", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Ars Technica.*the-multiverse", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Ars Technica.*Technology Lab", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Ars Technica.*Ministry of Innovation", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, "Reddit.*technology", false));
        tabs.add(new TabRule(DocumentClassifier.TECH, DocumentClassifier.NETFLIX, false));
        tabs.add(new TabRule(DocumentClassifier.TECH, DocumentClassifier.SPOTIFY, false));
        tabs.add(new TabRule(DocumentClassifier.TECH, DocumentClassifier.IT_SÄKERHET, false));
        tabs.add(new TabRule(DocumentClassifier.TECH, DocumentClassifier.FORSKNING, false));

        tabs.add(new TabRule(DocumentClassifier.FUN, "AskReddit", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*gaming", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*pics", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*gifs", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*funny", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*PoliticalHumor", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*mildlyinteresting", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*Design", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*aww", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*sports", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*music", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*videos", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*todayilearned", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*NatureIsFuckingLit", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*nottheonion", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*MarchAgainstTrump", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*Showerthoughts", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*photoshopbattles", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*oddlysatisfying", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*space", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*mildlyinfuriating", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "Reddit.*TrumpCriticizesTrump", false));
        tabs.add(new TabRule(DocumentClassifier.FUN, "xkcd", false));

        tabs.add(new TabRule(DocumentClassifier.BIZ, "näringsliv", false));
        tabs.add(new TabRule(DocumentClassifier.BIZ, "investerar", false));
        tabs.add(new TabRule(DocumentClassifier.BIZ, "börsraket", false));
        tabs.add(new TabRule(DocumentClassifier.BIZ, "New York Times - World.*business", false));
        tabs.add(new TabRule(DocumentClassifier.BIZ, "Slashdot.*business", false));
        tabs.add(new TabRule(DocumentClassifier.BIZ, DocumentClassifier.EKONOMI, false));

        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.HIRING, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.WEBB_TV, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.SERIER, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.CARS, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.DEALMASTER, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.LEAGUEOFLEGENDS, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.IDAGSIDAN, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.MAT, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.NEWS_GRID, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.FRÅGESPORT, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.JUNIOR, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.PERFECT_GUIDE, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.I_AM_A, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.BLACK_PEOPLE_TWITTER, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.THE_DENNIS, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.NUMBER_OF_PEOPLE, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.NUTIDSTESTET, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.RESOR, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, DocumentClassifier.UUTISET, false));
        tabs.add(new TabRule(DocumentClassifier.BAD, "interestingasfuck", false));
        tabs.add(new TabRule(DocumentClassifier.BAD, "Jokes", false));
        tabs.add(new TabRule(DocumentClassifier.BAD, "OldSchoolCool", false));
        tabs.add(new TabRule(DocumentClassifier.BAD, "GetMotivated", false));

        return tabs;
    }

    @PostConstruct
    public void postConstruct() {
        boolean isEmpty = !tabRuleRepository.findAll().iterator().hasNext();
        if (isEmpty) {
            hackCreateDefaultSubjectRules();
        }
    }

    private void hackCreateDefaultSubjectRules() {

        Collection<TabRule> defaultRules = getDefaultTabRules();

        defaultRules.forEach(this::put);
    }

    public void put(TabRule tabRule) {
        Pattern pattern = Pattern.compile(tabRule.getExpression());

        Optional<TabRule> existing = tabRuleRepository.findOneByNameAndExpression(tabRule.getName(), tabRule.getExpression());
        if (existing.isPresent() && !Objects.equals(tabRule.getId(), existing.get().getId())) {
            throw new IllegalStateException("Tab Rule already present! " + tabRule + " vs " + existing.get());
        }

        tabRuleRepository.save(tabRule);

        documentService.invalidateSubjects();
    }

    public void parseTabsFor(Document document) {

        document.setTabsParsed(true);
        Collection<TabRule> tabRules = getAllTabRules();
        Set<TabRule> matchingTabs = tabRules.stream()
                .filter(r -> r.isMatch(document))
                .collect(Collectors.toSet());

        if (matchingTabs.isEmpty()) {
            matchingTabs.add(new TabRule(DocumentClassifier.UNMATCHED_TAB, "dummy", false));
        }

        document.setHidden(matchingTabs.stream().anyMatch(TabRule::isHideDocument));
        document.getTabs().clear();
        document.getTabs().addAll(matchingTabs.stream().map(TabRule::getName).collect(Collectors.toSet()));
        documentService.put(document);
    }

    public List<TabRule> getAllTabRules() {
        return Lists.newArrayList(tabRuleRepository.findAll());
    }

    public Optional<TabRule> findTabRule(Long id) {
        TabRule subjectRule = tabRuleRepository.findOne(id);
        return Optional.ofNullable(subjectRule);
    }
}
