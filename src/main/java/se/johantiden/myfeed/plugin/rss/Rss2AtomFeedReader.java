package se.johantiden.myfeed.plugin.rss;

import se.johantiden.myfeed.plugin.rss.v2.atom.Rss2AtomDoc;
import se.johantiden.myfeed.plugin.rss.v2.atom.Rss2AtomDoc.Item;

import java.util.List;

public abstract class Rss2AtomFeedReader extends RssFeedReader<Rss2AtomDoc, Item> {
    protected Rss2AtomFeedReader(String rssUrl) {
        super(rssUrl, Rss2AtomDoc.class);
    }

    @Override
    protected List<Item> getItems(Rss2AtomDoc doc) {
        return doc.channel.getItems();
    }
}
