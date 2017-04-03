package se.johantiden.myfeed.reader;

import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.PluginType;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.dn.DagensNyheterPlugin;
import se.johantiden.myfeed.plugin.reddit.RedditPlugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;
import se.johantiden.myfeed.plugin.svd.SvenskaDagbladetPlugin;
import se.johantiden.myfeed.plugin.twitter.TwitterPlugin;

import java.util.List;

public class FeedReaderService {

    public List<Document> readAll(Feed feed) {
        FeedReader reader = findFeedReader(feed);
        return reader.readAllAvailable();
    }

    private static FeedReader findFeedReader(Feed feed) {
        PluginType type = feed.getType();
        switch (type) {
            case RSS:
                return new RssPlugin().createFeedReader(feed);
            case TWITTER:
                return new TwitterPlugin().createFeedReader(feed);
            case DAGENS_NYHETER:
                return new DagensNyheterPlugin().createFeedReader(feed);
            case REDDIT:
                return new RedditPlugin().createFeedReader(feed);
            case SVENSKA_DAGBLADET:
                return new SvenskaDagbladetPlugin().createFeedReader(feed);
            case NONE:
                return new EmptyReader();
            default:
                throw new IllegalArgumentException("Plugin support not implemented! plugin:" + type.name());
        }
    }
}
