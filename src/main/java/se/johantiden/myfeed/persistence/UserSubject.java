package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.controller.Subject;

import java.util.Comparator;
import java.util.Set;

public class UserSubject {

    public static final Comparator<UserSubject> COMPARATOR =
            Comparator.comparing(UserSubject::size)
                    .thenComparing(UserSubject::getSubject, Subject.COMPARATOR);

    private final Subject subject;
    private final Set<UserDocument> userDocuments;

    public UserSubject(Subject subject, Set<UserDocument> userDocuments) {
        this.subject = subject;
        this.userDocuments = userDocuments;
    }

    public boolean hasUnread() {
        return userDocuments.stream().anyMatch(UserDocument::isUnread);
    }

    public Set<UserDocument> getUserDocuments() {
        return userDocuments;
    }

    public Subject getSubject() {
        return subject;
    }

    public int size() {
        return userDocuments.size();
    }
}
