package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.classification.DocumentMatcher;
import se.johantiden.myfeed.controller.NameAndUrl;
import se.johantiden.myfeed.controller.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@SuppressWarnings("SpellCheckingInspection")
public class DocumentClassifier {

    public static final String NEWS = "Nyheter";
    public static final String BIZ = "Biz";
    public static final String TECH = "Tech";
    public static final String BAD = "Bad";
    public static final String ERROR = "Errors";
    public static final String FUN = "Fun";
    public static final String CULTURE = "Kultur";
    public static final String UNMATCHED_TAB = "Unmatched";
    public static final String STORBRITANNIEN = "Storbritannien";
    public static final String HISTORIA = "Historia";
    public static final String BÖCKER = "Böcker";
    public static final String MUSEUM = "Museum";
    public static final String EUROVISION = "Eurovision";
    public static final String VÄDER = "Väder";
    public static final String FOTBOLL = "fotboll";
    public static final String FORSKNING = "Forskning";
    public static final String IT_SÄKERHET = "IT-Säkerhet";
    public static final String NETFLIX = "Netflix";
    public static final String SPOTIFY = "Spotify";
    public static final String HIRING = "Hiring";
    public static final String RECIPE = "Recipe";
    public static final String WEBB_TV = "webb-tv";
    public static final String SERIER = "serier";
    public static final String CARS = "cars";
    public static final String DEALMASTER = "dealmaster";
    public static final String LEAGUEOFLEGENDS = "leagueoflegends";
    public static final String IDAGSIDAN = "Idagsidan";
    public static final String MAT = "Mat";
    public static final String NEWS_GRID = "NewsGrid";
    public static final String FRÅGESPORT = "Frågesport";
    public static final String JUNIOR = "Junior";
    public static final String SPORT = "Sport";
    public static final String STOCKHOLM = "Stockholm";
    public static final String TYSKLAND = "Tyskland";
    public static final String EKONOMI = "Ekonomi";
    public static final String PERFECT_GUIDE = "Perfect Guide";
    private static final boolean TAB_FROM_SOURCE_ONLY = false;
    public static final String I_AM_A = "IAmA";
    public static final String BLACK_PEOPLE_TWITTER = "BlackPeopleTwitter";
    public static final String THE_DENNIS = "The_Dennis";
    private static final String NUMBER_OF_PEOPLE = "Number of People";
    public static final String NUTIDSTESTET = "nutidstestet";
    public static final String RESOR = "resor";

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

