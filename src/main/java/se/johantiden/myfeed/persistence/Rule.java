package se.johantiden.myfeed.persistence;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.time.Instant;

@MappedSuperclass
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    private final Timestamp created = Timestamp.from(Instant.now());

    public Rule() {
        this.id = null;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getCreated() {
        return created;
    }
}
