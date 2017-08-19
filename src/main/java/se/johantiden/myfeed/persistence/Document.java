package se.johantiden.myfeed.persistence;


import com.google.common.collect.Lists;
import se.johantiden.myfeed.controller.NameAndUrl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Document.findDocumentsNotParsedSubjects", query = "SELECT d FROM Document d WHERE d.subjectsParsed = false"),
        @NamedQuery(name = "Document.findDocumentsNotParsedTabs", query = "SELECT d FROM Document d WHERE d.tabsParsed = false"),

})
public class Document extends BaseEntity {
    @Column(length = 2000)
    public String title;

    @Column(length = 2000)
    public final String text;

    public NameAndUrl author;

    @Column(length = 2000)
    public final String pageUrl;

    @Column(length = 2000)
    public String imageUrl;

    public final Instant publishedDate;

    @Column(length = 8000)
    public String html;

    public Double score;
    public boolean isPaywalled;

    @Column(length = 2000)
    public ArrayList<Video> videos = new ArrayList<>();

    private boolean subjectsParsed = false;
    private final ArrayList<String> subjects;

    private boolean tabsParsed = false;
    private final ArrayList<String> tabs;

    private final String feedName;
    private final String feedUrl;

    @Column(length = 2000)
    private final ArrayList<String> sourceCategories;

    // JPA
    protected Document() {

        text = null;
        pageUrl = null;
        publishedDate = null;
        subjects = new ArrayList<>();
        tabs = new ArrayList<>();
        feedName = null;
        feedUrl = null;
        sourceCategories = new ArrayList<>();
    }

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
        this.tabs = new ArrayList<>();
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

    public boolean isTabsParsed() {
        return tabsParsed;
    }

    public void setTabsParsed(boolean tabsParsed) {
        this.tabsParsed = tabsParsed;
    }

    public List<String> getSourceCategories() {
        return sourceCategories;
    }

    public String getPublishedShortString() {
        return dateToShortString(publishedDate);
    }

    public ArrayList<String> getTabs() {
        return tabs;
    }

    public final Instant getPublishDate() {
        return publishedDate;
    }

    public final Double getScore() {
        return score;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public String getFeedName() {
        return feedName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }
}
