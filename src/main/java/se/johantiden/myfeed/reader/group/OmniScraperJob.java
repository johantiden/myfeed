package se.johantiden.myfeed.reader.group;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.service.DocumentService;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OmniScraperJob {

    public static final String HTTPS_WWW_OMNI_SE = "https://www.omni.se";
    private static final Logger log = LoggerFactory.getLogger(OmniScraperJob.class);
    private WebClient client;

    @Autowired
    private DocumentService documentService;

    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        log.info("Scraping Omni...");
        Set<String> hrefs = new HashSet<>();

        try {
            org.jsoup.nodes.Document index = Jsoup.parse(new URL(HTTPS_WWW_OMNI_SE), 20_000);
            Elements articles = index.select(".group.group--cluster > article > a.article-link");

            for (Element article : articles) {
                String href = article.attr("href");
                hrefs.add(href);
            }
        } catch (IOException | RuntimeException e) {
            log.error("Failed to scrape omni", e);
            return;
        }
        hrefs.parallelStream()
                .forEach(this::consumeArticle);

        log.info("Done scraping Omni index...");

    }

    public void consumeArticle(String href) {
        String pageUrl = HTTPS_WWW_OMNI_SE + href;

        if (documentService.hasDocumentWithUrl(pageUrl)) {
            return;
        }

        try {
            org.jsoup.nodes.Document parse = Jsoup.parse(new URL(pageUrl), 10_000);

//            Elements supertagDoms = parse.select(".resource--vignette-supertag");
//            Element supertagDom = supertagDoms.get(0);
//            String supertag = supertagDom.html();

//            Elements topicDoms = parse.select(".resource--vignette-topic");
//            Element topicDom = topicDoms.get(0);
//            String topic = topicDom.html();

            Elements titleDoms = parse.select(".resource.resource--title");
            Element titleDom = titleDoms.get(0);
            String title = titleDom.html();

            Elements textDoms = parse.select(".resource.resource--text");
            Element textDom = textDoms.get(0);
            String textHtml = textDom.html();

            Elements imageDoms = parse.select("img.resource-img");
            Element imageDom = imageDoms.get(0);
            String imageUrl = imageDom.attr("src");

//            Elements publishedDoms = parse.select(".article-meta-published");
//            Element publishedDom = publishedDoms.get(0);
//            String publisedText = publishedDom.html();
            Instant published = Instant.now();

            Document document = new Document(
                    title,
                    null,
                    null,
                    pageUrl,
                    imageUrl,
                    published,
                    textHtml,
                    new HashSet<>(),
                    "Omni",
                    HTTPS_WWW_OMNI_SE
            );
            documentService.put(document);

            boolean a = true;
        } catch (IOException e) {
            log.error("Could not parse omni article", e);
        }
    }

    private WebClient getClient() {
        if (client == null) {
            client = new WebClient(BrowserVersion.BEST_SUPPORTED);
            client.getOptions().setThrowExceptionOnScriptError(false);
        }
        return client;
    }

    private void consume(Element cluster) {

        Element a = cluster.child(0).child(0);
        String title = a.html();
        log.info("title: {}", title);
        Elements articles = cluster.select(".article.article--fadehr.article--compact");

        articles.forEach(article -> {
//            article.removeClass("article--fadehr");
//            article.removeClass("article--compact");
//            article.addClass("article--full");
//            article.addClass("article--expand");
            String href = article.attr("href");

        });


        List<Element> links = articles.stream().map(ar -> ar.select("resource--link-link"))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        log.info("  links: {}", links);

    }

}
