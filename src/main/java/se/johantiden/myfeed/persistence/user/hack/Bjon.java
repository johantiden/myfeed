package se.johantiden.myfeed.persistence.user.hack;

import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

public class Bjon extends User {
    public Bjon() {
        super(Keys.user("bjon"), "bjon", Johan.johanFilter());
    }

}
