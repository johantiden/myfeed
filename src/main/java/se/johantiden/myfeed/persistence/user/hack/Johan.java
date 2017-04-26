package se.johantiden.myfeed.persistence.user.hack;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.util.JCollections;

import java.util.ArrayList;
import java.util.function.Predicate;

public class Johan extends User {
    public Johan() {
        super(Keys.user("johan"), "johan", johanFilter());
    }

    public static Predicate<Document> johanFilter() {
        ArrayList<Predicate<Document>> predicates = Lists.<Predicate<Document>>newArrayList(
                isNotPaywalled());

        return JCollections.reduce(predicates, Predicate::and, d->true);
    }
    private static Predicate<Document> isNotPaywalled() {
        return document -> !document.isPaywalled;
    }

}
