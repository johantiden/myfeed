package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.util.JString;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Predicate;

public class Document implements Persistable<Document> {

    public final Key<Document> key;
    public final String feedName;
    public String feedUrl;
    public String title;
    public final String text;
    public String authorName;
    public String authorUrl;
    public final String cssClass;
    public final String pageUrl;
    public final String imageUrl;
    public final Instant publishedDate;
    public String html;
    private Key<Feed> feed;
    public String categoryName;
    public String categoryUrl;
    public Double score;
    public boolean isPaywalled;

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

    public static Predicate<Document> categoryEquals(String category) {
        return document -> category.equals(document.categoryName);
    }

    public static Predicate<Document> categoryContains(String category) {
        return document -> JString.containsIgnoreCase(document.categoryName, category);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Document document = (Document) o;

        if (isPaywalled != document.isPaywalled) {
            return false;
        }
        if (key != null ? !key.equals(document.key) : document.key != null) {
            return false;
        }
        if (feedName != null ? !feedName.equals(document.feedName) : document.feedName != null) {
            return false;
        }
        if (feedUrl != null ? !feedUrl.equals(document.feedUrl) : document.feedUrl != null) {
            return false;
        }
        if (title != null ? !title.equals(document.title) : document.title != null) {
            return false;
        }
        if (text != null ? !text.equals(document.text) : document.text != null) {
            return false;
        }
        if (authorName != null ? !authorName.equals(document.authorName) : document.authorName != null) {
            return false;
        }
        if (authorUrl != null ? !authorUrl.equals(document.authorUrl) : document.authorUrl != null) {
            return false;
        }
        if (cssClass != null ? !cssClass.equals(document.cssClass) : document.cssClass != null) {
            return false;
        }
        if (pageUrl != null ? !pageUrl.equals(document.pageUrl) : document.pageUrl != null) {
            return false;
        }
        if (imageUrl != null ? !imageUrl.equals(document.imageUrl) : document.imageUrl != null) {
            return false;
        }
        if (publishedDate != null ? !publishedDate.equals(document.publishedDate) : document.publishedDate != null) {
            return false;
        }
        if (html != null ? !html.equals(document.html) : document.html != null) {
            return false;
        }
        if (feed != null ? !feed.equals(document.feed) : document.feed != null) {
            return false;
        }
        if (categoryName != null ? !categoryName.equals(document.categoryName) : document.categoryName != null) {
            return false;
        }
        if (categoryUrl != null ? !categoryUrl.equals(document.categoryUrl) : document.categoryUrl != null) {
            return false;
        }
        return !(score != null ? !score.equals(document.score) : document.score != null);

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (feedName != null ? feedName.hashCode() : 0);
        result = 31 * result + (feedUrl != null ? feedUrl.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorUrl != null ? authorUrl.hashCode() : 0);
        result = 31 * result + (cssClass != null ? cssClass.hashCode() : 0);
        result = 31 * result + (pageUrl != null ? pageUrl.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (publishedDate != null ? publishedDate.hashCode() : 0);
        result = 31 * result + (html != null ? html.hashCode() : 0);
        result = 31 * result + (feed != null ? feed.hashCode() : 0);
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (categoryUrl != null ? categoryUrl.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (isPaywalled ? 1 : 0);
        return result;
    }
}
