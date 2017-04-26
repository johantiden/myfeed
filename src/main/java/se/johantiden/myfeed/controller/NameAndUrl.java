package se.johantiden.myfeed.controller;

import java.io.Serializable;

public class NameAndUrl implements Serializable {
    private static final long serialVersionUID = -1797226876013074980L;

    public final String name;
    public final String url;

    public NameAndUrl(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public final String getName() {
        return name;
    }

    public final String getUrl() {
        return url;
    }
}
