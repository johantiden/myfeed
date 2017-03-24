package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class User implements Persistable<User> {

    private final List<UserDocument> documents;
    private final List<FeedUser> feedsForUser;
    private final Key<User> key;
    private Filter userGlobalFilter;

    public User(Key<User> key) {
        this.key = key;
        this.documents = new ArrayList<>();
        this.feedsForUser = new ArrayList<>();
    }

    public Filter getUserGlobalFilter() {
        return userGlobalFilter;
    }

    public List<FeedUser> getFeedsForUser() {
        return feedsForUser;
    }

    public void setUserGlobalFilter(Filter userGlobalFilter) {
        this.userGlobalFilter = Objects.requireNonNull(userGlobalFilter);
    }

    public static User johan() {

        User user = new User(Keys.user("johan"));

        johanFilters(user);

        return user;
    }

    private static void johanFilters(User user) {

        Predicate<Document> notKultur = filter(s -> {
            boolean kultur = s.contains("kultur");
            boolean svd = s.contains("svd.se");
            boolean svdKultur = svd && kultur;
            return !svdKultur;
        });
        Predicate<Document> notZlatan = filter(s -> {
            boolean isZlatan = s.contains("zlatan");
            boolean isIbrahimovic = s.contains("ibrahimovic");
            return !isZlatan && !isIbrahimovic;
        });
        Predicate<Document> notTrump = filter(s -> {
            boolean trump = s.contains("trump");
            return !trump;
        });
        user.setUserGlobalFilter(new Filter(Lists.newArrayList(notKultur, notZlatan, notTrump)));
    }

    private static Predicate<Document> filter(Predicate<String> searchPredicate) {
        return e -> {
            String document = e.fullSourceEntryForSearch.toLowerCase();
            return searchPredicate.test(document);
        };
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
