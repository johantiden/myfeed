package se.johantiden.myfeed.controller;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

public class Subject implements Serializable {

    private static final long serialVersionUID = -3724512594914225106L;
    private final String title;

    public Subject(String title) {
        this.title = Objects.requireNonNull(title);
    }

    public String getTitle() {
        return title;
    }

    public Key<Subject> getKey() {
        return Keys.subject(title);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) { return true; }
        if(o == null || getClass() != o.getClass()) { return false; }

        Subject subject = (Subject) o;

        return !(title != null ? !title.equals(subject.title) : subject.title != null);

    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
