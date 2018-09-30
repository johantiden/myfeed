package se.johantiden.myfeed.persistence;

import java.time.Instant;

public abstract class BaseEntity<T extends BaseEntity<T>> {

    private Long id;

    private Instant created;

    public BaseEntity() {
        this.id = null;
    }

    public long getId() {
        if (!hasId()) {
            throw new IllegalStateException("Don't get the id until the value has been persisted!");
        }
        return id;
    }

    public Instant getCreated() {
        return created;
    }

    public boolean hasId() {
        return id != null;
    }

    protected final void setId(long id) {
        this.id = id;
    }

    public final void setCreated(Instant instant) {
        this.created = instant;
    }


}
