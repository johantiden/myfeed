package se.johantiden.myfeed.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Subject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SubjectServiceTest {


    @Test
    public void testName() {

        Subject s = new Subject(Lists.newArrayList(Subject.ROOT), "#A", "A", false, false, false);


        Document d = new Document("A", "", null, "foo", null, null, null, null);

        boolean match = s.isMatch(d);
        assertThat(match, is(true));


    }
}