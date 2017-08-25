package se.johantiden.myfeed.controller;

import java.util.Objects;

public class TabRuleBean {

    public final long id;
    public final String name;
    public final String expression;
    public final boolean hideDocument;
    public final long created;

    public TabRuleBean(long id, String name, String expression, boolean hideDocument, long created) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.expression = Objects.requireNonNull(expression);
        this.hideDocument = hideDocument;
        this.created = Objects.requireNonNull(created);
    }
}
