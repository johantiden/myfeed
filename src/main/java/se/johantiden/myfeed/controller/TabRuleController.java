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
import se.johantiden.myfeed.persistence.TabRule;
import se.johantiden.myfeed.service.TabService;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class TabRuleController {

    private static final Logger log = LoggerFactory.getLogger(TabRuleController.class);
    @Autowired
    private TabService tabService;

    @RequestMapping("/rest/tabRules")
    public Collection<TabRuleBean> getTabRules() {

        List<TabRule> tabRules = tabService.getAllTabRules();

        return tabRules.stream().map(TabRuleController::toBean).collect(Collectors.toList());
    }

    @RequestMapping(value = "/rest/tabRules/{tabRuleId}", method = RequestMethod.DELETE)
    public void deleteTabRule(@PathVariable("tabRuleId") Long id) {

        Optional<TabRule> tabRuleOptional = tabService.findTabRule(id);

        if (!tabRuleOptional.isPresent()) {
            throw new NotFound404("Could not find TabRule with id " + id);
        }

        log.info("Deleting " + tabRuleOptional.get());
        tabService.deleteTabRule(id);
    }

    @RequestMapping(value = "/rest/tabRules", method = RequestMethod.PUT)
    public TabRuleBean putTabRule(@RequestBody TabRulePutBean tabRuleBean) {


        String name = tabRuleBean.getName();
        String expression = tabRuleBean.getExpression();
        long id = tabRuleBean.getId();
        boolean hideDocument = tabRuleBean.isHideDocument();

        TabRule tabRule;
        if (id > 0) {
            Optional<TabRule> tabRuleOptional = Optional.ofNullable(id)
                    .flatMap(tabService::findTabRule);

            if (!tabRuleOptional.isPresent()) {
                throw new NotFound404("Could not find TabRule with id " + id);
            }

            tabRule = tabRuleOptional.get();
            tabRule.setName(name);
            tabRule.setExpression(expression);
            tabRule.setHideDocument(hideDocument);
        } else {
            log.info("New tab rule: {}, {}", name, expression);
            tabRule = new TabRule(name, expression, hideDocument);
        }

        tabService.put(tabRule);
        return toBean(tabRule);

    }

    private static TabRuleBean toBean(TabRule tabRule) {
        Objects.requireNonNull(tabRule);
        Objects.requireNonNull(tabRule.getId());
        return new TabRuleBean(tabRule.getId(), tabRule.getName(), tabRule.getExpression(), tabRule.isHideDocument(), tabRule.getCreated().toInstant().toEpochMilli());
    }
}
