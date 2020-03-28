package se.johantiden.myfeed.plugin.rss.v2;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rss")
public class Rss2Doc {

    @XmlAttribute
    public String version;

    public Channel channel;

}