        if(m.anyCategoryEquals("ekonomi", "Näringsliv")) { s.add(s(EKONOMI)); }
        if(m.anyCategoryEquals("ledare")) { s.add(s("Ledare")); }
        if(m.anyCategoryEquals("sport", "sports")) { s.add(s("Sport")); }
        if(m.has("your") && m.has("briefing")) { s.add(s("Briefing")); }
        if(m.has("Tim Berners-Lee")) { s.add(s("Tim Berners-Lee")); }
        if(m.has("german") || m.has("tysk") || m.has("merkel") || m.has("Brandenburg")) { s.add(s(TYSKLAND)); }
        if(m.has("merkel")) { s.add(s("Merkel")); }
        if(m.has("Hitler") || m.hasCaseSensitive("Nazi")) { s.add(s("Nazism")); }
        if(m.has("twitter") || m.has("tweet")) { s.add(s("Twitter")); }
        if(m.has("macron")) { s.add(s("Macron")); }
        if(m.anyCategoryEquals("film")) { s.add(s("Film")); }
        if(m.has("turist")) { s.add(s("Turist")); }
        if(m.has("formel 1")) { s.add(s("Formel 1")); }
        if(m.has("rallycross")) { s.add(s("Rallycross")); }
        if(m.has("GP-hoppning","travlopp")) { s.add(s("Hästsport")); }
        if(m.has("Tennis","Federer")) { s.add(s("Tennis")); }
        if(m.has("sprang maran")) { s.add(s("Löpning")); }
        if(m.has("hockey", "Henrik Lundqvist", "New York Rangers", "Nicklas Bäckström")) { s.add(s("Hockey")); }
        if(m.has(FOTBOLL, "allsvensk", "Champions League", "premier league", "superettan", "Benfica ", "Malmö FF")) { s.add(s("Fotboll")); }
        if(m.has("handboll", "H65")) { s.add(s("Handboll")); }
        if(m.hasCaseSensitive("NBA")) { s.add(s("Basket")); }
        if(m.has("bordtennis", "pingis")) { s.add(s("Bordtennis")); }
        if(m.has("Giro") && m.has("Italia")) { s.add(s("Cykling")); }
        if(m.has("Diamond League")) { s.add(s("Friidrott")); }
        if(m.has("V75")) { s.add(s("Trav")); }
        if(m.has("Johaug", "alpin", "skidor", "Charlotte Kalla")) { s.add(s("Skidor")); }
        if(m.has("speedway")) { s.add(s("Speedway")); }
        if(anySubjectEquals(s, SPORT) && m.hasCaseSensitive("OS") || m.has("olympisk")) { s.add(s("OS")); }
        if(m.has("netflix")) { s.add(s("Netflix")); }
        if(m.has("Boko Haram")) { s.add(s("Boko Haram")); }
        if(m.has("väder ", "väder.", " väder", "blåsväder", "Cyclone", "Cyklon")) { s.add(s("Väder")); }
        if(m.has(EUROVISION)) { s.add(s(EUROVISION)); }
        if(m.has("Nepal")) { s.add(s("Nepal")); }
        if(m.has("Syria", "Syrien", "syrisk", "Syrier", "Damascus", "Damaskus")) { s.add(s("Syrien")); }
        if(m.has("Venezuela", "Maduro")) { s.add(s("Venezuela")); }
        if(m.has("North Korea", "Nordkorea")) { s.add(s("Nordkorea")); }
        if(m.has("South Korea", "Sydkorea", "Seoul")) { s.add(s("Sydkorea")); }
        if(m.has("Myanmar", "Burma")|| m.has("Aung San Suu Kyi")) { s.add(s("Myanmar")); }
        if(m.hasCaseSensitive("Iran") || m.has("Rouhani", "Rohani")) { s.add(s("Iran")); }
        if(m.has("Rouhani", "Rohani")) { s.add(s("Rouhani")); }
        if(m.has("China", "Kina", "Xi Jinping", "Kines")) { s.add(s("Kina")); }
        if(m.has("Net Neutrality")) { s.add(s("Net Neutrality")); }
        if(m.has("Albanien")) { s.add(s("Albanien")); }
        if(m.has("Bosnia", "Bosnien")) { s.add(s("Bosnien")); }
        if(m.has("Belgium")) { s.add(s("Belgien")); }
        if(m.has("India")) { s.add(s("Indien")); }
        if(m.has("Brazil")) { s.add(s("Brasilien")); }
        if(m.has("Egypt")) { s.add(s("Egypten")); }
        if(m.has("Yemen", "Jemen")) { s.add(s("Jemen")); }
        if(m.has("Danmark", "Köpenhamn")) { s.add(s("Danmark")); }
        if(m.has("Bangladesh")) { s.add(s("Bangladesh")); }
        if(m.has("Malaysia")) { s.add(s("Malaysia")); }
        if(m.has("AskReddit")) { s.add(s("AskReddit")); }
        if(m.anyCategoryEquals("science")) { s.add(s(FORSKNING)); }
        if(m.has("France", "Frankrike", "Fransk", "French", "Paris")) { s.add(s("Frankrike")); }
        if(m.has("Australia")) { s.add(s("Australia")); }
        if(m.has("Göteborg", "Gothenburg")) { s.add(s("Göteborg")); }
        if(m.has("Malmö")) { s.add(s("Malmö")); }
        if(m.has("Dutch", "Netherlands")) { s.add(s("Nederländerna")); }
        if(m.has("Italien")) { s.add(s("Italien")); }
        if(m.has("Tjeckien", "Tjeckisk", "Czech")) { s.add(s("Tjeckien")); }
        if(m.has("Libyen")) { s.add(s("Libyen")); }
        if(m.has("Kuwait")) { s.add(s("Kuwait")); }
        if(m.has("Saudi Arabia", "Saudiarabien")) { s.add(s("Saudiarabien")); }
        if(m.has("Uganda")) { s.add(s("Uganda")); }
        if(m.has("South Africa")) { s.add(s("Sydafrika")); }
        if(m.has("European Union") || m.hasCaseSensitive("EU")) { s.add(s("EU")); }
        if(m.has("Europe", "Europa")) { s.add(s("Europa")); }
        if(m.has("Elfenbenskusten", "Ivory Coast", "Ivorian")) { s.add(s("Elfenbenskusten")); }
        if(m.has(STORBRITANNIEN, "London", "England", "Britain", "Scotland")) { s.add(s(STORBRITANNIEN)); }
        if(m.hasCaseSensitive("US", "FBI") || m.has("america") && !m.has("south america") || m.has("U.S.", "america", "obama", "trump")) { s.add(s("USA")); }
        if(m.has("obama")) { s.add(s("Obama")); }
        if(m.has("trump")) { s.add(s("Trump")); }
        if(m.hasCaseSensitive("FBI")) { s.add(s("FBI")); }
        if(m.has("Mexiko", "Mexico", "Mexican")) { s.add(s("Mexico")); }
        if(m.has("Turkey", "Turkish", "Turkiet", "Recep Tayyip Erdogan", "Istanbul")) { s.add(s("Turkiet")); }
        if(m.has("Greece", "Greek", "Grekland", "Grek")) { s.add(s("Grekland")); }
        if(m.has("Österrike", "Austria")) { s.add(s("Österrike")); }
        if(m.has(SPOTIFY)) { s.add(s(SPOTIFY)); }
        if(m.has("Microsoft")) { s.add(s("Microsoft")); }
        if(m.has("Samsung")) { s.add(s("Samsung")); }
        if(m.has("Apple")) { s.add(s("Apple")); }
        if(m.has("block") && m.has("chain")) { s.add(s("Blockchain")); }
        if(m.has("Facebook")) { s.add(s("Facebook")); }
        if(m.has("Google")) { s.add(s("Google")); }
        if(m.has("Palestin")) { s.add(s("Palestina")); }
        if(m.has("Afghanistan", "Afganistan", "Baloch")) { s.add(s("Afghanistan")); }
        if(m.has("Pakistan", "Baloch")) { s.add(s("Pakistan")); }
        if(m.has("Baloch")) { s.add(s("Baloch")); }
        if(m.has("Taiwan")) { s.add(s("Taiwan")); }
        if(m.has("Israel")) { s.add(s("Israel")); }
        if(m.has("Schweiz")) { s.add(s("Schweiz")); }
        if(m.has("Ungern")) { s.add(s("Ungern")); }
        if(m.has("Tunis")) { s.add(s("Tunisien")); }
        if(m.has("Portugal")) { s.add(s("Portugal")); }
        if(m.has("Japan")) { s.add(s("Japan")); }
        if(m.has("Argentin")) { s.add(s("Argentina")); }
        if(m.has("Russia", "Ryssland", "Rysk") || m.hasCaseSensitive("Moskva")) { s.add(s("Ryssland")); }
        if(m.has("Canada", "Kanada", "Canadian", "Kanaden")) { s.add(s("Kanada")); }
        if(m.has(MUSEUM)) { s.add(s(MUSEUM)); }
        if(m.has("musik", "hiphop")) { s.add(s("Musik")); }
        if(m.has("konstnär")) { s.add(s("Konst")); }
        if(m.has("författare")) { s.add(s(BÖCKER)); }
        if(m.has("dramaserie")) { s.add(s("Film/TV")); }
        if(m.has("terror")) { s.add(s("Terror")); }
        if(m.has("Nigeria")) { s.add(s("Nigeria")); }
        if(m.authorEquals("TT")) { s.add(s("TT"));}
        if(m.has("Finland")) { s.add(s("Finland")); }
        if(m.has("South Sudan")) { s.add(s("Sydsudan")); }
        if(m.has("Ebola")) { s.add(s("Ebola")); }
        if(m.has("Cholera") | m.has("Kolera")) { s.add(s("Kolera")); }
        if(m.has("Kiev", "Ukrain")) { s.add(s("Ukraina")); }
        if(m.has("IT-attacken", "Ransomware", "Cyberattack", "malware", "WanaCry", "WannaCry", "Hacker", "hacking", "IT-utpressning", "IT-angrepp", "Internet Security", "eternalblue", "botnet")) { s.add(s(IT_SÄKERHET)); }
        if(m.has("Brexit")) { s.add(s("Brexit")); }
        if(m.has("Stockholm") || m.anyCategoryEquals("sthlm")) { s.add(s(STOCKHOLM)); }
        if(anySubjectEquals(s, STOCKHOLM) || m.anyCategoryEquals("Sverige") || m.hasCaseSensitive("Umeå", "Liseberg", "Strömsund", "Norrköping", "Östersund", "Swedish", "Swede", "Västervik", "Katrineholm", "Uppsala", "Linköping")) {
            s.add(s("Inrikes")); }
        if(m.has("Norge", "Norway", "norska")) { s.add(s("Norge")); }
        if(m.has("Feministiskt initiativ")) { s.add(s("Feministiskt Initiativ")); }
        if((m.has("Miljöpartiet") || m.hasCaseSensitive(" MP ", " MP.")) && !anySubjectEquals(s, STORBRITANNIEN)) { s.add(s("Miljöpartiet")); }
        if(m.has("Sverigemokraterna") && m.hasCaseSensitive("SD", "Jimmie Åkesson")) { s.add(s("Sverigemokraterna")); }
        if(m.has("Jimmie Åkesson")) { s.add(s("Jimmie Åkesson")); }
        if(m.hasCaseSensitive("LO")) { s.add(s("LO")); }
        if(m.has("Moderaterna", "Kinberg Batra")) { s.add(s("Moderaterna")); }
        if(m.has("Kinberg Batra")) { s.add(s("Kinberg Batra")); }
        if(m.has("debatt")) { s.add(s("Debatt")); }
        if(m.has("Kongo-Kinshasa")) { s.add(s("Kongo-Kinshasa")); }
        if(m.hasCaseSensitive("Oman")) { s.add(s("Oman")); }
        if(m.hasCaseSensitive("CCTV")) { s.add(s("Foliehatt")); }
        if(m.hasCaseSensitive("Iraq", "Irak")) { s.add(s("Irak")); }
        if(m.hasCaseSensitive("Cuba", "Kuba")) { s.add(s("Kuba")); }
        if(m.has("kvinnor") && m.has("män")) { s.add(s("Kvinnor")); }
        if(m.has("kvinnor") && m.has("män")) { s.add(s("Män")); }
        if(m.isFromFeed("TheLocal") && m.has("recipe:")) { s.add(s(RECIPE)); }
        if(m.isFromFeed("HackerNews") && m.has("hiring")) { s.add(s(HIRING)); }
        if(m.has("Vänsterpartiet")) { s.add(s("Vänsterpartiet")); }
        if(m.has("Centerpartiet", "Annie Lööf")) { s.add(s("Centerpartiet")); }
        if(m.has("Annie Lööf")) { s.add(s("Annie Lööf")); }
        if(m.has("Vänsterpartiet", "Jonas Sjöstedt")) { s.add(s("Vänsterpartiet")); }
        if(m.has("Jonas Sjöstedt")) { s.add(s("Jonas Sjöstedt")); }
        if(m.has("Kristdemokraterna", "Busch Thor") || m.hasCaseSensitive("KD")) { s.add(s("Kristdemokraterna")); }
        if(m.has("Busch Thor")) { s.add(s("Ebba Busch Thor")); }
        if(m.has(IDAGSIDAN)) { s.add(s(IDAGSIDAN)); }
        if(m.has("historian", "1500", "1600", "1700", "1800")) { s.add(s(HISTORIA)); }
        if(m.has("Daesh") || m.hasCaseSensitive("ISIL", "ISIS") || m.has("terror")&&m.hasCaseSensitive("IS")) { s.add(s("Daesh")); }
        if(m.has("Socialdemokraterna") && !anySubjectEquals(s, TYSKLAND)) { s.add(s("Socialdemokraterna")); }
        if(m.has("mat-dryck") || m.anyCategoryEquals("Restaurants")) { s.add(s(MAT)); }
        if(m.anyCategoryEquals("gaming")) { s.add(s("Gaming")); }

