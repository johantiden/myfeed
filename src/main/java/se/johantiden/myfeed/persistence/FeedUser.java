package se.johantiden.myfeed.persistence;

public class FeedUser {
    private final Username user;
    private final Feed feed;

    public FeedUser(Feed feed, Username user) {
        this.user = user;
        this.feed = feed;
    }

    public Username getUser() {
        return user;
    }

    public Feed getFeed() {
        return feed;
    }
}
