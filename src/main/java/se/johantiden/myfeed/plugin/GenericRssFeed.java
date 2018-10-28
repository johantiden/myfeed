package se.johantiden.myfeed.plugin;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Rss2Doc.Item;

public class GenericRssFeed extends Feed {

    public GenericRssFeed(String name, String url, String rssUrl) {
        super(name, url, new MyRss2FeedReader(rssUrl));
    }

    private static class MyRss2FeedReader extends Rss2FeedReader {
        MyRss2FeedReader(String rssUrl) {super(rssUrl);}

        @Override
        public Document toDocument(Item item) {
            throw new RuntimeException();
        }
    }
}
