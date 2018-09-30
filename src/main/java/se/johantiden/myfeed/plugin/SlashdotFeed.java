package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SlashdotFeed extends Feed {

    public static final String URL = "https://slashdot.org";
    public static final String NAME = "Slashdot";
    public static final String URL_RSS = "http://rss.slashdot.org/Slashdot/slashdotMainatom";

    public SlashdotFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new RssFeedReader(NAME, URL, URL_RSS).readAllAvailable();
            return documents.stream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return entry -> entry;
    }

    public static String prune(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.select("p").remove();
        doc.select(".share_submission").remove();
        doc.select("head").remove();
        String pruned2 = doc.body().html();
        return pruned2;
    }
}
