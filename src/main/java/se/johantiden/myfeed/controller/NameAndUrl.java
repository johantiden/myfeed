package se.johantiden.myfeed.controller;

import java.io.Serializable;

public class NameAndUrl implements Serializable {
    private static final long serialVersionUID = -1797226876013074980L;

    public final String name;
    public final String url;

    public NameAndUrl(String name, String url) {
        verifyUrl(url);
        this.name = name;
        this.url = url;
    }

    private void verifyUrl(String url) {
        if (url.contains("google")) {
            throw new IllegalArgumentException("Google? Maybe there is a google analytics link?");
        }
    }

    public final String getName() {
        return name;
    }

    public final String getUrl() {
        return url;
    }
}
