package se.johantiden.myfeed.util;

import se.johantiden.myfeed.persistence.Document;

import java.util.Arrays;
import java.util.function.Predicate;

public class DocumentPredicates {

    public static Predicate<Document> has(String... strings) {
        return d -> Arrays.stream(strings).anyMatch(s -> has(s).test(d));
    }

    public static Predicate<Document> has(String string) {
        String string2 = string.toLowerCase();
        return d -> d.text != null && d.text.toLowerCase().contains(string2) ||
                    d.title != null && d.title.toLowerCase().contains(string2) ||
                    d.pageUrl != null && d.pageUrl.toLowerCase().contains(string2) ||
                    d.categories.stream().anyMatch(c -> c.name.toLowerCase().contains(string2));
    }
    public static Predicate<Document> hasCaseSensitive(String... strings) {
        return d -> Arrays.stream(strings).anyMatch(s -> hasCaseSensitive(s).test(d));
    }

    public static Predicate<Document> hasCaseSensitive(String string) {
        return d -> d.text != null && d.text.contains(string) ||
                            d.title != null && d.title.contains(string) ||
                            d.categories.stream().anyMatch(c -> c.name.contains(string));
    }

    public static Predicate<Document> startsWithCaseSensitive(String string) {
        return d -> d.text != null && d.text.startsWith(string) ||
                            d.title != null && d.title.startsWith(string);
    }

    public static Predicate<Document> startsWith(String string) {
        String string2 = string.toLowerCase();
        return d -> d.text != null && d.text.toLowerCase().startsWith(string2) ||
                            d.title != null && d.title.toLowerCase().startsWith(string2);
    }

    public static Predicate<Document> anyCategoryEquals(String... strings) {
        return d -> Arrays.stream(strings).anyMatch(s -> anyCategoryEquals(s).test(d));
    }

    public static Predicate<Document> anyCategoryEquals(String string) {
        return d -> d.categories.stream().anyMatch(c -> c.name.equalsIgnoreCase(string));
    }

    public static Predicate<Document> anySubjectEquals(String string) {
        return d -> d.subjects.stream().anyMatch(subject -> subject.equalsIgnoreCase(string));
    }

    public static Predicate<Document> authorEquals(String string) {
        return d -> d.author != null && d.author.name.equals(string);
    }

    public static Predicate<Document> isFromFeed(String feedName) {
        return d -> d.feed.getName().equals(feedName);
    }

    public static Predicate<Document> feedNameStartsWith(String feedName) {
        return d -> d.feed.getName().startsWith(feedName);
    }

    public static Predicate<Document> hasEscapeCharacters() {
        return has("&quot;").or(has("&#")).or(has("&amp;")).or(has("â€™"));
    }
}
