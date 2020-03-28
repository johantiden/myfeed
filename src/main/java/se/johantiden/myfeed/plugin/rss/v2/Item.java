package se.johantiden.myfeed.plugin.rss.v2;

import se.johantiden.myfeed.plugin.rss.atom.Link;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Item {
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
        public MrssContent.MrssThumbnail thumbnail;

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
