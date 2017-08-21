package se.johantiden.myfeed.service;

import com.google.common.collect.Lists;
import org.junit.Test;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.SubjectRule;
import se.johantiden.myfeed.persistence.SubjectRuleRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubjectServiceTest {


    @Test
    public void testName() throws Exception {

        SubjectRule s = new SubjectRule("A", "A");


        Document d = new Document("A", "", null, null, null, null, null, new HashSet<>(), null, null);

        boolean match = s.isMatch(d);
        assertThat(match, is(true));


    }
}