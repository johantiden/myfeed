package se.johantiden.myfeed.plugin;

import com.google.common.collect.Lists;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.persistence.Video;
import se.johantiden.myfeed.plugin.rss.RssReader;
import se.johantiden.myfeed.plugin.rss.v1.atom.RssV1AtomDoc;
import se.johantiden.myfeed.util.Chrono;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RedditFeed extends Feed {
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'+00:00'";

    private static final Logger log = LoggerFactory.getLogger(RedditFeed.class);

    public RedditFeed(String subreddit, Predicate<Document> filter) {
        super(getFeedName(subreddit), getFeedUrl(subreddit), createFeedReader(subreddit, filter));
    }

    public static FeedReader createFeedReader(String subreddit, Predicate<Document> filter) {
        return () -> new Reddit(subreddit).readAll(filter);
    }

    static String getRssUrl(String subreddit) {
        return "https://www.reddit.com/" + subreddit + ".rss?t=month";
    }

    static String getFeedUrl(String subreddit) {
        return "https://www.reddit.com/" + subreddit;
    }

    static String getFeedName(String subreddit) {
        return "Reddit - " + subreddit;
    }

    private static void findContent(org.jsoup.nodes.Document jsoupDocument, Document document) {
        Elements imgs = jsoupDocument.select("img");
        if(!imgs.isEmpty()) {
            Element img = imgs.get(0);
            String imgSrc = img.attr("src");
            document.imageUrl = imgSrc;
        }

        Elements linkLinks = jsoupDocument.select("td > span > a");

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
                throw new RuntimeException("Not supported");
//                String id = linkHref.split("/")[3]; //yECd_Sz_zJ8
//                document.imageUrl = null;
//                document.html = "<iframe class=\"image-box\" src=\"https://www.youtube.com/embed/" + id + "?ecver=1?autoplay=1\" frameborder=\"0\" allowfullscreen></iframe>";
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
        if (sources.size() > 0) {
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
        if (parse.location().contains("over18")) {
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
        if (select.size() == 1) {
            Element element = select.get(0);
            String html = element.html();
            html = html.replace(",", "");

            int i = Integer.parseInt(html);
            return (double) i;
        }
        return null;
    }

    private static class Reddit {
        private final String subreddit;

        public Reddit(String subreddit) {

            this.subreddit = subreddit;
        }

        public List<Document> readAll(Predicate<Document> filter) {

            try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
                HttpGet httpGet = new HttpGet(getRssUrl(subreddit));
                httpGet.setHeader("User-Agent", "/u/joihoin");

                try (CloseableHttpResponse response = httpclient.execute(httpGet);) {
                    if (response.getStatusLine().getStatusCode() != 200) {
                        throw new RuntimeException(response.getStatusLine().getReasonPhrase() +" - " + getRssUrl(subreddit));
                    }

                    RssV1AtomDoc doc = RssReader.read(response.getEntity().getContent(), RssV1AtomDoc.class);

                    return doc.getEntries()
                            .stream()
                            .map(this::toDocument)
                            .filter(filter)
                            .collect(Collectors.toList());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }


        public Document toDocument(RssV1AtomDoc.Entry entry) {
            String title = entry.title;
            String text = null;
            String html = entry.content.body;
            String pageUrl = entry.link.href;


            org.jsoup.nodes.Document jsoupDocument = getJsoupDocument(pageUrl);
            Double votes = null;
            if (jsoupDocument != null && !isNSFW(jsoupDocument)) {
                votes = findVotes(jsoupDocument);
            }

            Instant publishedDate = Chrono.parse(entry.updated, DATE_FORMAT);
            Document document = new Document(title, text, html, pageUrl, null, publishedDate, getFeedName(subreddit), getFeedUrl(subreddit));
            document.score = votes;

            if (jsoupDocument != null) {
                findContent(jsoupDocument, document);
            }
            return document;

        }
    }
}