        // Badness:
        if(m.hasCaseSensitive(NEWS_GRID)) { s.add(s(NEWS_GRID)); }
        if(m.has("cars technica")) { s.add(s("Cars")); }
        if(m.has(WEBB_TV)) { s.add(s(WEBB_TV)); }
        if(m.has(LEAGUEOFLEGENDS)) { s.add(s(LEAGUEOFLEGENDS)); }
        if(m.anyCategoryEquals(DEALMASTER)) { s.add(s(DEALMASTER)); }
        if(m.has("dödsfäll")) { s.add(s("Dödsfälla")); }
        if(m.anyCategoryEquals("motor")) { s.add(s("Motor")); }
        if(m.anyCategoryEquals(SERIER)) { s.add(s("Serier")); }
        if(m.hasCaseSensitive("Här är") || m.has("– här är", "- här är")) { s.add(s("Här är")); }
        if(m.has("turist")) { s.add(s("Turist")); }
        if(d.pageUrl.contains("uutiset")) { s.add(s("Uutiset")); }
        if(m.isFromFeed("Svenska Dagbladet") && m.startsWithCaseSensitive("VIDEO")) { s.add(s("VIDEO")); }
        if(m.has("fragesport")) { s.add(s(FRÅGESPORT)); }
        if(m.anyCategoryEquals(JUNIOR)) { s.add(s(JUNIOR)); }
        if(m.anyCategoryEquals(NUTIDSTESTET)) { s.add(s(NUTIDSTESTET)); }
        if(m.anyCategoryEquals(PERFECT_GUIDE)) { s.add(s(PERFECT_GUIDE)); }
        if(m.anyCategoryEquals(RESOR)) { s.add(s(RESOR)); }
        if(m.has("Reddit") && m.anyCategoryEquals("IAmA")) { s.add(s(I_AM_A)); }
        if(m.has("Reddit") && m.anyCategoryEquals(BLACK_PEOPLE_TWITTER)) { s.add(s(BLACK_PEOPLE_TWITTER)); }
        if(m.has("Reddit") && m.anyCategoryEquals(THE_DENNIS)) { s.add(s(THE_DENNIS)); }
        if(m.has("Reddit") && m.has("-- number", "--number")) { s.add(s(NUMBER_OF_PEOPLE)); }


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
            return VÄDER;
        }

        if(isNews(document)) {
            return NEWS;
        }

        return UNMATCHED_TAB;
    }

    private static boolean isVäder(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);
        return m.anySubjectEquals(VÄDER);
    }

    private static boolean isSport(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
            m.anyCategoryEquals("sport") ||
            m.anySubjectEquals(SPORT) ||
            m.has("bordtennis") ||
            m.anySubjectEquals(FOTBOLL) ||
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
            m.anySubjectEquals(EUROVISION)||
            m.anySubjectEquals(MUSEUM) ||
            m.anySubjectEquals(BÖCKER) ||
            m.anySubjectEquals(HISTORIA);
    }

    private static boolean isNews(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
            TAB_FROM_SOURCE_ONLY && m.isFromFeed("Al Jazeera") ||
            TAB_FROM_SOURCE_ONLY && m.isFromFeed("SVT Nyheter") ||
            m.isFromFeed("Dagens Nyheter") && m.anyCategoryEquals("nyheter") ||
            m.isFromFeed("TheLocal") ||
            m.isFromFeed("Los Angeles Times - World") ||
            m.isFromFeed("Reddit - r/worldnews") ||
            m.anyCategoryEquals("worldnews") ||
            m.isFromFeed("Al Jazeera") && m.anyCategoryEquals("news", "insidestory", "opinion", "features", "indepth", "programmes") ||
            m.isFromFeed("SVT Nyheter") && m.anyCategoryEquals("nyheter") ||
            m.isFromFeed("New York Times - World") ||
            m.has("Reddit") && m.anyCategoryEquals("politics", "news", "worldnews", "esist") ||
            m.anyCategoryEquals("sthlm") ||
            m.anyCategoryEquals("debatt") ||
            m.anyCategoryEquals("världen") ||
            m.anyCategoryEquals("ledare") ||
            m.anyCategoryEquals("sverige");
    }

    private static boolean isTech(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
            TAB_FROM_SOURCE_ONLY && m.isFromFeed("Ars Technica") ||
            TAB_FROM_SOURCE_ONLY && m.isFromFeed("Slashdot") ||
            TAB_FROM_SOURCE_ONLY && m.isFromFeed("HackerNews") ||
            m.isFromFeed("Breakit") ||
            m.isFromFeed("Engadget") ||
            m.anyCategoryEquals("ProgrammerHumor") ||
            m.isFromFeed("HackerNews") ||
            m.isFromFeed("Ars Technica") && m.anyCategoryEquals("tech-policy", "gadgets", "gaming", "facebook", "opposable thumbs", "the-multiverse", "Technology Lab", "Ministry of Innovation") ||
            m.has("Reddit") && m.anyCategoryEquals("technology") ||
            m.isFromFeed("Slashdot") && m.anyCategoryEquals("story") ||
            m.anySubjectEquals(FORSKNING) ||
            m.anySubjectEquals(IT_SÄKERHET) ||
            m.anySubjectEquals(NETFLIX) ||
            m.anySubjectEquals(SPOTIFY);
    }

    private static boolean isFun(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
            TAB_FROM_SOURCE_ONLY && m.isFromFeed("Reddit - top") ||
            TAB_FROM_SOURCE_ONLY && m.isFromFeed("Reddit - r/all") ||
            m.anyCategoryEquals("AskReddit") ||
            m.has("Reddit") && m.anyCategoryEquals("gaming", "pics", "gifs", "funny", "PoliticalHumor", "mildlyinteresting", "Design", "aww", "sports", "music", "videos", "todayilearned", "NatureIsFuckingLit", "nottheonion", "MarchAgainstTrump", "Showerthoughts", "photoshopbattles", "oddlysatisfying", "space") ||
            m.isFromFeed("xkcd");
    }

    private static boolean isBiz(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return
            m.anyCategoryEquals("näringsliv") ||
            m.anyCategoryEquals("ekonomi") ||
            m.isFromFeed("New York Times - World") && m.anyCategoryEquals("business") ||
            m.isFromFeed("Slashdot") && m.anyCategoryEquals("business") ||
            m.anySubjectEquals(EKONOMI) ;
    }


    private static boolean isError(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);

        return m.has("&#") && m.has(";");
    }

    private static boolean isBad(Document d) {
        // Only match on subjects here to give the user a reason why it is "bad"
        DocumentMatcher m = new DocumentMatcher(d);
        return
            m.anySubjectEquals(HIRING) ||
            m.anySubjectEquals(RECIPE) ||
            m.anySubjectEquals(WEBB_TV) ||
            m.anySubjectEquals(SERIER) ||
            m.anySubjectEquals(CARS) ||
            m.anySubjectEquals(DEALMASTER) ||
            m.anySubjectEquals(LEAGUEOFLEGENDS) ||
            m.anySubjectEquals(IDAGSIDAN) ||
            m.anySubjectEquals(MAT) ||
            m.anySubjectEquals(NEWS_GRID) ||
            m.anySubjectEquals(FRÅGESPORT) ||
            m.anySubjectEquals(JUNIOR) ||
            m.anySubjectEquals(PERFECT_GUIDE) ||
            m.anySubjectEquals(I_AM_A) ||
            m.anySubjectEquals(BLACK_PEOPLE_TWITTER) ||
            m.anySubjectEquals(THE_DENNIS) ||
            m.anySubjectEquals(NUMBER_OF_PEOPLE) ||
            m.anySubjectEquals(NUTIDSTESTET) ||
            m.anySubjectEquals(RESOR) ||
            m.anyCategoryEquals("Jokes", "OldSchoolCool");
    }

    public static void appendUrlFoldersAsCategory(Document document) {

        DocumentMatcher m = new DocumentMatcher(document);

        if (!m.isFromFeed("HackerNews")) {
            List<String> folders = parseUrlFolders(document.pageUrl).stream()
                                   .filter(DocumentClassifier::urlFilter)
                                   .collect(Collectors.toList());

            folders.stream()
            .filter(f -> !m.anyCategoryEquals(f))
            .filter(f -> !f.equals("artikel") && !f.equals("comments"))
            .map(f -> new NameAndUrl(f, endUrlAt(f, document.pageUrl)))
            .forEach(document.categories::add);
        }
    }

    private static String endUrlAt(String firstFolder, String pageUrl) {

        int i = pageUrl.indexOf(firstFolder);

        String substring = pageUrl.substring(0, i + firstFolder.length());
        return substring;
    }

    private static boolean urlFilter(String string) {
        boolean matches = string.matches("([a-zA-Z]{2,})|([a-zA-Z]{2,}\\-[a-zA-Z]{2,})");
        return matches;
    }

    private static List<String> parseUrlFolders(String pageUrl) {
        StringTokenizer stringTokenizer = new StringTokenizer(pageUrl);

        List<String> folders = Lists.newArrayList();

        while (stringTokenizer.hasMoreTokens()) {
            folders.add(stringTokenizer.nextToken("/"));
        }
        return folders;
    }
}
