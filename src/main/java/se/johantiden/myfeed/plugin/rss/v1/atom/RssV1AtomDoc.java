package se.johantiden.myfeed.plugin.rss.v1.atom;

import se.johantiden.myfeed.plugin.rss.atom.Link;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.List;

@XmlRootElement(name = "feed")
public class RssV1AtomDoc {
    public String title;
    public String id;
    public Link link;
    public String subtitle;
    public String rights;
    public String updated;
    public Author author;
    public Category category;
    public String logo;
    public String icon;

    @XmlElement(namespace = "http://purl.org/rss/1.0/modules/syndication/", name = "updatePeriod")
    public String syndUpdatePeriod;
    @XmlElement(namespace = "http://purl.org/rss/1.0/modules/syndication/", name = "updateFrequency")
    public String syndUpdateFrequency;
    @XmlElement(namespace = "http://purl.org/rss/1.0/modules/syndication/", name = "updateBase")
    public String syndUpdateBase;
    @XmlElement(namespace = "http://rssnamespace.org/feedburner/ext/1.0", name = "info")
    public String feedBurnerInfo;
    private List<Entry> entries;

    public static class Author {
        public String name;
        public String email;
        public String uri;
    }

    public static class Category {
        @XmlAttribute
        public String term;
    }

    @XmlElement(name = "entry")
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public static class Entry {
        public String id;
        public String title;
        public Link link;
        public Summary summary;
        public Content content;
        public String updated;
        public Author author;
        public Category category;

        @XmlElement(namespace = "http://search.yahoo.com/mrss/", name = "thumbnail")
        public String yahooThumbnail;

        @XmlElement(namespace = "http://purl.org/rss/1.0/modules/slash/", name = "department")
        public String slashDepartment;
        @XmlElement(namespace = "http://purl.org/rss/1.0/modules/slash/", name = "section")
        public String slashSection;
        @XmlElement(namespace = "http://purl.org/rss/1.0/modules/slash/", name = "hit_parade")
        public String slashHitParade;
        @XmlElement(namespace = "http://purl.org/rss/1.0/modules/slash/", name = "comments")
        public String slashComments;

        public static class Summary {
            @XmlAttribute
            public String type;
            @XmlValue
            public String body;
        }

        public static class Content {
            @XmlAttribute
            public String type;
            @XmlValue
            public String body;
        }
    }
}
