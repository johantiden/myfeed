package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.user.User;

public class FeedUser {
    private final User user;
    private final Feed feed;

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
