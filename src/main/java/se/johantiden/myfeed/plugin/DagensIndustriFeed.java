package se.johantiden.myfeed.plugin;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.Feed;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class DagensIndustriFeed extends Feed {

    private static final Logger log = LoggerFactory.getLogger(DagensIndustriFeed.class);
    public static final String NAME = "Dagens Industri";
    public static final String URL = "https://www.di.se/rss";

    public DagensIndustriFeed() {
        super(NAME, URL, createFeedReader());
    }

    public static FeedReader createFeedReader() {
        return () -> {
            List<Document> documents = new RssFeedReader(NAME, "https://www.svd.se/?service=rss", URL).readAllAvailable();
            return documents.stream()
                    .map(createEntryMapper())
                    .collect(Collectors.toList());
        };
    }


    private static Function<Document, Document> createEntryMapper() {
        return document -> {

            document.imageUrl = findImage(document);

            handleCapitalizedSubject(document);

            return document;
        };
    }

    private static void handleCapitalizedSubject(Document document) {

        Pattern pattern = Pattern.compile("^[A-ZÅÄÖ]+\\. ");
        if (pattern.matcher(document.text).find()) {

            String[] splitText = pattern.split(document.text);
            document.text = splitText[1];

            String[] splitHtml = pattern.split(document.html);
            document.html = splitHtml[1];

        }
    }

    private static String findImage(Document document) {
        org.jsoup.nodes.Document doc = getJsoupDocument(document.getPageUrl());

        Elements figureImg = doc.select(".di_article-figure__picture > img");
        if(!figureImg.isEmpty()) {
            String src = figureImg.attr("srcset");
            String s = src.split(" ")[0];
            if (s.startsWith("//")) {
                s = "https:" + s;
            }
            return s;
        }

        return null;
    }


    private static org.jsoup.nodes.Document getJsoupDocument(String pageUrl) {
        try {
            return Jsoup.parse(new URL(pageUrl), 10_000);
        } catch (IOException e) {
            log.error("Could not jsoup-parse " + pageUrl, e);
            return null;
        }
    }

    private static boolean isPaywalled(Document document) {
        try {
            org.jsoup.nodes.Document parse = Jsoup.parse(new URL(document.getPageUrl()), 10_000);

            Elements select = parse.select(".paywall-loader");
            if(!select.isEmpty()) {
                log.debug("SVD Paywall: {}", document.getPageUrl());
                return true;
            }

            return false;

        } catch (IOException e) {
            log.error("Could not determine paywall", e);
            return false;
        }

    }

}
