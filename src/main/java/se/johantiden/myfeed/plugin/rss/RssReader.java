package se.johantiden.myfeed.plugin.rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.plugin.rss.v1.atom.RssV1AtomDoc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RssReader<T> {

    private static final Logger log = LoggerFactory.getLogger(RssReader.class);

    public static void main(String[] ar) {
        try {
            RssV1AtomDoc read = read(new URL("http://rss.slashdot.org/Slashdot/slashdotMainatom"), RssV1AtomDoc.class);
            return;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T read(URL url, Class<T> clazz) {

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            ValidationEventHandler defaultEventHandler = unmarshaller.getEventHandler();
            unmarshaller.setEventHandler(e -> {
                boolean b = defaultEventHandler.handleEvent(e);
                if (e.getSeverity() >= ValidationEvent.WARNING) {
                    if (e.getSeverity() == ValidationEvent.WARNING) {
                        boolean a = true;
                    }
                    return false;
                }
                return b;

            });

            Object unmarshal = unmarshaller.unmarshal(url);

            return clazz.cast(unmarshal);
        } catch (JAXBException e) {
            log.error("Could not parse document", e);
            return null;
        }
    }


    public static <T> T read(InputStream input, Class<T> clazz) {

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            ValidationEventHandler defaultEventHandler = unmarshaller.getEventHandler();
            unmarshaller.setEventHandler(e -> {
                boolean b = defaultEventHandler.handleEvent(e);
                if (e.getSeverity() >= ValidationEvent.WARNING) {
                    if (e.getSeverity() == ValidationEvent.WARNING) {
                        boolean a = true;
                    }
                    return false;
                }
                return b;

            });

            Object unmarshal = unmarshaller.unmarshal(input);

            return clazz.cast(unmarshal);
        } catch (JAXBException e) {
            log.error("Could not parse document", e);
            return null;
        }
    }
}
