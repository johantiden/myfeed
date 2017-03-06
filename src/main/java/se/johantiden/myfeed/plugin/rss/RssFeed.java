package se.johantiden.myfeed.plugin.rss;

import com.google.common.collect.Lists;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import se.johantiden.myfeed.plugin.Entry;
import se.johantiden.myfeed.plugin.Feed;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class RssFeed implements Feed {

    private final String rssUrl;
    private final String cssClass;
    private final String feedName;
    private final String feedWebUrl;

    public RssFeed(String rssUrl, String cssClass, String feedName, String feedWebUrl) {
        this.feedName = feedName;
        this.feedWebUrl = feedWebUrl;
        this.rssUrl = requireNonNull(rssUrl);
        this.cssClass = cssClass;
    }

    @Override
    public List<Entry> readAllAvailable() {

        SyndFeed feed = getFeed();

        List<SyndEntry> entries = feed.getEntries();

        return Lists.transform(entries, e -> {
            String title = e.getTitle();
            String link = e.getLink();
            String imageUrl = getImageUrl(e);
            String author = e.getAuthor();
            Instant publishedDate = e.getPublishedDate().toInstant();

            return new Entry(feedName, feedWebUrl, title, author, cssClass, link, imageUrl, publishedDate);
        });
    }

    private static String getImageUrl(SyndEntry e) {
        List<SyndEnclosure> enclosures = e.getEnclosures();

        if (enclosures.isEmpty()) {
            return null;
        }

        return enclosures.get(0).getUrl();
    }

    private SyndFeed getFeed() {
        SyndFeedInput input = new SyndFeedInput();
        return getFeed(input);
    }

    private SyndFeed getFeed(SyndFeedInput input) {


        try(XmlReader reader = getXmlReader()) {
            return input.build(reader);
        } catch (FeedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private XmlReader getXmlReader() {
        try {
            URL url = getUrl();
            return new XmlReader(url);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private URL getUrl() {
        try {
            return new URL(rssUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
