package se.johantiden.myfeed.plugin;

import com.google.common.util.concurrent.RateLimiter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.Video;
import se.johantiden.myfeed.plugin.rss.Rss2FeedReader;
import se.johantiden.myfeed.plugin.rss.v2.Rss2Doc.Item;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RedditFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(RedditFeed.class);

    public RedditFeed(RateLimiter rateLimiter, String subreddit, Predicate<Document> filter) {
        super(getFeedName(subreddit), getFeedUrl(subreddit), createFeedReader(rateLimiter, subreddit, filter));
    }

    public static FeedReader createFeedReader(RateLimiter rateLimiter, String subreddit, Predicate<Document> filter) {
        return () -> {
            rateLimiter.acquire();
            List<Document> documents = new MyRss2FeedReader(subreddit).readAllAvailable();
            return documents.stream().filter(filter).collect(Collectors.toList());
        };
    }

    static String getRssUrl(String subreddit) {
        return "https://www.reddit.com/" + subreddit + "/.rss";
    }

    static String getFeedUrl(String subreddit) {
        return "https://www.reddit.com/" + subreddit;
    }

    static String getFeedName(String subreddit) {
        return "Reddit - " + subreddit;
    }

//    private static Function<Document> createEntryMapper() {
//        return pair -> {
//            org.jsoup.nodes.Document jsoupDocument = getJsoupDocument(pair.right.getPageUrl());
//            parseStuffz(jsoupDocument, pair.right);
//            if (!isNSFW(jsoupDocument)) {
//                pair.right.score = findVotes(jsoupDocument);
//            }
//            return pair.right;
//        };
//    }
//
//    private static void parseStuffz(org.jsoup.nodes.Document webDocument, Document document) {
//        Elements imgs = webDocument.select("img");
//        if(!imgs.isEmpty()) {
//            Element img = imgs.get(0);
//            String imgSrc = img.attr("src");
//            document.imageUrl = imgSrc;
//        }
//
//        Elements linkLinks = webDocument.select("td > span > a");
//
//        if(linkLinks.size() == 2) {
//            Element link = linkLinks.get(0);
//            String linkHref = link.attr("href");
//
//            if(linkHref.contains("https://gfycat.com")) {
//                parseGfycat(document, linkHref);
//            }
//
//            if(linkHref.contains("http://i.imgur.com") && !linkHref.contains("jpg") && !linkHref.contains("png")) {
//                String imgurRawUrl = getRawImgurUrl(linkHref);
//                String webmSrc = imgurRawUrl + ".webm";
//                String mp4Src = imgurRawUrl + ".mp4";
//                String gifvSrc = imgurRawUrl + ".gifv";
//                ArrayList<Video> videos = Lists.newArrayList(
//                                                            new Video(webmSrc, "video/webm"),
//                                                            new Video(gifvSrc, "video/mp4"),
//                                                            new Video(mp4Src, "video/mp4"));
//                document.videos = videos;
//                return;
//            }
//            if(linkHref.contains("https://media.giphy.com/media/")) {
//                String id = linkHref.split("/")[4];
//                document.imageUrl = "https://i.giphy.com/" + id + ".gif";
//            }
//            if(linkHref.contains("https://youtu.be/")) {
//                throw new RuntimeException("Not supported");
////                String id = linkHref.split("/")[3]; //yECd_Sz_zJ8
////                document.imageUrl = null;
////                document.html = "<iframe class=\"image-box\" src=\"https://www.youtube.com/embed/" + id + "?ecver=1?autoplay=1\" frameborder=\"0\" allowfullscreen></iframe>";
//            }
//            if(linkHref.contains("i.redd.it") || linkHref.contains("imgur")) {
//                if(linkHref.endsWith("jpg") || linkHref.endsWith("png")) {
//                    document.imageUrl = linkHref;
//                } else {
//                    document.imageUrl = linkHref + ".jpg";
//                }
//            }
//        }
//    }

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
            log.error("Could not jsoup-parse {}", pageUrl, e);
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

    private static class MyRss2FeedReader extends Rss2FeedReader {
        private final String subreddit;

        MyRss2FeedReader(String subreddit) {
            super(getRssUrl(subreddit));
            this.subreddit = subreddit;
        }

        @Override
        public Document toDocument(Item item) {
            String title = item.title;
            String text = null;
            String html = null;
            String pageUrl = null;
            String imageUrl = null;
            Instant publishedDate = null;
            return new Document(title, text, html, pageUrl, imageUrl, publishedDate, getFeedName(subreddit), getFeedUrl(subreddit));
        }
    }
}
