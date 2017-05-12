package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.classification.DocumentMatcher;
import se.johantiden.myfeed.controller.Subject;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Subject> getSubjectFor(Document d) {
        List<Subject> subjects = new ArrayList<>();
        DocumentMatcher m = new DocumentMatcher(d);

        if(m.has("trump")) { subjects.add(s("Trump")); }
        if(m.has("macron")) { subjects.add(s("Macron")); }
        if(m.has("turist")) { subjects.add(s("turist")); }
        if(m.has("hockey")) { subjects.add(s("Hockey")); }
        if(m.has("fotboll")) { subjects.add(s("Fotboll")); }

        return subjects;
    }

    private static Subject s(final String title) {return new Subject(title);}

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
                m.anyCategoryEquals("sport") ||
                m.has("bordtennis") ||
                m.has("fotboll") ||
                m.has("handboll") ||
                m.has("hockey") ||
                m.has("soccer") ||
                m.has("champions league") ||
                m.has("bordtennis");
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
            m.isFromFeed("HackerNews") ||
            m.has("ransomware") ||
            m.has("virus") && m.has("säkerhet") ||
            m.has("cyber attack") || 
            m.has("cyberattack");
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
        m.isFromFeed("Dagens Nyheter") && m.anyCategoryEquals("serier") ||
        m.has("nutidstest");
    }
}
