package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.classification.DocumentMatcher;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
public class SubjectRule extends Rule {

    private final String subject;
    private final String expression;

    //JPA
    protected SubjectRule() {
        subject = null;
        expression = null;
    }

    public SubjectRule(String subject, String expression) {
        this.subject = Objects.requireNonNull(subject);
        this.expression = Objects.requireNonNull(expression);
    }

    @Override
    public boolean isMatch(Document document) {
        Pattern pattern = Pattern.compile(expression);

        boolean match = new DocumentMatcher(document).matches(pattern);
        return match;
    }

    public String getSubject() {
        return subject;
    }

    public String getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        SubjectRule that = (SubjectRule) o;

        if (subject != null ? !subject.equals(that.subject) : that.subject != null) { return false; }
        return !(expression != null ? !expression.equals(that.expression) : that.expression != null);

    }

    @Override
    public int hashCode() {
        int result = subject != null ? subject.hashCode() : 0;
        result = 31 * result + (expression != null ? expression.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SubjectRule{" +
                "subject='" + subject + '\'' +
                ", expression='" + expression + '\'' +
                '}';
    }
}
