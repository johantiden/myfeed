package se.johantiden.myfeed.persistence.user.hack;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Filter;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

import java.util.function.Predicate;

import static se.johantiden.myfeed.persistence.Document.categoryContains;
import static se.johantiden.myfeed.util.JPredicates.and;

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
            boolean sport = s.contains("nhl") || s.contains("premier league")
                    || s.contains("allsvenskan");
            return !sport;
        });

        return new Filter(Lists.<Predicate<Document>>newArrayList(
                isNotPaywalled(),
                categoryContains("sport").negate(),
                categoryContains("kultur").negate(),
                and(categoryContains("mat"), categoryContains("dryck")).negate(),
                and(categoryContains("mat"), categoryContains("vin")).negate(),
                categoryContains("resor").negate(),
                categoryContains("webb-tv").negate(),
                categoryContains("dnbok").negate(),
                categoryContains("familj").negate(),
                and(isFrom("reddit"), categoryContains("gaming")).negate(),
                and(isFrom("ars"), categoryContains("dealmaster")).negate(),
                and(isFrom("ars"), categoryContains("opposable thumbs")).negate(),
                and(isFrom("svenska dagbladet"), categoryContains("perfect guide")).negate(),
                notZlatan,
                notSport,
                freeSearch(s -> !s.contains("medan du sov")),
                freeSearch(s -> !s.contains("trump"))
        ));
    }

    private static Predicate<Document> isNotPaywalled() {
        return document -> !document.isPaywalled;
    }

    private static Predicate<Document> isFrom(String feedName) {
        return document -> document.feedName.toLowerCase().contains(feedName);
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
