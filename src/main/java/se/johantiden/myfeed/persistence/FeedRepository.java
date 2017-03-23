package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.plugin.rss.RssPlugin;
import se.johantiden.myfeed.plugin.twitter.TwitterPlugin;

import java.util.ArrayList;
import java.util.List;

import static se.johantiden.myfeed.util.Maps2.newHashMap;

public class FeedRepository {

    private List<Feed> allFeeds = null;


    public List<Feed> allFeeds() {
        if (allFeeds == null) {
            allFeeds = allFeedsHack();
        }

        return allFeeds;
    }

    private static List<Feed> allFeedsHack() {
        List<Feed> feeds = new ArrayList<>();
        RssPlugin rss = new RssPlugin();
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


        feeds.add(rss.createFeed(
                "xkcd",
                "https://xkcd.com",
                "xkcd",
                newHashMap("rssUrl", "https://xkcd.com/atom.xml")));


        feeds.add(rss.createFeed(
                "Ars Technica",
                "https://arstechnica.com/",
                "arstechnica",
                newHashMap("rssUrl", "http://feeds.arstechnica.com/arstechnica/index")));



        feeds.add(createTwitter("pwolodarski"));
        feeds.add(createTwitter("BillGates"));
        feeds.add(createTwitter("github"));

        User johan = User.johan();
        feeds.forEach(f -> f.getFeedUsers().add(new FeedUser(f, johan)));
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
