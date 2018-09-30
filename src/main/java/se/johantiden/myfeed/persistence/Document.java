package se.johantiden.myfeed.persistence;


import com.google.common.collect.Lists;
import se.johantiden.myfeed.controller.NameAndUrl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Document extends BaseEntity<Document> {
    public String title;
    public String text;
    public NameAndUrl author;
    private final String pageUrl;
    public String imageUrl;
    public final Instant publishedDate;
    public String html;
    public Double score;
    public boolean isPaywalled;
    public ArrayList<Video> videos = new ArrayList<>();
    private boolean subjectsParsed = false;
    private final ArrayList<Subject> subjects;
    private final String feedName;
    private final String feedUrl;
    private final ArrayList<String> sourceCategories;
    private boolean hidden;
    private boolean read;

    public Document(
            String title,
            String text,
            NameAndUrl author,
            String pageUrl,
            String imageUrl,
            Instant publishedDate,
            String html,
            Set<String> sourceCategories,
            String feedName,
            String feedUrl) {

        this.title = title;
        this.text = text;
        this.author = author;
        this.pageUrl = pageUrl;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.html = html;
        this.subjects = new ArrayList<>();
        this.feedName = feedName;
        this.feedUrl = feedUrl;
        this.sourceCategories = Lists.newArrayList(sourceCategories);
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

    public boolean isSubjectsParsed() {
        return subjectsParsed;
    }

    public void setSubjectsParsed(boolean subjectsParsed) {
        this.subjectsParsed = subjectsParsed;
    }

    public List<String> getSourceCategories() {
        return sourceCategories;
    }

    public String getPublishedShortString() {
        return dateToShortString(publishedDate);
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

    public String getFeedName() {
        return feedName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    @javax.annotation.Nonnull
    public String getPageUrl() {
        return pageUrl;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                '}';
    }
}
