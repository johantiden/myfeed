package se.johantiden.myfeed.user;

import se.johantiden.myfeed.plugin.Feed;
import se.johantiden.myfeed.plugin.rss.DagensNyheterFeed;
import se.johantiden.myfeed.plugin.rss.RssFeed;
import se.johantiden.myfeed.plugin.rss.SvenskaDagbladetFeed;
import se.johantiden.myfeed.plugin.rss.TheLocalFeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private final List<Feed> feeds = new ArrayList<>();

    public List<Feed> getFeeds() {
        return Collections.unmodifiableList(feeds);
    }

    public void addFeed(Feed feed) {
        feeds.add(feed);
    }

    public static User johan() {

        User user = new User();

        user.addFeed(new TheLocalFeed());
        user.addFeed(new RssFeed(
                "http://computersweden.idg.se/rss/systemutveckling",
                ".computersweden",
                "ComputerSweden:Systemutveckling",
                "http://computersweden.idg.se/systemutveckling"));
        user.addFeed(new RssFeed(
                "http://computersweden.idg.se/rss/prylar",
                ".computersweden", "ComputerSweden:Prylar",
                "http://computersweden.idg.se/prylar"));
        user.addFeed(new SvenskaDagbladetFeed());
        user.addFeed(DagensNyheterFeed.nyheter());
        user.addFeed(DagensNyheterFeed.worldNews());


        return user;
    }
}
