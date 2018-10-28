package se.johantiden.myfeed.plugin.rss;

import se.johantiden.myfeed.plugin.rss.v1.atom.RssV1AtomDoc;
import se.johantiden.myfeed.plugin.rss.v1.atom.RssV1AtomDoc.Entry;

import java.util.List;

public abstract class Rss1AtomFeedReader extends RssFeedReader<RssV1AtomDoc, Entry> {
    protected Rss1AtomFeedReader(String rssUrl) {
        super(rssUrl, RssV1AtomDoc.class);
    }

    @Override
    protected List<Entry> getItems(RssV1AtomDoc doc) {
        return doc.getEntries();
    }
}
