package se.johantiden.myfeed.controller;

public class TabRulePutBean {

    public long id;
    public String name;
    public String expression;
    public boolean hideDocument;

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
