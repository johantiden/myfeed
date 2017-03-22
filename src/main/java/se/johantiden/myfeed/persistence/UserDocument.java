package se.johantiden.myfeed.persistence;

public class UserDocument {

    private final User user;
    private final Document document;
    private boolean read;

    public UserDocument(User user, Document document) {
        this.user = user;
        this.document = document;
    }

    public User getUser() {
        return user;
    }

    public Document getDocument() {
        return document;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isUnread() {
        return !read;
    }
}
