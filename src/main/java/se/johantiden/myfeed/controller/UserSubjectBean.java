package se.johantiden.myfeed.controller;

import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.UserSubject;
import se.johantiden.myfeed.service.DocumentService;

import java.time.Instant;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSubjectBean {

    public final Set<DocumentBean> userDocuments;
    public final String title;
    public final String tab;

    public UserSubjectBean(UserSubject userSubject, DocumentService documentService) {
        userDocuments = userSubject.getUserDocuments().stream()
                .filter(UserDocument::isUnread)
                .map(ud -> new DocumentBean(ud, documentService.find(ud.getDocumentKey()).orElse(null)))
                .collect(Collectors.toSet());
        title = userSubject.getSubject().getTitle();
        tab = userSubject.getSubject().getTab();
    }

    public Set<DocumentBean> getUserDocuments() {
        return userDocuments;
    }

    public String getTitle() {
        return title;
    }

    public String getTab() {
        return tab;
    }

    public Instant getLatestPublishedDate() {
        return userDocuments.stream()
               .map(DocumentBean::getPublishedDate)
               .max(Comparator.<Instant>naturalOrder()).get();
    }
}
