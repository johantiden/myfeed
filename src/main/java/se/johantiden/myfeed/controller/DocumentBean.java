package se.johantiden.myfeed.controller;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.UserDocument;
import se.johantiden.myfeed.persistence.Video;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DocumentBean {

    public final long userDocumentId;
    public final NameAndUrl feed;
    public final String title;
    public final List<String> tabs;
    public final String text;
    public final Double score;
    public final NameAndUrl author;
    public final String pageUrl;
    public final String imageUrl;
    public final Instant publishedDate;
    public final String html;
    public final boolean read;
    public final List<Video> videos;
    public final List<String> subjects;

    public DocumentBean(UserDocument userDocument, Document document) {
        verifyHtml(document.html, document.getFeedName());

        this.feed = new NameAndUrl(document.getFeedName(), document.getFeedUrl());
        this.title = document.title;
        this.text = document.text;
        this.author = document.author;
        this.pageUrl = document.pageUrl;
        this.imageUrl = document.imageUrl;
        this.publishedDate = document.publishedDate;
        this.html = document.html;
        this.read = userDocument.isRead();
        this.score = document.score;
        this.userDocumentId = userDocument.getId();
        this.videos = new ArrayList<>(document.videos);
        this.tabs = document.getTabs();
        this.subjects = document.getSubjects();
    }

    private void verifyHtml(String html, String feedName) {
        if (html == null) {
            return;
        }

        if (feedName.contains("xkcd")) {
            return;
        }

        if (html.contains("<img") || html.contains("< img")) {
            throw new RuntimeException("No images allowed!");
        }
        if (html.contains("<script") || html.contains("< script")) {
            throw new RuntimeException("No script allowed!");
        }

        if (html.contains("twitter_icon_large.png") || html.contains("facebook_icon_large.png") || html.contains("plus.google.com")) {
            throw new RuntimeException("Ping images detected!");
        }

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

    public final NameAndUrl getAuthor() {
        return author;
    }

    public final boolean isRead() {
        return read;
    }

    public final Double getScore() {
        return score;
    }

    public final long getUserDocumentId() {
        return userDocumentId;
    }

    public final List<Video> getVideos() {
        return videos;
    }

    public List<String> getTabs() {
        return tabs;
    }

    public List<String> getSubjects() {
        return subjects;
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
                "userDocumentId='" + userDocumentId + '\'' +
                ", feed=" + feed +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", author=" + author +
                ", pageUrl='" + pageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishedDate=" + publishedDate +
                ", html='" + html + '\'' +
                ", read=" + read +
                ", videos=" + videos +
                '}';
    }
}
