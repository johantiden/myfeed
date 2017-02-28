package se.johantiden.myfeed.output;


import static java.util.Objects.requireNonNull;

public class OutputBean {

    public final String title;
    public final String pageUrl;
    public final String imageUrl;

    public OutputBean(String title, String pageUrl, String imageUrl) {
        this.title = requireNonNull(title);
        this.pageUrl = requireNonNull(pageUrl);
        this.imageUrl = requireNonNull(imageUrl);
    }

    public String getTitle() {
        return title;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
