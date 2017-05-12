package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.classification.DocumentMatcher;
import se.johantiden.myfeed.controller.Subject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public class DocumentClassifier {

    public static final String NEWS = "Nyheter";
    public static final String BIZ = "Biz";
    public static final String TECH = "Tech";
    public static final String SPORT = "Sport";
    public static final String BAD = "Bad";
    public static final String ERROR = "Errors";
    public static final String FUN = "Fun";
    public static final String CULTURE = "Kultur";
    public static final String TORRENTS = "Torrents";

    public static final String UNMATCHED_TAB = "Unmatched";

    private DocumentClassifier() {
    }

    public static List<Subject> getSubjectFor(Document d) {
        List<Subject> s = new ArrayList<>();
        DocumentMatcher m = new DocumentMatcher(d);

        if(m.anyCategoryEquals("ledare")) { s.add(s("Ledare")); }
        if(m.has("your") && m.has("briefing")) { s.add(s("Briefing")); }
        if(m.has("trump")) { s.add(s("Trump")); }
        if(m.has("german") || m.has("tysk") || m.has("merkel")) { s.add(s("Tyskland")); }
        if(m.has("merkel")) { s.add(s("Merkel")); }
        if(m.has("Hitler") || m.hasCaseSensitive("Nazi")) { s.add(s("Nazism")); }
        if(m.has("twitter") || m.has("tweet")) { s.add(s("Twitter")); }
        if(m.has("macron")) { s.add(s("Macron")); }
        if(m.has("turist")) { s.add(s("Turist")); }
        if(m.has("hockey") || m.has("Henrik Lundqvist") || m.has("New York Rangers") || m.has("Nicklas Bäckström")) { s.add(s("Hockey")); }
        if(m.has("fotboll") || m.has("allsvensk") || m.has("Champions League") || m.has("premier league") || m.has("superettan")) { s.add(s("Fotboll")); }
        if(m.has("bordtennis") || m.has("pingis")) { s.add(s("Bordtennis")); }
        if(m.has("handboll")) { s.add(s("Handboll")); }
        if(m.has("netflix")) { s.add(s("Netflix")); }
        if(m.has("Venezuela")) { s.add(s("Venezuela")); }
        if(m.has("North Korea") || m.has("Nordkorea")) { s.add(s("Nordkorea")); }
        if(m.has("South Korea") || m.has("Sydkorea") || m.has("Seoul")) { s.add(s("Sydkorea")); }
        if(m.has("Myanmar") || m.has("Burma")|| m.has("Aung San Suu Kyi")) { s.add(s("Myanmar")); }
        if(m.has("Iran") || m.has("Rouhani")) { s.add(s("Iran")); }
        if(m.has("Rouhani")) { s.add(s("Rouhani")); }
        if(m.has("China")) { s.add(s("Kina")); }
        if(m.has("Belgium")) { s.add(s("Belgien")); }
        if(m.has("India")) { s.add(s("Indien")); }
        if(m.has("Brazil")) { s.add(s("Brasilien")); }
        if(m.has("Bangladesh")) { s.add(s("Bangladesh")); }
        if(m.has("Malaysia")) { s.add(s("Malaysia")); }
        if(m.has("France ")) { s.add(s("France")); }
        if(m.has("Australia")) { s.add(s("Australia")); }
        if(m.has("Göteborg") || m.has("Gothemburg")) { s.add(s("Göteborg")); }
        if(m.has("Malmö")) { s.add(s("Malmö")); }
        if(m.has("European Union")) { s.add(s("EU")); }
        if(m.has("Storbritannien") || m.has("London")) { s.add(s("Storbritannien")); }
        if(m.has("U.S.") || m.has("america") && !m.has("south america") || m.hasCaseSensitive("FBI")) { s.add(s("USA")); }
        if(m.hasCaseSensitive("FBI")) { s.add(s("FBI")); }
        if(m.has("Mexiko") || m.has("Mexico") || m.has("Mexican")) { s.add(s("Mexico")); }
        if(m.has("Turkey") || m.has("Turkish") || m.has("Turkiet")) { s.add(s("Turkiet")); }
        if(m.has("Greece") || m.has("Greek") || m.has("Grekland")) { s.add(s("Grekland")); }
        if(m.has("Spotify")) { s.add(s("Spotify")); }
        if(m.has("Palestin")) { s.add(s("Palestina")); }
        if(m.has("Israel")) { s.add(s("Israel")); }
        if(m.has("Ebola")) { s.add(s("Ebola")); }
        if(m.has("Brexit")) { s.add(s("Brexit")); }
        if(m.isFromFeed("TheLocal") && m.has("recipe:")) { s.add(s("Recipe")); }
        if(m.isFromFeed("HackerNews") && m.has("hiring")) { s.add(s("Hiring")); }
        if(m.has("Vänsterpartiet")) { s.add(s("Vänsterpartiet")); }
        if(m.has("Centerpartiet") || m.has("Swed") && m.has("Centre")) { s.add(s("Centerpartiet")); }
        if(m.has("Daesh") || m.hasCaseSensitive("ISIL") || m.hasCaseSensitive("ISIS") || m.has("terror") && m.hasCaseSensitive("IS")) { s.add(s("Daesh")); }

        return s;
    }

    private static Subject s(final String title) {return new Subject(title);}

    public static String getTabFor(Document document) {
        if(isError(document)) {
            return ERROR;
        }

        if(isBad(document)) {
            return BAD;
        }

        if(isTech(document)) {
            return TECH;
        }

        if(isCulture(document)) {
            return CULTURE;
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
            m.has("säkerhetshål") ||
            m.has("cyber attack") ||
            m.has("cyberattack") ||
            m.anySubjectEquals("Netflix") ||
            m.anySubjectEquals("Spotify");
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
         m.has("rate") ||
         m.has("recipe:")) ||
        m.isFromFeed("TheLocal") && m.has("hiring") ||
        m.anyCategoryEquals("motor") ||
        m.anyCategoryEquals("cars technica") ||
        m.startsWithCaseSensitive("Årets") ||
        m.startsWithCaseSensitive("I dag") ||
        m.hasCaseSensitive("Här är") ||
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
        m.has("nutidstest") ||
        m.anySubjectEquals("Hiring")||
        m.anySubjectEquals("Recipe");
    }
}
