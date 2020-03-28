package se.johantiden.myfeed.plugin.rss.v2;

import se.johantiden.myfeed.plugin.rss.atom.Link;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Channel {
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

    public String uri;
    public String pubDate;

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
