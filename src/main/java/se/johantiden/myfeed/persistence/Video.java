package se.johantiden.myfeed.persistence;


import javax.persistence.Entity;

@Entity
public class Video extends BaseEntity {

    private final String src;
    private final String type;

    public Video(String src, String type) {
        this.src = src;
        this.type = type;
    }

    public final String getSrc() {
        return src;
    }

    public final String getType() {
        return type;
    }
}
