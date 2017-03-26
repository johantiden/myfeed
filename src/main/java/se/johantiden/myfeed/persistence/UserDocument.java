package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.time.Instant;
import java.util.Objects;

public class UserDocument implements Persistable<UserDocument> {

    private final Key<User> userKey;
    private final Key<Document> documentKey;
    private final Instant publishedDate;
    private boolean read;
    public String feedName;

    public UserDocument(Key<User> userKey, Document document) {

        this.userKey = Objects.requireNonNull(userKey);
        this.documentKey = Objects.requireNonNull(document).getKey();
        this.publishedDate = document.publishedDate;
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

    public Instant getPublishedDate() {
        return publishedDate;
    }
}
