package se.johantiden.myfeed.persistence;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class TabRule extends Rule {

    private final String tab;

    public TabRule(String tab) {
        this.tab = Objects.requireNonNull(tab);
    }

    @Override
    public boolean isMatch(Document document) {
        return false;
    }

    public String getTab() {
        return tab;
    }
}
