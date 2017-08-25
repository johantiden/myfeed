package se.johantiden.myfeed.controller;

import java.util.Objects;

public class TabRuleBean {

    public final long id;
    public final String name;
    public final String expression;
    public final boolean hideDocument;

    public TabRuleBean(long id, String name, String expression, boolean hideDocument) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.expression = Objects.requireNonNull(expression);
        this.hideDocument = hideDocument;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getExpression() {
        return expression;
    }

    public boolean isHideDocument() {
        return hideDocument;
    }
}
