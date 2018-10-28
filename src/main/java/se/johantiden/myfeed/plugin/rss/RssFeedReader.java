package se.johantiden.myfeed.plugin.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.plugin.FeedReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public abstract class RssFeedReader<T, I> implements FeedReader {

    private static final Logger log = LoggerFactory.getLogger(RssFeedReader.class);
    private final String rssUrl;
    private final Class<T> feedClass;

    protected RssFeedReader(String rssUrl, Class<T> feedClass) {
        this.rssUrl = requireNonNull(rssUrl);
        this.feedClass = requireNonNull(feedClass);
    }

    public abstract Document toDocument(I item);
    protected abstract List<I> getItems(T doc);


    public List<Document> readAllAvailable() {

        try {
            return tryReadAllAvailable();
        } catch (RuntimeException e) {
            log.warn("RssFeed failed. url:{}:", rssUrl, e);
            return new ArrayList<>();
        }
    }

    private List<Document> tryReadAllAvailable() {
        T doc = RssReader.read(getUrl(), feedClass);
        if (doc == null) {
            return new ArrayList<>();
        }

        return getItems(doc).stream().map(this::toDocument).collect(Collectors.toList());
    }

    private URL getUrl() {
        try {
            return new URL(rssUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
