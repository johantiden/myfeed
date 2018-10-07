package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Subject;
import se.johantiden.myfeed.persistence.Video;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentBean {

    private static final Logger log = LoggerFactory.getLogger(DocumentBean.class);

    public final long documentId;
    public final NameAndUrl feed;
    public final String title;
    public final String text;
    public final Double score;
    public final NameAndUrl author;
    public final String pageUrl;
    public final String imageUrl;
    public final long publishedDate;
    public final String publishedDateShortString;
    public final boolean read;
    public final List<Video> videos;
    public final List<SubjectBean> subjects;

    public DocumentBean(Document document) {
        this.feed = new NameAndUrl(document.getFeedName(), document.getFeedUrl());
        this.title = document.title;
        this.text = document.text;
        this.author = document.author;
        this.pageUrl = document.getPageUrl();
        this.imageUrl = document.imageUrl;
        this.publishedDate = document.publishedDate.toEpochMilli();
        this.publishedDateShortString = dateToShortString(document.publishedDate);
        this.read = document.isRead();
        this.score = document.score;
        this.documentId = document.getId();
        this.videos = new ArrayList<>(document.videos);
        this.subjects = toSubjectBeans(document.getSubjects());
    }

    private List<SubjectBean> toSubjectBeans(List<Subject> subjects) {
        return subjects.stream()
                .map(this::toSubjectBean)
                .collect(Collectors.toList());
    }

    private SubjectBean toSubjectBean(Subject subject) {
        return new SubjectBean(
                subject.getName(),
                subject.isHashTag(),
                subject.isShowAsTab(),
                subject.getMinDepth());
    }

    public final String getTitle() {
        return title;
    }

    public final String getText() {
        return text;
    }

    public final String getPageUrl() {
        return pageUrl;
    }

    public final String getImageUrl() {
        return imageUrl;
    }

    public final long getPublishedDate() {
        return publishedDate;
    }

    public final String getPublishedDateShort() {
        return publishedDateShortString;
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

    public long getDocumentId() {
        return documentId;
    }

    public final List<Video> getVideos() {
        return videos;
    }

    public List<SubjectBean> getSubjects() {
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
                "documentId='" + documentId + '\'' +
                ", feed=" + feed +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", score=" + score +
                ", author=" + author +
                ", pageUrl='" + pageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishedDate=" + publishedDate +
                ", read=" + read +
                ", videos=" + videos +
                '}';
    }

    private class SubjectBean {
        final boolean hashTag;
        final boolean showAsTab;
        final String name;
        final int depth;

        private SubjectBean(String name, boolean hashTag, boolean showAsTab, int depth) {
            this.name = name;
            this.hashTag = hashTag;
            this.showAsTab = showAsTab;
            this.depth = depth;
        }

        public String getName() {
            return name;
        }

        public boolean isHashTag() {
            return hashTag;
        }

        public boolean isShowAsTab() {
            return showAsTab;
        }

        public int getDepth() {
            return depth;
        }
    }
}
