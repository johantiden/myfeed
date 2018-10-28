package se.johantiden.myfeed.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Subject;
import se.johantiden.myfeed.util.DocumentPredicates;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SubjectServiceTest {


    @Test
    public void testName() {

        Subject s = new Subject(Lists.newArrayList(Subject.ROOT), "#A", DocumentPredicates.has("A"), false, false, false);

        Document d = new Document("A", "", null, "foo", null, null, null, null);

        boolean match = s.isMatch(d);
        assertThat(match, is(true));


    }
}