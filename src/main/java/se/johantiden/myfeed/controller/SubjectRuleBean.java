package se.johantiden.myfeed.controller;

import java.util.Objects;

public class SubjectRuleBean {

    public final long id;
    public final String name;
    public final String expression;

    public SubjectRuleBean(long id, String name, String expression) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.expression = Objects.requireNonNull(expression);
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
}
