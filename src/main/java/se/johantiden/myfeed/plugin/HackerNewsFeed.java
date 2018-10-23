package se.johantiden.myfeed.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Doc;
import se.johantiden.myfeed.plugin.rss.Item;
import se.johantiden.myfeed.plugin.rss.Rss2Doc;
import se.johantiden.myfeed.plugin.rss.RssFeedReader;
import se.johantiden.myfeed.util.Pair;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HackerNewsFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(HackerNewsFeed.class);
    public static final String NAME = "HackerNews";
    public static final String URL = "https://news.ycombinator.com/news";
    public static final String URL_RSS = "https://news.ycombinator.com/rss";

    public HackerNewsFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Pair<Item, Document>> documents = new RssFeedReader(NAME, URL, URL_RSS, HackerNewsDoc.class).readAllAvailable();
            return documents.stream().map(Pair::getRight).collect(Collectors.toList());
        };
    }

    private static class HackerNewsDoc implements Doc {
        public String version;
        public String title;
        public Channel channel;

        @Override
        public List<Item> getItems() {
            return channel.items;
        }

        private static class Channel {
            public String title;
            public String link;
            public String description;

            public List<se.johantiden.myfeed.plugin.rss.Item> items = new ArrayList<>();

            public void setItem(Channel.Item item) {
                this.items.add(item);
            }

            static class Item implements se.johantiden.myfeed.plugin.rss.Item {
                public String title;
                public String link;
                public String description;
                public String pubDate;
                public String date;
                public String guid;
                public String creator;
                public List<se.johantiden.myfeed.plugin.rss.Item.Content> content = new ArrayList<>();


                public void setComments(String commentsUrl) {
                    this.link = commentsUrl;
                }

                public void setLink(String link) {
                }

                public void setContent(Channel.Item.Content content) {
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
                    return Rss2Doc.instantFromPubDate(pubDate);
                }

                static class Content implements se.johantiden.myfeed.plugin.rss.Item.Content {
                    public String type;
                    public String url;
                    public String credit;
                    public String description;
                    public String thumbnail;

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
                        return null;
                    }
                }
            }
        }


    }

}
