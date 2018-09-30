package se.johantiden.myfeed.reader.group;

import org.junit.Ignore;
import org.junit.Test;
import se.johantiden.myfeed.plugin.OmniFeed;
import se.johantiden.myfeed.service.DocumentService;

import static org.mockito.Mockito.mock;

public class OmniFeedTest {
    @Ignore
    @Test
    public void name() {
        OmniFeed.run(mock(DocumentService.class));

    }

    @Ignore
    @Test
    public void name2() {
        OmniFeed.scrapeArticle("/uppgifter-flera-fel-vid-rostrakningen-i-stockholm/a/Qlb2rR", mock(DocumentService.class));
    }
}