package se.johantiden.myfeed.plugin;

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
import se.johantiden.myfeed.controller.NameAndUrl;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.util.DocumentPredicates;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class RssFeedReader implements FeedReader {

    private static final Logger log = LoggerFactory.getLogger(RssFeedReader.class);
    private final String rssUrl;
    private final String feedName;
    private final String feedUrl;

    public RssFeedReader(String feedName, String feedUrl, String rssUrl) {
        this.feedName = requireNonNull(feedName);
        this.feedUrl = requireNonNull(feedUrl);
        this.rssUrl = requireNonNull(rssUrl);
    }

    @Override
    public List<Document> readAllAvailable() {

        try {
            return tryReadAllAvailable();
        } catch (RuntimeException e) {
//            throw new RuntimeException("RssFeed '" + feedName + "' failed url:" + rssUrl + "   :", e);
            log.warn("RssFeed '{}' failed url:{}:", feedName, rssUrl, e);
            return new ArrayList<>();
        }
    }

    private List<Document> tryReadAllAvailable() {
        SyndFeed syndFeed = getFeed();

        List<SyndEntry> entries = syndFeed.getEntries();

        return Lists.transform(entries, e -> {
            String title = unescape(e.getTitle());
            String link = e.getLink();
            String imageUrl = getImageUrl(e);
            String authorName = e.getAuthor();
            String authorUrl = getAuthorUrl(e);
            Instant publishedDate = getDate(e);
            Set<String> categories = getCategories(e);
            String descriptionHtml = getDescription(e);
            String contentHtml = getContentHtml(e);
            String text = descriptionHtml == null ? null : html2text(descriptionHtml);
            String html = descriptionHtml == null ? null : descriptionHtml;
            if(html == null) {
                html = contentHtml;
            }
            if(html != null && html.toLowerCase().contains("google-analytics")) {
                throw new IllegalArgumentException("Google? Maybe there is a google analytics link?");
            }

            NameAndUrl author = new NameAndUrl(authorName, authorUrl);

            Document document = new Document(title, text, author, link, imageUrl, publishedDate, html, categories, feedName, feedUrl);

            if(DocumentPredicates.hasEscapeCharacters().test(document)) {
                throw new RuntimeException("Escape characters!");
            }
            return document;
        });
    }

    private static Set<String> getCategories(SyndEntry e) {
        return e.getCategories().stream()
               .map(c -> unescape(c.getName()))
               .collect(Collectors.toSet());
    }

    private static String unescape(String string) {
        String unescaped = string.replaceAll("&#38;", "&");
        unescaped = unescaped.replaceAll("&#34;", "\"");
        unescaped = unescaped.replaceAll("&#039;", "'");
        unescaped = unescaped.replaceAll("&#8216;", "'");
        unescaped = unescaped.replaceAll("&#8217;", "'");
        unescaped = unescaped.replaceAll("&#252;", "ü");
        unescaped = unescaped.replaceAll("â€™", "ü");
        unescaped = unescaped.replaceAll("&amp;", "&");
        unescaped = unescaped.replaceAll("&ndash;", "–");
        unescaped = unescaped.replaceAll("&mdash;", "—");

//        if(!unescaped.equals(string)) {
//            log.debug("Unescaped! {} -> {}", string, unescaped);
//        }

        return unescaped;
    }

    private static String getDescription(SyndEntry e) {
        SyndContent description = e.getDescription();
        if(description == null) {
            return null;
        }
        return description.getValue();
    }

    private static String getContentHtml(SyndEntry e) {
        List<SyndContent> contents = e.getContents();
        if(contents.isEmpty()) {
            return null;
        }
        if(contents.size() > 1) {
            log.warn("More than one content!");
        }

        SyndContent syndContent = contents.get(0);
        return syndContent.getValue();

    }

    private static Instant getDate(SyndEntry e) {
        @SuppressWarnings("UseOfObsoleteDateTimeApi") Date date = e.getPublishedDate();

        if(date == null) {
            date = e.getUpdatedDate();
        }

        if(date == null) {
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
        return unescape(Jsoup.parse(html).text());
    }

    private static String getImageUrl(SyndEntry e) {
        List<SyndEnclosure> enclosures = e.getEnclosures();

        if(enclosures.isEmpty()) {
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

        try (XmlReader reader = getXmlReader()) {
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
