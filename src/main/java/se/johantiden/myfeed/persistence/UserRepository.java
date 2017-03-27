package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static se.johantiden.myfeed.persistence.Document.hasCategory;

public class UserRepository {

    private final Map<Key<User>, User> users = new HashMap<>();


    public UserRepository() {
        put(jocke());
        put(johan());
    }

    public void put(User user) {
        users.put(user.getKey(), user);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUser(Key<User> userKey) {
        return users.get(userKey);
    }




    public static User johan() {

        User user = new User(Keys.user("johan"), "johan");

        johanFilters(user);

        return user;
    }

    public static User jocke() {

        User user = new User(Keys.user("jocke"), "jocke");

        return user;
    }

    private static void johanFilters(User user) {

        Predicate<Document> notResor = hasCategory("resor").negate();
        Predicate<Document> notWebbTv = hasCategory("webb-tv").negate();
        Predicate<Document> notKultur = hasCategory("kultur").negate();
        Predicate<Document> notSvdMatOchDryck = hasCategory("mat &#38; dryck").negate();
        Predicate<Document> notDnBok = hasCategory("dnbok").negate();


        Predicate<Document> notZlatan = freeSearch(s -> {
            boolean isZlatan = s.contains("zlatan");
            boolean isIbrahimovic = s.contains("ibrahimovic");
            return !isZlatan && !isIbrahimovic;
        });
        Predicate<Document> notTrump = freeSearch(s -> {
            boolean trump = s.contains("trump");
            return !trump;
        });
        Predicate<Document> notDnMedanDuSov = freeSearch(s -> {
            boolean medanDuSov = s.contains("dn.se") && s.contains("medan du sov");
            return !medanDuSov;
        });

        Predicate<Document> notSport = freeSearch(s -> {
            boolean sport = s.contains("categories[0].name=sport") || s.contains("nhl") || s.contains("premier league")
                    || s.contains("allsvenskan") || s.contains("dn.se/sport");
            return !sport;
        });

        user.setUserGlobalFilter(new Filter(Lists.<Predicate<Document>>newArrayList(
                notKultur, notZlatan, notTrump, notDnMedanDuSov, notSvdMatOchDryck, notSport, notResor, notWebbTv, notDnBok)));
    }



    private static Predicate<Document> freeSearch(Predicate<String> searchPredicate) {
        return e -> {
            String document = e.fullSourceEntryForSearch.toLowerCase();
            return searchPredicate.test(document);
        };
    }

}
