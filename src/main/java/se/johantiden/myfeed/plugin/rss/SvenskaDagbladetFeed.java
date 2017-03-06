package se.johantiden.myfeed.plugin.rss;

public class SvenskaDagbladetFeed extends RssFeed {

    public SvenskaDagbladetFeed() {
        super("https://www.svd.se/?service=rss", ".svd", "Svenska Dagbladet", "https://www.svd.se");
    }
}
