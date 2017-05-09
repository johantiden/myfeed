package se.johantiden.myfeed.persistence.user.hack;

import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

public class Danne extends User {
    public Danne() {
        super(Keys.user("danne"), "danne", Johan.johanFilter());
    }

}
