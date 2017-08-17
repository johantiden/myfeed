package se.johantiden.myfeed.persistence;

import javax.persistence.Entity;

@Entity
public class User extends BaseEntity {

    private final String username;

    // JPA
    protected User() {
        username = null;
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                '\'' + username + '\'' +
                '}';
    }
}
