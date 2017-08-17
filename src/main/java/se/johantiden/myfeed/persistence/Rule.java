package se.johantiden.myfeed.persistence;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Rule extends BaseEntity {

    public abstract boolean isMatch(Document document);

}
