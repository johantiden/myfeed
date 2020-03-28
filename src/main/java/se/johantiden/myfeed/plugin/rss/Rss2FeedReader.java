package se.johantiden.myfeed.plugin.rss;

import se.johantiden.myfeed.plugin.rss.v2.Rss2Doc;
import se.johantiden.myfeed.plugin.rss.v2.Item;

import java.util.List;

public abstract class Rss2FeedReader extends RssFeedReader<Rss2Doc, Item> {
    protected Rss2FeedReader(String rssUrl) {
        super(rssUrl, Rss2Doc.class);
    }

    @Override
    protected List<Item> getItems(Rss2Doc doc) {
        return doc.channel.getItems();
    }
}
