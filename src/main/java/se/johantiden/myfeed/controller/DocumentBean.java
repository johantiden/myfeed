package se.johantiden.myfeed.controller;

import se.johantiden.myfeed.persistence.UserDocument;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DocumentBean {

    public final NameAndUrlBean feed;
    public final NameAndUrlBean category;
    public final String title;
    public final String text;
    public final NameAndUrlBean author;
    public final String cssClass;
    public final String pageUrl;
    public final String imageUrl;
    public final Instant publishedDate;
    public final String fullSourceEntryForSearch;
    public final String html;
    public final boolean read;

    public DocumentBean(UserDocument userDocument) {
        this.feed = userDocument.feedBean;
        this.category = userDocument.category;

        this.title = userDocument.title;
        this.text = userDocument.text;
        this.author = userDocument.author;

        this.cssClass = userDocument.cssClass;
        this.pageUrl = userDocument.pageUrl;
        this.imageUrl = userDocument.imageUrl;
        this.publishedDate = userDocument.publishedDate;
        this.fullSourceEntryForSearch = userDocument.fullSourceEntryForSearch;
        this.html = userDocument.html;
        this.read = userDocument.isRead();
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

    public String getFullSourceEntryForSearch() {
        return fullSourceEntryForSearch;
    }

    public boolean isRead() {
        return read;
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
                ", fullSourceEntryForSearch='" + fullSourceEntryForSearch + '\'' +
                ", html='" + html + '\'' +
                ", read=" + read +
                '}';
    }
}
