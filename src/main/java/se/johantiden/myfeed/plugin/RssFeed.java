package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Feed;

public class RssFeed extends Feed {

    public RssFeed(String name, String url, String rssUrl) {
        super(name, url, new RssFeedReader(name, url, rssUrl));
    }
}
