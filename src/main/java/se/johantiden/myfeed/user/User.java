package se.johantiden.myfeed.user;

import se.johantiden.myfeed.plugin.Entry;
import se.johantiden.myfeed.plugin.Feed;
import se.johantiden.myfeed.plugin.rss.DagensNyheterFeed;
import se.johantiden.myfeed.plugin.rss.RssFeed;
import se.johantiden.myfeed.plugin.rss.SvenskaDagbladetFeed;
import se.johantiden.myfeed.plugin.rss.TheLocalFeed;
import se.johantiden.myfeed.plugin.twitter.TwitterUserFeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class User {
    private final List<Feed> feeds = new ArrayList<>();
    private final List<Predicate<Entry>> filters = new ArrayList<>();

    public List<Feed> getFeeds() {
        return Collections.unmodifiableList(feeds);
    }

    public void addFeed(Feed feed) {
        feeds.add(feed);
    }

    private void addFilter(Predicate<Entry> filter) {
        filters.add(filter);
    }

    public List<Predicate<Entry>> getFilters() {
        return filters;
    }

    public static User johan() {

        User user = new User();

        johanFeeds(user);
        johanFilters(user);

        return user;
    }

    private static void johanFilters(User user) {

        user.addFilter(filter(s -> {
                    boolean kultur = s.contains("kultur");
                    boolean svd = s.contains("svd.se");
                    boolean svdKultur = svd && kultur;
                    return !svdKultur;
                }
        ));

        user.addFilter(filter(s -> {
                    boolean zlatan = s.contains("zlatan");
                    boolean ibrahimovic = s.contains("ibrahimovic");
                    return !zlatan && !ibrahimovic;
                }
        ));

        user.addFilter(filter(s -> {
                    boolean trump = s.contains("trump");
                    return !trump;
                }
        ));

    }

    private static Predicate<Entry> filter(Predicate<String> searchPredicate) {
        return e -> {
            String document = e.fullSourceEntryForSearch.toLowerCase();
            return searchPredicate.test(document);
        };
    }

    private static void johanFeeds(User user) {
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
        user.addFeed(new RssFeed(
                "http://rss.slashdot.org/Slashdot/slashdotMainatom",
                ".slashdot", "Slashdot",
                "https://slashdot.org"));
        user.addFeed(new SvenskaDagbladetFeed());

        user.addFeed(DagensNyheterFeed.worldNews());


        user.addFeed(new TwitterUserFeed("pwolodarski"));
        user.addFeed(new TwitterUserFeed("BillGates"));
        user.addFeed(new TwitterUserFeed("github"));
    }
}
