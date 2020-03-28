package se.johantiden.myfeed.plugin.rss.v2;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class MrssCredit {
    @XmlAttribute
    public String role;
    @XmlAttribute
    public String scheme;
    @XmlValue
    public String body;
}
