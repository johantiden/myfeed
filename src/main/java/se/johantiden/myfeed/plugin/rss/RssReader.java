package se.johantiden.myfeed.plugin.rss;

import com.google.common.collect.Lists;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import se.johantiden.myfeed.output.OutputBean;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class RssReader {


    private final String rssUrl;

    public RssReader(String rssUrl) {
        this.rssUrl = requireNonNull(rssUrl);
    }

    public List<OutputBean> readAll() {

        SyndFeed feed = getFeed();

        List<SyndEntry> entries = feed.getEntries();

        return Lists.transform(entries, e -> {
            String title = e.getTitle();
            String link = e.getLink();
            String imageUrl = getImageUrl(e);
            Instant publishedDate = e.getPublishedDate().toInstant();

            return new OutputBean(title, link, imageUrl, publishedDate);
        });
    }

    private String getImageUrl(SyndEntry e) {
        List<SyndEnclosure> enclosures = e.getEnclosures();

        if (enclosures.isEmpty()) {
            return null;
        }

        return enclosures.get(0).getUrl();
    }

    private SyndFeed getFeed() {
        SyndFeedInput input = new SyndFeedInput();
        XmlReader reader = getXmlReader();
        return getFeed(input, reader);
    }

    private SyndFeed getFeed(SyndFeedInput input, XmlReader reader) {
        try {
            return input.build(reader);
        } catch (FeedException e) {
            throw new RuntimeException(e);
        }
    }

    private XmlReader getXmlReader() {
        try {
            return new XmlReader(getUrl());
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
