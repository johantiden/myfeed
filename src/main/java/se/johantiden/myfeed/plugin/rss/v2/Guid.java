package se.johantiden.myfeed.plugin.rss.v2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Guid {

    @XmlAttribute
    public Boolean isPermalink;
    @XmlValue
    public String body;
}
