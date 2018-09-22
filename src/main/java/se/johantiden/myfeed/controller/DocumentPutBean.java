package se.johantiden.myfeed.controller;

public class DocumentPutBean {

    public String feedName;
    public String feedUrl;
    public String title;
    public String text;
    public String pageUrl;
    public boolean read;
    public long documentId;

    public String getFeedName() {
        return feedName;
    }

    public String getFeedUrl() {
        return feedUrl;
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

    public boolean isRead() {
        return read;
    }

    @Override
    public String toString() {
        return "DocumentPutBean{" +
                "feedName='" + feedName + '\'' +
                ", feedUrl='" + feedUrl + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", read=" + read +
                '}';
    }
}
