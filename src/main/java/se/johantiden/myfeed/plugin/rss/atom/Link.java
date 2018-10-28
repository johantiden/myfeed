package se.johantiden.myfeed.plugin.rss.atom;

import javax.xml.bind.annotation.XmlAttribute;

public class Link {
    @XmlAttribute
    public String href;
    @XmlAttribute
    public String rel;
}
