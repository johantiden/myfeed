package se.johantiden.myfeed.persistence;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Feed {

    private final String name;
    private final String webUrl;
    private final PluginType type;
    private final Map<String, String> feedReaderParameters;
    private final List<FeedUser> feedUsers;
    private final String cssClass;
    private Instant lastRead = Instant.EPOCH;

    public Feed(PluginType type, String name, String webUrl, Map<String, String> feedReaderParameters, String cssClass) {
        this.name = name;
        this.webUrl = webUrl;
        this.type = type;
        this.feedReaderParameters = feedReaderParameters;
        this.cssClass = cssClass;
        this.feedUsers = new ArrayList<>();
    }


    public PluginType getType() {
        return type;
    }

    public Map<String, String> getFeedReaderParameters() {
        return feedReaderParameters;
    }

    public List<FeedUser> getFeedUsers() {
        return feedUsers;
    }

    public String getCssClass() {
        return cssClass;
    }

    public Instant getLastRead() {
        return lastRead;
    }

    public String getName() {
        return name;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setLastRead(Instant lastRead) {
        this.lastRead = lastRead;
    }
}
