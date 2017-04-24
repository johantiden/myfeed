package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Document implements Persistable<Document>, Serializable {

    public final Key<Document> key;
    public final String feedName;
    public String feedUrl;
    public String title;
    public final String text;
    public String authorName;
    public String authorUrl;
    public final String cssClass;
    public final String pageUrl;
    public String imageUrl;
    public final Instant publishedDate;
    public String html;
    private Key<Feed> feed;
    public String categoryName;
    public String categoryUrl;
    public Double score;
    public boolean isPaywalled;
    public List<Video> videos = new ArrayList<>();

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
        this.categoryName = categoryName;
        this.html = html;
        this.categoryUrl = categoryUrl;
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
        return key;
    }

    public Instant getPublishDate() {
        return publishedDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
