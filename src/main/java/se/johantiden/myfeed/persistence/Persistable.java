package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.persistence.redis.Key;

public interface Persistable<T> {

    Key<T> getKey();

}
