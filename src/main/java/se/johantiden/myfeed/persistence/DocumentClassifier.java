package se.johantiden.myfeed.persistence;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import se.johantiden.myfeed.classification.DocumentMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
    public static final String MILJÖPARTIET = "Miljöpartiet";
    public static final String SYDAFRIKA = "Sydafrika";

    private DocumentClassifier() {
    }


    public static List<SubjectRule> getDefaultSubjectRules() {
        List<SubjectRule> l = new ArrayList<>();
        add(l, EKONOMI, "ekonomi");
        add(l, EKONOMI, "näringsliv");
        add(l, "Ledare", "ledare");
        add(l, "Sport", "sport");
        add(l, "Briefing", "your.+briefing");
        add(l, "Tim Berners-Lee", "Tim Berners\\-Lee");

        add(l, TYSKLAND, "german");
        add(l, TYSKLAND, "tysk");
        add(l, TYSKLAND, "Brandenburg");
        add(l, TYSKLAND, "merkel");
        add(l, "Angela Merkel", "merkel");

        add(l, "Nazism", "Hitler");
        add(l, "Nazism", "Nazi");

        add(l, "Twitter", "[Tt]witter");
        add(l, "Twitter", "[Tt]weet");

        add(l, "Macron", "Macron");

/*
        if(m.anyCategoryEquals("film")) { l.add("Film"); }
        if(m.has("turist")) { l.add("Turist"); }
        if(m.has("formel 1")) { l.add("Formel 1"); }
        if(m.has("rallycross")) { l.add("Rallycross"); }
        if(m.has("GP-hoppning","travlopp")) { l.add("Hästsport"); }
        if(m.has("Tennis","Federer")) { l.add("Tennis"); }
        if(m.has("sprang maran")) { l.add("Löpning"); }
        if(m.has("hockey", "Henrik Lundqvist", "New York Rangers", "Nicklas Bäckström")) { l.add("Hockey"); }
        if(m.has(FOTBOLL, "allsvensk", "Champions League", "premier league", "superettan", "Benfica ", "Malmö FF", "Ronaldo", "La Liga", "Real Madrid")) {
            l.add("Fotboll"); }
        if(m.has("handboll", "H65")) { l.add("Handboll"); }
        if(m.hasCaseSensitive("NBA")) { l.add("Basket"); }
        if(m.has("bordtennis", "pingis")) { l.add("Bordtennis"); }
        if(m.has("Giro") && m.has("Italia")) { l.add("Cykling"); }
        if(m.has("Diamond League")) { l.add("Friidrott"); }
        if(m.has("V75")) { l.add("Trav"); }
        if(m.has("Johaug", "alpin", "skidor", "Charlotte Kalla")) { l.add("Skidor"); }
        if(m.has("speedway")) { l.add("Speedway"); }
        if(anySubjectEquals(l, SPORT) && m.hasCaseSensitive("OS") || m.has("olympisk")) { l.add("OS"); }
        if(m.has("netflix")) { l.add("Netflix"); }
        if(m.has("Boko Haram")) { l.add("Boko Haram"); }
        if(m.has("Climate", "Klimat")) { l.add("Klimat"); }
        if(m.has("väder ", "väder.", " väder", "blåsväder", "Cyclone", "Cyklon") || m.hasCaseSensitive("SMHI")) {
            l.add("Väder"); }
        if(m.has(EUROVISION)) { l.add(EUROVISION); }
        if(m.has("Rouhani", "Rohani")) { l.add("Rouhani"); }
        if(m.has("Net Neutrality")) { l.add("Net Neutrality"); }
        if(m.anyCategoryEquals("science")) { l.add(FORSKNING); }
        if(m.has("Göteborg", "Gothenburg")) { l.add("Göteborg"); }
        if(m.has("Malmö")) { l.add("Malmö"); }
        if(m.has("obama")) { l.add("Obama"); }
        if(m.has("trump")) { l.add("Trump"); }
        if(m.hasCaseSensitive("FBI")) { l.add("FBI"); }
        if(m.has(SPOTIFY)) { l.add(SPOTIFY); }
        if(m.has("Microsoft")) { l.add("Microsoft"); }
        if(m.has("Samsung")) { l.add("Samsung"); }
        if(m.hasCaseSensitive("Apple")) { l.add("Apple"); }
        if(m.has("block") && m.has("chain")) { l.add("Blockchain"); }
        if(m.has("Facebook")) { l.add("Facebook"); }
        if(m.has("Google")) { l.add("Google"); }
        if(m.has("Baloch")) { l.add("Baloch"); }
        if(m.has(MUSEUM)) { l.add(MUSEUM); }
        if(m.has("musik", "hiphop")) { l.add("Musik"); }
        if(m.has("konstnär")) { l.add("Konst"); }
        if(m.has("författare")) { l.add(BÖCKER); }
        if(m.has("dramaserie")) { l.add("Film/TV"); }
        if(m.has("terror")) { l.add("Terror"); }
        if(m.authorEquals("TT")) { l.add("TT");}
        if(m.has("Ebola")) { l.add("Ebola"); }
        if(m.has("Cholera") | m.has("Kolera")) { l.add("Kolera"); }
        if(m.has("IT-attacken", "Ransomware", "Cyberattack", "malware", "WanaCry", "WannaCry", "Hacker", "hacking", "IT-utpressning", "IT-angrepp", "Internet Security", "eternalblue", "botnet")) {
            l.add(IT_SÄKERHET); }
        if(m.has("Brexit")) { l.add("Brexit"); }
        if(m.has("Feministiskt initiativ")) { l.add("Feministiskt Initiativ"); }
        if((m.has(MILJÖPARTIET) || m.hasCaseSensitive(" MP ", " MP.")) && !anySubjectEquals(l, STORBRITANNIEN)) {
            l.add(MILJÖPARTIET); }
        if(m.has(SVERIGEMOKRATERNA) && m.hasCaseSensitive("SD")) { l.add(SVERIGEMOKRATERNA); }
        if(m.has("Moderaterna", "Kinberg Batra")) { l.add("Moderaterna"); }
        if(m.has("Kinberg Batra")) { l.add("Kinberg Batra"); }
        if(m.has("Pope Francis")) { l.add("Pope Francis"); }
        if(m.has("debatt")) { l.add("Debatt"); }
        if(m.hasCaseSensitive("CCTV")) { l.add("Foliehatt"); }
        addPlaces(l, m);
        if(m.has("kvinnor") && m.has("män")) { l.add("Kvinnor"); }
        if(m.has("kvinnor") && m.has("män")) { l.add("Män"); }
        if(m.isFromFeed("TheLocal") && m.has("recipe:")) { l.add(RECIPE); }
        if(m.isFromFeed("HackerNews") && m.has("hiring")) { l.add(HIRING); }
        if(m.has(VÄNSTERPARTIET)) { l.add(VÄNSTERPARTIET); }
        if(m.has(CENTERPARTIET)) { l.add(CENTERPARTIET); }
        if(m.has(VÄNSTERPARTIET)) { l.add(VÄNSTERPARTIET); }
        if(m.has(KRISTDEMOKRATERNA) || m.hasCaseSensitive("KD")) { l.add(KRISTDEMOKRATERNA); }

        addPeople(l, m);
        if(m.has(IDAGSIDAN)) { l.add(IDAGSIDAN); }
        if(m.has("historian", "1500", "1600", "1700", "1800")) { l.add(HISTORIA); }
        if(m.has("Daesh", "Islamic State") || m.hasCaseSensitive("ISIL", "ISIS") || m.has("terror")&&m.hasCaseSensitive("IS")) {
            l.add("Daesh"); }
        if(m.has("Socialdemokraterna") && !anySubjectEquals(l, TYSKLAND)) { l.add("Socialdemokraterna"); }
        if(m.has("mat-dryck") || m.anyCategoryEquals("Restaurants")) { l.add(MAT); }
        if(m.anyCategoryEquals("gaming")) { l.add("Gaming"); }
        if(m.feedStartsWith("Reddit")) {
            String redditCategory = d.getSourceCategories().iterator().next();
            l.add(redditCategory);
        }

        // Badness:
        if(m.hasCaseSensitive(NEWS_GRID)) { l.add(NEWS_GRID); }
        if(m.has("cars technica")) { l.add("Cars"); }
        if(m.has(WEBB_TV)) { l.add(WEBB_TV); }
        if(m.has(LEAGUEOFLEGENDS)) { l.add(LEAGUEOFLEGENDS); }
        if(m.anyCategoryEquals(DEALMASTER)) { l.add(DEALMASTER); }
        if(m.has("dödsfäll")) { l.add("Dödsfälla"); }
        if(m.anyCategoryEquals("motor")) { l.add("Motor"); }
        if(m.anyCategoryEquals(SERIER)) { l.add("Serier"); }
        if(m.hasCaseSensitive("Här är") || m.has("– här är", "- här är")) { l.add("Här är"); }
        if(m.has("turist")) { l.add("Turist"); }
        if(d.pageUrl.contains(UUTISET)) { l.add("Uutiset"); }
        if(m.isFromFeed("Svenska Dagbladet") && m.startsWithCaseSensitive("VIDEO")) { l.add("VIDEO"); }
        if(m.has("fragesport")) { l.add(FRÅGESPORT); }
        if(m.anyCategoryEquals(JUNIOR)) { l.add(JUNIOR); }
        if(m.anyCategoryEquals(NUTIDSTESTET)) { l.add(NUTIDSTESTET); }
        if(m.anyCategoryEquals(PERFECT_GUIDE)) { l.add(PERFECT_GUIDE); }
        if(m.anyCategoryEquals(RESOR)) { l.add(RESOR); }
        if(m.has("Reddit") && m.anyCategoryEquals("IAmA")) { l.add(I_AM_A); }
        if(m.has("Reddit") && m.anyCategoryEquals(BLACK_PEOPLE_TWITTER)) { l.add(BLACK_PEOPLE_TWITTER); }
        if(m.has("Reddit") && m.anyCategoryEquals(THE_DENNIS)) { l.add(THE_DENNIS); }
        if(m.has("Reddit") && m.has("-- number", "--number")) { l.add(NUMBER_OF_PEOPLE); }
*/

        return l;
    }

    private static boolean add(Collection<SubjectRule> list, String name, String expression) {
        return list.add(new SubjectRule(name, expression));
    }

    private static void addPeople(Collection<String> s, DocumentMatcher m) {

        if(m.has("Busch Thor")) {
            s.add("Ebba Busch Thor");
            s.add(KRISTDEMOKRATERNA);
        }
        if(m.has("Alice Bah Kuhnke")) {
            s.add("Alice Bah Kuhnke");
            s.add(MILJÖPARTIET);
        }
        if(m.has("Mugabe")) {
            s.add(SYDAFRIKA);
        }
        if(m.has("Jonas Sjöstedt")) {
            s.add("Jonas Sjöstedt");
            s.add(VÄNSTERPARTIET);
        }
        if(m.has("Annie Lööf")) {
            s.add("Annie Lööf");
            s.add(CENTERPARTIET);
        }
        if(m.has("Löfven")) {
            s.add("Stefan Löfven");
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
        if (m.has("Malala")) {
            s.add("Malala Yousafzai");
        }
        if (m.has("Kim Wall")) {
            s.add("Kim Wall");
        }
        if (anySubjectEquals(s, STORBRITANNIEN) && m.hasCaseSensitive("May")) {
            s.add("Theresa May");
            s.add(STORBRITANNIEN);
        }
    }

    private static void addPlaces(Collection<SubjectRule> l) {
        add(l, "Kuba", "Cuba");
        add(l, "Kuba", "Kuba");

        add(l, "Irak", "Iraq");
        add(l, "Irak", "Irak");
        add(l, "Irak", "Mosul");

        add(l, "Oman", "Oman");

        addAfrica(l);

        add(l, "Oman", "Oman");
        add(l, "Nepal", "Nepal");

        add(l, "Syrien", "Syrien");
        add(l, "Syrien", "Syria");
        add(l, "Syrien", "[Ss]yrisk");
        add(l, "Syrien", "Syrier");
        add(l, "Syrien", "Damascus");
        add(l, "Syrien", "Damaskus");

        add(l, "Venezuela", "Venezuela");
        add(l, "Venezuela", "Maduro");

        add(l, "Nordkorea", "Nordkorea");
        add(l, "Nordkorea", "North Korea");

        add(l, "Sydkorea", "Sydkorea");
        add(l, "Sydkorea", "South Korea");
        add(l, "Sydkorea", "Seoul");
        add(l, "Seoul", "Seoul");

        add(l, "Myanmar", "Myanmar");
        add(l, "Myanmar", "Burma");
        add(l, "Myanmar", "Aung San Suu Kyi");
        add(l, "Aung San Suu Kyi", "Aung San Suu Kyi");

        add(l, "Iran", "Iran");
        add(l, "Iran", "Rouhani");
        add(l, "Iran", "Rohani");

        add(l, "Kina", "Kina");
        add(l, "Kina", "China");
        add(l, "Kina", "Xi Jinping");
        add(l, "Kina", "[Kk]ines");
        add(l, "Xi Jinping", "Xi Jinping");

        add(l, "Albanien", "Albanien");
        add(l, "Albanien", "Albania");

        add(l, "Bosnien", "Bosnien");
        add(l, "Bosnien", "Bosnia");

        add(l, "Belgien", "Belgien");
        add(l, "Belgien", "Belgium");

        add(l, "Indien", "Indien");
        add(l, "Indien", "India");

        add(l, "Brasilien", "Brasilien");
        add(l, "Brasilien", "Brazil");

        add(l, "Chile", "Chile");

        add(l, "Egypten", "Egypt");

        add(l, "Spanien", "Spanien");
        add(l, "Spanien", "Spain");
        add(l, "Spanien", "Spanish");
        add(l, "Spanien", "Barcelona");
        add(l, "Barcelona", "Barcelona");

        add(l, "Sierra Leone", "Sierra Leone");

        add(l, "Kenya", "Kenya");

        add(l, "Hong Kong", "Hong Kong");

        add(l, "Jemen", "Jemen");
        add(l, "Jemen", "Yemen");

        add(l, "Danmark", "Danmark");
        add(l, "Danmark", "Denmark");
        add(l, "Danmark", "Köpenhamn");
        add(l, "Danmark", "Copenhagen");
        add(l, "Köpenhamn", "Köpenhamn");
        add(l, "Köpenhamn", "Copenhagen");

        add(l, "Bangladesh", "Bangladesh");

        add(l, "Malaysia", "Malaysia");

        add(l, "Frankrike", "Frankrike");
        add(l, "Frankrike", "France");
        add(l, "Frankrike", "[Ff]ransk");
        add(l, "Frankrike", "French");
        add(l, "Frankrike", "Paris");
        add(l, "Paris", "Paris");

        add(l, "Australia", "Australia");

        add(l, "Nederländerna", "Nederländerna");
        add(l, "Nederländerna", "Netherlands");
        add(l, "Nederländerna", "Dutch");

        add(l, "Italien", "Italien");
        add(l, "Italien", "Italy");

        add(l, "Tjeckien", "Tjeckien");
        add(l, "Tjeckien", "Tjeckisk");
        add(l, "Tjeckien", "Czech");

        add(l, "Kuwait", "Kuwait");

        add(l, "Saudiarabien", "Saudiarabien");
        add(l, "Saudiarabien", "Saudi Arabia");

        add(l, "Qatar", "Qatar");

        add(l, SYDAFRIKA, SYDAFRIKA);
        add(l, SYDAFRIKA, "South Africa");

        add(l, "EU", "EU");
        add(l, "EU", "European Union");

        add(l, "Europa", "Europa");
        add(l, "Europa", "Europe");


//        addUsa(l, m);

        add(l, "Mexiko", "Mexico");
        add(l, "Mexiko", "Mexiko");
        add(l, "Mexiko", "Mexican");

        add(l, "Turkiet", "Turkiet");
        add(l, "Turkiet", "Turkey");
        add(l, "Turkiet", "Turkish");
        add(l, "Turkiet", "Recep Tayyip Erdogan");
        add(l, "Turkiet", "Istanbul");

/*        if(m.has("Greece", "Greek", "Grekland", "Grek")) { l.add("Grekland"); }
        if(m.has("Österrike", "Austria")) { l.add("Österrike"); }
        if(m.has("Finland")) { l.add("Finland"); }
        if(m.has("Nigeria")) { l.add("Nigeria"); }
        if(m.has("South Sudan")) { l.add("Sydsudan"); }
        if(m.has("Kiev", "Ukrain")) { l.add("Ukraina"); }
        if(m.has("Norge", "Norway", "norska")) { l.add("Norge"); }
        if(m.has("Taiwan")) { l.add("Taiwan"); }
        if(m.has("Israel", "West Bank")) { l.add("Israel"); }
        if(m.has("Schweiz")) { l.add("Schweiz"); }
        if(m.has("Ungern")) { l.add("Ungern"); }
        if(m.has("Portugal")) { l.add("Portugal"); }
        if(m.has("Japan")) { l.add("Japan"); }
        if(m.has("Argentin")) { l.add("Argentina"); }
        if(m.has("Russia", RUSSIA, "Rysk") || m.hasCaseSensitive("Moskva")) { l.add(RUSSIA); }
        if(m.has("Canada", "Kanada", "Canadian", "Kanaden")) { l.add("Kanada"); }
        if(m.has("Palestin")) { l.add("Palestina"); }
        if(m.has("Afghanistan", "Afganistan", "Baloch", "kabul")) { l.add("Afghanistan"); }
        if(m.has("Pakistan", "Baloch")) { l.add("Pakistan"); }
        if(m.has("Manila", FILIPPINERNA, "Philippines")) { l.add(FILIPPINERNA); }
        if(m.has("Irish", "Ireland", "Irland")) { l.add("Irland"); }
        if(m.has("Belgien", "Belgium")) { l.add("Belgien"); }
        if(m.has("Tibet")) { l.add("Tibet"); }
        if(m.has("Montenegro")) { l.add("Montenegro"); }

        addGreatBritain(l, m);
        addSweden(l, m);*/
    }

    private static void addUsa(Collection<String> s, DocumentMatcher m) {
        if(m.hasCaseSensitive("US", "FBI") || m.has("america") && !m.has("south america", "americas") || m.has("U.S.", "obama", "trump")) {
            s.add(USA);
        }
        if(m.has("Orlando")) {
            s.add("Orlando");
            s.add((USA));
        }
    }

    private static void addGreatBritain(Collection<String> s, DocumentMatcher m) {
        if(m.has("Manchester")) {
            s.add("Manchester");
            s.add((STORBRITANNIEN));
        }
        if(m.has(STORBRITANNIEN, "London", "England", "Britain", "Scotland", "British", "Britain")) {
            s.add(STORBRITANNIEN);
        }

    }

    private static void addSweden(Collection<String> s, DocumentMatcher m) {
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

    private static void addAfrica(Collection<SubjectRule> s) {
        add(s, "Libyen", "Libyen");
        add(s, "Libyen", "Libya");
        add(s, AFRIKA, "Libyen");
        add(s, AFRIKA, "Libya");

        add(s, "Lesotho", "Lesotho");
        add(s, AFRIKA, "Lesotho");

        add(s, "Marocko", "Marocko");
        add(s, AFRIKA, "Marocko");
        add(s, "Marocko", "Morocco");
        add(s, AFRIKA, "Morocco");


        add(s, "Tunisien", "Tunis");
        add(s, AFRIKA, "Tunis");

        add(s, "Angola", "Angola");
        add(s, AFRIKA, "Angola");

        add(s, "Kongo-Kinshasa", "Kongo-Kinshasa");
        add(s, AFRIKA, "Kongo-Kinshasa");

        add(s, "Elfenbenskusten", "Elfenbenskusten");
        add(s, AFRIKA, "Elfenbenskusten");
        add(s, "Elfenbenskusten", "Ivory Coast");
        add(s, AFRIKA, "Ivory Coast");
        add(s, "Elfenbenskusten", "Ivorian");
        add(s, AFRIKA, "Ivorian");

        add(s, "Uganda", "Uganda");
        add(s, AFRIKA, "Uganda");

        add(s, "Kamerun", "Kamerun");
        add(s, AFRIKA, "Kamerun");
        add(s, "Kamerun", "Cameroon");
        add(s, AFRIKA, "Cameroon");
    }

    private static boolean anySubjectEquals(Collection<String> subjects, Collection<String> matchAny) {
        return matchAny.stream().anyMatch(s -> anySubjectEquals(subjects, s));
    }
    private static boolean anySubjectEquals(Collection<String> subjects, String match) {
        return subjects.stream().anyMatch(s -> s.equalsIgnoreCase(match));
    }

    public static Set<String> getTabsFor(Document document) {
        if(isError(document)) {
            return Collections.singleton(ERROR);
        }

        if(isBad(document)) {
            return Collections.singleton(BAD);
        }

        if(isTech(document)) {
            return Collections.singleton(TECH);
        }

        if(isCulture(document)) {
            return Collections.singleton(CULTURE);
        }

        if(isTorrents(document)) {
            return Collections.singleton(TORRENTS);
        }

        if(isFun(document)) {
            return Collections.singleton(FUN);
        }

        if(isBiz(document)) {
            return Collections.singleton(BIZ);
        }

        if(isSport(document)) {
            return Collections.singleton(SPORT);
        }

        if(isVäder(document)) {
            return Collections.singleton(VÄDER);
        }

        if(isNews(document)) {
            return Collections.singleton(NEWS);
        }

        return Collections.singleton(UNMATCHED_TAB);
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
            m.has("Reddit") && m.anyCategoryEquals("gaming", "pics", "gifs", "funny", "PoliticalHumor", "mildlyinteresting", "Design", "aww", "sports", "music", "videos", "todayilearned", "NatureIsFuckingLit", "nottheonion", "MarchAgainstTrump", "Showerthoughts", "photoshopbattles", "oddlysatisfying", "space", "mildlyinfuriating") ||
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
            m.anyCategoryEquals("Jokes", "OldSchoolCool", "GetMotivated");
    }

    public static void appendUrlFoldersAsCategories(Document document) {

        DocumentMatcher m = new DocumentMatcher(document);

        if (!m.isFromFeed("HackerNews")) {
            List<String> folders = parseUrlFolders(document.pageUrl).stream()
                                   .filter(DocumentClassifier::urlFilter)
                                   .collect(Collectors.toList());

            folders.stream()
                .filter(f -> !m.anyCategoryEquals(f))
                .filter(f -> !f.equals("artikel"))
                .filter(f -> !f.equals("comments"))
                .filter(f -> !f.equals("worldNews"))
                .filter(f -> !f.equals("Reuters"))
                .filter(f -> !f.equals("story"))
                .map(StringUtils::capitalize)
                .forEach(document.getSourceCategories()::add);
        }
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
