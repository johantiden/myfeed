package se.johantiden.myfeed.persistence.user.hack;

import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

public class Jocke extends User {
    public Jocke() {
        super(Keys.user("jocke"), "jocke");

        setUserGlobalFilter(Johan.johanFilter());
    }

}
