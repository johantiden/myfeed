package se.johantiden.myfeed.controller;

import java.util.Objects;

public class TabRuleBean {

    public final long id;
    public final String name;
    public final String expression;
    public final boolean hideDocument;
    public final long created;
    public final Long latestMatch;

    public TabRuleBean(long id, String name, String expression, boolean hideDocument, long created, Long latestMatch) {
        this.id = id;
        this.latestMatch = latestMatch;
        this.name = Objects.requireNonNull(name);
        this.expression = Objects.requireNonNull(expression);
        this.hideDocument = hideDocument;
        this.created = Objects.requireNonNull(created);
    }
}
