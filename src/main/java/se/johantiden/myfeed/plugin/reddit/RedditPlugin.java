package se.johantiden.myfeed.plugin.reddit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.FeedImpl;
import se.johantiden.myfeed.persistence.Filter;
import se.johantiden.myfeed.persistence.PluginType;
import se.johantiden.myfeed.plugin.FeedReader;
import se.johantiden.myfeed.plugin.Plugin;
import se.johantiden.myfeed.plugin.rss.RssPlugin;

import java.io.IOException;
import java.net.URL;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static se.johantiden.myfeed.util.JCollections.map;

public class RedditPlugin implements Plugin {

    private static final Logger log = LoggerFactory.getLogger(RedditPlugin.class);

    @Override
    public Feed createFeed(String feedName, String cssClass, String webUrl, Map<String, String> readerParameters, long invalidationPeriod, TemporalUnit invalidationPeriodUnit, Filter filter) {
        return new FeedImpl(PluginType.REDDIT, feedName, webUrl, cssClass, readerParameters, invalidationPeriod, invalidationPeriodUnit, filter);
    }

    @Override
    public FeedReader createFeedReader(Feed feed) {
        return () -> {
            List<Document> documents = new RssPlugin().createFeedReader(feed).readAllAvailable();
            return map(documents, createEntryMapper());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return entry -> {
            entry.putExtra("votes", findVotes(entry));

            entry.authorName = null;
            entry.authorUrl = null;
            return entry;
        };
    }

    private static int findVotes(Document entry) {
        try {
            org.jsoup.nodes.Document parse = Jsoup.parse(new URL(entry.pageUrl), 10_000);
            int votes = findVotes(parse);
            return votes;
        } catch (IOException e) {
            log.error("Could not find score of " + entry.pageUrl, e);
            return -1;
        }
    }

    private static int findVotes(org.jsoup.nodes.Document parse) {

        Elements select = parse.select(".score > span.number");
        if (select.size() == 1) {
            Element element = select.get(0);
            String html = element.html();
            html = html.replace(",", "");

            int i = Integer.parseInt(html);
            return i;
        }
        return -1;
    }

}
