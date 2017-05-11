package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.classification.DocumentMatcher;
import se.johantiden.myfeed.controller.Subject;

@SuppressWarnings("SpellCheckingInspection")
public class DocumentClassifier {

    public static final String NEWS = "News";
    public static final String BIZ = "Biz";
    public static final String TECH = "Tech";
    public static final String SPORT = "Sport";
    public static final String BAD = "Bad";
    public static final String ERROR = "Errors";
    public static final String FUN = "Fun";
    public static final String CULTURE = "Culture";
    public static final String TORRENTS = "Torrents";

    public static final String UNMATCHED_TAB = "Unmatched";

    private DocumentClassifier() {
    }

    public static Subject getSubjectFor(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        if(m.has("trump")) { return new Subject("Trump"); }
        if(m.has("macron")) { return new Subject("Macron"); }

        return new Subject("");
    }

    public static String getTabFor(Document document) {
        if(isError(document)) {
            return ERROR;
        }

        if(isBad(document)) {
            return BAD;
        }

        if(isCulture(document)) {
            return CULTURE;
        }

        if(isTech(document)) {
            return TECH;
        }

        if(isFun(document)) {
            return FUN;
        }

        if(isBiz(document)) {
            return BIZ;
        }

        if(isSport(document)) {
            return SPORT;
        }

        if(isNews(document)) {
            return NEWS;
        }

        if(isTorrents(document)) {
            return TORRENTS;
        }

        return UNMATCHED_TAB;
    }

    private static boolean isTorrents(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return m.isFromFeed("eztv");

    }

    private static boolean isSport(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
        m.anyCategoryEquals("sport");
    }

    private static boolean isCulture(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
        m.anyCategoryEquals("kultur") ||
        m.anyCategoryEquals("kultur-noje") ||
        m.anyCategoryEquals("film") ||
        m.anyCategoryEquals("kultur-noje") ||
        m.anyCategoryEquals("movies") ||
        m.anyCategoryEquals("dnbok");
    }

    private static boolean isNews(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
        m.isFromFeed("Dagens Nyheter") && m.anyCategoryEquals("nyheter") ||
        m.isFromFeed("Al Jazeera") ||
        m.isFromFeed("TheLocal") ||
        m.isFromFeed("New York Times :: World") ||
        m.isFromFeed("Reddit::r/worldnews") ||
        m.isFromFeed("SVT Nyheter") ||
        m.anyCategoryEquals("sthlm") ||
        m.anyCategoryEquals("debatt") ||
        m.anyCategoryEquals("världen") ||
        m.anyCategoryEquals("ledare") ||
        m.anyCategoryEquals("sverige")

        ;
    }

    private static boolean isTech(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
        m.isFromFeed("Ars Technica") ||
        m.isFromFeed("Slashdot") ||
        m.anyCategoryEquals("science") ||
        m.isFromFeed("HackerNews")

        ;
    }

    private static boolean isFun(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
        m.isFromFeed("Reddit::top") ||
        m.isFromFeed("Reddit::r/all") ||
        m.isFromFeed("Reddit::r/AskReddit") ||
        m.isFromFeed("xkcd");
    }

    private static boolean isBiz(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
        m.anyCategoryEquals("näringsliv") ||
        m.anyCategoryEquals("ekonomi")
        ;
    }


    private static boolean isError(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return m.has("&#") && m.has(";");
    }

    private static boolean isBad(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);
        return
        m.hasCaseSensitive("NewsGrid") ||
        m.has("webb-tv") ||
        m.has("mat-dryck") ||
        m.isFromFeed("TheLocal") &&
        (m.startsWith("these are") ||
         m.startsWithCaseSensitive("WATCH") ||
         m.startsWith("this") ||
         m.has("book") ||
         m.has("rate")) ||
        m.isFromFeed("TheLocal") && m.has("hiring") ||
        m.anyCategoryEquals("motor") ||
        m.anyCategoryEquals("cars technica") ||
        m.startsWithCaseSensitive("Årets") ||
        m.startsWithCaseSensitive("I dag") ||
        m.has("här är") ||
        m.startsWith("enkät") ||
        m.anyCategoryEquals("blackpeopletwitter") ||
        m.anyCategoryEquals("oldschoolcool") ||
        m.anyCategoryEquals("quityourbullshit") ||
        m.anyCategoryEquals("adviceanimals") ||
        m.isFromFeed("Svenska Dagbladet") && m.startsWithCaseSensitive("VIDEO") ||
        m.has("turist") ||
        d.pageUrl.contains("uutiset") ||
        m.has("-- number") ||
        m.has("--number") ||
        m.has("blondinbella") ||
        m.anyCategoryEquals("familj") ||
        d.isPaywalled ||
        m.isFromFeed("eztv") && !m.has("720p") ||
        m.anyCategoryEquals("vin & mat") ||
        m.has("nutidstest");
    }
}
