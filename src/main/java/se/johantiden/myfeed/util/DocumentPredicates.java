package se.johantiden.myfeed.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import se.johantiden.myfeed.persistence.Document;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class DocumentPredicates {

    public static Predicate<Document> has(String... strings) {
        return d -> Arrays.stream(strings).anyMatch(s -> has(s).test(d));
    }

    public static Predicate<Document> has(String string) {
        Preconditions.checkArgument(!string.contains("["), "This should not be a regex! Use the regex predicate instead!");
        Preconditions.checkArgument(!string.contains("]"), "This should not be a regex! Use the regex predicate instead!");
        Preconditions.checkArgument(!string.contains("|"), "This should not be a regex! Use the regex predicate instead!");
        Preconditions.checkArgument(!string.contains("*"), "This should not be a regex! Use the regex predicate instead!");
        Preconditions.checkArgument(!string.contains("\\"), "This should not be a regex! Use the regex predicate instead!");


        String string2 = string.toLowerCase();
        return d -> d.text != null && d.text.toLowerCase().contains(string2) ||
                    d.title != null && d.title.toLowerCase().contains(string2) ||
                    d.getPageUrl() != null && d.getPageUrl().toLowerCase().contains(string2) ||
                    d.getSubjects().stream().anyMatch(s -> s.getName().toLowerCase().contains(string2));
    }

    public static Predicate<Document> anyCategoryEquals(String... strings) {
        return d -> Arrays.stream(strings).anyMatch(s -> anyCategoryEquals(s).test(d));
    }

    public static Predicate<Document> isFromFeed(String feedName) {
        return d -> d.getFeedName().equals(feedName);
    }

    public static Predicate<Document> hasEscapeCharacters() {
        return has("&quot;").or(has("&#")).or(has("&amp;")).or(has("â€™"));
    }

    public static Predicate<Document> matches(Pattern pattern) {


        return d -> {

            String megaConcat =
                    ifPresent(d.getFeedName()) +
                    "subjects:" + d.getSubjects() + " " +
                    ifPresent(d.title) +
                    ifPresent(d.text) +
                    ifPresent(d.getPageUrl()) +
                    ifPresent(d.getFeedUrl());

            return pattern.matcher(megaConcat).find();
        };

    }

    private static String ifPresent(String text) {

        if (Strings.isNullOrEmpty(text)) {
            return "";
        } else {
            return text + " ";
        }

    }

    public static Predicate<Document> matches(String regex) {
        return matches(Pattern.compile(regex));
    }
}
