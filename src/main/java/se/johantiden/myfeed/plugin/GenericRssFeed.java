package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss2Doc;
import se.johantiden.myfeed.plugin.rss.RssFeedReader;
import se.johantiden.myfeed.util.Pair;

import static se.johantiden.myfeed.util.JCollections.map;

public class GenericRssFeed extends Feed {

    public GenericRssFeed(String name, String url, String rssUrl) {
        super(name, url,
                () -> map(Pair::getRight, new RssFeedReader(name, url, rssUrl, Rss2Doc.class).readAllAvailable()));
    }
}
