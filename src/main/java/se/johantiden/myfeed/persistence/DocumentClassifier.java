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
        if(m.has("obama")) { s.add(s("Obama")); }
        if(m.has("trump")) { s.add(s("Trump")); }
        if(m.has("Tim Berners-Lee")) { s.add(s("Tim Berners-Lee")); }
        if(m.has("german") || m.has("tysk") || m.has("merkel")) { s.add(s("Tyskland")); }
        if(m.has("merkel")) { s.add(s("Merkel")); }
        if(m.has("Hitler") || m.hasCaseSensitive("Nazi")) { s.add(s("Nazism")); }
        if(m.has("twitter") || m.has("tweet")) { s.add(s("Twitter")); }
        if(m.has("macron")) { s.add(s("Macron")); }
        if(m.has("turist")) { s.add(s("Turist")); }
        if(m.has("hockey") || m.has("Henrik Lundqvist") || m.has("New York Rangers") || m.has("Nicklas Bäckström")) { s.add(s("Hockey")); }
        if(m.has("fotboll") || m.has("allsvensk") || m.has("Champions League") || m.has("premier league") || m.has("superettan") || m.has("Benfica ")) { s.add(s("Fotboll")); }
        if(m.has("handboll") || m.has("H65")) { s.add(s("Handboll")); }
        if(m.has("bordtennis") || m.has("pingis")) { s.add(s("Bordtennis")); }
        if(m.has("Giro") && m.has("Italia")) { s.add(s("Cykling")); }
        if(m.has("Diamond League")) { s.add(s("Friidrott")); }
        if(m.has("V75")) { s.add(s("Trav")); }
        if(m.has("Johaug")) { s.add(s("Skidor")); }
        if(m.has("speedway")) { s.add(s("Speedway")); }
        if(m.anySubjectEquals("sport") && m.hasCaseSensitive("OS") || m.has("olympisk")) { s.add(s("OS")); }
        if(m.has("netflix")) { s.add(s("Netflix")); }
        if(m.has("Boko Haram")) { s.add(s("Boko Haram")); }
        if(m.has("väder") || m.has("Cyclone") || m.has("Cyklon")) { s.add(s("Väder")); }
        if(m.has("Eurovision")) { s.add(s("Eurovision")); }
        if(m.has("Venezuela") || m.has("Maduro")) { s.add(s("Venezuela")); }
        if(m.has("North Korea") || m.has("Nordkorea")) { s.add(s("Nordkorea")); }
        if(m.has("South Korea") || m.has("Sydkorea") || m.has("Seoul")) { s.add(s("Sydkorea")); }
        if(m.has("Myanmar") || m.has("Burma")|| m.has("Aung San Suu Kyi")) { s.add(s("Myanmar")); }
        if(m.has("Iran") || m.has("Rouhani")) { s.add(s("Iran")); }
        if(m.has("Rouhani")) { s.add(s("Rouhani")); }
        if(m.has("China") || m.has("Kina") || m.has("Kines")) { s.add(s("Kina")); }
        if(m.has("Net Neutrality")) { s.add(s("Net Neutrality")); }
        if(m.has("Albanien")) { s.add(s("Albanien")); }
        if(m.has("Bosnia") || m.has("Bosnien")) { s.add(s("Bosnien")); }
        if(m.has("Belgium")) { s.add(s("Belgien")); }
        if(m.has("India")) { s.add(s("Indien")); }
        if(m.has("Brazil")) { s.add(s("Brasilien")); }
        if(m.has("Egypt")) { s.add(s("Egypten")); }
        if(m.has("Yemen")) { s.add(s("Yemen")); }
        if(m.has("Ukrain")) { s.add(s("Ukraina")); }
        if(m.has("Bangladesh")) { s.add(s("Bangladesh")); }
        if(m.has("Malaysia")) { s.add(s("Malaysia")); }
        if(m.has("France ")) { s.add(s("France")); }
        if(m.has("Australia")) { s.add(s("Australia")); }
        if(m.has("Göteborg") || m.has("Gothemburg")) { s.add(s("Göteborg")); }
        if(m.has("Malmö")) { s.add(s("Malmö")); }
        if(m.has("European Union") || m.hasCaseSensitive("EU")) { s.add(s("EU")); }
        if(m.has("Europe") || m.has("Europa")) { s.add(s("Europa")); }
        if(m.has("Elfenbenskusten") || m.has("Ivory Coast")) { s.add(s("Elfenbenskusten")); }
        if(m.has("Storbritannien") || m.has("London") || m.has("England") || m.has("Britain")) { s.add(s("Storbritannien")); }
        if(m.hasCaseSensitive("USA") || m.has("U.S.") || m.has("america") && !m.has("south america") || m.hasCaseSensitive("FBI")) { s.add(s("USA")); }
        if(m.hasCaseSensitive("FBI")) { s.add(s("FBI")); }
        if(m.has("Mexiko") || m.has("Mexico") || m.has("Mexican")) { s.add(s("Mexico")); }
        if(m.has("Turkey") || m.has("Turkish") || m.has("Turkiet")) { s.add(s("Turkiet")); }
        if(m.has("Greece") || m.has("Greek") || m.has("Grekland") || m.has("Grek")) { s.add(s("Grekland")); }
        if(m.has("Spotify")) { s.add(s("Spotify")); }
        if(m.has("Microsoft")) { s.add(s("Microsoft")); }
        if(m.has("Samsung")) { s.add(s("Samsung")); }
        if(m.has("Apple")) { s.add(s("Apple")); }
        if(m.has("block") && m.has("chain")) { s.add(s("Blockchain")); }
        if(m.has("Facebook")) { s.add(s("Facebook")); }
        if(m.has("Google")) { s.add(s("Google")); }
        if(m.has("Palestin")) { s.add(s("Palestina")); }
        if(m.has("Afghanistan") || m.has("Afganistan") || m.has("Baloch")) { s.add(s("Afghanistan")); }
        if(m.has("Pakistan") || m.has("Baloch")) { s.add(s("Pakistan")); }
        if(m.has("Baloch")) { s.add(s("Baloch")); }
        if(m.has("Taiwan")) { s.add(s("Taiwan")); }
        if(m.has("Israel")) { s.add(s("Israel")); }
        if(m.has("Schweiz")) { s.add(s("Schweiz")); }
        if(m.has("Tunis")) { s.add(s("Tunisien")); }
        if(m.has("Portugal")) { s.add(s("Portugal")); }
        if(m.has("Japan")) { s.add(s("Japan")); }
        if(m.has("Argentin")) { s.add(s("Argentina")); }
        if(m.has("Russia") || m.has("Ryssland") || m.has("Rysk")) { s.add(s("Ryssland")); }
        if(m.has("Canada") || m.has("Kanada") || m.has("Canadian") || m.has("Kanaden")) { s.add(s("Kanada")); }
        if(m.has("Museum")) { s.add(s("Museum")); }
        if(m.has("terror")) { s.add(s("Terror")); }
        if(m.has("Nigeria")) { s.add(s("Nigeria")); }
        if(m.has("Ebola")) { s.add(s("Ebola")); }
        if(m.has("Kiev") || m.has("Ukrain")) { s.add(s("Ukraina")); }
        if(m.isFromFeed("TheLocal") && m.has("book")) { s.add(s("Books")); }
        if(m.has("IT-attacken") || m.has("Ransomware") || m.has("Cyberattack") || m.has("cyber") && m.has("attack") || m.has("Malware") || m.has("WanaCry") || m.has("WannaCry")) { s.add(s("Security")); }
        if(m.has("Brexit")) { s.add(s("Brexit")); }
        if(m.has("Stockholm")) { s.add(s("Stockholm")); }
        if(m.has("Norge") || m.has("Norway")) { s.add(s("Norge")); }
        if(m.has("Feministiskt initiativ")) { s.add(s("Feministiskt Initiativ")); }
        if(m.has("Miljöpartiet") || m.hasCaseSensitive("MP")) { s.add(s("Miljöpartiet")); }
        if(m.has("Socialdemokraterna")) { s.add(s("Socialdemokraterna")); }
        if(m.has("Sverigemokraterna") && m.hasCaseSensitive("SD")) { s.add(s("Sverigemokraterna")); }
        if(m.has("Moderaterna")) { s.add(s("Moderaterna")); }
        if(m.has("kvinnor") && m.has("män")) { s.add(s("Kvinnor")); }
        if(m.has("kvinnor") && m.has("män")) { s.add(s("Män")); }
        if(m.isFromFeed("TheLocal") && m.has("recipe:")) { s.add(s("Recipe")); }
        if(m.isFromFeed("HackerNews") && m.has("hiring")) { s.add(s("Hiring")); }
        if(m.has("Vänsterpartiet")) { s.add(s("Vänsterpartiet")); }
        if(m.has("Centerpartiet")) { s.add(s("Centerpartiet")); }
        if(m.has("Daesh") || m.hasCaseSensitive("ISIL") || m.hasCaseSensitive("ISIS") || m.has("terror") && m.hasCaseSensitive("IS")) { s.add(s("Daesh")); }


        // Badness:
        if(m.hasCaseSensitive("NewsGrid")) { s.add(s("NewsGrid")); }
        if(m.has("cars technica")) { s.add(s("Cars")); }
        if(m.has("webb-tv")) { s.add(s("webb-tv")); }
        if(m.anyCategoryEquals("dealmaster")) { s.add(s("dealmaster")); }
        if(m.has("dödsfäll")) { s.add(s("Dödsfälla")); }
        if(m.has("mat-dryck")) { s.add(s("mat-dryck")); }
        if(m.anyCategoryEquals("motor")) { s.add(s("Motor")); }
        if(m.anyCategoryEquals("serier")) { s.add(s("Serier")); }
        if(m.hasCaseSensitive("Här är") || m.has("– här är") || m.has("- här är")) { s.add(s("Här är")); }
        if(m.has("turist")) { s.add(s("Turist")); }
        if(d.pageUrl.contains("uutiset")) { s.add(s("Uutiset")); }
        if(m.isFromFeed("Svenska Dagbladet") && m.startsWithCaseSensitive("VIDEO")) { s.add(s("VIDEO")); }


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
            m.anyCategoryEquals("dnbok") ||
            m.anySubjectEquals("Eurovision")||
            m.anySubjectEquals("Museum") ||
            m.anySubjectEquals("Books");
    }

    private static boolean isNews(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
            m.isFromFeed("Dagens Nyheter") && m.anyCategoryEquals("nyheter") ||
            m.isFromFeed("Al Jazeera") ||
            m.isFromFeed("TheLocal") ||
            m.isFromFeed("New York Times - World") ||
            m.isFromFeed("Los Angeles Times - World") ||
            m.isFromFeed("Reddit - r/worldnews") ||
            m.isFromFeed("SVT Nyheter") ||
            m.anyCategoryEquals("sthlm") ||
            m.anyCategoryEquals("debatt") ||
            m.anyCategoryEquals("världen") ||
            m.anyCategoryEquals("ledare") ||
            m.anyCategoryEquals("sverige");
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
            m.anySubjectEquals("Security") ||
            m.anySubjectEquals("Netflix") ||
            m.anySubjectEquals("Spotify") ||
            m.anySubjectEquals("Security");
    }

    private static boolean isFun(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
            m.isFromFeed("Reddit - top") ||
            m.isFromFeed("Reddit - r/all") ||
            m.isFromFeed("Reddit - r/AskReddit") ||
            m.isFromFeed("xkcd");
    }

    private static boolean isBiz(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
            m.anyCategoryEquals("näringsliv") ||
            m.anyCategoryEquals("ekonomi");
    }


    private static boolean isError(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return m.has("&#") && m.has(";");
    }

    private static boolean isBad(Document d) {
        // Only match on subjects here to give the user a reason why it is "bad"
        DocumentMatcher m = new DocumentMatcher(d);
        return
            m.anySubjectEquals("Hiring") ||
            m.anySubjectEquals("Recipe") ||
            m.anySubjectEquals("webb-tv") ||
            m.anySubjectEquals("serier") ||
            m.anySubjectEquals("cars") ||
            m.anySubjectEquals("dealmaster");
    }
}
