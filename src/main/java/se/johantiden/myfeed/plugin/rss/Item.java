package se.johantiden.myfeed.plugin.rss;

import java.time.Instant;
import java.util.List;

public interface Item {

    String getTitle();

    String getLink();

    String getBody();

    List<Content> getContent();

    Instant getDate();

    Enclosure getEnclosure();

    interface Content {

        String getDescription();

        String getType();

        String getUrl();

        String getMedium();
    }

    interface Enclosure {
        String getUrl();
        String getType();
    }
}
