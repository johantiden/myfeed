package se.johantiden.myfeed.persistence;

import com.google.common.collect.ImmutableList;
import se.johantiden.myfeed.classification.DocumentMatcher;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Subject extends BaseEntity<Subject> {

    public static final Subject ROOT = new Subject("All");
    public static final Subject UNCLASSIFIED = new Subject("Unclassified");

    private final ImmutableList<Subject> parents;
    private final List<Subject> children = new ArrayList<>();
    private final String name;
    @Nullable
    private final String expression;
    private boolean hideDocument;
    private final boolean isHashTag;

    // only root!
    private Subject(String all) {
        this.parents = ImmutableList.of();
        this.name = all;
        this.expression = null;
        isHashTag = false;
    }

    public Subject(List<Subject> parents, String name, @Nullable String expression, boolean hideDocument, boolean isHashTag) {
        Objects.requireNonNull(parents);
        this.parents = ImmutableList.copyOf(parents);
        if (parents.isEmpty()) {
            throw new RuntimeException("Please choose at least one parent!");
        }
        parents.forEach(p -> p.children.add(this));

        this.name = Objects.requireNonNull(name);
        this.expression = expression;
        this.isHashTag = isHashTag;
        this.hideDocument = hideDocument;
    }

    public String getName() {
        return name;
    }

    public String getExpression() {
        return expression;
    }

    public boolean isHideDocument() {
        return hideDocument ||
                parents.stream()
                        .map(Subject::isHideDocument)
                        .reduce(Boolean::logicalOr)
                        .orElse(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Subject subject = (Subject) o;
        return hideDocument == subject.hideDocument &&
                isHashTag == subject.isHashTag &&
                Objects.equals(parents, subject.parents) &&
                Objects.equals(name, subject.name) &&
                Objects.equals(expression, subject.expression);
    }

    @Override
    public int hashCode() {

        return Objects.hash(parents, name, expression, hideDocument, isHashTag);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "parents=" + parents +
                ", name='" + name + '\'' +
                ", expression='" + expression + '\'' +
                ", hideDocument=" + hideDocument +
                ", isHashTag=" + isHashTag +
                '}';
    }

    public final boolean isMatch(Document document) {

        boolean matchSelf = isMatchSelf(document);
        return matchSelf || children.stream()
                .anyMatch(c -> c.isMatch(document));
    }

    private boolean isMatchSelf(Document document) {
        if (expression == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(getExpression());

        boolean match = new DocumentMatcher(document).matches(pattern);
        return match;
    }

    public int getMinDepth() {
        return 1 + parents.stream()
                .mapToInt(Subject::getMinDepth)
                .min()
                .orElse(0);
    }

    public List<Subject> getParents() {
        return parents;
    }

    public boolean isHashTag() {
        return isHashTag;
    }
}
