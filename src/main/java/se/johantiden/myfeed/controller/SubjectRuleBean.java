package se.johantiden.myfeed.controller;

import java.util.Objects;

public class SubjectRuleBean {

    public final long id;
    public final String name;
    public final String expression;
    public final long created;
    public final Long latestMatch;

    public SubjectRuleBean(long id, String name, String expression, long created, Long latestMatch) {
        this.id = id;
        this.latestMatch = latestMatch;
        this.name = Objects.requireNonNull(name);
        this.expression = Objects.requireNonNull(expression);
        this.created = Objects.requireNonNull(created);
    }
}
