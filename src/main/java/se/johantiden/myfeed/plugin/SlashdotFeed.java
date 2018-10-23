package se.johantiden.myfeed.plugin;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import org.jsoup.Jsoup;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.plugin.rss.Doc;
import se.johantiden.myfeed.plugin.rss.Item;
import se.johantiden.myfeed.plugin.rss.RssFeedReader;
import se.johantiden.myfeed.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SlashdotFeed extends Feed {

    public static final String URL = "https://slashdot.org";
    public static final String NAME = "Slashdot";
    public static final String URL_RSS = "http://rss.slashdot.org/Slashdot/slashdotMainatom";

    public SlashdotFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Pair<Item, Document>> documents = new RssFeedReader(NAME, URL, URL_RSS, SlashdotDoc.class).readAllAvailable();
            return documents.stream().map(Pair::getRight).collect(Collectors.toList());
        };
    }


    public static class SlashdotDoc implements Doc {
        public String lang;
        public String title;
        public String id;
        public Link link;
        public String subtitle;
        public String rights;
        public String updated;
        public Author author;
        public Category category;

        public String updatePeriod;
        public String updateFrequency;
        public String updateBase;
        public String logo;
        public String info;

        private List<Item> items = new ArrayList<>();


        public void setEntry(Entry entry) {
            items.add(entry);
        }

        static class Entry implements Item {

            public String title;
            public String link;
            public String id;
            public Author author;
            public Entry.Summary summary;
            public String updated;
            public Category category;

            public String department;
            public String section;
            public String comments;
            public String hit_parade;

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
                return summary.body;
            }

            @Override
            public List<Content> getContent() {
                return null;
            }

            @Override
            public Instant getDate() {
                return instantFromUpdated(updated);
            }

            @Override
            public Enclosure getEnclosure() {
                return null;
            }

            public static String PUB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
            public static Instant instantFromUpdated(String updated) {
                Instant instant = null;
                try {
                    instant = new SimpleDateFormat(PUB_DATE_FORMAT).parse(updated).toInstant();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return instant;
            }

            public static class Summary {
                @JacksonXmlProperty(isAttribute = true)
                public String type;
                @JacksonXmlText
                public String body;
            }
        }



        @Override
        public List<Item> getItems() {
            return items;
        }

        public static class Author {
            public String name;
            public String email;
        }

        public static class Category {
            public String term;
        }

        public static class Link {
            public String href;
            public String rel;
            public String type;
        }
    }

}
