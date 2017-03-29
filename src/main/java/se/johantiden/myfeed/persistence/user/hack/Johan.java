package se.johantiden.myfeed.persistence.user.hack;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Filter;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

import java.util.function.Predicate;

import static se.johantiden.myfeed.persistence.Document.hasCategory;

public class Johan extends User {
    public Johan() {
        super(Keys.user("johan"), "johan");

        setUserGlobalFilter(johanFilter());
    }




    public static Filter johanFilter() {

        Predicate<Document> notZlatan = freeSearch(s -> {
            boolean isZlatan = s.contains("zlatan");
            boolean isIbrahimovic = s.contains("ibrahimovic");
            return !isZlatan && !isIbrahimovic;
        });

        Predicate<Document> notSport = freeSearch(s -> {
            boolean sport = s.contains("categories[0].name=sport") || s.contains("nhl") || s.contains("premier league")
                    || s.contains("allsvenskan") || s.contains("dn.se/sport");
            return !sport;
        });

        return new Filter(Lists.<Predicate<Document>>newArrayList(
                notSport,
                notZlatan,
                hasCategory("kultur").negate(),
                hasCategory("mat &#38; dryck").negate(),
                hasCategory("resor").negate(),
                hasCategory("webb-tv").negate(),
                hasCategory("dnbok").negate(),
                hasCategory("familj").negate(),
                freeSearch(s -> !(s.contains("dn.se") && s.contains("medan du sov"))),
                freeSearch(s -> !s.contains("trump"))
        ));
    }


    private static Predicate<Document> freeSearch(Predicate<String> searchPredicate) {
        return e -> {
            String document = e.fullSourceEntryForSearch.toLowerCase();
            return searchPredicate.test(document);
        };
    }

}
