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
        this.feed = new NameAndUrlBean(
                userDocument.getDocument().getFeed().getName(),
                userDocument.getDocument().feedUrl);
        this.category = new NameAndUrlBean(
                userDocument.getDocument().category,
                userDocument.getDocument().categoryUrl);

        this.title = userDocument.getDocument().title;
        this.text = userDocument.getDocument().text;
        this.author = new NameAndUrlBean(
                userDocument.getDocument().author,
                userDocument.getDocument().authorUrl);

        this.cssClass = userDocument.getDocument().cssClass;
        this.pageUrl = userDocument.getDocument().pageUrl;
        this.imageUrl = userDocument.getDocument().imageUrl;
        this.publishedDate = userDocument.getDocument().publishedDate;
        this.fullSourceEntryForSearch = userDocument.getDocument().fullSourceEntryForSearch;
        this.html = userDocument.getDocument().html;
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
