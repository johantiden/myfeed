package se.johantiden.myfeed.persistence;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class TabRule extends Rule {

    private String name;
    private String expression;
    private boolean hideDocument;

    // JPA
    protected TabRule() {
        name = null;
        expression = null;
        hideDocument = false;
    }

    public TabRule(String name, String expression, boolean hideDocument) {
        this.name = Objects.requireNonNull(name);
        this.expression = Objects.requireNonNull(expression);
        this.hideDocument = hideDocument;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    public boolean isHideDocument() {
        return hideDocument;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setHideDocument(boolean hideDocument) {
        this.hideDocument = hideDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        TabRule tabRule = (TabRule) o;

        if (hideDocument != tabRule.hideDocument) { return false; }
        if (!name.equals(tabRule.name)) { return false; }
        return expression.equals(tabRule.expression);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + expression.hashCode();
        result = 31 * result + (hideDocument ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TabRule{" +
                "name='" + name + '\'' +
                ", expression='" + expression + '\'' +
                ", hideDocument=" + hideDocument +
                '}';
    }
}
