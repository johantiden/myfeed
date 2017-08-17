package se.johantiden.myfeed.persistence;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class SubjectRule extends Rule {

    private final String subject;
    private final String elExpression;

    public SubjectRule(String subject, String elExpression) {
        this.subject = Objects.requireNonNull(subject);
        this.elExpression = Objects.requireNonNull(elExpression);
    }

    @Override
    public boolean isMatch(Document document) {
        return false;
    }

    public String getSubject() {
        return subject;
    }

    public String getElExpression() {
        return elExpression;
    }
}
