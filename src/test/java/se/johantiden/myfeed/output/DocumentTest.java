package se.johantiden.myfeed.output;

import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static se.johantiden.myfeed.persistence.Document.dateToShortString;

public class DocumentTest {


    @Test
    public void testName() throws Exception {


        assertThat(dateToShortString(millisBeforeNow(1000)), is("1s"));
        assertThat(dateToShortString(millisBeforeNow(1000 * 10)), is("10s"));
        assertThat(dateToShortString(millisBeforeNow(1000 * 60)), is("1m"));
        assertThat(dateToShortString(millisBeforeNow(1000 * 60 + 1)), is("1m"));
        assertThat(dateToShortString(millisBeforeNow(1000 * 60 * 2)), is("2m"));
        assertThat(dateToShortString(millisBeforeNow(1000 * 60 * 2 + 1)), is("2m"));
        assertThat(dateToShortString(millisBeforeNow(1000 * 60 * 60)), is("1h"));
        assertThat(dateToShortString(millisBeforeNow(1000 * 60 * 60 - 1)), is("59m"));

    }

    private Instant millisBeforeNow(long millis) {
        return Instant.now().minusMillis(millis);
    }
}