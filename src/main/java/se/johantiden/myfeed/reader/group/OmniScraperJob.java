package se.johantiden.myfeed.reader.group;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//@Component
public class OmniScraperJob {

    private static final Logger log = LoggerFactory.getLogger(OmniScraperJob.class);


//    @Scheduled(fixedRate = 10*1000)
    public void run() throws IOException {
        log.info("Scraping Omni...");

        try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52)) {
            final HtmlPage page = webClient.getPage("https://omni.se");
            List<DomElement> clusters = page.getByXPath("//div[contains(@class, 'cluster')]");

            for (DomElement cluster : clusters) {
                List<DomElement> articles = cluster.getByXPath("//a[contains(@class, 'article-link')]");
                List<DomElement> links = cluster.getByXPath("//div[contains(@class, 'resource--link')]");
                for (DomElement article : articles) {
                    article.click();
                    List<DomElement> links2 = cluster.getByXPath("//div[contains(@class, 'resource--link')]");

//                    article.getByXPath("//article[contains(@class, 'article')]");
                    boolean a = true;
                }

            }
        }


        org.jsoup.nodes.Document document = Jsoup.parse(new URL("https://omni.se"), 30_000);
        Elements groupClusters = document.select(".group.group--cluster");

        groupClusters.forEach(this::consume);

        log.info("Done scraping Omni...");

    }

    private void consume(Element cluster) {

        Element a = cluster.child(0).child(0);
        String title = a.html();
        log.info("title: {}", title);
        Elements articles = cluster.select(".article.article--fadehr.article--compact");

        articles.forEach(article -> {
            article.removeClass("article--fadehr");
            article.removeClass("article--compact");
            article.addClass("article--full");
            article.addClass("article--expand");
        });


        List<Element> links = articles.stream().map(ar -> ar.select("resource--link-link"))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        log.info("  links: {}", links);

    }


    private static boolean isPaywalled(Document document) {
        try {
            org.jsoup.nodes.Document parse = Jsoup.parse(new URL(document.pageUrl), 10_000);

            Elements select = parse.select(".paywall-loader");
            if (!select.isEmpty()) {
                return true;
            }

            return false;

        } catch (IOException e) {
            return false;
        }

    }

}
