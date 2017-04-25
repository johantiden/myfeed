package se.johantiden.myfeed.persistence;

import java.io.Serializable;

public class Video implements Serializable {
    private static final long serialVersionUID = 7559774172003829234L;

    private final String src;
    private final String type;

    public Video(String src) {
        this.src = src;
        type = null;
    }

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
