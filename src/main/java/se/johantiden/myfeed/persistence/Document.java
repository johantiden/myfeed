package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.util.JString;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

public class Document implements Persistable<Document> {

    public final String feedName;
    public String feedUrl;
    public String title;
    public final String text;
    public String authorName;
    public String authorUrl;
    public final String cssClass;
    public final String pageUrl;
    public final String imageUrl;
    public final Instant publishedDate;
    public final String fullSourceEntryForSearch;
    public final String html;
    private Key<Feed> feed;
    public String categoryName;
    public String categoryUrl;

    public Document(
            Key<Feed> feed,
            String feedName,
            String feedUrl,
            String title,
            String text,
            String authorName,
            String authorUrl,
            String cssClass,
            String pageUrl,
            String imageUrl,
            Instant publishedDate,
            String fullSourceEntryForSearch,
            String html, String categoryName,
            String categoryUrl) {

        this.feedName = feedName;
        this.feed = feed;
        this.feedUrl = feedUrl;
        this.title = title;
        this.text = text;
        this.authorName = authorName;
        this.authorUrl = authorUrl;
        this.cssClass = cssClass;
        this.pageUrl = pageUrl;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.fullSourceEntryForSearch = fullSourceEntryForSearch;
        this.categoryName = categoryName;
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
        return document -> JString.containsIgnoreCase(document.categoryName, category);
    }

    @Override
    public String toString() {
        return "Document{" +
                "feedName='" + feedName + '\'' +
                ", feedUrl='" + feedUrl + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorUrl='" + authorUrl + '\'' +
                ", cssClass='" + cssClass + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishedDate=" + publishedDate +
                ", fullSourceEntryForSearch='" + fullSourceEntryForSearch + '\'' +
                ", html='" + html + '\'' +
                ", feed=" + feed +
                ", categoryName='" + categoryName + '\'' +
                ", categoryUrl='" + categoryUrl + '\'' +
                '}';
    }

    public Key<Feed> getFeed() {
        return feed;
    }

    @Override
    public Key<Document> getKey() {
        return Keys.document(this);
    }
}
