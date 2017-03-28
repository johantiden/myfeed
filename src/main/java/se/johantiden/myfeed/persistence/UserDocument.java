package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.controller.NameAndUrlBean;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

import java.time.Instant;
import java.util.Objects;

public class UserDocument implements Persistable<UserDocument> {

    private final Key<User> userKey;
    private final Key<Document> documentKey;

    public final Instant publishedDate;
    public final NameAndUrlBean feedBean;
    public final NameAndUrlBean category;
    public final String title;
    public final String text;
    public final NameAndUrlBean author;
    public final String cssClass;
    public final String pageUrl;
    public final String imageUrl;
    public final String fullSourceEntryForSearch;
    public final String html;
    private boolean read;

    public UserDocument(Key<User> userKey, Document document) {

        this.userKey = Objects.requireNonNull(userKey);
        this.documentKey = Objects.requireNonNull(document).getKey();
        this.publishedDate = document.publishedDate;
        this.title = document.title;
        this.feedBean = new NameAndUrlBean(document.feedName, document.feedUrl);
        this.category = new NameAndUrlBean(document.categoryName, document.categoryUrl);
        this.author = new NameAndUrlBean(document.authorName, document.authorUrl);
        this.text = document.text;
        this.cssClass = document.cssClass;
        this.pageUrl = document.pageUrl;
        this.imageUrl = document.imageUrl;
        this.fullSourceEntryForSearch = document.fullSourceEntryForSearch;
        this.html = document.html;
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

    public Key<User> getUserKey() {
        return userKey;
    }

    public Key<Document> getDocumentKey() {
        return documentKey;
    }
}
