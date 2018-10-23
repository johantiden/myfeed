package se.johantiden.myfeed.plugin.rss;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.plugin.rss.Item.Content;
import se.johantiden.myfeed.util.DocumentPredicates;
import se.johantiden.myfeed.util.Pair;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static se.johantiden.myfeed.util.Pair.pair;

public class RssFeedReader {

    private static final Logger log = LoggerFactory.getLogger(RssFeedReader.class);
    private final String rssUrl;
    private final String feedName;
    private final String feedUrl;
    private final Class<? extends Doc> docClass;

    public RssFeedReader(String feedName, String feedUrl, String rssUrl, Class<? extends Doc> docClass) {
        this.feedName = requireNonNull(feedName);
        this.feedUrl = requireNonNull(feedUrl);
        this.rssUrl = requireNonNull(rssUrl);
        this.docClass = requireNonNull(docClass);
    }

    public List<Pair<Item, Document>> readAllAvailable() {

        try {
            return tryReadAllAvailable();
        } catch (RuntimeException e) {
//            throw new RuntimeException("RssFeed '" + feedName + "' failed url:" + rssUrl + "   :", e);
            log.warn("RssFeed '{}' failed url:{}:", feedName, rssUrl, e);
            return new ArrayList<>();
        }
    }

    private List<Pair<Item, Document>> tryReadAllAvailable() {
        Doc doc = new RssReader().read(getUrl(), docClass);
        if (doc == null) {
            return new ArrayList<>();
        }

        List<Item> entries = doc.getItems();

        return entries.stream().map(e -> {
            String title = unescape(e.getTitle());
            String pageUrl = e.getLink();
            String imageUrl = getImageUrl(e);
            Instant publishedDate = getDate(e);
            String descriptionHtml = getDescription(e);
            String contentHtml = getContentHtml(e);

            String text = descriptionHtml == null ? null : html2text(descriptionHtml);
            String html = descriptionHtml;
            if (html == null) {
                html = contentHtml;
            }
            if (html != null && html.toLowerCase().contains("google-analytics")) {
                throw new IllegalArgumentException("Google? Maybe there is a google analytics link?");
            }

            Document document = createDocument(title, pageUrl, imageUrl, publishedDate, text, html, feedName, feedUrl);

            if (DocumentPredicates.hasEscapeCharacters().test(document)) {
                throw new RuntimeException("Escape characters!");
            }
            return pair(e, document);
        }).collect(Collectors.toList());
    }

    protected Document createDocument(String title, String pageUrl, String imageUrl, Instant publishedDate, String text, String html, String feedName, String feedUrl) {
        return new Document(title, text, pageUrl, imageUrl, publishedDate, feedName, feedUrl);
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
        unescaped = unescaped.replaceAll("â€™", "'");
        unescaped = unescaped.replaceAll("&quot;", "\"");

//        if(!unescaped.equals(string)) {
//            log.debug("Unescaped! {} -> {}", string, unescaped);
//        }

        return unescaped;
    }

    private static String getDescription(Item e) {
        String description = e.getBody();
        if(description == null) {
            return null;
        }
        return description;
    }

    private static String getContentHtml(Item e) {
        List<Content> content = e.getContent();
        if(content == null || content.isEmpty()) {
            return null;
        }
        return content.get(0).getDescription();

    }

    private static Instant getDate(Item e) {
        Instant instant = e.getDate();

        if(instant == null) {
            return Instant.now();
        }

        return instant;
    }

    public static String html2text(String html) {
        return unescape(Jsoup.parse(html).text());
    }

    private static String getImageUrl(Item e) {
        Objects.requireNonNull(e);
        if (e.getContent() == null) {
            return null;
        }

        if (e.getEnclosure() != null && e.getEnclosure().getType() != null && e.getEnclosure().getType().startsWith("image")) {
            String url = e.getEnclosure().getUrl();
            if (url != null) {
                return url;
            }
        }

        String imgUrlFromContent = e.getContent().stream()
                .filter(Objects::nonNull)
                .filter(c -> c.getType() != null && c.getType().startsWith("image") || c.getMedium() != null && c.getMedium().startsWith("image"))
                .map(Content::getUrl)
                .findAny()
                .orElse(null);
        if (imgUrlFromContent != null) {
            return imgUrlFromContent;
        }

        return null;
    }

    private URL getUrl() {
        try {
            return new URL(rssUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
