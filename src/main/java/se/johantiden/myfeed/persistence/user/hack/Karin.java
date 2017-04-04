package se.johantiden.myfeed.persistence.user.hack;

import se.johantiden.myfeed.persistence.redis.Keys;
import se.johantiden.myfeed.persistence.user.User;

public class Karin extends User {
    public Karin() {
        super(Keys.user("karin"), "karin", Johan.johanFilter(), Johan.flagFilter());
    }

}
