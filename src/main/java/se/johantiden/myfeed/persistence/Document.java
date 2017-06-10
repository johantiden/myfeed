package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.controller.NameAndUrl;

import javax.persistence.Entity;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Document extends BaseEntity {

    public Feed feed;
    public String title;
    public final String text;
    public NameAndUrl author;
    public final String pageUrl;
    public String imageUrl;
    public final Instant publishedDate;
    public String html;
    public List<NameAndUrl> categories;
    public Double score;
    public boolean isPaywalled;
    public List<Video> videos = new ArrayList<>();

    public boolean subjectsParsed = false;
    public String tab;
    public final List<String> subjects = new ArrayList<>();

    public Document(
            Feed feed,
            String title,
            String text,
            NameAndUrl author,
            String pageUrl,
            String imageUrl,
            Instant publishedDate,
            String html,
            List<NameAndUrl> categories) {

        this.feed = feed;
        this.title = title;
        this.text = text;
        this.author = author;
        this.pageUrl = pageUrl;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.html = html;
        this.categories = Objects.requireNonNull(categories);
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

    public String getTab() {
        return tab;
    }

    public final Feed getFeed() {
        return feed;
    }

    public final Instant getPublishDate() {
        return publishedDate;
    }

    public final Double getScore() {
        return score;
    }

    public List<String> getSubjects() {
        return subjects;
    }
}
