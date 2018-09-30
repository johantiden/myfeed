package se.johantiden.myfeed.persistence;

import org.junit.Test;

import java.util.Set;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class DocumentClassifierTest {

    @Test
    public void testStartsWith() throws Exception {
        Pattern pattern = Pattern.compile("^VIDEO");

        assertThat(pattern.matcher("VIDEO: Hej").find(), is(true));
        assertThat(pattern.matcher("Bra VIDEO").find(), is(false));
    }

    @Test
    public void testGetDefault() throws Exception {
        Set<Subject> defaultSubjects = SubjectClassifier.getSubjects();
        assertThat(defaultSubjects.isEmpty(), is(false));

    }
}