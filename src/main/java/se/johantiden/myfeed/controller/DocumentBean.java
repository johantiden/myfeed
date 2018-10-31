package se.johantiden.myfeed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Subject;
import se.johantiden.myfeed.persistence.Video;
import se.johantiden.myfeed.plugin.HackerNewsFeed;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentBean {

    private static final Logger log = LoggerFactory.getLogger(DocumentBean.class);

    public final long documentId;
    public final NameAndUrl feed;
    public final String title;
    public final String text;
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
        this.pageUrl = document.getPageUrl();
        this.imageUrl = document.imageUrl;
        if (this.imageUrl == null && !document.getFeedName().equals(HackerNewsFeed.NAME)) {
            log.debug("No image!");
        }
        this.publishedDate = document.publishedDate.toEpochMilli();
        this.publishedDateShortString = dateToShortString(document.publishedDate);
        this.read = document.isRead();
        this.documentId = document.getId();
        this.videos = new ArrayList<>(document.videos);
        this.subjects = toSubjectBeans(document.getSubjects());
    }

    private List<SubjectBean> toSubjectBeans(Collection<Subject> subjects) {
        if (subjects.isEmpty()) {
            throw new RuntimeException("Subjects cant be empty! At least the feed name should be put as a subject.");
        }

        List<SubjectBean> subjectBeans = subjects.stream()
                .map(this::toSubjectBean)
                .collect(Collectors.toList());

        if (subjects.size() == 1) {
            subjectBeans.add(toSubjectBean(Subject.UNCLASSIFIED));
        }

        return subjectBeans;
    }

    private SubjectBean toSubjectBean(Subject subject) {
        return new SubjectBean(
                subject.getName(),
                subject.isHashTag(),
                subject.isShowAsTab(),
                subject.getMinDepth(),
                subject.getType());
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

    public long getPublishedDate() {
        return publishedDate;
    }

    public String getPublishedDateShort() {
        return publishedDateShortString;
    }

    public NameAndUrl getFeed() {
        return feed;
    }

    public boolean isRead() {
        return read;
    }

    public long getDocumentId() {
        return documentId;
    }

    public List<Video> getVideos() {
        return Collections.unmodifiableList(videos);
    }

    public List<SubjectBean> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }

    public static String dateToShortString(Instant instant) {

        Instant now = Instant.now();

        long days = instant.until(now, ChronoUnit.DAYS);
        if (days >= 1L) {
            return days + "d";
        }

        long hours = instant.until(now, ChronoUnit.HOURS);
        if (hours >= 1L) {
            return hours + "h";
        }

        long minutes = instant.until(now, ChronoUnit.MINUTES);
        if (minutes >= 1L) {
            return minutes + "m";
        }

        long seconds = instant.until(now, ChronoUnit.SECONDS);
        if (seconds >= 1L) {
            return seconds + "s";
        }

        return "";
    }

    @Override
    public String toString() {
        return "DocumentBean{" +
                "documentId='" + documentId + '\'' +
                ", feed=" + feed +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishedDate=" + publishedDate +
                ", read=" + read +
                ", videos=" + videos +
                '}';
    }

    private static class SubjectBean {
        final boolean hashTag;
        final boolean showAsTab;
        final String name;
        final int depth;
        final SubjectTypeBean subjectType;

        SubjectBean(String name, boolean hashTag, boolean showAsTab, int depth, Subject.SubjectType subjectType) {
            this.name = name;
            this.hashTag = hashTag;
            this.showAsTab = showAsTab;
            this.depth = depth;
            this.subjectType = toBean(subjectType);
        }

        private SubjectTypeBean toBean(Subject.SubjectType subjectType) {
            return new SubjectTypeBean(subjectType.name(), subjectType.title, subjectType.order);
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

        public SubjectTypeBean getSubjectType() {
            return subjectType;
        }

        private class SubjectTypeBean {
            private final String constant;
            private final String title;
            private final int order;

            public SubjectTypeBean(String constant, String title, int order) {
                this.constant = constant;
                this.title = title;
                this.order = order;
            }

            public String getConstant() {
                return constant;
            }

            public String getTitle() {
                return title;
            }

            public int getOrder() {
                return order;
            }
        }
    }
}
