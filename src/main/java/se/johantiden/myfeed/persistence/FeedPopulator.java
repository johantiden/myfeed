package se.johantiden.myfeed.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.plugin.AlJazeeraPlugin;
import se.johantiden.myfeed.plugin.BreakitPlugin;
import se.johantiden.myfeed.plugin.EngadgetPlugin;
import se.johantiden.myfeed.plugin.ReutersPlugin;
import se.johantiden.myfeed.plugin.DagensNyheterPlugin;
import se.johantiden.myfeed.plugin.HackerNewsPlugin;
import se.johantiden.myfeed.plugin.RedditPlugin;
import se.johantiden.myfeed.plugin.RssPlugin;
import se.johantiden.myfeed.plugin.SlashdotPlugin;
import se.johantiden.myfeed.plugin.SvenskaDagbladetPlugin;
import se.johantiden.myfeed.plugin.SVTPlugin;
import se.johantiden.myfeed.plugin.WashingtonPostPlugin;
import se.johantiden.myfeed.service.FeedService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FeedPopulator {

    public static final int REDDIT_MIN_SCORE = 20000;
    public static final Duration INVALIDATION_PERIOD = Duration.ofMinutes(10);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedService feedService;


    public FeedPopulator() {
        allFeedsHack();
    }

    private void allFeedsHack() {
        List<Feed> feeds = new ArrayList<>();

        feeds.add(new HackerNewsPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new SlashdotPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new SvenskaDagbladetPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new DagensNyheterPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new ReutersPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new BreakitPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new AlJazeeraPlugin(INVALIDATION_PERIOD).createFeed());
        feeds.add(new EngadgetPlugin(INVALIDATION_PERIOD).createFeed());

        feeds.add(createRss(
                "xkcd",
                "https://xkcd.com",
                "https://xkcd.com/atom.xml", Duration.ofDays(30)));

        feeds.add(createRss(
                "Elvis",
                "http://www.elvisthecomic.com",
                "http://www.elvisthecomic.com/feed/", Duration.ofDays(30)));

        feeds.add(new SVTPlugin(INVALIDATION_PERIOD).createFeed());

        feeds.add(createRss(
                "tv-time",
                "http://tv:5000",
                "http://tv:5000/rss", Duration.ofDays(300)));

        feeds.add(createRss(
                "New York Times - World",
                "https://www.nytimes.com/section/world",
                "https://rss.nytimes.com/services/xml/rss/nyt/World.xml", INVALIDATION_PERIOD));

        feeds.add(createRss(
                "Los Angeles Times - World",
                "http://www.latimes.com",
                "http://www.latimes.com/world/rss2.0.xml", INVALIDATION_PERIOD));

        feeds.add(new WashingtonPostPlugin(
                "Washington Post - The Fact Checker",
                "https://www.washingtonpost.com/news/fact-checker/",
                "http://feeds.washingtonpost.com/rss/rss_fact-checker", INVALIDATION_PERIOD)
                    .createFeed());

        feeds.add(new WashingtonPostPlugin(
                "Washington Post - WorldViews",
                "https://www.washingtonpost.com/news/worldviews/",
                "http://feeds.washingtonpost.com/rss/rss_blogpost", INVALIDATION_PERIOD)
                    .createFeed());

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

        Iterable<User> allUsers = userRepository.findAll();
        allUsersHasAllFeedsHack(allUsers, feeds);

        feeds.forEach(feedService::put);
    }

    private static void allUsersHasAllFeedsHack(Iterable<User> allUsers, List<Feed> feeds) {
        for (Feed feed : feeds) {
            allUsers.forEach(u -> feed.getFeedUsers().add(u));
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
}
