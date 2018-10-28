package se.johantiden.myfeed.plugin.rss.v2.atom;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "rss")
public class Rss2AtomDoc {
    public static String PUB_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";

    @XmlAttribute
    public String version;

    public Channel channel;

    public static class Channel {
        public String title;
        public String link;
        public String description;
        List<Item> items;

        @XmlElement(name = "item")
        public void setItems(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }
    }


    public static class Item {
        public String title;
        public String link;
        public String pubDate;
        public String comments;
        public String description;
    }
}
