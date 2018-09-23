package se.johantiden.myfeed.reader.group;

import org.junit.Ignore;
import org.junit.Test;

import java.time.Instant;

public class OmniScraperJobTest {
    @Ignore
    @Test
    public void name() {
        new OmniScraperJob().run();

    }
    @Ignore

    @Test
    public void name2() {
        new OmniScraperJob().consumeArticle("/uppgifter-flera-fel-vid-rostrakningen-i-stockholm/a/Qlb2rR");
    }
}