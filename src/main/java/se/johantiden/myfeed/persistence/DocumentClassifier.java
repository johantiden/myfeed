package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import se.johantiden.myfeed.classification.DocumentMatcher;
import se.johantiden.myfeed.controller.NameAndUrl;

import java.util.ArrayList;
import java.util.Arrays;
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
    private static final String TORRENTS = "Torrents";
    public static final String UUTISET = "uutiset";
    public static final String KRISTDEMOKRATERNA = "Kristdemokraterna";
    public static final String VÄNSTERPARTIET = "Vänsterpartiet";
    public static final String CENTERPARTIET = "Centerpartiet";
    public static final String SVERIGEMOKRATERNA = "Sverigemokraterna";
    public static final String LIBERALERNA = "Liberalerna";
    public static final String FILIPPINERNA = "Filippinerna";
    public static final String RUSSIA = "Ryssland";
    public static final String AFRIKA = "Afrika";
    public static final String INRIKES = "Inrikes";
    public static final String USA = "USA";

    private DocumentClassifier() {
    }

    public static List<String> getSubjectFor(Document d) {
        List<String> s = new ArrayList<>();
        DocumentMatcher m = new DocumentMatcher(d) {
            @Override
            public boolean anySubjectEquals(String subject) {
                throw new IllegalStateException("Don't check subjects here! The list will be set later. Check subjects directly in the list instead.");
            }
        };

        if(m.anyCategoryEquals("ekonomi", "Näringsliv")) { s.add(EKONOMI); }
        if(m.anyCategoryEquals("ledare")) { s.add("Ledare"); }
        if(m.anyCategoryEquals("sport", "sports")) { s.add("Sport"); }
        if(m.has("your") && m.has("briefing")) { s.add("Briefing"); }
        if(m.has("Tim Berners-Lee")) { s.add("Tim Berners-Lee"); }
        if(m.has("german") || m.has("tysk") || m.has("merkel") || m.has("Brandenburg")) { s.add(TYSKLAND); }
        if(m.has("merkel")) { s.add("Merkel"); }
        if(m.has("Hitler") || m.hasCaseSensitive("Nazi")) { s.add("Nazism"); }
        if(m.has("twitter") || m.has("tweet")) { s.add("Twitter"); }
        if(m.has("macron")) { s.add("Macron"); }
        if(m.anyCategoryEquals("film")) { s.add("Film"); }
        if(m.has("turist")) { s.add("Turist"); }
        if(m.has("formel 1")) { s.add("Formel 1"); }
        if(m.has("rallycross")) { s.add("Rallycross"); }
        if(m.has("GP-hoppning","travlopp")) { s.add("Hästsport"); }
        if(m.has("Tennis","Federer")) { s.add("Tennis"); }
        if(m.has("sprang maran")) { s.add("Löpning"); }
        if(m.has("hockey", "Henrik Lundqvist", "New York Rangers", "Nicklas Bäckström")) { s.add("Hockey"); }
        if(m.has(FOTBOLL, "allsvensk", "Champions League", "premier league", "superettan", "Benfica ", "Malmö FF", "Ronaldo", "La Liga", "Real Madrid")) {
            s.add("Fotboll"); }
        if(m.has("handboll", "H65")) { s.add("Handboll"); }
        if(m.hasCaseSensitive("NBA")) { s.add("Basket"); }
        if(m.has("bordtennis", "pingis")) { s.add("Bordtennis"); }
        if(m.has("Giro") && m.has("Italia")) { s.add("Cykling"); }
        if(m.has("Diamond League")) { s.add("Friidrott"); }
        if(m.has("V75")) { s.add("Trav"); }
        if(m.has("Johaug", "alpin", "skidor", "Charlotte Kalla")) { s.add("Skidor"); }
        if(m.has("speedway")) { s.add("Speedway"); }
        if(anySubjectEquals(s, SPORT) && m.hasCaseSensitive("OS") || m.has("olympisk")) { s.add("OS"); }
        if(m.has("netflix")) { s.add("Netflix"); }
        if(m.has("Boko Haram")) { s.add("Boko Haram"); }
        if(m.has("Climate", "Klimat")) { s.add("Klimat"); }
        if(m.has("väder ", "väder.", " väder", "blåsväder", "Cyclone", "Cyklon") || m.hasCaseSensitive("SMHI")) {
            s.add("Väder"); }
        if(m.has(EUROVISION)) { s.add(EUROVISION); }
        if(m.has("Rouhani", "Rohani")) { s.add("Rouhani"); }
        if(m.has("Net Neutrality")) { s.add("Net Neutrality"); }
        if(m.has("AskReddit")) { s.add("AskReddit"); }
        if(m.anyCategoryEquals("science")) { s.add(FORSKNING); }
        if(m.has("Göteborg", "Gothenburg")) { s.add("Göteborg"); }
        if(m.has("Malmö")) { s.add("Malmö"); }
        if(m.has("obama")) { s.add("Obama"); }
        if(m.has("trump")) { s.add("Trump"); }
        if(m.hasCaseSensitive("FBI")) { s.add("FBI"); }
        if(m.has(SPOTIFY)) { s.add(SPOTIFY); }
        if(m.has("Microsoft")) { s.add("Microsoft"); }
        if(m.has("Samsung")) { s.add("Samsung"); }
        if(m.hasCaseSensitive("Apple")) { s.add("Apple"); }
        if(m.has("block") && m.has("chain")) { s.add("Blockchain"); }
        if(m.has("Facebook")) { s.add("Facebook"); }
        if(m.has("Google")) { s.add("Google"); }
        if(m.has("Baloch")) { s.add("Baloch"); }
        if(m.has(MUSEUM)) { s.add(MUSEUM); }
        if(m.has("musik", "hiphop")) { s.add("Musik"); }
        if(m.has("konstnär")) { s.add("Konst"); }
        if(m.has("författare")) { s.add(BÖCKER); }
        if(m.has("dramaserie")) { s.add("Film/TV"); }
        if(m.has("terror")) { s.add("Terror"); }
        if(m.authorEquals("TT")) { s.add("TT");}
        if(m.has("Ebola")) { s.add("Ebola"); }
        if(m.has("Cholera") | m.has("Kolera")) { s.add("Kolera"); }
        if(m.has("IT-attacken", "Ransomware", "Cyberattack", "malware", "WanaCry", "WannaCry", "Hacker", "hacking", "IT-utpressning", "IT-angrepp", "Internet Security", "eternalblue", "botnet")) {
            s.add(IT_SÄKERHET); }
        if(m.has("Brexit")) { s.add("Brexit"); }
        if(m.has("Feministiskt initiativ")) { s.add("Feministiskt Initiativ"); }
        if((m.has("Miljöpartiet") || m.hasCaseSensitive(" MP ", " MP.")) && !anySubjectEquals(s, STORBRITANNIEN)) {
            s.add("Miljöpartiet"); }
        if(m.has(SVERIGEMOKRATERNA) && m.hasCaseSensitive("SD")) { s.add(SVERIGEMOKRATERNA); }
        if(m.has("Moderaterna", "Kinberg Batra")) { s.add("Moderaterna"); }
        if(m.has("Kinberg Batra")) { s.add("Kinberg Batra"); }
        if(m.has("Pope Francis")) { s.add("Pope Francis"); }
        if(m.has("debatt")) { s.add("Debatt"); }
        if(m.hasCaseSensitive("CCTV")) { s.add("Foliehatt"); }
        addPlaces(s, m);
        if(m.has("kvinnor") && m.has("män")) { s.add("Kvinnor"); }
        if(m.has("kvinnor") && m.has("män")) { s.add("Män"); }
        if(m.isFromFeed("TheLocal") && m.has("recipe:")) { s.add(RECIPE); }
        if(m.isFromFeed("HackerNews") && m.has("hiring")) { s.add(HIRING); }
        if(m.has(VÄNSTERPARTIET)) { s.add(VÄNSTERPARTIET); }
        if(m.has(CENTERPARTIET)) { s.add(CENTERPARTIET); }
        if(m.has(VÄNSTERPARTIET)) { s.add(VÄNSTERPARTIET); }
        if(m.has(KRISTDEMOKRATERNA) || m.hasCaseSensitive("KD")) { s.add(KRISTDEMOKRATERNA); }

        addPeople(s, m);
        if(m.has(IDAGSIDAN)) { s.add(IDAGSIDAN); }
        if(m.has("historian", "1500", "1600", "1700", "1800")) { s.add(HISTORIA); }
        if(m.has("Daesh") || m.hasCaseSensitive("ISIL", "ISIS") || m.has("terror")&&m.hasCaseSensitive("IS")) {
            s.add("Daesh"); }
        if(m.has("Socialdemokraterna") && !anySubjectEquals(s, TYSKLAND)) { s.add("Socialdemokraterna"); }
        if(m.has("mat-dryck") || m.anyCategoryEquals("Restaurants")) { s.add(MAT); }
        if(m.anyCategoryEquals("gaming")) { s.add("Gaming"); }
        if(m.feedStartsWith("Reddit")) {
            String redditCategory = d.categories.get(0).name;
            s.add(redditCategory);
        }

        // Badness:
        if(m.hasCaseSensitive(NEWS_GRID)) { s.add(NEWS_GRID); }
        if(m.has("cars technica")) { s.add("Cars"); }
        if(m.has(WEBB_TV)) { s.add(WEBB_TV); }
        if(m.has(LEAGUEOFLEGENDS)) { s.add(LEAGUEOFLEGENDS); }
        if(m.anyCategoryEquals(DEALMASTER)) { s.add(DEALMASTER); }
        if(m.has("dödsfäll")) { s.add("Dödsfälla"); }
        if(m.anyCategoryEquals("motor")) { s.add("Motor"); }
        if(m.anyCategoryEquals(SERIER)) { s.add("Serier"); }
        if(m.hasCaseSensitive("Här är") || m.has("– här är", "- här är")) { s.add("Här är"); }
        if(m.has("turist")) { s.add("Turist"); }
        if(d.pageUrl.contains(UUTISET)) { s.add("Uutiset"); }
        if(m.isFromFeed("Svenska Dagbladet") && m.startsWithCaseSensitive("VIDEO")) { s.add("VIDEO"); }
        if(m.has("fragesport")) { s.add(FRÅGESPORT); }
        if(m.anyCategoryEquals(JUNIOR)) { s.add(JUNIOR); }
        if(m.anyCategoryEquals(NUTIDSTESTET)) { s.add(NUTIDSTESTET); }
        if(m.anyCategoryEquals(PERFECT_GUIDE)) { s.add(PERFECT_GUIDE); }
        if(m.anyCategoryEquals(RESOR)) { s.add(RESOR); }
        if(m.has("Reddit") && m.anyCategoryEquals("IAmA")) { s.add(I_AM_A); }
        if(m.has("Reddit") && m.anyCategoryEquals(BLACK_PEOPLE_TWITTER)) { s.add(BLACK_PEOPLE_TWITTER); }
        if(m.has("Reddit") && m.anyCategoryEquals(THE_DENNIS)) { s.add(THE_DENNIS); }
        if(m.has("Reddit") && m.has("-- number", "--number")) { s.add(NUMBER_OF_PEOPLE); }

        return s;
    }

    private static void addPeople(List<String> s, DocumentMatcher m) {

        if(m.has("Busch Thor")) {
            s.add("Ebba Busch Thor");
            s.add(KRISTDEMOKRATERNA);
        }
        if(m.has("Jonas Sjöstedt")) {
            s.add("Jonas Sjöstedt");
            s.add(VÄNSTERPARTIET);
        }
        if(m.has("Annie Lööf")) {
            s.add("Annie Lööf");
            s.add(CENTERPARTIET);
        }

        if(m.has("Jimmie Åkesson")) {
            s.add("Jimmie Åkesson");
            s.add(SVERIGEMOKRATERNA);
        }
        if(m.has("Björklund")) {
            s.add("Jan Björklund");
            s.add(LIBERALERNA);
        }
        if (m.has("Varadkar")) {
            s.add("Leo Varadkar");
            s.add("Irland");
        }
        if (m.has("Duterte")) {
            s.add("Rodrigo Duterte");
            s.add(FILIPPINERNA);
        }
        if (m.has("Putin")) {
            s.add("Vladimir Putin");
            s.add(RUSSIA);
        }
        if (anySubjectEquals(s, STORBRITANNIEN) && m.hasCaseSensitive("May")) {
            s.add("Theresa May");
            s.add(STORBRITANNIEN);
        }

    }

    private static void addPlaces(List<String> s, DocumentMatcher m) {
        if(m.hasCaseSensitive("Cuba", "Kuba")) { s.add("Kuba"); }
        if(m.hasCaseSensitive("Iraq", "Irak", "Mosul")) {
            s.add("Irak");
        }
        if(m.hasCaseSensitive("Oman")) { s.add("Oman"); }

        addAfrica(s, m);
        if(m.has("Nepal")) { s.add("Nepal"); }
        if(m.has("Syria", "Syrien", "syrisk", "Syrier", "Damascus", "Damaskus")) { s.add("Syrien"); }
        if(m.has("Venezuela", "Maduro")) { s.add("Venezuela"); }
        if(m.has("North Korea", "Nordkorea")) { s.add("Nordkorea"); }
        if(m.has("South Korea", "Sydkorea", "Seoul")) { s.add("Sydkorea"); }
        if(m.has("Myanmar", "Burma")|| m.has("Aung San Suu Kyi")) { s.add("Myanmar"); }
        if(m.hasCaseSensitive("Iran") || m.has("Rouhani", "Rohani")) { s.add("Iran"); }
        if(m.has("China", "Kina", "Xi Jinping", "Kines")) { s.add("Kina"); }
        if(m.has("Albanien")) { s.add("Albanien"); }
        if(m.has("Bosnia", "Bosnien")) { s.add("Bosnien"); }
        if(m.has("Belgium")) { s.add("Belgien"); }
        if(m.has("India", "Indien")) { s.add("Indien"); }
        if(m.has("Brazil")) { s.add("Brasilien"); }
        if(m.has("Egypt")) { s.add("Egypten"); }
        if(m.has("Yemen", "Jemen")) { s.add("Jemen"); }
        if(m.has("Danmark", "Köpenhamn")) { s.add("Danmark"); }
        if(m.has("Köpenhamn")) {
            s.add("Köpenhamn");
            s.add("Danmark");
        }
        if(m.has("Bangladesh")) { s.add("Bangladesh"); }
        if(m.has("Malaysia")) { s.add("Malaysia"); }
        if(m.has("France", "Frankrike", "Fransk", "French", "Paris")) { s.add("Frankrike"); }
        if(m.has("Australia")) { s.add("Australia"); }
        if(m.has("Dutch", "Netherlands", "Nederländerna")) { s.add("Nederländerna"); }
        if(m.has("Italien")) { s.add("Italien"); }
        if(m.has("Tjeckien", "Tjeckisk", "Czech")) { s.add("Tjeckien"); }
        if(m.has("Kuwait")) { s.add("Kuwait"); }
        if(m.has("Saudi Arabia", "Saudiarabien")) { s.add("Saudiarabien"); }
        if(m.has("Qatar")) { s.add("Qatar"); }
        if(m.has("South Africa")) { s.add("Sydafrika"); }
        if(m.has("European Union") || m.hasCaseSensitive("EU")) { s.add("EU"); }
        if(m.has("Europe", "Europa")) { s.add("Europa"); }
        addUsa(s, m);
        if(m.has("Mexiko", "Mexico", "Mexican")) { s.add("Mexico"); }
        if(m.has("Turkey", "Turkish", "Turkiet", "Recep Tayyip Erdogan", "Istanbul")) { s.add("Turkiet"); }
        if(m.has("Greece", "Greek", "Grekland", "Grek")) { s.add("Grekland"); }
        if(m.has("Österrike", "Austria")) { s.add("Österrike"); }
        if(m.has("Finland")) { s.add("Finland"); }
        if(m.has("Nigeria")) { s.add("Nigeria"); }
        if(m.has("South Sudan")) { s.add("Sydsudan"); }
        if(m.has("Kiev", "Ukrain")) { s.add("Ukraina"); }
        if(m.has("Norge", "Norway", "norska")) { s.add("Norge"); }
        if(m.has("Taiwan")) { s.add("Taiwan"); }
        if(m.has("Israel", "West Bank")) { s.add("Israel"); }
        if(m.has("Schweiz")) { s.add("Schweiz"); }
        if(m.has("Ungern")) { s.add("Ungern"); }
        if(m.has("Portugal")) { s.add("Portugal"); }
        if(m.has("Japan")) { s.add("Japan"); }
        if(m.has("Argentin")) { s.add("Argentina"); }
        if(m.has("Russia", RUSSIA, "Rysk") || m.hasCaseSensitive("Moskva")) { s.add(RUSSIA); }
        if(m.has("Canada", "Kanada", "Canadian", "Kanaden")) { s.add("Kanada"); }
        if(m.has("Palestin")) { s.add("Palestina"); }
        if(m.has("Afghanistan", "Afganistan", "Baloch", "kabul")) { s.add("Afghanistan"); }
        if(m.has("Pakistan", "Baloch")) { s.add("Pakistan"); }
        if(m.has("Manila", FILIPPINERNA, "Philippines")) { s.add(FILIPPINERNA); }
        if(m.has("Irish", "Ireland", "Irland")) { s.add("Irland"); }
        if(m.has("Belgien", "Belgium")) { s.add("Belgien"); }
        if(m.has("Tibet")) { s.add("Tibet"); }
        if(m.has("Montenegro")) { s.add("Montenegro"); }

        addGreatBritain(s, m);
        addSweden(s, m);
    }

    private static void addUsa(List<String> s, DocumentMatcher m) {
        if(m.hasCaseSensitive("US", "FBI") || m.has("america") && !m.has("south america") || m.has("U.S.", "america", "obama", "trump")) {
            s.add(USA);
        }
        if(m.has("Orlando")) {
            s.add("Orlando");
            s.add((USA));
        }
    }

    private static void addGreatBritain(List<String> s, DocumentMatcher m) {
        if(m.has("Manchester")) {
            s.add("Manchester");
            s.add((STORBRITANNIEN));
        }
        if(m.has(STORBRITANNIEN, "London", "England", "Britain", "Scotland", "British", "Britain")) {
            s.add(STORBRITANNIEN);
        }

    }

    private static void addSweden(List<String> s, DocumentMatcher m) {
        if(m.has("Gröna Lund")) {
            s.add("Gröna Lund");
            s.add("Stockholm");
            s.add(INRIKES);
        }
        if(m.has("Eskilstuna")) {
            s.add("Eskilstuna");
            s.add(INRIKES);
        }
        if(m.has("Bromma flygplats")) {
            s.add("Bromma flygplats");
            s.add(INRIKES);
        }
        if(m.has("Kristianstad")) {
            s.add("Kristianstad");
            s.add(INRIKES);
        }
        if(m.has("Arboga")) {
            s.add("Arboga");
            s.add(INRIKES);
        }
        if(m.has("Stockholm") || m.anyCategoryEquals("sthlm")) {
            s.add(STOCKHOLM);
            s.add(INRIKES);
        }
        if(m.anyCategoryEquals("Sverige") || m.hasCaseSensitive("Umeå", "Liseberg", "Strömsund", "Norrköping", "Östersund", "Swedish", "Swede", "Västervik", "Katrineholm", "Uppsala", "Linköping")) {
            s.add(INRIKES);
        }
    }

    private static void addAfrica(List<String> s, DocumentMatcher m) {
        if(m.has("Libyen", "Libya")) {
            s.add("Libyen");
            s.add(AFRIKA);
        }
        if(m.has("Lesotho")) {
            s.add("Lesotho");
            s.add(AFRIKA);
        }
        if(m.has("Morocco", "Marocko")) {
            s.add("Marocko");
            s.add(AFRIKA);
        }
        if(m.has("Tunis")) {
            s.add("Tunisien");
            s.add(AFRIKA);
        }
        if(m.has("Angola")) {
            s.add("Angola");
            s.add(AFRIKA);
        }

        if(m.has("Kongo-Kinshasa")) {
            s.add("Kongo-Kinshasa");
            s.add(AFRIKA);
        }
        if(m.has("Elfenbenskusten", "Ivory Coast", "Ivorian")) {
            s.add("Elfenbenskusten");
            s.add(AFRIKA);
        }
        if(m.has("Uganda")) {
            s.add("Uganda");
            s.add(AFRIKA);
        }
        if(m.hasCaseSensitive("Kamerun", "Cameroon")) {
            s.add("Kamerun");
            s.add(AFRIKA);
        }

    }

    private static boolean anySubjectEquals(List<String> subjects, String... matchAny) {
        return Arrays.stream(matchAny).anyMatch(s -> anySubjectEquals(subjects, s));
    }
    private static boolean anySubjectEquals(List<String> subjects, String match) {
        return subjects.stream().anyMatch(s -> s.equalsIgnoreCase(match));
    }

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

        if(isTorrents(document)) {
            return TORRENTS;
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

    private static boolean isTorrents(Document d) {
        DocumentMatcher m = new DocumentMatcher(d);
        return m.isFromFeed("tv-time");
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
            m.isFromFeed("Washington Post - The Fact Checker") ||
            m.isFromFeed("Washington Post - WorldViews") ||
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
            m.anySubjectEquals(UUTISET) ||
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
