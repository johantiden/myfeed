package se.johantiden.myfeed.plugin.reddit;

import se.johantiden.myfeed.plugin.rss.RssReader;

public class TheLocalReader extends RssReader {

    public TheLocalReader() {
        super("http://www.thelocal.se/feeds/rss.php");
    }
}
