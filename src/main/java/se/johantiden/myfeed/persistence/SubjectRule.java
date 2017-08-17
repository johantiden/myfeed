package se.johantiden.myfeed.persistence;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class SubjectRule extends Rule {


    private final Tab tab;

    public SubjectRule(Tab tab) {
        this.tab = Objects.requireNonNull(tab);
    }

    @Override
    public boolean isMatch(Document document) {
        return false;
    }

}
