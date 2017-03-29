package se.johantiden.myfeed.persistence.user.hack;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Filter;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

import java.util.function.Predicate;

import static se.johantiden.myfeed.persistence.Document.hasCategory;
import static se.johantiden.myfeed.util.JPredicates.*;

public class Jocke extends User {
    public Jocke() {
        super(Keys.user("jocke"), "jocke");

        setUserGlobalFilter(jockeFilter());
    }

    public static Filter jockeFilter() {

        Predicate<Document> notZlatan = freeSearch(s -> {
            boolean isZlatan = s.contains("zlatan");
            boolean isIbrahimovic = s.contains("ibrahimovic");
            return !isZlatan && !isIbrahimovic;
        });

        Predicate<Document> notSport = freeSearch(s -> {
            boolean sport = s.contains("nhl") || s.contains("premier league")
                    || s.contains("allsvenskan");
            return !sport;
        });

        return new Filter(Lists.<Predicate<Document>>newArrayList(
                hasCategory("sport").negate(),
                hasCategory("kultur").negate(),
                hasCategory("mat &#38; dryck").negate(),
                hasCategory("resor").negate(),
                hasCategory("webb-tv").negate(),
                hasCategory("dnbok").negate(),
                hasCategory("familj").negate(),
                and(isFrom("reddit"), hasCategory("gaming")).negate(),
                and(isFrom("svenska dagbladet"), hasCategory("perfect guide")).negate(),
                notZlatan,
                notSport,
                freeSearch(s -> !(s.contains("dn.se") && s.contains("medan du sov"))),
                freeSearch(s -> !s.contains("trump"))
        ));
    }

    private static Predicate<Document> isFrom(String feedName) {
        return document -> document.feedName.toLowerCase().equals(feedName);
    }


    private static Predicate<Document> freeSearch(Predicate<String> searchPredicate) {
        return e -> {
            String mergedString = "";
            mergedString += e.authorName;
            mergedString += e.categoryName;
            mergedString += e.cssClass;
            mergedString += e.feedName;
            mergedString += e.html;
            mergedString += e.text;
            mergedString += e.title;
            mergedString = mergedString.toLowerCase();
            return searchPredicate.test(mergedString);
        };
    }

}
