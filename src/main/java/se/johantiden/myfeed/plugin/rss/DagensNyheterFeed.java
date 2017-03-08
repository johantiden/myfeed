package se.johantiden.myfeed.plugin.rss;

public class DagensNyheterFeed extends RssFeed {

    public DagensNyheterFeed(String rssUrl, String feedName) {
        super(rssUrl, "dn", feedName, "https://www.dn.se");
    }


    public static DagensNyheterFeed nyheter() {
        return new DagensNyheterFeed("https://www.dn.se/nyheter/rss/", "Dagens Nyheter");
    }

    public static DagensNyheterFeed worldNews() {
        return new DagensNyheterFeed("http://www.dn.se/nyheter/varlden/rss/", "Dagens Nyheter - Världen");
    }
    public static DagensNyheterFeed senasteNytt() {
        return new DagensNyheterFeed("http://www.dn.se/rss/senaste-nytt/", "Dagens Nyheter - Senaste Nytt");
    }
}
