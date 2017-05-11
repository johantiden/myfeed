package se.johantiden.myfeed.util;

import se.johantiden.myfeed.persistence.Document;

import java.util.function.Predicate;

public class DocumentPredicates {

    public static Predicate<Document> has(String string) {
        String lowerCase = string.toLowerCase();
        if (!lowerCase.equals(string)) {
            throw new IllegalArgumentException("Input must be lower case only!");
        }
        return d -> d.text != null && d.text.toLowerCase().contains(string) ||
                            d.title != null && d.title.toLowerCase().contains(string) ||
                            d.categories.stream().anyMatch(c -> c.name.toLowerCase().contains(string));
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
        return d -> d.text != null && d.text.toLowerCase().startsWith(string) ||
                            d.title != null && d.title.toLowerCase().startsWith(string);
    }

    public static Predicate<Document> anyCategoryEquals(String string) {
        if (!string.toLowerCase().equals(string)) {
            throw new IllegalArgumentException("Input must be lower case only!");
        }
        return d -> d.categories.stream().anyMatch(c -> c.name.equalsIgnoreCase(string));
    }

    public static Predicate<Document> isFromFeed(String feedName) {
        return d -> d.feed.name.equals(feedName);
    }

    public static Predicate<Document> hasEscapeCharacters() {
        return has("&quot;").or(has("&#")).or(has("&amp;"));
    }
}
