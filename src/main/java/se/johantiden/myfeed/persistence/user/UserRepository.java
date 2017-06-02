package se.johantiden.myfeed.persistence.user;

import se.johantiden.myfeed.persistence.Username;
import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.redis.Keys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class UserRepository {

    private final List<Username> users = new ArrayList<>();

    public UserRepository() {
        put(Keys.user("jocke"));
        put(Keys.user("johan"));
        put(Keys.user("karin"));
        put(Keys.user("bjon"));
        put(Keys.user("danne"));
        put(Keys.user("lisa"));
        put(Keys.user("test"));
        put(Keys.user("test1"));
        put(Keys.user("test2"));
        put(Keys.user("test3"));
    }

    public void put(Username user) {
        users.add(user);
    }

    public Collection<Username> getAllUsers() {
        return users;
    }
}
