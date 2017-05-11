package se.johantiden.myfeed.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.user.UserRepository;
import se.johantiden.myfeed.plugin.dn.DagensNyheterPlugin;
import se.johantiden.myfeed.plugin.hackenews.HackerNewsPlugin;
import se.johantiden.myfeed.plugin.reddit.RedditPlugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;
import se.johantiden.myfeed.plugin.slashdot.SlashdotPlugin;
import se.johantiden.myfeed.plugin.svd.SvenskaDagbladetPlugin;
import se.johantiden.myfeed.plugin.twitter.TwitterPlugin;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static se.johantiden.myfeed.util.Maps2.newHashMap;

public class FeedRepository {

    public static final int REDDIT_MIN_SCORE = 20000;
    private List<Feed> allFeeds;
    public static final Duration INVALIDATION_PERIOD = Duration.ofMinutes(5);

    @Autowired
    private UserRepository userRepository;

    private static List<Feed> allFeedsHack(UserRepository userRepository) {
        List<Feed> feeds = new ArrayList<>();

        feeds.add(new HackerNewsPlugin().createFeed(
                "HackerNews",
                "hackernews",
                "https://news.ycombinator.com/news",
                newHashMap("rssUrl", "https://news.ycombinator.com/rss"),
                INVALIDATION_PERIOD,
                null));

        feeds.add(new SlashdotPlugin().createFeed(
                "Slashdot",
                "slashdot",
                "https://slashdot.org",
                newHashMap("rssUrl", "http://rss.slashdot.org/Slashdot/slashdotMainatom"),
                INVALIDATION_PERIOD, null));

        feeds.add(new SvenskaDagbladetPlugin().createFeed(
                "Svenska Dagbladet",
                "svd",
                "https://www.svd.se",
                newHashMap("rssUrl", "https://www.svd.se/?service=rss"),
                INVALIDATION_PERIOD,
                d -> !d.isPaywalled));

        DagensNyheterPlugin dn = new DagensNyheterPlugin();
        feeds.add(dn.createFeed(
                "Dagens Nyheter",
                "dn",
                "https://www.dn.se",
                newHashMap("rssUrl", "http://www.dn.se/nyheter/rss/"), INVALIDATION_PERIOD, null));

        feeds.add(createRss(
                "xkcd",
                "xkcd",
                "https://xkcd.com",
                "https://xkcd.com/atom.xml", Duration.ofDays(30)));

        feeds.add(createRss(
                "SVT Nyheter",
                "svt",
                "https://www.svt.se/nyheter",
                "https://www.svt.se/nyheter/rss.xml", INVALIDATION_PERIOD));

        feeds.add(createRss(
                "Ars Technica",
                "arstechnica",
                "https://arstechnica.com/",
                "http://feeds.arstechnica.com/arstechnica/index", INVALIDATION_PERIOD));

        feeds.add(createRss(
                "Al Jazeera",
                "aljazeera",
                "http://www.aljazeera.com",
                "http://www.aljazeera.com/xml/rss/all.xml", INVALIDATION_PERIOD));

        feeds.add(createRss(
                "New York Times :: World",
                "nyt",
                "https://www.nytimes.com/section/world",
                "https://rss.nytimes.com/services/xml/rss/nyt/World.xml", INVALIDATION_PERIOD));

        feeds.add(createReddit("r/worldnews", REDDIT_MIN_SCORE));
        feeds.add(createReddit("r/AskReddit", REDDIT_MIN_SCORE));
        feeds.add(createReddit("r/science", REDDIT_MIN_SCORE));
        feeds.add(createReddit("top", REDDIT_MIN_SCORE));
        feeds.add(createReddit("r/all", REDDIT_MIN_SCORE));
        feeds.add(createReddit("r/announcements", REDDIT_MIN_SCORE));

        feeds.add(createRss(
                "TheLocal",
                "thelocal",
                "https://www.thelocal.se/",
                "https://www.thelocal.se/feeds/rss.php", INVALIDATION_PERIOD));

        feeds.add(createRss(
                "9gag",
                "9gag",
                "https://9gag.com/",
                "http://9gagrss.com/feed/", INVALIDATION_PERIOD));

//        feeds.add(createTwitter("pwolodarski"));
//        feeds.add(createTwitter("kinbergbatra"));
//        feeds.add(createTwitter("annieloof"));
//        feeds.add(createTwitter("deepdarkfears"));
//        feeds.add(createTwitter("tastapod"));
//        feeds.add(createTwitter("elonmusk"));
//        feeds.add(createTwitter("github"));

        Collection<Username> allUsers = userRepository.getAllUsers();
        allUsersHasAllFeedsHack(allUsers, feeds);

        return feeds;
    }

    private static void allUsersHasAllFeedsHack(Collection<Username> allUsers, List<Feed> feeds) {
        for (Feed feed : feeds) {
            for (Username user : allUsers) {
                feed.getFeedUsers().add(new FeedUser(feed, user));
            }

        }
    }

    private static Feed createRss(String feedName, String cssClass, String webUrl, String rssUrl, Duration invalidationPeriod) {
        RssPlugin rss = new RssPlugin();
        return rss.createFeed(
                feedName,
                cssClass,
                webUrl,
                newHashMap("rssUrl", rssUrl), invalidationPeriod, null);
    }

    private static Feed createReddit(String subreddit, double minScore) {
        return createReddit(subreddit, minScore, INVALIDATION_PERIOD);
    }

    private static Feed createReddit(String subreddit, double minScore, Duration invalidationPeriod) {
        return new RedditPlugin().createFeed(
                "Reddit::"+subreddit,
                "reddit", "https://www.reddit.com/" + subreddit,
                newHashMap("rssUrl", "https://www.reddit.com/" + subreddit + "/.rss"), invalidationPeriod,
                scoreMoreThan(minScore));
    }

    private static Predicate<Document> scoreMoreThan(double score) {
        return d -> {
            boolean ok = d.getScore() != null && d.getScore() > score;
            return ok;
        };
    }


    private static Feed createTwitter(String username) {
        TwitterPlugin twitter = new TwitterPlugin();
        return twitter.createFeed(
                "Twitter",
                "twitter", "https://twitter.com/" + username,
                newHashMap("username", username), INVALIDATION_PERIOD, null);
    }

    public List<Feed> allFeeds() {
        if (allFeeds == null) {
            allFeeds = allFeedsHack(userRepository);
        }

        return allFeeds;
    }

    public List<Feed> invalidatedFeeds() {
        return allFeeds().stream()
                .filter(Feed::isInvalidated)
                .collect(Collectors.toList());
    }

    public Feed get(Key<Feed> feedKey) {
        return allFeeds().stream()
                .filter(
                        f -> {
                            Key<Feed> key = f.getKey();
                            return feedKey.equals(key);
                        }
                )
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Could not find feed with key " + feedKey));
    }
}
