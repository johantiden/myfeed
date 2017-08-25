package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.classification.DocumentMatcher;

import javax.persistence.Entity;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
public class SubjectRule extends Rule {

    public static final Comparator<SubjectRule> COMPARATOR = Comparator.comparing(SubjectRule::getName).thenComparing(SubjectRule::getExpression);
    private String name;
    private String expression;

    //JPA
    protected SubjectRule() {
        name = null;
        expression = null;
    }

    public SubjectRule(String subject, String expression) {
        this.name = Objects.requireNonNull(subject);
        this.expression = Objects.requireNonNull(expression);
    }

    public String getName() {
        return name;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        SubjectRule that = (SubjectRule) o;

        if (!name.equals(that.name)) { return false; }
        return expression.equals(that.expression);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + expression.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SubjectRule{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", expression='" + expression + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public void setExpression(String expression) {
        this.expression = Objects.requireNonNull(expression);
    }
}
