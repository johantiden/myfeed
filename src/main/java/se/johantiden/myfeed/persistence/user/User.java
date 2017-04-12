package se.johantiden.myfeed.persistence.user;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.FeedUser;
import se.johantiden.myfeed.persistence.Persistable;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.redis.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class User implements Persistable<User> {

    private final List<UserDocument> documents;
    private final List<FeedUser> feedsForUser;
    private final Key<User> key;
    private final String username;
    private final Predicate<Document> userGlobalFilter;

    public User(Key<User> key, String username, Predicate<Document> userGlobalFilter) {
        this.key = key;
        this.username = username;
        this.userGlobalFilter = userGlobalFilter;
        this.documents = new ArrayList<>();
        this.feedsForUser = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public Predicate<Document> getUserGlobalFilter() {
        return userGlobalFilter;
    }

    public List<FeedUser> getFeedsForUser() {
        return feedsForUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!documents.equals(user.documents)) {
            return false;
        }
        if (!feedsForUser.equals(user.feedsForUser)) {
            return false;
        }
        if (!key.equals(user.key)) {
            return false;
        }
        return userGlobalFilter.equals(user.userGlobalFilter);

    }

    @Override
    public int hashCode() {
        int result = documents.hashCode();
        result = 31 * result + feedsForUser.hashCode();
        result = 31 * result + key.hashCode();
        result = 31 * result + userGlobalFilter.hashCode();
        return result;
    }

    @Override
    public Key<User> getKey() {
        return key;
    }
}
