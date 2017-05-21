package se.johantiden.myfeed.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.user.UserRepository;
import se.johantiden.myfeed.plugin.AFPPlugin;
import se.johantiden.myfeed.plugin.AlJazeeraPlugin;
import se.johantiden.myfeed.plugin.BreakitPlugin;
import se.johantiden.myfeed.plugin.EngadgetPlugin;
import se.johantiden.myfeed.plugin.ReutersPlugin;
import se.johantiden.myfeed.plugin.dn.DagensNyheterPlugin;
import se.johantiden.myfeed.plugin.hackenews.HackerNewsPlugin;
import se.johantiden.myfeed.plugin.reddit.RedditPlugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;
import se.johantiden.myfeed.plugin.slashdot.SlashdotPlugin;
import se.johantiden.myfeed.plugin.svd.SvenskaDagbladetPlugin;
import se.johantiden.myfeed.plugin.svt.SVTPlugin;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FeedRepository {

    public static final int REDDIT_MIN_SCORE = 20000;
    private List<Feed> allFeeds;
    public static final Duration INVALIDATION_PERIOD = Duration.ofMinutes(5);

    @Autowired
    private UserRepository userRepository;

    private static List<Feed> allFeedsHack(UserRepository userRepository) {
        List<Feed> feeds = new ArrayList<>();

        feeds.add(new HackerNewsPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new SlashdotPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new SvenskaDagbladetPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new DagensNyheterPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new ReutersPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new BreakitPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new AlJazeeraPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new EngadgetPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new AFPPlugin(INVALIDATION_PERIOD).createFeed());

        feeds.add(createRss(
                "xkcd",
                "https://xkcd.com",
                "https://xkcd.com/atom.xml", Duration.ofDays(30)));

        feeds.add(new SVTPlugin(INVALIDATION_PERIOD).createFeed());

        feeds.add(createRss(
                "Ars Technica",
                "https://arstechnica.com/",
                "http://feeds.arstechnica.com/arstechnica/index", INVALIDATION_PERIOD));

        feeds.add(createRss(
                "New York Times - World",
                "https://www.nytimes.com/section/world",
                "https://rss.nytimes.com/services/xml/rss/nyt/World.xml", INVALIDATION_PERIOD));

        feeds.add(createRss(
                "Los Angeles Times - World",
                "http://www.latimes.com",
                "http://www.latimes.com/world/rss2.0.xml", INVALIDATION_PERIOD));


        feeds.add(createReddit("r/worldnews", 1000));
        feeds.add(createReddit("r/AskReddit", 1000));
        feeds.add(createReddit("r/ProgrammerHumor", 600));
        feeds.add(createReddit("r/science", 1000));
        feeds.add(createReddit("top", 1000));
        feeds.add(createReddit("r/all", REDDIT_MIN_SCORE));
        feeds.add(createReddit("r/announcements", 10000));

        feeds.add(createRss(
                "TheLocal",
                "https://www.thelocal.se/",
                "https://www.thelocal.se/feeds/rss.php", INVALIDATION_PERIOD));

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

    private static Feed createRss(String feedName, String webUrl, String rssUrl, Duration invalidationPeriod) {
        RssPlugin rss = new RssPlugin(feedName, webUrl, rssUrl, invalidationPeriod);
        return rss.createFeed();
    }

    private static Feed createReddit(String subreddit, double minScore) {
        return createReddit(subreddit, minScore, INVALIDATION_PERIOD);
    }

    private static Feed createReddit(String subreddit, double minScore, Duration ttl) {
        return new RedditPlugin(subreddit, ttl, scoreMoreThan(minScore)).createFeed();
    }

    private static Predicate<Document> scoreMoreThan(double score) {
        return d -> {
            boolean ok = d.getScore() != null && d.getScore() > score;
            return ok;
        };
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
