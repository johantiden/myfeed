package se.johantiden.myfeed.plugin;

public class FeedException extends Exception {
    private final String feedName;

    public FeedException(String feedName, Throwable cause) {
        super(cause);
        this.feedName = feedName;
    }

    public String getFeedName() {
        return feedName;
    }
}
