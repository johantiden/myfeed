package se.johantiden.myfeed.service;

import org.junit.Test;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.SubjectRule;

import java.util.HashSet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SubjectServiceTest {


    @Test
    public void testName() throws Exception {

        SubjectRule s = new SubjectRule("A", "A");


        Document d = new Document("A", "", null, "foo", null, null, null, new HashSet<>(), null, null);

        boolean match = s.isMatch(d);
        assertThat(match, is(true));


    }
}