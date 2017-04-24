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
    public final NameAndUrlBean feed;
    public final NameAndUrlBean category;
    public final String title;
    public final String text;
    public final Double score;
    public final NameAndUrlBean author;
    public final String cssClass;
    public final String pageUrl;
    public final String imageUrl;
    public final Instant publishedDate;
    public final String html;
    public final boolean read;
    public final List<Video> videos;

    public DocumentBean(UserDocument userDocument, Document document) {
        this.feed = new NameAndUrlBean(document.feedName, document.feedUrl);
        this.category = new NameAndUrlBean(document.categoryName, document.categoryUrl);

        this.title = document.title;
        this.text = document.text;
        this.author = new NameAndUrlBean(document.authorName, document.authorUrl);

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

    public String getCssClass() {
        return cssClass;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getHtml() {
        return html;
    }
    public String getPageUrl() {
        return pageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }

    public String getPublishedDateShort() {
        return dateToShortString(publishedDate);
    }

    public NameAndUrlBean getFeed() {
        return feed;
    }

    public NameAndUrlBean getCategory() {
        return category;
    }

    public NameAndUrlBean getAuthor() {
        return author;
    }

    public boolean isRead() {
        return read;
    }

    public Double getScore() {
        return score;
    }

    public String getUserDocumentKey() {
        return userDocumentKey;
    }

    public List<Video> getVideos() {
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
    public String toString() {
        return "DocumentBean{" +
                "feed=" + feed +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", cssClass='" + cssClass + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishedDate=" + publishedDate +
                ", html='" + html + '\'' +
                ", read=" + read +
                '}';
    }
}
