package se.johantiden.myfeed.persistence;


import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Document {

    public String feedUrl;
    public String title;
    public String text;
    public String author;
    public String authorUrl;
    public String cssClass;
    public String pageUrl;
    public String imageUrl;
    public Instant publishedDate;
    public String fullSourceEntryForSearch;
    private Feed feed;

    public Document(
            Feed feed,
            String feedUrl,
            String title,
            String text,
            String author,
            String authorUrl,
            String cssClass,
            String pageUrl,
            String imageUrl,
            Instant publishedDate,
            String fullSourceEntryForSearch) {
        this.feed = feed;
        this.feedUrl = feedUrl;
        this.title = title;
        this.text = text;
        this.author = author;
        this.authorUrl = authorUrl;
        this.cssClass = cssClass;
        this.pageUrl = pageUrl;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.fullSourceEntryForSearch = fullSourceEntryForSearch;
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

    public Feed getFeed() {
        return feed;
    }
}
