package se.johantiden.myfeed.plugin;

import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.Video;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RedditFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(RedditFeed.class);

    public RedditFeed(String subreddit, Predicate<Document> filter) {
        super(getFeedName(subreddit), getWebUrl(subreddit), createFeedReader(subreddit, filter));
    }

    public static FeedReader createFeedReader(String subreddit, Predicate<Document> filter) {
        return () -> {
            List<Document> documents = new RssFeedReader(getFeedName(subreddit), getWebUrl(subreddit), getRssUrl(subreddit)).readAllAvailable();
            return documents.stream().map(createEntryMapper()).filter(filter).collect(Collectors.toList());
        };
    }

    private static String getRssUrl(String subreddit) {
        return "https://www.reddit.com/" + subreddit + "/.rss";
    }

    private static String getWebUrl(String subreddit) {
        return "https://www.reddit.com/" + subreddit;
    }

    private static String getFeedName(String subreddit) {
        return "Reddit - " + subreddit;
    }

    private static Function<Document, Document> createEntryMapper() {
        return document -> {
            org.jsoup.nodes.Document jsoupDocument = getJsoupDocument(document.pageUrl);
            parseStuffz(document);
            if (!isNSFW(jsoupDocument)) {
                document.score = findVotes(jsoupDocument);
            }
            document.author = null;
            return document;
        };
    }

    private static void parseStuffz(Document document) {
        org.jsoup.nodes.Document rssDocument = Jsoup.parse(document.html);
        document.html = null;
        Elements imgs = rssDocument.select("img");
        if(!imgs.isEmpty()) {
            Element img = imgs.get(0);
            String imgSrc = img.attr("src");
            document.imageUrl = imgSrc;
        }

        Elements linkLinks = rssDocument.select("td > span > a");

        if(linkLinks.size() == 2) {
            Element link = linkLinks.get(0);
            String linkHref = link.attr("href");

            if(linkHref.contains("https://gfycat.com")) {
                parseGfycat(document, linkHref);
            }

            if(linkHref.contains("http://i.imgur.com") && !linkHref.contains("jpg") && !linkHref.contains("png")) {
                String imgurRawUrl = getRawImgurUrl(linkHref);
                String webmSrc = imgurRawUrl + ".webm";
                String mp4Src = imgurRawUrl + ".mp4";
                String gifvSrc = imgurRawUrl + ".gifv";
                ArrayList<Video> videos = Lists.newArrayList(
                                                            new Video(webmSrc, "video/webm"),
                                                            new Video(gifvSrc, "video/mp4"),
                                                            new Video(mp4Src, "video/mp4"));
                document.videos = videos;
                return;
            }
            if(linkHref.contains("https://media.giphy.com/media/")) {
                String id = linkHref.split("/")[4];
                document.imageUrl = "https://i.giphy.com/" + id + ".gif";
            }
            if(linkHref.contains("https://youtu.be/")) {
                String id = linkHref.split("/")[3]; //yECd_Sz_zJ8
                document.imageUrl = null;
                document.html = "<iframe class=\"image-box\" src=\"https://www.youtube.com/embed/" + id + "?ecver=1?autoplay=1\" frameborder=\"0\" allowfullscreen></iframe>";
            }
            if(linkHref.contains("i.redd.it") || linkHref.contains("imgur")) {
                if(linkHref.endsWith("jpg") || linkHref.endsWith("png")) {
                    document.imageUrl = linkHref;
                } else {
                    document.imageUrl = linkHref + ".jpg";
                }
            }
        }
    }

    static String getRawImgurUrl(String linkHref) {
        String[] split = linkHref.split("\\.");

        return split[0] + "." + split[1] + "." + split[2];
    }

    private static void parseGfycat(Document document, String linkHref) {
        org.jsoup.nodes.Document gfyCat = getJsoupDocument(linkHref);
        Elements sources = gfyCat.select("video.share-video > source");
        if(sources.size() > 0) {
            List<Video> videoSources = sources.stream().map(s -> new Video(s.attr("src"), s.attr("type"))).collect(Collectors.toList());
            document.videos.clear();
            document.videos.addAll(videoSources);
            document.imageUrl = null;
        }
    }

    private static boolean isNSFW(org.jsoup.nodes.Document parse) {
        return parse.location().contains("over18");
    }

    private static Double findVotes(org.jsoup.nodes.Document parse) {
        if(parse.location().contains("over18")) {
            throw new IllegalStateException("Cannot find score for NSFW documents. Please filter them out before checking votes.");
        }
        return findVotes2(parse);
    }

    private static org.jsoup.nodes.Document getJsoupDocument(String pageUrl) {
        try {
            return Jsoup.parse(new URL(pageUrl), 10_000);
        } catch (IOException e) {
            log.error("Could not jsoup-parse " + pageUrl, e);
            return null;
        }
    }

    private static Double findVotes2(org.jsoup.nodes.Document parse) {

        Elements select = parse.select(".score > span.number");
        if(select.size() == 1) {
            Element element = select.get(0);
            String html = element.html();
            html = html.replace(",", "");

            int i = Integer.parseInt(html);
            return (double) i;
        }
        return null;
    }

}
