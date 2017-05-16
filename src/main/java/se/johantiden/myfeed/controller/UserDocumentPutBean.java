package se.johantiden.myfeed.controller;

public class UserDocumentPutBean {

    public String feedName;
    public String feedUrl;
    public String title;
    public String text;
    public String pageUrl;
    public String username;
    public boolean read;
    public String userDocumentKey;

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

    public String getUsername() {
        return username;
    }

    public boolean isRead() {
        return read;
    }

    @Override
    public String toString() {
        return "UserDocumentPutBean{" +
                "feedName='" + feedName + '\'' +
                ", feedUrl='" + feedUrl + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", username='" + username + '\'' +
                ", read=" + read +
                '}';
    }
}
