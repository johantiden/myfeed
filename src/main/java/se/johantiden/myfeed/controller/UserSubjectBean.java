package se.johantiden.myfeed.controller;

import java.time.Instant;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;

public class UserSubjectBean {

    public final Set<DocumentBean> userDocuments;
    public final Instant latestPublishedDate;

    public UserSubjectBean(Set<DocumentBean> userDocuments) {
        this.userDocuments = Objects.requireNonNull(userDocuments);
        this.latestPublishedDate = userDocuments.stream()
                .map(DocumentBean::getPublishedDate)
                .max(Comparator.<Instant>naturalOrder()).get();
    }

    public Set<DocumentBean> getUserDocuments() {
        return userDocuments;
    }

    public Instant getLatestPublishedDate() {
        return latestPublishedDate;
    }
}
