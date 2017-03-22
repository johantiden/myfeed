package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class User {

    private final long id;
    private final List<UserDocument> documents;
    private final List<FeedUser> feedsForUser;
    private Filter userGlobalFilter;

    public User(long id) {
        this.id = id;
        this.documents = new ArrayList<>();
        this.feedsForUser = new ArrayList<>();
    }

    public List<UserDocument> getDocuments() {
        return documents;
    }

    public List<UserDocument> getUnreadEntries() {
        return documents.stream().filter(UserDocument::isUnread).collect(Collectors.toList());
    }

    public Filter getUserGlobalFilter() {
        return userGlobalFilter;
    }

    public List<FeedUser> getFeedsForUser() {
        return feedsForUser;
    }

    public void setUserGlobalFilter(Filter userGlobalFilter) {
        this.userGlobalFilter = userGlobalFilter;
    }

    public static User johan() {

        User user = new User(1337);

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

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ id >>> 32);
    }
}
