package se.johantiden.myfeed.plugin.rss;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Rss2Doc implements Doc {
    private static final Logger log = LoggerFactory.getLogger(Rss2Doc.class);
    public String version;
    public String title;
    public Channel channel;

    // Mon, 22 Oct 2018 22:32:37 +0000
    public static String PUB_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";
    public static Instant instantFromPubDate(String pubDate) {
        Instant instant = null;
        try {
            instant = new SimpleDateFormat(PUB_DATE_FORMAT).parse(pubDate).toInstant();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return instant;
    }

    @Override
    public List<Item> getItems() {
        return channel.items;
    }

    static class Channel {
        public String title;
        public String link;
        public String description;
        public String language;
        public String copyright;
        public String lastBuildDate;
        public String info;
        public String docs;
        public String managingEditor;
        public String webMaster;
        public String generator;
        public Image image;
        public Category category;
        public String ttl;

        @JacksonXmlProperty(namespace = "feedburner", localName = "emailServiceId")
        public String emailServiceId;

        @JacksonXmlProperty(namespace = "feedburner", localName = "feedburnerHostname")
        public String feedburnerHostname;

        @JacksonXmlProperty(namespace = "feedburner", localName = "feedFlare")
        public String feedFlare;

        public List<se.johantiden.myfeed.plugin.rss.Item> items = new ArrayList<>();

        public void setItem(Channel.Item item) {
            this.items.add(item);
        }

        static class Item implements se.johantiden.myfeed.plugin.rss.Item {
            public String title;
            public String link;
            public String description;
            public Instant pubDate;
            public Instant date;
            public Guid guid;
            public String creator;
            public String category;
            public String comments;
            public Enclosure enclosure;

            @JacksonXmlProperty(namespace = "feedburner", localName = "origLink")
            public String feedBurnerOrigLink;
            @JacksonXmlProperty(namespace = "dc", localName = "identifier")
            public String dcIdentifier;
            @JacksonXmlProperty(namespace = "media", localName = "credit")
            public String credit;

            public List<se.johantiden.myfeed.plugin.rss.Item.Content> content = new ArrayList<>();

            public void setPubDate(String pubDate) {
                this.pubDate = instantFromPubDate(pubDate);
            }

            public void setDate(String date) {
                this.date = Instant.parse(date);
            }

            public void setContent(Content content) {
                this.content.add(content);
            }

            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public String getLink() {
                return link;
            }

            @Override
            public String getBody() {
                return description;
            }

            @Override
            public List<se.johantiden.myfeed.plugin.rss.Item.Content> getContent() {
                return content;
            }

            @Override
            public Instant getDate() {
                if (pubDate != null) {
                    return pubDate;
                }

                if (date != null) {
                    return date;
                }

                log.warn("no date returned!");
                return null;
            }

            @Override
            public se.johantiden.myfeed.plugin.rss.Item.Enclosure getEnclosure() {
                return enclosure;
            }

            static class Content implements se.johantiden.myfeed.plugin.rss.Item.Content {
                public String type;
                public String url;
                public String credit;
                public String description;
                public String medium;
                public String thumbnail;
                public int width;
                public int height;

                @Override
                public String getDescription() {
                    return description;
                }

                @Override
                public String getType() {
                    return type;
                }

                @Override
                public String getUrl() {
                    return url;
                }

                @Override
                public String getMedium() {
                    return medium;
                }
            }

            public static class Guid {
                @JacksonXmlProperty(isAttribute = true)
                public boolean isPermaLink;
                @JacksonXmlText
                public String body;
            }

            public static class Enclosure implements se.johantiden.myfeed.plugin.rss.Item.Enclosure {
                String url;
                String length;
                String type;

                @Override
                public String getUrl() {
                    return url;
                }

                @Override
                public String getType() {
                    return type;
                }
            }
        }

        public static class Image {
            public String link;
            public String title;
            public String url;
            public String description;
            public int width;
            public int height;
        }

        public static class Category {
            @JacksonXmlProperty(isAttribute = true)
            public String domain;
            @JacksonXmlText
            public String body;
        }
    }


}
