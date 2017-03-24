package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

public class UserDocument implements Persistable<UserDocument> {

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

    @Override
    public Key<UserDocument> getKey() {
        return Keys.userDocument(user.getKey(), document.getKey());
    }
}
