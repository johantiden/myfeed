package se.johantiden.myfeed.controller;

import java.util.Objects;

public class SubjectRuleBean {

    public final String name;
    public final String expression;

    public SubjectRuleBean(String name, String expression) {
        this.name = Objects.requireNonNull(name);
        this.expression = Objects.requireNonNull(expression);
    }

    public String getName() {
        return name;
    }

    public String getExpression() {
        return expression;
    }
}
