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
    private static final String WEATHER = "Väder";
    public static final String CULTURE = "Kultur";

    public static final String UNMATCHED_TAB = "Unmatched";
    public static final String STORBRITANNIEN = "Storbritannien";

    private DocumentClassifier() {
    }

    public static List<Subject> getSubjectFor(Document d) {
        List<Subject> s = new ArrayList<>();
        DocumentMatcher m = new DocumentMatcher(d) {
            @Override
            public boolean anySubjectEquals(String subject) {
                throw new IllegalStateException("Don't check subjects here! The list will be set later. Check subjects directly in the list instead.");
            }
        };

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
        if(m.anyCategoryEquals("film")) { s.add(s("Film")); }
        if(m.has("turist")) { s.add(s("Turist")); }
        if(m.has("formel 1")) { s.add(s("Formel 1")); }
        if(m.has("rallycross")) { s.add(s("Rallycross")); }
        if(m.has("GP-hoppning")) { s.add(s("Hästsport")); }
        if(m.has("hockey") || m.has("Henrik Lundqvist") || m.has("New York Rangers") || m.has("Nicklas Bäckström")) { s.add(s("Hockey")); }
        if(m.has("fotboll") || m.has("allsvensk") || m.has("Champions League") || m.has("premier league") || m.has("superettan") || m.has("Benfica ") || m.has("Malmö FF")) { s.add(s("Fotboll")); }
        if(m.has("handboll") || m.has("H65")) { s.add(s("Handboll")); }
        if(m.has("bordtennis") || m.has("pingis")) { s.add(s("Bordtennis")); }
        if(m.has("Giro") && m.has("Italia")) { s.add(s("Cykling")); }
        if(m.has("Diamond League")) { s.add(s("Friidrott")); }
        if(m.has("V75")) { s.add(s("Trav")); }
        if(m.has("Johaug")) { s.add(s("Skidor")); }
        if(m.has("speedway")) { s.add(s("Speedway")); }
        if(anySubjectEquals(s, "sport") && m.hasCaseSensitive("OS") || m.has("olympisk")) { s.add(s("OS")); }
        if(m.has("netflix")) { s.add(s("Netflix")); }
        if(m.has("Boko Haram")) { s.add(s("Boko Haram")); }
        if(m.has("väder") && !m.has("blåsväder") || m.has("Cyclone") || m.has("Cyklon") || m.has("Thunder")) { s.add(s("Väder")); }
        if(m.has("Eurovision")) { s.add(s("Eurovision")); }
        if(m.has("Nepal")) { s.add(s("Nepal")); }
        if(m.has("Syria") || m.has("Syrien") || m.has("Damascus") || m.has("Damaskus")) { s.add(s("Syrien")); }
        if(m.has("Venezuela") || m.has("Maduro")) { s.add(s("Venezuela")); }
        if(m.has("North Korea") || m.has("Nordkorea")) { s.add(s("Nordkorea")); }
        if(m.has("South Korea") || m.has("Sydkorea") || m.has("Seoul")) { s.add(s("Sydkorea")); }
        if(m.has("Myanmar") || m.has("Burma")|| m.has("Aung San Suu Kyi")) { s.add(s("Myanmar")); }
        if(m.hasCaseSensitive("Iran") || m.has("Rouhani")) { s.add(s("Iran")); }
        if(m.has("Rouhani")) { s.add(s("Rouhani")); }
        if(m.has("China") || m.has("Kina") || m.has("Xi Jinping") || m.has("Kines")) { s.add(s("Kina")); }
        if(m.has("Net Neutrality")) { s.add(s("Net Neutrality")); }
        if(m.has("Albanien")) { s.add(s("Albanien")); }
        if(m.has("Bosnia") || m.has("Bosnien")) { s.add(s("Bosnien")); }
        if(m.has("Belgium")) { s.add(s("Belgien")); }
        if(m.has("India")) { s.add(s("Indien")); }
        if(m.has("Brazil")) { s.add(s("Brasilien")); }
        if(m.has("Egypt")) { s.add(s("Egypten")); }
        if(m.has("Yemen")) { s.add(s("Yemen")); }
        if(m.has("Bangladesh")) { s.add(s("Bangladesh")); }
        if(m.has("Malaysia")) { s.add(s("Malaysia")); }
        if(m.has("AskReddit")) { s.add(s("AskReddit")); }
        if(m.anyCategoryEquals("science")) { s.add(s("Forskning")); }
        if(m.has("France") || m.has("Frankrike") || m.has("Fransk") || m.has("French") || m.has("Paris")) { s.add(s("Frankrike")); }
        if(m.has("Australia")) { s.add(s("Australia")); }
        if(m.has("Göteborg") || m.has("Gothenburg")) { s.add(s("Göteborg")); }
        if(m.has("Malmö")) { s.add(s("Malmö")); }
        if(m.has("Dutch")) { s.add(s("Nederländerna")); }
        if(m.has("European Union") || m.hasCaseSensitive("EU")) { s.add(s("EU")); }
        if(m.has("Europe") || m.has("Europa")) { s.add(s("Europa")); }
        if(m.has("Elfenbenskusten") || m.has("Ivory Coast")) { s.add(s("Elfenbenskusten")); }
        if(m.has(STORBRITANNIEN) || m.has("London") || m.has("England") || m.has("Britain") || m.has("Scotland")) { s.add(s(STORBRITANNIEN)); }
        if(m.hasCaseSensitive("US") || m.has("U.S.") || m.has("america") && !m.has("south america") || m.hasCaseSensitive("FBI")) { s.add(s("USA")); }
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
        if(m.has("Russia") || m.has("Ryssland") || m.has("Rysk") || m.hasCaseSensitive("Moskva")) { s.add(s("Ryssland")); }
        if(m.has("Canada") || m.has("Kanada") || m.has("Canadian") || m.has("Kanaden")) { s.add(s("Kanada")); }
        if(m.has("Museum")) { s.add(s("Museum")); }
        if(m.has("musik") || m.has("hiphop")) { s.add(s("Musik")); }
        if(m.has("terror")) { s.add(s("Terror")); }
        if(m.has("Nigeria")) { s.add(s("Nigeria")); }
        if(m.has("Finland")) { s.add(s("Finland")); }
        if(m.has("South Sudan")) { s.add(s("Sydsudan")); }
        if(m.has("Ebola")) { s.add(s("Ebola")); }
        if(m.has("Cholera") | m.has("Kolera")) { s.add(s("Kolera")); }
        if(m.has("Kiev") || m.has("Ukrain")) { s.add(s("Ukraina")); }
        if(m.has("IT-attacken") || m.has("Ransomware") || m.has("Cyberattack") || m.has("cyber") && m.has("attack") || m.has("Malware") || m.has("WanaCry") || m.has("WannaCry") || m.has("Hacker") && !m.has("Hacker News") && !m.has("HackerNews") || m.has("hacking") || m.has("security") && m.has("computer") || m.has("IT-utpressning") || m.has("IT-angrepp") || m.has("Internet Security")) { s.add(s("IT-Säkerhet")); }
        if(m.has("Brexit")) { s.add(s("Brexit")); }
        if(m.has("Stockholm")) { s.add(s("Stockholm")); }
        if(anySubjectEquals(s, "Stockholm") || m.has("Östersund") || m.anyCategoryEquals("Sverige") || m.hasCaseSensitive("Umeå") || m.has("Liseberg") || m.has("Strömsund") || m.has("Norrköping") || m.hasCaseSensitive("Swedish") || m.hasCaseSensitive("Swede") || m.hasCaseSensitive("Västervik") || m.has("Katrineholm") || m.has("Uppsala")) {
            s.add(s("Inrikes")); }
        if(m.has("Norge") || m.has("Norway") || m.has("norska")) { s.add(s("Norge")); }
        if(m.has("Feministiskt initiativ")) { s.add(s("Feministiskt Initiativ")); }
        if(m.has("Miljöpartiet") || m.hasCaseSensitive("MP") && !anySubjectEquals(s, STORBRITANNIEN)) { s.add(s("Miljöpartiet")); }
        if(m.has("Sverigemokraterna") && m.hasCaseSensitive("SD") || m.has("Jimmie Åkesson")) { s.add(s("Sverigemokraterna")); }
        if(m.has("Jimmie Åkesson")) { s.add(s("Jimmie Åkesson")); }
        if(m.hasCaseSensitive("LO")) { s.add(s("LO")); }
        if(m.has("Moderaterna") || m.has("Kinberg Batra")) { s.add(s("Moderaterna")); }
        if(m.has("Kinberg Batra")) { s.add(s("Kinberg Batra")); }
        if(m.has("debatt")) { s.add(s("Debatt")); }
        if(m.has("Kongo-Kinshasa")) { s.add(s("Kongo-Kinshasa")); }
        if(m.hasCaseSensitive("Oman")) { s.add(s("Oman")); }
        if(m.hasCaseSensitive("CCTV")) { s.add(s("Foliehatt")); }
        if(m.hasCaseSensitive("Iraq") || m.hasCaseSensitive("Irak")) { s.add(s("Irak")); }
        if(m.hasCaseSensitive("Cuba") || m.hasCaseSensitive("Kuba")) { s.add(s("Kuba")); }
        if(m.has("kvinnor") && m.has("män")) { s.add(s("Kvinnor")); }
        if(m.has("kvinnor") && m.has("män")) { s.add(s("Män")); }
        if(m.isFromFeed("TheLocal") && m.has("recipe:")) { s.add(s("Recipe")); }
        if(m.isFromFeed("HackerNews") && m.has("hiring")) { s.add(s("Hiring")); }
        if(m.has("Vänsterpartiet")) { s.add(s("Vänsterpartiet")); }
        if(m.has("Centerpartiet") || m.has("Annie Lööf")) { s.add(s("Centerpartiet")); }
        if(m.has("Annie Lööf")) { s.add(s("Annie Lööf")); }
        if(m.has("Vänsterpartiet") || m.has("Jonas Sjöstedt")) { s.add(s("Vänsterpartiet")); }
        if(m.has("Jonas Sjöstedt")) { s.add(s("Jonas Sjöstedt")); }
        if(m.has("Kristdemokraterna") || m.has("Busch Thor") || m.hasCaseSensitive("KD")) { s.add(s("Kristdemokraterna")); }
        if(m.has("Busch Thor")) { s.add(s("Ebba Busch Thor")); }
        if(m.has("Idagsidan")) { s.add(s("Idagsidan")); }
        if(m.has("historian")) { s.add(s("History")); }
        if(m.has("Daesh") || m.hasCaseSensitive("ISIL") || m.hasCaseSensitive("ISIS") || m.has("terror") && m.hasCaseSensitive("IS")) { s.add(s("Daesh")); }
        if(m.has("Socialdemokraterna") && !s.contains("Tyskland")) { s.add(s("Socialdemokraterna")); }
        if(m.has("mat-dryck") || m.anyCategoryEquals("Restaurants")) { s.add(s("Mat")); }

        // Badness:
        if(m.hasCaseSensitive("NewsGrid")) { s.add(s("NewsGrid")); }
        if(m.has("cars technica")) { s.add(s("Cars")); }
        if(m.has("webb-tv")) { s.add(s("webb-tv")); }
        if(m.has("leagueoflegends")) { s.add(s("leagueoflegends")); }
        if(m.anyCategoryEquals("dealmaster")) { s.add(s("dealmaster")); }
        if(m.has("dödsfäll")) { s.add(s("Dödsfälla")); }
        if(m.anyCategoryEquals("motor")) { s.add(s("Motor")); }
        if(m.anyCategoryEquals("serier")) { s.add(s("Serier")); }
        if(m.hasCaseSensitive("Här är") || m.has("– här är") || m.has("- här är")) { s.add(s("Här är")); }
        if(m.has("turist")) { s.add(s("Turist")); }
        if(d.pageUrl.contains("uutiset")) { s.add(s("Uutiset")); }
        if(m.isFromFeed("Svenska Dagbladet") && m.startsWithCaseSensitive("VIDEO")) { s.add(s("VIDEO")); }


        return s;
    }

    private static boolean anySubjectEquals(List<Subject> subjects, String subject) {
        return subjects.stream().anyMatch(s -> s.getTitle().equalsIgnoreCase(subject));
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

        if(isVäder(document)) {
            return WEATHER;
        }

        if(isNews(document)) {
            return NEWS;
        }

        return UNMATCHED_TAB;
    }

    private static boolean isVäder(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return m.anySubjectEquals("väder");
    }

    private static boolean isSport(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
            m.anyCategoryEquals("sport") ||
            m.has("bordtennis") ||
            m.anySubjectEquals("fotboll") ||
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
            m.anySubjectEquals("Books") ||
            m.anySubjectEquals("History");
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
            m.anyCategoryEquals("worldnews") ||
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
            m.anySubjectEquals("Forskning") ||
            m.anyCategoryEquals("ProgrammerHumor") ||
            m.isFromFeed("HackerNews") ||
            m.anySubjectEquals("IT-Säkerhet") ||
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
            m.anySubjectEquals("dealmaster") ||
            m.anySubjectEquals("leagueoflegends") ||
            m.anySubjectEquals("Idagsidan") ||
            m.anySubjectEquals("Mat") ||
            m.anySubjectEquals("NewsGrid");
    }
}
