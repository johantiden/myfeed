package se.johantiden.myfeed.reader.group;

import org.junit.Test;

import java.time.Instant;

public class OmniScraperJobTest {
    @Test
    public void name() {
        new OmniScraperJob().run();

    }

    @Test
    public void name2() {
        new OmniScraperJob().consumeArticle("/uppgifter-flera-fel-vid-rostrakningen-i-stockholm/a/Qlb2rR");
    }

    @Test
    public void published() {
        Instant instant = Instant.now();


    }
}