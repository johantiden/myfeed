package se.johantiden.myfeed.persistence.file;

import java.util.Optional;

public interface Saver<T> {

    void save(T t);
    Optional<T> load();

}
