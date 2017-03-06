package se.johantiden.myfeed.plugin.rss;

public class TheLocalFeed extends RssFeed {

    public TheLocalFeed() {
        super("http://www.thelocal.se/feeds/rss.php", ".thelocal", "TheLocal", "http://www.thelocal.se");
    }
}
