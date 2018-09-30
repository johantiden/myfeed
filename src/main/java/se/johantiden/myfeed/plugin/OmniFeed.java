package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;
import se.johantiden.myfeed.service.DocumentService;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.jsoup.parser.TokenQueue.unescape;

public class OmniFeed extends Feed {

    public static final String HTTPS_WWW_OMNI_SE = "https://www.omni.se";
    private static final Logger log = LoggerFactory.getLogger(OmniFeed.class);

    public OmniFeed(DocumentService documentService) {
        super("Omni", HTTPS_WWW_OMNI_SE, feedReader(documentService));
    }

    private static FeedReader feedReader(DocumentService documentService) {
        return () -> run(documentService);
    }

    public static List<Document> run(DocumentService documentService) {
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
            return null;
        }
        List<Document> documents = hrefs.parallelStream()
                .map(href -> scrapeArticle(href, documentService))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        log.info("Done scraping Omni index...");

        return documents;
    }

    public static Document scrapeArticle(String href, DocumentService documentService
    ) {
        String pageUrl = HTTPS_WWW_OMNI_SE + href;

        if (documentService.hasDocumentWithUrl(pageUrl)) {
            return null;
        }

        try {
            org.jsoup.nodes.Document parse = Jsoup.parse(new URL(pageUrl), 10_000);

            Elements titleDoms = parse.select(".resource.resource--title");
            Element titleDom = titleDoms.get(0);
            String title = titleDom.html();

            Elements textDoms = parse.select(".resource.resource--text");
            Element textDom = textDoms.get(0);
            String textHtml = textDom.html();
            String text = html2text(textHtml);

            Elements imageDoms = parse.select("img.resource-img");
            Element imageDom = imageDoms.get(0);
            String imageUrl = imageDom.attr("src");

            Instant published = Instant.now();

            Document document = new Document(
                    title,
                    text,
                    null,
                    pageUrl,
                    imageUrl,
                    published,
                    "Omni",
                    HTTPS_WWW_OMNI_SE
            );
            return document;
        } catch (IOException e) {
            log.error("Could not parse omni article", e);
            return null;
        }
    }

    public static String html2text(String html) {
        return unescape(Jsoup.parse(html).text());
    }
}
