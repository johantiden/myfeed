package se.johantiden.myfeed.util;

import com.google.common.base.Strings;
import se.johantiden.myfeed.persistence.Document;

import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class DocumentPredicates {

    public static Predicate<Document> has(String... strings) {
        return d -> Arrays.stream(strings).anyMatch(s -> has(s).test(d));
    }

    public static Predicate<Document> has(String string) {
        String string2 = string.toLowerCase();
        return d -> d.text != null && d.text.toLowerCase().contains(string2) ||
                    d.title != null && d.title.toLowerCase().contains(string2) ||
                    d.pageUrl != null && d.pageUrl.toLowerCase().contains(string2) ||
                    d.getSubjects().stream().anyMatch(s -> s.toLowerCase().contains(string2)) ||
                    d.getSourceCategories().stream().anyMatch(s -> s.toLowerCase().contains(string2));
    }
    public static Predicate<Document> hasCaseSensitive(String... strings) {
        return d -> Arrays.stream(strings).anyMatch(s -> hasCaseSensitive(s).test(d));
    }

    public static Predicate<Document> hasCaseSensitive(String string) {
        return d -> d.text != null && d.text.contains(string) ||
                            d.title != null && d.title.contains(string) ||
                            d.getSubjects().stream().anyMatch(s -> s.contains(string)) ||
                            d.getSourceCategories().stream().anyMatch(s -> s.contains(string));
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
        return d -> d.getSourceCategories().stream().anyMatch(s -> s.equalsIgnoreCase(string));
    }

    public static Predicate<Document> anySubjectEquals(String string) {
        return d -> d.getSubjects().stream().anyMatch(subject -> subject.equalsIgnoreCase(string));
    }

    public static Predicate<Document> authorEquals(String string) {
        return d -> d.author != null && d.author.name.equals(string);
    }

    public static Predicate<Document> isFromFeed(String feedName) {
        return d -> d.getFeedName().equals(feedName);
    }

    public static Predicate<Document> feedNameStartsWith(String feedName) {
        return d -> d.getFeedName().startsWith(feedName);
    }

    public static Predicate<Document> hasEscapeCharacters() {
        return has("&quot;").or(has("&#")).or(has("&amp;")).or(has("â€™"));
    }

    public static Predicate<Document> matches(Pattern pattern) {


        return d -> {

            String megaConcat =
                    ifPresent(d.text) +
                    ifPresent(d.title) +
                    ifPresent(d.html) +
                    ifPresent(d.pageUrl) +
                    d.getSubjects().stream().map(s -> s + " ").reduce(String::join).orElse("") +
                    d.getSourceCategories().stream().map(s -> s + " ").reduce(String::join).orElse("");

            return pattern.matcher(megaConcat).matches();
        };

    }

    private static String ifPresent(String text) {

        if (Strings.isNullOrEmpty(text)) {
            return "";
        } else {
            return text + " ";
        }

    }
}
