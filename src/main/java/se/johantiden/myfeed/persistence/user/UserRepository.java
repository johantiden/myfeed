package se.johantiden.myfeed.persistence.user;

import se.johantiden.myfeed.persistence.redis.Key;
import se.johantiden.myfeed.persistence.user.hack.Bjon;
import se.johantiden.myfeed.persistence.user.hack.Jocke;
import se.johantiden.myfeed.persistence.user.hack.Johan;
import se.johantiden.myfeed.persistence.user.hack.Karin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class UserRepository {

    private final Map<Key<User>, User> users = new HashMap<>();

    public UserRepository() {
        put(new Jocke());
        put(new Johan());
        put(new Karin());
        put(new Bjon());
        put(new Danne());
    }

    public void put(User user) {
        users.put(user.getKey(), user);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User getUser(Key<User> userKey) {
        return users.get(userKey);
    }
}
