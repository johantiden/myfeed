package se.johantiden.myfeed.controller;

public class UserDocumentPutBean {

    public String feedName;
    public String feedUrl;
    public String title;
    public String text;
    public String cssClass;
    public String pageUrl;
    public boolean read;

    public String getFeedName() {
        return feedName;
    }

    public String getFeedUrl() {
        return feedUrl;
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
                ", cssClass='" + cssClass + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", read=" + read +
                '}';
    }
}
