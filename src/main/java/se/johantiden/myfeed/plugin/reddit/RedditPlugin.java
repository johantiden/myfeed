package se.johantiden.myfeed.plugin.reddit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.persistence.PluginType;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RedditPlugin implements Plugin {

    private static final Logger log = LoggerFactory.getLogger(RedditPlugin.class);

    @Override
    public Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, Duration ttl, Predicate<Document> filter) {
        return new FeedImpl(PluginType.REDDIT, feedName, webUrl, cssClass, readerParameters, ttl, filter);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin().createFeedReader(feed).readAllAvailable();
            return documents.parallelStream().map(createEntryMapper()).collect(Collectors.toList());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return document -> {
            document.score = findVotes(document);
            document.authorName = null;
            document.authorUrl = null;
            return document;
        };
    }

    private static Double findVotes(Document entry) {
        try {
            org.jsoup.nodes.Document parse = Jsoup.parse(new URL(entry.pageUrl), 10_000);
            if (parse.location().contains("over18")) {
//                log.info("Skipping NSFW");
                return 0D;
            }
            Double votes = findVotes(parse);
            return votes;
        } catch (IOException e) {
            log.error("Could not find score of " + entry.pageUrl, e);
            return null;
        }
    }

    private static Double findVotes(org.jsoup.nodes.Document parse) {

        Elements select = parse.select(".score > span.number");
        if (select.size() == 1) {
            Element element = select.get(0);
            String html = element.html();
            html = html.replace(",", "");

            int i = Integer.parseInt(html);
            return (double) i;
        }
        return null;
    }

}
