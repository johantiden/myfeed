package se.johantiden.myfeed.plugin;


import org.jsoup.Jsoup;
import se.johantiden.myfeed.persistence.Document;

import java.util.List;


public interface FeedReader {

    static String unescape(String string) {

        String unescaped = string.replaceAll("&#38;", "&");
        unescaped = unescaped.replaceAll("&#34;", "\"");
        unescaped = unescaped.replaceAll("&#039;", "'");
        unescaped = unescaped.replaceAll("&#8216;", "'");
        unescaped = unescaped.replaceAll("&#8217;", "'");
        unescaped = unescaped.replaceAll("&#252;", "ü");
        unescaped = unescaped.replaceAll("â€™", "ü");
        unescaped = unescaped.replaceAll("&amp;", "&");
        unescaped = unescaped.replaceAll("&ndash;", "–");
        unescaped = unescaped.replaceAll("&mdash;", "—");
        unescaped = unescaped.replaceAll("â€™", "'");
        unescaped = unescaped.replaceAll("&quot;", "\"");

//        if(!unescaped.equals(string)) {
//            log.debug("Unescaped! {} -> {}", string, unescaped);
//        }

        return unescaped;
    }

    static String html2text(String html) {
        return unescape(Jsoup.parse(html).text());
    }

    static String pruneUntrustedHtml(String text) {
        if (text.contains("<")) {
            int i = text.indexOf('<');
            String pruned = text.substring(0, i);
            return pruned;
        }
        return text;
    }

    List<Document> readAllAvailable();

}
