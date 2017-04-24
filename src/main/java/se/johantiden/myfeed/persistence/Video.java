package se.johantiden.myfeed.persistence;

public class Video {
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

    public String getSrc() {
        return src;
    }

    public String getType() {
        return type;
    }
}
