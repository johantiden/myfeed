package se.johantiden.myfeed.plugin.rss.v2;

import se.johantiden.myfeed.plugin.rss.atom.Link;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlValue;
import java.util.List;
@XmlRootElement(name = "rss")
public class Rss2Doc {

    @XmlAttribute
    public String version;

    public Channel channel;

    public static class Channel {
        public String title;
        public String link;
        public String description;
        public String category;
        public String language;
        public String copyright;
        public String docs;
        public String managingEditor;
        public String webMaster;
        public String generator;
        public String lastBuildDate;
        public String ttl;
        @XmlElement(namespace = "http://www.w3.org/2005/Atom", name = "link")
        public Link atomLink;
        public Image image;

        @XmlElement(namespace = "http://rssnamespace.org/feedburner/ext/1.0", name = "info")
        public String feedBurnerInfo;
        @XmlElement(namespace = "http://rssnamespace.org/feedburner/ext/1.0", name = "feedburnerHostname")
        public String feedburnerHostname;
        @XmlElement(namespace = "http://rssnamespace.org/feedburner/ext/1.0", name = "feedFlare")
        public String feedBurnerFeedFlare;
        @XmlElement(namespace = "http://rssnamespace.org/feedburner/ext/1.0", name = "emailServiceId")
        public String feedBurnerEmailServiceId;

        @XmlElement(namespace = "http://purl.org/rss/1.0/modules/syndication/", name = "updatePeriod")
        public String syndUpdatePeriod;
        @XmlElement(namespace = "http://purl.org/rss/1.0/modules/syndication/", name = "updateFrequency")
        public String syndUpdateFrequency;
        @XmlElement(namespace = "http://purl.org/rss/1.0/modules/syndication/", name = "updateBase")
        public String syndUpdateBase;


        List<Item> items;

        public List<Item> getItems() {
            return items;
        }

        @XmlElement(name = "item")
        public void setItems(List<Item> items) {
            this.items = items;
        }

    }

    public static class Item {
        public String title;

        public String link;
        public String pubDate;
        public String comments;
        public String description;
        public Guid guid;
        public String category;
        public Enclosure enclosure;

        @XmlElement(namespace = "https://purl.org/dc/elements/1.1/", name = "creator")
        public String dcCreator;
        @XmlElement(namespace = "http://purl.org/dc/elements/1.1/", name = "creator")
        public String dcCreator2;

        @XmlElement(namespace = "https://purl.org/dc/elements/1.1/", name = "identifier")
        public String dcIdentifier;
        @XmlElement(namespace = "http://purl.org/dc/elements/1.1/", name = "date")
        public String dcDate;
        @XmlElement(namespace = "http://search.yahoo.com/mrss/", name = "content")
        public MrssContent mrssContent;
        @XmlElement(namespace = "http://search.yahoo.com/mrss/", name = "description")
        public String mrssDescription;

        @XmlElement(namespace = "http://search.yahoo.com/mrss/", name = "credit")
        public MrssCredit credit;

        @XmlElement(namespace = "http://rssnamespace.org/feedburner/ext/1.0", name = "origLink")
        public String feedBurnerOrigLink;

        @XmlElement(namespace = "http://www.w3.org/2005/Atom", name = "link")
        public Link atomLink;

        public static class MrssContent {
            @XmlAttribute
            public String type;
            @XmlAttribute
            public String url;
            @XmlAttribute
            public String medium;
            @XmlAttribute
            public Integer height;
            @XmlAttribute
            public Integer width;


            @XmlElement(namespace = "http://search.yahoo.com/mrss/", name = "credit")
            public MrssCredit credit;

            @XmlElement(namespace = "http://search.yahoo.com/mrss/", name = "description")
            public String description;

            @XmlElement(namespace = "http://search.yahoo.com/mrss/", name = "thumbnail")
            public MrssThumbnail thumbnail;

            public static class MrssThumbnail {

                @XmlAttribute
                public String url;
                @XmlAttribute
                public Integer width;
            }
        }

        public static class Enclosure {
            @XmlAttribute
            public String url;
            @XmlAttribute
            public Integer length;
            @XmlAttribute
            public String type;
        }
    }

    public static class MrssCredit {
        @XmlAttribute
        public String role;
        @XmlAttribute
        public String scheme;
        @XmlValue
        public String body;
    }

    public static class Image {

        public String title;
        public String url;
        public String link;
        public Integer width;
        public Integer height;
        public String description;

    }

    public static class Guid {

        @XmlAttribute
        public Boolean isPermalink;
        @XmlValue
        public String body;
    }
}
