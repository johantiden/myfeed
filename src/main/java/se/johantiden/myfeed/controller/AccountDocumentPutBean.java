package se.johantiden.myfeed.controller;

public class AccountDocumentPutBean {

    public String feedName;
    public String feedUrl;
    public String title;
    public String text;
    public String pageUrl;
    public String accountname;
    public boolean read;
    public long accountDocumentId;

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

    public String getAccountName() {
        return accountname;
    }

    public boolean isRead() {
        return read;
    }

    @Override
    public String toString() {
        return "AccountDocumentPutBean{" +
                "feedName='" + feedName + '\'' +
                ", feedUrl='" + feedUrl + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", pageUrl='" + pageUrl + '\'' +
                ", accountname='" + accountname + '\'' +
                ", read=" + read +
                '}';
    }
}
