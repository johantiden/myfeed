package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;

public class Username extends Key<String> {
    private static final long serialVersionUID = -2174296463666107734L;

    public Username(String userName) {
        super(userName);
    }
}
