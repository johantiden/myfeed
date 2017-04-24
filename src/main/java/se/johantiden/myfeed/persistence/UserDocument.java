package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class UserDocument implements Persistable<UserDocument>, Serializable {

    private static final long serialVersionUID = 3545729809583935357L;

    private final Instant publishDate;
    private final Key<User> userKey;
    private final Key<Document> documentKey;
    private boolean read;

    public UserDocument(Key<User> userKey, Key<Document> documentKey, Instant publishDate) {
        this.publishDate = publishDate;
        this.userKey = Objects.requireNonNull(userKey);
        this.documentKey = Objects.requireNonNull(documentKey);
    }

    public final Instant getPublishDate() {
        return publishDate;
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

    @Override
    public final Key<UserDocument> getKey() {
        return Keys.userDocument(userKey, documentKey);
    }

    public final Key<User> getUserKey() {
        return userKey;
    }

    public final Key<Document> getDocumentKey() {
        return documentKey;
    }
}
