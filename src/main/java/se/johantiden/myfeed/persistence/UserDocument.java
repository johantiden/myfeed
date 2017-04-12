package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

import java.time.Instant;
import java.util.Objects;

public class UserDocument implements Persistable<UserDocument> {

    private final Instant publishDate;
    private final Key<User> userKey;
    private final Key<Document> documentKey;
    private boolean read;

    public UserDocument(Key<User> userKey, Key<Document> documentKey, Instant publishDate) {
        this.publishDate = publishDate;
        this.userKey = Objects.requireNonNull(userKey);
        this.documentKey = Objects.requireNonNull(documentKey);
    }

    public Instant getPublishDate() {
        return publishDate;
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
        return Keys.userDocument(userKey, documentKey);
    }

    public Key<User> getUserKey() {
        return userKey;
    }

    public Key<Document> getDocumentKey() {
        return documentKey;
    }
}
