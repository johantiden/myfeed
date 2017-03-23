package se.johantiden.myfeed.plugin.rss;

import com.google.common.collect.Lists;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.FeedReader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class RssFeedReader implements FeedReader {

    private static final Logger log = LoggerFactory.getLogger(RssFeedReader.class);
    private final Feed feed;
    private final String rssUrl;
    private final String cssClass;
    private final String feedName;
    private final String feedWebUrl;

    public RssFeedReader(String rssUrl, String cssClass, String feedName, String feedWebUrl, Feed feed) {
        this.feed = feed;
        this.feedName = feedName;
        this.feedWebUrl = feedWebUrl;
        this.rssUrl = requireNonNull(rssUrl);
        this.cssClass = cssClass;
    }

    @Override
    public List<Document> readAllAvailable() {

        try {
            return tryReadAllAvailable();
        } catch (RuntimeException e) {
            throw new RuntimeException("RssFeed '"+feedName+"' failed:", e);
        }
    }

    private List<Document> tryReadAllAvailable() {
        SyndFeed syndFeed = getFeed();

        List<SyndEntry> entries = syndFeed.getEntries();

        return Lists.transform(entries, e -> {
            String title = e.getTitle();
            String link = e.getLink();
            String imageUrl = getImageUrl(e);
            String author = e.getAuthor();
            String authorUrl = getAuthorUrl(e);
            SyndContent description = e.getDescription();
            String text = description == null ? null : html2text(description.getValue());
            Instant publishedDate = getDate(e);
            String html = description == null ? null : description.getValue();

            return new Document(feed, feedWebUrl, title, text, author, authorUrl, cssClass, link, imageUrl, publishedDate, e.toString(), html);
        });
    }

    private static Instant getDate(SyndEntry e) {
        @SuppressWarnings("UseOfObsoleteDateTimeApi") Date date = e.getPublishedDate();

        if (date == null) {
            date = e.getUpdatedDate();
        }

        if (date == null) {
            return Instant.now();
        }

        return date.toInstant();
    }

    private static String getAuthorUrl(SyndEntry entry) {
        return Optional.ofNullable(entry)
                .map(SyndEntry::getAuthors)
                .flatMap(l -> l.isEmpty() ? Optional.empty() : Optional.ofNullable(l.get(0)))
                .map(SyndPerson::getUri)
                .orElse(null);
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
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
        input.setAllowDoctypes(true);
        input.setXmlHealerOn(true);

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
