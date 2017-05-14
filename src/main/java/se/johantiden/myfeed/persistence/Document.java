package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.controller.NameAndUrl;
import se.johantiden.myfeed.controller.Subject;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.util.DocumentPredicates;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Document implements Persistable<Document>, Serializable {

    private static final long serialVersionUID = 7281769259354137360L;

    public final Key<Document> key;
    public Key<Feed> feedKey;
    public NameAndUrl feed;
    public String tab;
    public String title;
    public final String text;
    public NameAndUrl author;
    public final String cssClass;
    public final String pageUrl;
    public String imageUrl;
    public final Instant publishedDate;
    public String html;
    public List<NameAndUrl> categories;
    public Double score;
    public boolean isPaywalled;
    public List<Video> videos = new ArrayList<>();
    public final List<Subject> subjects = new ArrayList<>();

    public Document(
            Key<Feed> feedKey,
            NameAndUrl feed,
            String title,
            String text,
            NameAndUrl author,
            String cssClass,
            String pageUrl,
            String imageUrl,
            Instant publishedDate,
            String html,
            List<NameAndUrl> categories) {

        this.feed = feed;
        this.feedKey = feedKey;
        this.title = title;
        this.text = text;
        this.author = author;
        this.cssClass = cssClass;
        this.pageUrl = pageUrl;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.html = html;
        this.categories = Objects.requireNonNull(categories);
        this.key = Keys.document(this.pageUrl);
    }

    public static String dateToShortString(Instant instant) {

        Instant now = Instant.now();

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

    public String getPublishedShortString() {
        return dateToShortString(publishedDate);
    }

    public final Key<Feed> getFeedKey() {
        return feedKey;
    }

    @Override
    public final Key<Document> getKey() {
        return key;
    }

    public final Instant getPublishDate() {
        return publishedDate;
    }

    public final Double getScore() {
        return score;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
