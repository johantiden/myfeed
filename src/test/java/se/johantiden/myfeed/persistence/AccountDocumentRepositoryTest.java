package se.johantiden.myfeed.persistence;

import org.junit.Test;
import se.johantiden.myfeed.util.Chrono;

import java.time.Duration;
import java.time.Instant;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountDocumentRepositoryTest {


    @Test
    public void testSuperOld() throws Exception {

        boolean olderThan = Chrono.isOlderThan(Duration.ofDays(2), Instant.ofEpochMilli(0));
        assertThat(olderThan, is(true));


    }
    @Test
    public void testFresh() throws Exception {

        boolean olderThan = Chrono.isOlderThan(Duration.ofDays(2), Instant.now());
        assertThat(olderThan, is(false));


    }
}