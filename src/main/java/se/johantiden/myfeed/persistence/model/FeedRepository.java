package se.johantiden.myfeed.persistence.model;

import se.johantiden.myfeed.plugin.rss.RssPlugin;
import se.johantiden.myfeed.plugin.twitter.TwitterPlugin;

import java.util.ArrayList;
import java.util.List;

import static se.johantiden.myfeed.util.Maps2.newHashMap;

public class FeedRepository {

    public List<Feed> allFeeds() {
        return allFeedsHack();
    }

    private static List<Feed> allFeedsHack() {
        List<Feed> feeds = new ArrayList<>();
        RssPlugin rss = new RssPlugin();
        feeds.add(rss.createFeed(
                "ComputerSweden:Systemutveckling",
                "http://computersweden.idg.se/rss/systemutveckling",
                "computersweden",
                newHashMap("rssUrl", "http://computersweden.idg.se/systemutveckling")));

        feeds.add(rss.createFeed(
                "TheLocal",
                "http://www.thelocal.se",
                "thelocal",
                newHashMap("rssUrl", "http://www.thelocal.se/feeds/rss.php")));

        feeds.add(rss.createFeed(
                "Slashdot",
                "https://slashdot.org",
                "slashdot",
                newHashMap("rssUrl", "http://rss.slashdot.org/Slashdot/slashdotMainatom")));

        feeds.add(rss.createFeed(
                "Svenska Dagbladet",
                "https://www.svd.se",
                "svd",
                newHashMap("rssUrl", "https://www.svd.se/?service=rss")));

        feeds.add(rss.createFeed(
                "Svenska Dagbladet",
                "https://www.svd.se",
                "svd",
                newHashMap("rssUrl", "https://www.svd.se/?service=rss")));

        feeds.add(rss.createFeed(
                "Dagens Nyheter - Världen",
                "https://www.dn.se",
                "dn",
                newHashMap("rssUrl", "http://www.dn.se/nyheter/varlden/rss/")));

        feeds.add(createTwitter("pwolodarski"));
        feeds.add(createTwitter("BillGates"));
        feeds.add(createTwitter("github"));
        return feeds;
    }

    private static Feed createTwitter(String username) {
        TwitterPlugin twitter = new TwitterPlugin();
        return twitter.createFeed(
                "Twitter",
                "https://twitter.com/"+username,
                "twitter",
                newHashMap("username", username));
    }

}
