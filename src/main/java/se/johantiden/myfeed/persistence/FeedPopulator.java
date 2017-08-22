package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.plugin.AlJazeeraFeed;
import se.johantiden.myfeed.plugin.BreakitFeed;
import se.johantiden.myfeed.plugin.EngadgetFeed;
import se.johantiden.myfeed.plugin.ReutersFeed;
import se.johantiden.myfeed.plugin.DagensNyheterFeed;
import se.johantiden.myfeed.plugin.HackerNewsFeed;
import se.johantiden.myfeed.plugin.RedditFeed;
import se.johantiden.myfeed.plugin.RssFeed;
import se.johantiden.myfeed.plugin.SlashdotFeed;
import se.johantiden.myfeed.plugin.SvenskaDagbladetFeed;
import se.johantiden.myfeed.plugin.SVTNyheterFeed;
import se.johantiden.myfeed.plugin.WashingtonPostFeed;
import se.johantiden.myfeed.service.FeedService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@SuppressWarnings("SpellCheckingInspection")
public class FeedPopulator {

    public static final int REDDIT_MIN_SCORE = 20000;
    public static final Duration INVALIDATION_PERIOD = Duration.ofMinutes(1);

    private FeedService feedService;

    public FeedPopulator(FeedService feedService) {
        this.feedService = Objects.requireNonNull(feedService);
        allFeedsHack();
    }

    private void allFeedsHack() {
        Objects.requireNonNull(feedService);

        List<Feed> feeds = new ArrayList<>();

        feeds.add(new HackerNewsFeed());
        feeds.add(new SlashdotFeed());
        feeds.add(new SvenskaDagbladetFeed());
        feeds.add(new DagensNyheterFeed());
        feeds.add(new ReutersFeed());
        feeds.add(new BreakitFeed());
        feeds.add(new AlJazeeraFeed());
        feeds.add(new EngadgetFeed());

        feeds.add(createRss(
                "Dagens Industri",
                "https://www.di.se",
                "https://www.di.se/rss"));

        feeds.add(createRss(
                "xkcd",
                "https://xkcd.com",
                "https://xkcd.com/atom.xml"));

        feeds.add(createRss(
                "Elvis",
                "http://www.elvisthecomic.com",
                "http://www.elvisthecomic.com/feed/"));

        feeds.add(new SVTNyheterFeed());

        feeds.add(createRss(
                "New York Times - World",
                "https://www.nytimes.com/section/world",
                "https://rss.nytimes.com/services/xml/rss/nyt/World.xml"));

        feeds.add(createRss(
                "Los Angeles Times - World",
                "http://www.latimes.com",
                "http://www.latimes.com/world/rss2.0.xml"));

        feeds.add(new WashingtonPostFeed(
                INVALIDATION_PERIOD
        ));

        feeds.add(new WashingtonPostFeed(INVALIDATION_PERIOD));

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
                "https://www.thelocal.se/feeds/rss.php"));

        feeds.forEach(feedService::put);
    }

    private static Feed createRss(String feedName, String webUrl, String rssUrl) {
        return new RssFeed(feedName, webUrl, rssUrl);
    }

    private static Feed createReddit(String subreddit, double minScore) {
        return new RedditFeed(subreddit, scoreMoreThan(minScore));
    }

    private static Predicate<Document> scoreMoreThan(double score) {
        return d -> {
            boolean ok = d.getScore() != null && d.getScore() > score;
            return ok;
        };
    }
}
