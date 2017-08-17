package se.johantiden.myfeed.plugin;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RedditFeedTest {

    @Test
    public void testImgur() throws Exception {


        assertThat(RedditFeed.getRawImgurUrl("http://i.imgur.com/4SYSJEX.gifv"), is("http://i.imgur.com/4SYSJEX"));
        assertThat(RedditFeed.getRawImgurUrl("http://i.imgur.com/dVGvzy6.gifv"), is("http://i.imgur.com/dVGvzy6"));

    }


}