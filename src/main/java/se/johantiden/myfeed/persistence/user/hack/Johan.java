package se.johantiden.myfeed.persistence.user.hack;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.util.JCollections;

import java.util.ArrayList;
import java.util.function.Predicate;

import static se.johantiden.myfeed.persistence.Document.categoryContains;

public class Johan extends User {
    public Johan() {
        super(Keys.user("johan"), "johan", johanFilter(), flagFilter());
    }




    public static Predicate<Document> johanFilter() {

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

        ArrayList<Predicate<Document>> predicates = Lists.<Predicate<Document>>newArrayList(
                isNotPaywalled(),
                categoryContains("sport").negate(),
                categoryContains("kultur").negate(),
                categoryContains("mat").and(categoryContains("dryck")).negate(),
                categoryContains("mat").and(categoryContains("vin")).negate(),
                categoryContains("resor").negate(),
                categoryContains("webb-tv").negate(),
                categoryContains("dnbok").negate(),
                categoryContains("familj").negate(),
                isFrom("reddit").and(categoryContains("iama")).negate(),
                isFrom("reddit").and(categoryContains("wholesomememes")).negate(),
                isFrom("reddit").and(categoryContains("art")).negate(),
                isFrom("reddit").and(categoryContains("aww")).negate(),
                isFrom("reddit").and(categoryContains("gaming")).negate(),
                isFrom("ars").and(categoryContains("dealmaster")).negate(),
                isFrom("ars").and(categoryContains("opposable thumbs")).negate(),
                isFrom("svenska dagbladet").and(categoryContains("perfect guide")).negate(),
                notZlatan,
                notSport,
                freeSearch(s -> !s.contains("medan du sov")),
                freeSearch(s -> !s.contains("trump")));

        return JCollections.reduce(predicates, Predicate::and, d->true);
    }


    /**
     * Anything that is "flagged" will still show up in the feed (unless it is also killed by the main filter), but it
     * will be shown as flagged so that the user can quickly realise that it might be good to skip it.
     *
     * Add stuff here that might be too ambiguous to put in a hard filter, or use it to test out future filters.
     *
     * @return
     */
    public static Predicate<Document> flagFilter() {
        ArrayList<Predicate<Document>> clickBaityPhrases = Lists.<Predicate<Document>>newArrayList(
                freeSearch(s -> s.contains("bästa")),
                freeSearch(s -> s.contains("tipsen")),
                freeSearch(s -> s.contains("här är"))
        );
        return JCollections.reduce(clickBaityPhrases, Predicate::or, d->false);
    }

    private static Predicate<Document> isNotPaywalled() {
        return document -> !document.isPaywalled;
    }

    private static Predicate<Document> isFrom(String feedName) {
        return document -> document.feedName.toLowerCase().contains(feedName);
    }

    private static Predicate<Document> freeSearch(Predicate<String> searchPredicate) {
        return document -> {
            String mergedString = "";
            mergedString += document.authorName;
            mergedString += document.categoryName;
            mergedString += document.cssClass;
            mergedString += document.feedName;
            mergedString += document.html;
            mergedString += document.text;
            mergedString += document.title;
            mergedString = mergedString.toLowerCase();
            return searchPredicate.test(mergedString);
        };
    }
}