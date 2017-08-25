package se.johantiden.myfeed.controller;

import java.util.Objects;

public class SubjectRuleBean {

    public final long id;
    public final String name;
    public final String expression;
    public final long created;

    public SubjectRuleBean(long id, String name, String expression, long created) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.expression = Objects.requireNonNull(expression);
        this.created = Objects.requireNonNull(created);
    }
}
