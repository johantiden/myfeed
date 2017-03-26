package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.plugin.dn.DagensNyheterPlugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;
import se.johantiden.myfeed.plugin.twitter.TwitterPlugin;

import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;
import static se.johantiden.myfeed.util.Maps2.newHashMap;

public class FeedRepository {

    private List<Feed> allFeeds = null;
    public static final long INVALIDATION_PERIOD = 1;
    public static final TemporalUnit INVALIDATION_PERIOD_UNIT = MINUTES;


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
                newHashMap("rssUrl", "http://rss.slashdot.org/Slashdot/slashdotMainatom"), INVALIDATION_PERIOD, INVALIDATION_PERIOD_UNIT));

        feeds.add(rss.createFeed(
                "Svenska Dagbladet",
                "https://www.svd.se",
                "svd",
                newHashMap("rssUrl", "https://www.svd.se/?service=rss"), INVALIDATION_PERIOD, INVALIDATION_PERIOD_UNIT));

        DagensNyheterPlugin dn = new DagensNyheterPlugin();
        feeds.add(dn.createFeed(
                "Dagens Nyheter",
                "https://www.dn.se",
                "dn",
                newHashMap("rssUrl", "http://www.dn.se/nyheter/rss/"), INVALIDATION_PERIOD, INVALIDATION_PERIOD_UNIT));

        feeds.add(rss.createFeed(
                "xkcd",
                "https://xkcd.com",
                "xkcd",
                newHashMap("rssUrl", "https://xkcd.com/atom.xml"), INVALIDATION_PERIOD, INVALIDATION_PERIOD_UNIT));

        feeds.add(rss.createFeed(
                "Ars Technica",
                "https://arstechnica.com/",
                "arstechnica",
                newHashMap("rssUrl", "http://feeds.arstechnica.com/arstechnica/index"), INVALIDATION_PERIOD, INVALIDATION_PERIOD_UNIT));

        feeds.add(rss.createFeed(
                "Reddit",
                "https://reddit.com/r/worldnews",
                "reddit",
                newHashMap("rssUrl", "https://www.reddit.com/r/worldnews.rss"), INVALIDATION_PERIOD, INVALIDATION_PERIOD_UNIT));

        feeds.add(rss.createFeed(
                "TheLocal",
                "https://www.thelocal.se/",
                "thelocal",
                newHashMap("rssUrl", "https://www.thelocal.se/feeds/rss.php"), INVALIDATION_PERIOD, INVALIDATION_PERIOD_UNIT));

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
                newHashMap("username", username), INVALIDATION_PERIOD, INVALIDATION_PERIOD_UNIT);
    }

    public List<Feed> invalidatedFeeds() {
        return allFeeds().stream()
                .filter(Feed::isInvalidated)
                .collect(Collectors.toList());
    }
}
