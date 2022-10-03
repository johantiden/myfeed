package se.johantiden.myfeed.persistence;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

import static com.google.common.base.Objects.equal;

public class Subject extends BaseEntity<Subject> {

    public static final Subject ALL = new Subject("All", SubjectType.BASE);
    public static final Subject UNCLASSIFIED = new Subject("Unclassified", ALL, SubjectType.BASE);

    private final ImmutableList<Subject> parents;
    private final CopyOnWriteArrayList<Subject> children = new CopyOnWriteArrayList<>();
    private final String name;
    private final SubjectType type;
    private final Predicate<Document> documentPredicate;
    private final boolean hideDocument;
    private final boolean isHashTag;
    private final boolean showAsTab;

    private Subject(String name, SubjectType type) {
        this.parents = ImmutableList.of();
        this.name = name;
        this.type = type;
        this.documentPredicate = d -> false;
        isHashTag = false;
        showAsTab = true;
        hideDocument = false;
    }

    private Subject(String name, Subject parent, SubjectType type) {
        this.parents = ImmutableList.of(parent);
        this.name = name;
        this.type = type;
        this.documentPredicate = d -> false;
        isHashTag = false;
        showAsTab = true;
        hideDocument = false;
    }

    public Subject(List<Subject> parents, String name, SubjectType type, @Nonnull Predicate<Document> documentPredicate, boolean hideDocument, boolean isHashTag, boolean showAsTab) {
        this.type = type;
        this.showAsTab = showAsTab;
        Objects.requireNonNull(parents);
        this.parents = ImmutableList.copyOf(parents);
        if (parents.isEmpty()) {
            throw new RuntimeException("Please choose at least one parent!");
        }
        parents.forEach(p -> p.children.add(this));

        if (name.length() < 4 && !name.startsWith("#")) {
            throw new RuntimeException("Name '"+name+"' is too short. It will probably cause search problems. Add a '#' if you want it to be this short");
        }
        this.name = Objects.requireNonNull(name);
        this.documentPredicate = Objects.requireNonNull(documentPredicate);
        this.isHashTag = isHashTag;
        this.hideDocument = hideDocument;
    }

    public String getName() {
        return name;
    }

    public boolean isHideDocument() {
        return hideDocument ||
                parents.stream()
                        .map(Subject::isHideDocument)
                        .reduce(Boolean::logicalOr)
                        .orElse(false);
    }


    @Override
    public String toString() {
        return "Subject{" +
                "parents=" + parents +
                ", name='" + name + '\'' +
                ", documentPredicate=" + documentPredicate +
                ", hideDocument=" + hideDocument +
                ", isHashTag=" + isHashTag +
                ", showAsTab=" + showAsTab +
                '}';
    }

    public final boolean isMatch(Document document) {

        boolean matchSelf = isMatchSelf(document);
        return matchSelf || children.stream()
                .anyMatch(c -> c.isMatch(document));
    }

    private boolean isMatchSelf(Document document) {
        return documentPredicate.test(document);
    }

    public int getMinDepth() {
        if (parents.isEmpty()) {
            return 0;
        }
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

    public boolean isShowAsTab() {
        return showAsTab;
    }

    public SubjectType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject that = (Subject) o;

        return  equal(this.name, that.name) &&
                equal(this.hideDocument, that.hideDocument) &&
                equal(this.isHashTag, that.isHashTag) &&
                equal(this.showAsTab, that.showAsTab);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(name, hideDocument, isHashTag, showAsTab);
    }

    public enum SubjectType {
        BASE("BASE", 1),
        CATEGORY("Categories", 2),
        FEED("Feeds", 3),
        SUB_FEED("Subs", 4),
        CONTINENT("Continents", 5),
        COUNTRY("Countires", 6),
        SUBJECT("Subjects", 7),
        ORGANIZATION("Organizations", 8),
        LOCAL("Local", 9),
        PERSON("People", 10),
        EVENT("Events", 11);

        public final String title;
        public final int order;

        SubjectType(String title, int order) {
            this.title = title;
            this.order = order;
        }
    }
}
