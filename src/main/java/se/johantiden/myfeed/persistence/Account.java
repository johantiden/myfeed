package se.johantiden.myfeed.persistence;

import javax.persistence.Entity;

@Entity
public class Account extends BaseEntity {

    private final String name;

    // JPA
    protected Account() {
        name = null;
    }

    public Account(String accountname) {
        this.name = accountname;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Account{" +
                '\'' + name + '\'' +
                '}';
    }
}
