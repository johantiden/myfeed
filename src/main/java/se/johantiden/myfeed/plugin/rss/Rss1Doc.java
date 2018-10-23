package se.johantiden.myfeed.plugin.rss;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Rss1Doc implements Doc{
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

    public static class Entry implements Item {

        public String title;
        public String link;
        public String id;
        public Author author;
        public Summary summary;
        public String updated;
        public Category category;

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
            return Instant.parse(updated);
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
