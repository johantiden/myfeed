package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.util.JString;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

public class Document {

    public String feedUrl;
    public String title;
    public final String text;
    public String author;
    public String authorUrl;
    public final String cssClass;
    public final String pageUrl;
    public final String imageUrl;
    public final Instant publishedDate;
    public final String fullSourceEntryForSearch;
    public final String html;
    public String category;
    public String categoryUrl;
    private Feed feed;

    public Document(
            Feed feed,
            String feedUrl,
            String title,
            String text,
            String author,
            String authorUrl,
            String cssClass,
            String pageUrl,
            String imageUrl,
            Instant publishedDate,
            String fullSourceEntryForSearch,
            String category,
            String html,
            String categoryUrl) {
        this.feed = feed;
        this.feedUrl = feedUrl;
        this.title = title;
        this.text = text;
        this.author = author;
        this.authorUrl = authorUrl;
        this.cssClass = cssClass;
        this.pageUrl = pageUrl;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.fullSourceEntryForSearch = fullSourceEntryForSearch;
        this.category = category;
        this.html = html;
        this.categoryUrl = categoryUrl;
    }

    public static String dateToShortString(Instant instant) {

        Instant now = Instant.now();

        long weeks = instant.until(now, ChronoUnit.WEEKS);
        if (weeks >= 1) {
            return weeks + "d";
        }

        long days = instant.until(now, ChronoUnit.DAYS);
        if (days >= 1) {
            return days + "d";
        }

        long hours = instant.until(now, ChronoUnit.HOURS);
        if (hours >= 1) {
            return hours + "h";
        }

        long minutes = instant.until(now, ChronoUnit.MINUTES);
        if (minutes >= 1) {
            return minutes + "m";
        }

        long seconds = instant.until(now, ChronoUnit.SECONDS);
        if (seconds >= 1) {
            return seconds + "s";
        }

        return "";
    }


    public static Predicate<Document> hasCategory(String category) {
        return document -> JString.containsIgnoreCase(document.category, category);
    }


    @Override
    public String toString() {
        return "OutputBean{" +
                ", feedUrl='" + feedUrl + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", cssClass='" + cssClass + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }

    public Feed getFeed() {
        return feed;
    }
}
