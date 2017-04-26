package se.johantiden.myfeed.controller;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.Video;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DocumentBean {

    public final String userDocumentKey;
    public final NameAndUrl feed;
    public final List<NameAndUrl> categories;
    public final String title;
    public final String text;
    public final Double score;
    public final NameAndUrl author;
    public final String cssClass;
    public final String pageUrl;
    public final String imageUrl;
    public final Instant publishedDate;
    public final String html;
    public final boolean read;
    public final List<Video> videos;

    public DocumentBean(UserDocument userDocument, Document document) {
        this.feed = document.feed;
        this.categories = document.categories;

        this.title = document.title;
        this.text = document.text;
        this.author = document.author;

        this.cssClass = document.cssClass;
        this.pageUrl = document.pageUrl;
        this.imageUrl = document.imageUrl;
        this.publishedDate = document.publishedDate;
        this.html = document.html;
        this.read = userDocument.isRead();
        this.score = document.score;
        this.userDocumentKey = userDocument.getKey().toString();
        this.videos = new ArrayList<>(document.videos);
    }

    public final String getCssClass() {
        return cssClass;
    }

    public final String getTitle() {
        return title;
    }

    public final String getText() {
        return text;
    }

    public final String getHtml() {
        return html;
    }

    public final String getPageUrl() {
        return pageUrl;
    }

    public final String getImageUrl() {
        return imageUrl;
    }

    public final Instant getPublishedDate() {
        return publishedDate;
    }

    public final String getPublishedDateShort() {
        return dateToShortString(publishedDate);
    }

    public final NameAndUrl getFeed() {
        return feed;
    }

    public final List<NameAndUrl> getCategories() {
        return categories;
    }

    public final NameAndUrl getAuthor() {
        return author;
    }

    public final boolean isRead() {
        return read;
    }

    public final Double getScore() {
        return score;
    }

    public final String getUserDocumentKey() {
        return userDocumentKey;
    }

    public final List<Video> getVideos() {
        return videos;
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

    @Override
    public final String toString() {
        return "DocumentBean{" +
                "userDocumentKey='" + userDocumentKey + '\'' +
                ", feed=" + feed +
                ", categories=" + categories +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", author=" + author +
                ", cssClass='" + cssClass + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishedDate=" + publishedDate +
                ", html='" + html + '\'' +
                ", read=" + read +
                ", videos=" + videos +
                '}';
    }
}
