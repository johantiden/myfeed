package se.johantiden.myfeed.persistence.model;

public class FeedUser {
    private final User user;
    private final Feed feed;
    private Filter feedFilter;

    public FeedUser(Feed feed, User user) {
        this.user = user;
        this.feed = feed;
    }

    public User getUser() {
        return user;
    }

    public Feed getFeed() {
        return feed;
    }
}
