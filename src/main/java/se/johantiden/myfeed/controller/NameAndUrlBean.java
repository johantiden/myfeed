package se.johantiden.myfeed.controller;

public class NameAndUrlBean {
    public final String name;
    public final String url;

    public NameAndUrlBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
