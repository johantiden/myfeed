package se.johantiden.myfeed.controller;

import se.johantiden.myfeed.persistence.redis.Key;

import java.util.Objects;

public class SubjectBean {
    private final String title;
    private final String key;

    public SubjectBean(Subject subject) {
        Objects.requireNonNull(subject);
        this.title = subject.getTitle();
        this.key = subject.getKey().toString();
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }
}
