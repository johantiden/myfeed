package se.johantiden.myfeed.controller;

public class NameAndUrl {
    public final String name;
    public final String url;

    public NameAndUrl(String name, String url) {
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
