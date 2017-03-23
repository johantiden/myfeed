package se.johantiden.myfeed.controller;

import se.johantiden.myfeed.persistence.Document;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DocumentBean {

    public final String feedName;
    public final String feedUrl;
    public final String title;
    public final String text;
    public final String author;
    public final String authorUrl;
    public final String cssClass;
    public final String pageUrl;
    public final String imageUrl;
    public final Instant publishedDate;
    public final String fullSourceEntryForSearch;
    public final String html;

    public DocumentBean(Document document) {
        this.feedName = document.getFeed().getName();
        this.feedUrl = document.feedUrl;
        this.title = document.title;
        this.text = document.text;
        this.author = document.author;
        this.authorUrl = document.authorUrl;
        this.cssClass = document.cssClass;
        this.pageUrl = document.pageUrl;
        this.imageUrl = document.imageUrl;
        this.publishedDate = document.publishedDate;
        this.fullSourceEntryForSearch = document.fullSourceEntryForSearch;
        this.html = document.html;
    }

    public String getFeedName() {
        return feedName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public String getAuthor() {
        return author;
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

    public String getAuthorUrl() {
        return authorUrl;
    }

    public String getFullSourceEntryForSearch() {
        return fullSourceEntryForSearch;
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

}