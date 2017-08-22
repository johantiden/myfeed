package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.classification.DocumentMatcher;

import javax.persistence.MappedSuperclass;
import java.util.regex.Pattern;

@MappedSuperclass
public abstract class Rule extends BaseEntity {

    public final boolean isMatch(Document document) {
        Pattern pattern = Pattern.compile(getExpression());

        boolean match = new DocumentMatcher(document).matches(pattern);
        return match;
    }

    public abstract String getExpression();

}
