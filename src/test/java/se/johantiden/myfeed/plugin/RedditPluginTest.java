package se.johantiden.myfeed.plugin;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RedditPluginTest {

    @Test
    public void testImgur() throws Exception {


        assertThat(RedditPlugin.getRawImgurUrl("http://i.imgur.com/4SYSJEX.gifv"), is("http://i.imgur.com/4SYSJEX"));
        assertThat(RedditPlugin.getRawImgurUrl("http://i.imgur.com/dVGvzy6.gifv"), is("http://i.imgur.com/dVGvzy6"));

    }


}