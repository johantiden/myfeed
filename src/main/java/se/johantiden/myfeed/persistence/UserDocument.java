package se.johantiden.myfeed.persistence;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = "UserDocument.getReadyUserDocumentIdsForUser",
            query = "SELECT ud.id FROM UserDocument ud WHERE" +
                    " ud.user.id = :userId AND" +
                    " ud.read = false AND" +
                    " ud.document.subjectsParsed = true AND" +
                    " ud.document.tabsParsed = true"),
    @NamedQuery(name = "UserDocument.findAllRead",
            query = "SELECT ud FROM UserDocument ud WHERE" +
                    " ud.read = true")
})
public class UserDocument extends BaseEntity {

    @ManyToOne(targetEntity = User.class)
    private final User user;
    @ManyToOne(targetEntity = Document.class)
    private final Document document;
    private boolean read;

    // JPA
    protected UserDocument() {
        user = null;
        document = null;
    }

    public UserDocument(User user, Document document) {
        this.user = Objects.requireNonNull(user);
        this.document = Objects.requireNonNull(document);
    }

    public final void setRead(boolean read) {
        this.read = read;
    }

    public final boolean isRead() {
        return read;
    }

    public final boolean isUnread() {
        return !read;
    }

    public User getUser() {
        return user;
    }

    public Document getDocument() {
        return document;
    }
}
