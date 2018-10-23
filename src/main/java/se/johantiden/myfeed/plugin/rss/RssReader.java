package se.johantiden.myfeed.plugin.rss;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RssReader {

    private static final Logger log = LoggerFactory.getLogger(RssReader.class);

    public static void main(String[] ar) {
        try {
            new RssReader().read(new URL("https://www.dn.se/nyheter/rss/"), Rss2Doc.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Doc read(URL url, Class<? extends Doc> clazz) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            Doc value = xmlMapper.readValue(url, clazz);
            return value;
        } catch (IOException e) {
            log.error("Could not parse document", e);
            return null;
        }
    }

}
