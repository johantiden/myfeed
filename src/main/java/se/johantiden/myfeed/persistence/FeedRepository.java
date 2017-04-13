package se.johantiden.myfeed.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.user.User;
import se.johantiden.myfeed.persistence.user.UserRepository;
import se.johantiden.myfeed.plugin.dn.DagensNyheterPlugin;
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

    private static final Logger log = LoggerFactory.getLogger(FeedRepository.class);
    public static final int REDDIT_MIN_SCORE = 10000;
    private List<Feed> allFeeds;
    public static final Duration INVALIDATION_PERIOD = Duration.ofMinutes(2);

    @Autowired
    private UserRepository userRepository;

    private static List<Feed> allFeedsHack(UserRepository userRepository) {
        List<Feed> feeds = new ArrayList<>();

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
                "https://xkcd.com/atom.xml"));

        feeds.add(createRss(
                "Ars Technica",
                "arstechnica",
                "https://arstechnica.com/",
                "http://feeds.arstechnica.com/arstechnica/index"));

        feeds.add(createRss(
                "HackerNews",
                "hackernews",
                "https://news.ycombinator.com/news",
                "https://news.ycombinator.com/rss"));

        feeds.add(createReddit("r/worldnews", REDDIT_MIN_SCORE));
        feeds.add(createReddit("r/AskReddit", REDDIT_MIN_SCORE));
        feeds.add(createReddit("r/science", REDDIT_MIN_SCORE));
        feeds.add(createReddit("top", REDDIT_MIN_SCORE));
        feeds.add(createReddit("r/all", REDDIT_MIN_SCORE));
        feeds.add(createReddit("r/announcements", REDDIT_MIN_SCORE));
//        feeds.add(createReddit("r/random", REDDIT_MIN_SCORE, Duration.ofMillis(1)));

        feeds.add(createRss(
                "TheLocal",
                "thelocal",
                "https://www.thelocal.se/",
                "https://www.thelocal.se/feeds/rss.php"));

//        feeds.add(createTwitter("pwolodarski"));
        feeds.add(createTwitter("kinbergbatra"));
//        feeds.add(createTwitter("annieloof"));
        feeds.add(createTwitter("deepdarkfears"));
        feeds.add(createTwitter("tastapod"));
        feeds.add(createTwitter("elonmusk"));
        feeds.add(createTwitter("github"));

        Collection<User> allUsers = userRepository.getAllUsers();
        allUsersHasAllFeedsHack(allUsers, feeds);

        return feeds;
    }

    private static void allUsersHasAllFeedsHack(Collection<User> allUsers, List<Feed> feeds) {
        for (Feed feed : feeds) {
            for (User user : allUsers) {
                feed.getFeedUsers().add(new FeedUser(feed, user));
            }

        }
    }

    private static Feed createRss(String feedName, String cssClass, String webUrl, String rssUrl, Predicate<Document> filter) {
        RssPlugin rss = new RssPlugin();
        return rss.createFeed(
                feedName,
                cssClass,
                webUrl,
                newHashMap("rssUrl", rssUrl),
                INVALIDATION_PERIOD,
                filter);
    }
    private static Feed createRss(String feedName, String cssClass, String webUrl, String rssUrl) {
        RssPlugin rss = new RssPlugin();
        return rss.createFeed(
                feedName,
                cssClass,
                webUrl,
                newHashMap("rssUrl", rssUrl), INVALIDATION_PERIOD, null);
    }

    private static Feed createReddit(String subreddit, double minScore) {
        return createReddit(subreddit, minScore, INVALIDATION_PERIOD);
    }

    private static Feed createReddit(String subreddit, double minScore, Duration invalidationPeriod) {
        Predicate<Document> votesPredicate = d -> {
            boolean ok = d.getScore() != null && d.getScore() > minScore;
            return ok;
        };

        return new RedditPlugin().createFeed(
                "Reddit::"+subreddit,
                "reddit", "https://www.reddit.com/" + subreddit,
                newHashMap("rssUrl", "https://www.reddit.com/" + subreddit + "/.rss"), invalidationPeriod,
                votesPredicate);
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
                            boolean equals = feedKey.equals(key);
                            return equals;
                        }
                )
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Could not find feed with key " + feedKey));
    }
}
