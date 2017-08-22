package se.johantiden.myfeed.persistence;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
    public static final String FORSKNING = "Forskning";
    public static final String IT_SÄKERHET = "IT-Säkerhet";
    public static final String NETFLIX = "Netflix";
    public static final String SPOTIFY = "Spotify";
    public static final String HIRING = "Hiring";
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
    public static final String I_AM_A = "IAmA";
    public static final String BLACK_PEOPLE_TWITTER = "BlackPeopleTwitter";
    public static final String THE_DENNIS = "The_Dennis";
    public static final String NUMBER_OF_PEOPLE = "Number of People";
    public static final String NUTIDSTESTET = "nutidstestet";
    public static final String RESOR = "resor";
    public static final String UUTISET = "uutiset";
    public static final String KRISTDEMOKRATERNA = "Kristdemokraterna";
    public static final String VÄNSTERPARTIET = "Vänsterpartiet";
    public static final String CENTERPARTIET = "Centerpartiet";
    public static final String SVERIGEMOKRATERNA = "Sverigemokraterna";
    public static final String LIBERALERNA = "Liberalerna";
    public static final String FILIPPINERNA = "Filippinerna";
    public static final String RYSSLAND = "Ryssland";
    public static final String AFRIKA = "Afrika";
    public static final String INRIKES = "Inrikes";
    public static final String USA = "USA";
    public static final String MILJÖPARTIET = "Miljöpartiet";
    public static final String SYDAFRIKA = "Sydafrika";
    public static final String GÖTEBORG = "Göteborg";
    public static final String SOCIALDEMOKRATERNA = "Socialdemokraterna";
    public static final String IRLAND = "Irland";
    public static final String MODERATERNA = "Moderaterna";
    public static final String REGEX_KVINNOR_MÄN = "([Kk]vinnor.*[Mm]än)|([Mm]än.*[Kk]vinnor)";
    public static final String HÄR_ÄR = "Här är";
    public static final String MICROSOFT = "Microsoft";
    public static final String FACEBOOK = "Facebook";
    public static final String PROGRAMMING = "Programming";

    private DocumentClassifier() {
    }

    public static Set<TabRule> getDefaultTabRules() {
        Set<TabRule> tabs = new HashSet<>();

        boolean hideErrors = false;
        tabs.add(new TabRule(ERROR, "&#", hideErrors));

        boolean hideWeather = true;
        tabs.add(new TabRule(VÄDER, VÄDER, hideWeather));

        boolean hideSports = true;
        tabs.add(new TabRule(SPORT, SPORT, hideSports));

        boolean hideCulture = true;
        tabs.add(new TabRule(CULTURE, "kultur", hideCulture));
        tabs.add(new TabRule(CULTURE, "kultur-noje", hideCulture));
        tabs.add(new TabRule(CULTURE, "dnbok", hideCulture));

        tabs.add(new TabRule(NEWS, "Al Jazeera.*news", false));
        tabs.add(new TabRule(NEWS, "Al Jazeera.*insidestory", false));
        tabs.add(new TabRule(NEWS, "Al Jazeera.*opinion", false));
        tabs.add(new TabRule(NEWS, "Al Jazeera.*features", false));
        tabs.add(new TabRule(NEWS, "Al Jazeera.*indepth", false));
        tabs.add(new TabRule(NEWS, "Al Jazeera.*programmes", false));
        tabs.add(new TabRule(NEWS, "SVT Nyheter.*nyheter", false));
        tabs.add(new TabRule(NEWS, "Dagens Nyheter.*nyheter", false));
        tabs.add(new TabRule(NEWS, "Dagens Nyheter.*varlden", false));
        tabs.add(new TabRule(NEWS, "Dagens Nyheter.*sverige", false));
        tabs.add(new TabRule(NEWS, "Dagens Nyheter.*debatt", false));
        tabs.add(new TabRule(NEWS, "TheLocal", false));
        tabs.add(new TabRule(NEWS, "Reuters - World", false));
        tabs.add(new TabRule(NEWS, "Los Angeles Times - World", false));
        tabs.add(new TabRule(NEWS, "Washington Post - The Fact Checker", false));
        tabs.add(new TabRule(NEWS, "Washington Post - WorldViews", false));
        tabs.add(new TabRule(NEWS, "worldnews", false));
        tabs.add(new TabRule(NEWS, "New York Times - World", false));
        tabs.add(new TabRule(NEWS, "Reddit.*politics", false));
        tabs.add(new TabRule(NEWS, "Reddit.*news", false));
        tabs.add(new TabRule(NEWS, "Reddit.*worldnews", false));

        tabs.add(new TabRule(TECH, "Slashdot", false));
        tabs.add(new TabRule(TECH, "HackerNews", false));
        tabs.add(new TabRule(TECH, "Engadget", false));
        tabs.add(new TabRule(TECH, "science", false));
        tabs.add(new TabRule(TECH, "ProgrammerHumor", false));
        tabs.add(new TabRule(TECH, "Ars Technica.*tech-policy", false));
        tabs.add(new TabRule(TECH, "Ars Technica.*gadgets", false));
        tabs.add(new TabRule(TECH, "Ars Technica.*gaming", false));
        tabs.add(new TabRule(TECH, "Ars Technica.*facebook", false));
        tabs.add(new TabRule(TECH, "Ars Technica.*opposable thumbs", false));
        tabs.add(new TabRule(TECH, "Ars Technica.*the-multiverse", false));
        tabs.add(new TabRule(TECH, "Ars Technica.*Technology Lab", false));
        tabs.add(new TabRule(TECH, "Ars Technica.*Ministry of Innovation", false));
        tabs.add(new TabRule(TECH, "Reddit.*technology", false));
        tabs.add(new TabRule(TECH, NETFLIX, false));
        tabs.add(new TabRule(TECH, SPOTIFY, false));
        tabs.add(new TabRule(TECH, MICROSOFT, false));
        tabs.add(new TabRule(TECH, FACEBOOK, false));
        tabs.add(new TabRule(TECH, IT_SÄKERHET, false));
        tabs.add(new TabRule(TECH, FORSKNING, false));
        tabs.add(new TabRule(TECH, PROGRAMMING, false));

        tabs.add(new TabRule(FUN, "AskReddit", false));
        tabs.add(new TabRule(FUN, "Reddit.*gaming", false));
        tabs.add(new TabRule(FUN, "Reddit.*pics", false));
        tabs.add(new TabRule(FUN, "Reddit.*gifs", false));
        tabs.add(new TabRule(FUN, "Reddit.*funny", false));
        tabs.add(new TabRule(FUN, "Reddit.*PoliticalHumor", false));
        tabs.add(new TabRule(FUN, "Reddit.*mildlyinteresting", false));
        tabs.add(new TabRule(FUN, "Reddit.*Design", false));
        tabs.add(new TabRule(FUN, "Reddit.*aww", false));
        tabs.add(new TabRule(FUN, "Reddit.*sports", false));
        tabs.add(new TabRule(FUN, "Reddit.*music", false));
        tabs.add(new TabRule(FUN, "Reddit.*videos", false));
        tabs.add(new TabRule(FUN, "Reddit.*todayilearned", false));
        tabs.add(new TabRule(FUN, "Reddit.*NatureIsFuckingLit", false));
        tabs.add(new TabRule(FUN, "Reddit.*nottheonion", false));
        tabs.add(new TabRule(FUN, "Reddit.*MarchAgainstTrump", false));
        tabs.add(new TabRule(FUN, "Reddit.*Showerthoughts", false));
        tabs.add(new TabRule(FUN, "Reddit.*photoshopbattles", false));
        tabs.add(new TabRule(FUN, "Reddit.*oddlysatisfying", false));
        tabs.add(new TabRule(FUN, "Reddit.*space", false));
        tabs.add(new TabRule(FUN, "Reddit.*mildlyinfuriating", false));
        tabs.add(new TabRule(FUN, "Reddit.*TrumpCriticizesTrump", false));
        tabs.add(new TabRule(FUN, "xkcd", false));

        tabs.add(new TabRule(BIZ, "näringsliv", false));
        tabs.add(new TabRule(BIZ, "investerar", false));
        tabs.add(new TabRule(BIZ, "börsraket", false));
        tabs.add(new TabRule(BIZ, "New York Times - World.*business", false));
        tabs.add(new TabRule(BIZ, "Slashdot.*business", false));
        tabs.add(new TabRule(BIZ, "Breakit", false));
        tabs.add(new TabRule(BIZ, "Dagens Industri", false));
        tabs.add(new TabRule(BIZ, EKONOMI, false));

        boolean hideBad = true;
        tabs.add(new TabRule(BAD, HIRING, hideBad));
        tabs.add(new TabRule(BAD, WEBB_TV, hideBad));
        tabs.add(new TabRule(BAD, SERIER, hideBad));
        tabs.add(new TabRule(BAD, CARS, hideBad));
        tabs.add(new TabRule(BAD, DEALMASTER, hideBad));
        tabs.add(new TabRule(BAD, LEAGUEOFLEGENDS, hideBad));
        tabs.add(new TabRule(BAD, IDAGSIDAN, hideBad));
        tabs.add(new TabRule(BAD, MAT, hideBad));
        tabs.add(new TabRule(BAD, NEWS_GRID, hideBad));
        tabs.add(new TabRule(BAD, FRÅGESPORT, hideBad));
        tabs.add(new TabRule(BAD, JUNIOR, hideBad));
        tabs.add(new TabRule(BAD, PERFECT_GUIDE, hideBad));
        tabs.add(new TabRule(BAD, I_AM_A, hideBad));
        tabs.add(new TabRule(BAD, BLACK_PEOPLE_TWITTER, hideBad));
        tabs.add(new TabRule(BAD, THE_DENNIS, hideBad));
        tabs.add(new TabRule(BAD, NUMBER_OF_PEOPLE, hideBad));
        tabs.add(new TabRule(BAD, NUTIDSTESTET, hideBad));
        tabs.add(new TabRule(BAD, RESOR, hideBad));
        tabs.add(new TabRule(BAD, UUTISET, hideBad));
        tabs.add(new TabRule(BAD, HÄR_ÄR, hideBad));
        tabs.add(new TabRule(BAD, "interestingasfuck", hideBad));
        tabs.add(new TabRule(BAD, "Jokes", hideBad));
        tabs.add(new TabRule(BAD, "OldSchoolCool", hideBad));
        tabs.add(new TabRule(BAD, "GetMotivated", hideBad));
        tabs.add(new TabRule(BAD, "Dagens Nyheter.*blogg", false));

        return tabs;
    }

    public static Set<SubjectRule> getDefaultSubjectRules() {
        TreeSet<SubjectRule> s = new TreeSet<>(SubjectRule.COMPARATOR);
        add(s, EKONOMI, "ekonomi");
        add(s, EKONOMI, "näringsliv");
        add(s, "Ledare", "ledare");
        add(s, "Sport", "sport");
        add(s, "Briefing", "[Yy]our.+[Bb]riefing");
        add(s, "Tim Berners-Lee", "Tim Berners\\-Lee");

        add(s, TYSKLAND, "[Gg]erman");
        add(s, TYSKLAND, "[Tt]ysk");
        add(s, TYSKLAND, "Brandenburg");
        add(s, TYSKLAND, "merkel");
        add(s, "Angela Merkel", "merkel");

        add(s, "Nazism", "Hitler");
        add(s, "Nazism", "Nazi");

        add(s, "Twitter", "[Tt]witter");
        add(s, "Twitter", "[Tt]weet");

        add(s, "Macron", "Macron");

        addPlaces(s);
        addPeople(s);

        add(s, "Turist", "[Tt]urist");

        add(s, "Netflix", "Netflix");
        add(s, "Boko Haram", "Boko Haram");

        add(s, "Klimat", "[Kk]limat");
        add(s, "Klimat", "[Cc]limate");

        add(s, "Väder", " [Vv]äder");
        add(s, "Väder", " [Vv]ädret");

        add(s, EUROVISION, EUROVISION);


        add(s, "FBI");
        add(s, SPOTIFY);
        add(s, MICROSOFT);
        add(s, "Verizon");
        add(s, "Uber");
        add(s, "Samsung");
        add(s, "Apple");
        add(s, FACEBOOK);
        add(s, "Nintendo");
        add(s, "Gaming", "Nintendo");
        add(s, "Google");
        add(s, "H&M");
        add(s, "HP");

        add(s, "Net Neutrality", "Net Neutrality");
        add(s, "Blockchain", "[Bb]lock.*chain");
        add(s, "Blockchain", "[Bb]lockkedja");

        add(s, MUSEUM);
        add(s, "Musik", "[Mm]usik");
        add(s, "Musik", "[Hh]iphop");
        add(s, "Konst", "[Kk]onstnär");
        add(s, BÖCKER, "[Ff]författare");
        add(s, "Film/TV", "[Dd]ramaserie");

        add(s, "Terror", "[Tt]error");
        add(s, "TT", "TT");
        add(s, "Ebola", "Ebola");
        add(s, "Kolera", "Kolera");
        add(s, "Kolera", "Cholera");

        add(s, IT_SÄKERHET, "IT-attack");
        add(s, IT_SÄKERHET, "[Rr]ansomware");
        add(s, IT_SÄKERHET, "[Cc]yberattack");
        add(s, IT_SÄKERHET, "[Mm]alware");
        add(s, IT_SÄKERHET, "[Ww]ana[Cc]ry");
        add(s, IT_SÄKERHET, "[Ww]anna[Cc]ry");
        add(s, IT_SÄKERHET, "[Hh]acker[^N]");
        add(s, IT_SÄKERHET, "[Hh]acking");
        add(s, IT_SÄKERHET, "IT-utpressning");
        add(s, IT_SÄKERHET, "IT-angrepp");
        add(s, IT_SÄKERHET, "[Ii]nternet [Ss]ecurity");
        add(s, IT_SÄKERHET, "[Ee]ternalblue");
        add(s, IT_SÄKERHET, "[Bb]otnet");

        add(s, "Brexit", "Brexit");

        add(s, "Feministiskt Initiativ", "Feministiskt [Ii]nitiativ");
        add(s, VÄNSTERPARTIET);
        add(s, MILJÖPARTIET);
        add(s, SOCIALDEMOKRATERNA);
        add(s, CENTERPARTIET);
        add(s, LIBERALERNA);
        add(s, KRISTDEMOKRATERNA);
        add(s, KRISTDEMOKRATERNA, "KD[ \\.]");
        add(s, MODERATERNA);
        add(s, SVERIGEMOKRATERNA);
        add(s, SVERIGEMOKRATERNA, "SD[ \\.]");

        add(s, "Pope Francis");
        add(s, "Debatt", "[Dd]ebatt");

        add(s, "Mecca", "Mecca");
        add(s, "Mecca", "Hajj");
        add(s, "Mecca", "[Aa]l-[Aa]qsa");

        add(s, "Foliehatt", "CCTV");

        add(s, "Kvinnor", REGEX_KVINNOR_MÄN);
        add(s, "Män", REGEX_KVINNOR_MÄN);

        add(s, HIRING, "[Hh]iring.*HackerNews");
        add(s, IDAGSIDAN);

        add(s, HISTORIA, "historian");
        add(s, HISTORIA, "1500");
        add(s, HISTORIA, "1600");
        add(s, HISTORIA, "1700");
        add(s, HISTORIA, "1800");

        add(s, MAT, "[Rr]ecipe:.*TheLocal");
        add(s, MAT, "mat-dryck");
        add(s, MAT, "Restaurants");

        add(s, "Daesh", "Daesh");
        add(s, "Daesh", "Islamic State");
        add(s, "Daesh", "ISIL");
        add(s, "Daesh", "ISIS");
        add(s, "Daesh", "([Tt]error.*IS)|(IS.*[Tt]error)");

        add(s, "Taliban", "Taliban");

        add(s, "Gaming", "[Gg]aming");
        add(s, "Gaming", "[Gg]ames");
        add(s, "Cars", "cars technica");
        add(s, NEWS_GRID);
        add(s, WEBB_TV);
        add(s, LEAGUEOFLEGENDS);
        add(s, DEALMASTER);
        add(s, "Dödsfälla", "[Dd]ödsfälla");
        add(s, "Motor", "[Mm]otor");
        add(s, "Serier", "[Ss]erier");

        add(s, HÄR_ÄR, "– här är");
        add(s, HÄR_ÄR, "- här är");
        add(s, HÄR_ÄR, HÄR_ÄR);

        add(s, "Uutiset", UUTISET);
        add(s, "VIDEO", "^VIDEO");

        add(s, "Naturkatastrof", "[Jj]ordbävning");
        add(s, "Naturkatastrof", "[Ee]arthquake");
        add(s, "Naturkatastrof", "[Ll]andslide");
        add(s, "Naturkatastrof", "[Mm]udslide");

        add(s, "Talaq", "[Tt]alaq");

        add(s, FRÅGESPORT, "fragesport");
        add(s, JUNIOR);
        add(s, NUTIDSTESTET);
        add(s, PERFECT_GUIDE);
        add(s, RESOR);
        add(s, I_AM_A, "IAmA");
        add(s, BLACK_PEOPLE_TWITTER);
        add(s, THE_DENNIS);

        add(s, NUMBER_OF_PEOPLE, "-- number");
        add(s, NUMBER_OF_PEOPLE, "--number");

        add(s, PROGRAMMING, "[Pp]rogramming");

        add(s, "Solförmörkelse", "[Ee]cliipse");
        add(s, "Solförmörkelse", "[Ss]olförmörkelse");

        add(s, "Big Ben", "Big Ben");

        return s;
    }

    public static void add(Set<SubjectRule> set, String name, String expression) {
        int size = set.size();
        set.add(new SubjectRule(name, expression));
        if (size == set.size()) {
            throw new IllegalStateException("Duplicate detected! " + name + ", " + expression);
        }
    }

    public static void add(Set<SubjectRule> set, String name) {
        add(set, name, name);
    }

    private static void addPeople(Set<SubjectRule> s) {

        add(s, "Obama", "Obama");
        add(s, USA, "Obama");

        add(s, "Trump", "Trump");
        add(s, USA, "Trump");

        add(s, "Ebba Busch Thor", "Busch Thor");
        add(s, KRISTDEMOKRATERNA, "Busch Thor");
        add(s, INRIKES, "Busch Thor");

        add(s, "Alice Bah Kuhnke", "Alice Bah Kuhnke");
        add(s, MILJÖPARTIET, "Alice Bah Kuhnke");
        add(s, INRIKES, "Alice Bah Kuhnke");

        add(s, "Mugabe", "Mugabe");
        add(s, SYDAFRIKA, "Mugabe");
        add(s, AFRIKA, "Mugabe");

        add(s, "Jonas Sjöstedt", "Jonas Sjöstedt");
        add(s, VÄNSTERPARTIET, "Jonas Sjöstedt");
        add(s, INRIKES, "Jonas Sjöstedt");

        add(s, "Annie Lööf", "Annie Lööf");
        add(s, CENTERPARTIET, "Annie Lööf");
        add(s, INRIKES, "Annie Lööf");

        add(s, "Stefan Löfven", "Löfven");
        add(s, SOCIALDEMOKRATERNA, "Löfven");
        add(s, INRIKES, "Löfven");

        add(s, "Jimmie Åkesson", "Jimmie Åkesson");
        add(s, SOCIALDEMOKRATERNA, "Jimmie Åkesson");
        add(s, INRIKES, "Jimmie Åkesson");

        add(s, "Jan Björklund", "Björklund");
        add(s, LIBERALERNA, "Björklund");
        add(s, INRIKES, "Björklund");

        add(s, "Kinberg Batra");
        add(s, MODERATERNA, "Kinberg Batra");

        add(s, "Leo Varadkar", "Varadkar");
        add(s, IRLAND, "Varadkar");

        add(s, "Rodrigo Duterte", "Duterte");
        add(s, FILIPPINERNA, "Duterte");

        add(s, "Vladimir Putin", "Putin");
        add(s, RYSSLAND, "Putin");

        add(s, "Malala Yousafzai", "Malala");

        add(s, "Kim Wall", "Kim Wall");

        add(s, "Theresa May", "Theresa May");
        add(s, STORBRITANNIEN, "Theresa May");

        add(s, "Hassan Rouhani", "Rouhani");
        add(s, "Hassan Rouhani", "Rohani");
        add(s, "Iran", "Rouhani");
        add(s, "Iran", "Rohani");

        add(s, "Marcellus Williams");
        add(s, USA, "Marcellus Williams");

        add(s, "Therese Johaug", "Johaug");
    }

    private static void addPlaces(Set<SubjectRule> l) {

        add(l, "Libyen", "Libyen");
        add(l, "Libyen", "Libya");
        add(l, AFRIKA, "Libyen");
        add(l, AFRIKA, "Libya");

        add(l, AFRIKA, "Afrika");
        add(l, AFRIKA, "Africa");

        add(l, "Lesotho", "Lesotho");
        add(l, AFRIKA, "Lesotho");

        add(l, "Marocko", "Marocko");
        add(l, AFRIKA, "Marocko");
        add(l, "Marocko", "Morocc");
        add(l, AFRIKA, "Morocc");

        add(l, "Tunisien", "Tunis");
        add(l, AFRIKA, "Tunis");

        add(l, "Angola", "Angola");
        add(l, AFRIKA, "Angola");

        add(l, "Kongo-Kinshasa", "Kongo-Kinshasa");
        add(l, AFRIKA, "Kongo-Kinshasa");

        add(l, "Elfenbenskusten", "Elfenbenskusten");
        add(l, AFRIKA, "Elfenbenskusten");
        add(l, "Elfenbenskusten", "Ivory Coast");
        add(l, AFRIKA, "Ivory Coast");
        add(l, "Elfenbenskusten", "Ivorian");
        add(l, AFRIKA, "Ivorian");

        add(l, "Uganda", "Uganda");
        add(l, AFRIKA, "Uganda");

        add(l, "Kamerun", "Kamerun");
        add(l, AFRIKA, "Kamerun");
        add(l, "Kamerun", "Cameroon");
        add(l, AFRIKA, "Cameroon");

        add(l, "Albanien", "Albanien");
        add(l, "Albanien", "Albania");

        add(l, "Barcelona", "Barcelona");

        add(l, "Bosnien", "Bosnien");
        add(l, "Bosnien", "Bosnia");

        add(l, "Belgien", "Belgien");
        add(l, "Belgien", "Belgium");


        add(l, "Brasilien", "Brasilien");
        add(l, "Brasilien", "Brazil");

        add(l, "Chile", "Chile");

        add(l, "Egypten", "Egypt");


        add(l, "Irak", "Iraq");
        add(l, "Irak", "Irak");
        add(l, "Irak", "Mosul");

        add(l, "Indien", "Indien");
        add(l, "Indien", "India");

        add(l, "Oman", "Oman");

        add(l, "Nepal", "Nepal");

        add(l, "Syrien", "Syrien");
        add(l, "Syrien", "Syria");
        add(l, "Syrien", "[Ss]yrisk");
        add(l, "Syrien", "Syrier");
        add(l, "Syrien", "Damascus");
        add(l, "Syrien", "Damaskus");
        add(l, "Raqqa", "Raqqa");
        add(l, "Syrien", "Raqqa");

        add(l, "Venezuela", "Venezuela");
        add(l, "Venezuela", "Maduro");
        add(l, "Nicolás Maduro", "Maduro");

        add(l, "Nordkorea", "Nordkorea");
        add(l, "Nordkorea", "North Korea");
        add(l, "Nordkorea", "N Korea");
        add(l, "Nordkorea", "Kim Jong-un");
        add(l, "Kim Jong-un", "Kim Jong-un");

        add(l, "Sydkorea", "Sydkorea");
        add(l, "Sydkorea", "South Korea");
        add(l, "Sydkorea", "Seoul");
        add(l, "Seoul", "Seoul");
        add(l, "Sydkorea", "Moon Jae-in");
        add(l, "Moon Jae-in", "Moon Jae-in");

        add(l, "Myanmar", "Myanmar");
        add(l, "Myanmar", "Burma");
        add(l, "Myanmar", "Aung San Suu Kyi");
        add(l, "Aung San Suu Kyi", "Aung San Suu Kyi");

        add(l, "Iran", "Iran");

        add(l, "Kina", "Kina");
        add(l, "Kina", "China");
        add(l, "Kina", "Xi Jinping");
        add(l, "Kina", "[Kk]ines");
        add(l, "Xi Jinping", "Xi Jinping");

        add(l, "Kuba", "Cuba");
        add(l, "Kuba", "Kuba");

        add(l, "Spanien", "Spanien");
        add(l, "Spanien", "Spain");
        add(l, "Spanien", "Spanish");
        add(l, "Spanien", "Barcelona");

        add(l, "Italien", "Italien");
        add(l, "Italien", "Itali");
        add(l, "Italien", "Italy");

        add(l, "Sierra Leone", "Sierra Leone");
        add(l, AFRIKA, "Sierra Leone");
        add(l, "Sierra Leone", "Freetown");
        add(l, AFRIKA, "Freetown");

        add(l, "Singapore", "Singapore");

        add(l, "Hong Kong", "Hong Kong");

        add(l, "Jemen", "Jemen");
        add(l, "Jemen", "Yemen");

        add(l, "Kenya", "Kenya");

        add(l, "Kuwait", "Kuwait");

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

        add(l, "Tjeckien", "Tjeckien");
        add(l, "Tjeckien", "Tjeckisk");
        add(l, "Tjeckien", "Czech");

        add(l, "USA", "USA");
        add(l, "USA", "US");
        add(l, "USA", "FBI");
        add(l, "USA", "U\\.S\\.");
        add(l, "USA", "Orlando");
        add(l, "USA", "California");

        add(l, "Libanon", "Libanon");
        add(l, "Libanon", "Libanes");
        add(l, "Libanon", "Lebanon");
        add(l, "Libanon", "Lebanese");

        add(l, "EU", "EU");
        add(l, "EU", "E\\.U\\.");
        add(l, "EU", "European Union");

        add(l, "United Nations", "United Nations");
        add(l, "United Nations", "U\\.N\\.");

        add(l, "Europa", "Europa");
        add(l, "Europa", "Europe");

        add(l, "Finland", "Finland");

        add(l, "Grekland", "Grekland");
        add(l, "Grekland", "Greek");
        add(l, "Grekland", "Greece");
        add(l, "Grekland", "Grek");

        add(l, "Israel", "Israel");
        add(l, "Israel", "West Bank");

        add(l, "Malmö", "Malmö");

        add(l, "Mexiko", "Mexico");
        add(l, "Mexiko", "Mexiko");
        add(l, "Mexiko", "Mexican");
        add(l, "Tijuana", "Tijuana");
        add(l, "Mexiko", "Tijuana");

        add(l, "Nigeria", "Nigeria");

        add(l, "Norge", "Norge");
        add(l, "Norge", "Norway");
        add(l, "Norge", "[Nn]orska");

        add(l, "Qatar", "Qatar");

        add(l, "Dubai", "Dubai");
        add(l, "United Arab Emirates", "Dubai");
        add(l, "United Arab Emirates", "United Arab Emirates");
        add(l, "United Arab Emirates", "UAE");

        add(l, "Saudiarabien", "Saudiarabien");
        add(l, "Saudiarabien", "Saudi Arabia");

        add(l, SYDAFRIKA, SYDAFRIKA);
        add(l, SYDAFRIKA, "South Africa");
        add(l, AFRIKA, "South Africa");
        add(l, AFRIKA, SYDAFRIKA);

        add(l, "Tanzania", "Tanzania");
        add(l, AFRIKA, "Tanzania");

        add(l, "Sydsudan", "Sydsudan");
        add(l, "Sydsudan", "South Sudan");

        add(l, "Taiwan", "Taiwan");

        add(l, "Turkiet", "Turkiet");
        add(l, "Turkiet", "Turkey");
        add(l, "Turkiet", "Turkish");
        add(l, "Turkiet", "Recep Tayyip Erdogan");
        add(l, "Turkiet", "Istanbul");

        add(l, "Ukraina", "Ukrain");
        add(l, "Ukraina", "Kiev");

        add(l, "Österrike", "Österrike");
        add(l, "Österrike", "Austria");

        add(l, "Schweiz", "Schweiz");

        add(l, "Ungern", "Ungern");
        add(l, "Ungern", "Hungary");

        add(l, "Portugal", "Portugal");

        add(l, "Japan", "Japan");

        add(l, "Argentina", "Argentin");

        add(l, RYSSLAND, RYSSLAND);
        add(l, RYSSLAND, "Russia");
        add(l, RYSSLAND, "[Rr]ysk");
        add(l, RYSSLAND, "Moskva");
        add(l, RYSSLAND, "Kreml");

        add(l, "Kanada", "Kanada");
        add(l, "Kanada", "Canada");
        add(l, "Kanada", "Canadian");
        add(l, "Kanada", "Kanaden");

        add(l, "Palestina", "Palestin");

        add(l, "Afghanistan", "Afghan");
        add(l, "Afghanistan", "Afganistan");
        add(l, "Afghanistan", "Kabul");

        add(l, "Pakistan", "Pakistan");

        add(l, "Baloch", "Baloch");
        add(l, "Pakistan", "Baloch");
        add(l, "Afghanistan", "Baloch");


        add(l, FILIPPINERNA, FILIPPINERNA);
        add(l, FILIPPINERNA, "Manila");
        add(l, FILIPPINERNA, "Philippines");

        add(l, IRLAND, IRLAND);
        add(l, IRLAND, "Irish");
        add(l, IRLAND, "Ireland");

        add(l, "Tibet", "Tibet");

        add(l, "Montenegro", "Montenegro");

        add(l, STORBRITANNIEN, STORBRITANNIEN);
        add(l, STORBRITANNIEN, "Manchester");
        add(l, "Manchester", "Manchester");
        add(l, STORBRITANNIEN, "London");
        add(l, STORBRITANNIEN, "England");
        add(l, STORBRITANNIEN, "Britain");
        add(l, STORBRITANNIEN, "Scotland");
        add(l, STORBRITANNIEN, "British");

        addSweden(l);
    }

    private static void addSweden(Set<SubjectRule> l) {

        add(l, "Gröna Lund", "Gröna Lund");
        add(l, STOCKHOLM, "Gröna Lund");
        add(l, INRIKES, "Gröna Lund");

        add(l, "Eskilstuna", "Eskilstuna");
        add(l, INRIKES, "Eskilstuna");

        add(l, "Bromma flygplats", "Bromma flygplats");
        add(l, INRIKES, "Bromma flygplats");

        add(l, "Kristianstad", "Kristianstad");
        add(l, INRIKES, "Kristianstad");

        add(l, "Arboga", "Arboga");
        add(l, INRIKES, "Arboga");

        add(l, STOCKHOLM, "Stockholm");
        add(l, INRIKES, "Stockholm");
        add(l, STOCKHOLM, "sthlm");
        add(l, INRIKES, "sthlm");

        add(l, INRIKES, "Sverige");
        add(l, INRIKES, "Swedish");
        add(l, INRIKES, "Swede");

        add(l, "Umeå", "Umeå");
        add(l, INRIKES, "Umeå");

        add(l, "Liseberg", "Liseberg");
        add(l, GÖTEBORG, "Liseberg");
        add(l, INRIKES, "Liseberg");

        add(l, GÖTEBORG, GÖTEBORG);
        add(l, GÖTEBORG, "Gothenburg");

        add(l, "Strömsund", "Strömsund");
        add(l, INRIKES, "Strömsund");

        add(l, "Norrköping", "Norrköping");
        add(l, INRIKES, "Norrköping");

        add(l, "Östersund", "Östersund");
        add(l, INRIKES, "Östersund");

        add(l, "Västervik", "Västervik");
        add(l, INRIKES, "Västervik");

        add(l, "Katrineholm", "Katrineholm");
        add(l, INRIKES, "Katrineholm");

        add(l, "Uppsala", "Uppsala");
        add(l, INRIKES, "Uppsala");

        add(l, "Linköping", "Linköping");
        add(l, INRIKES, "Linköping");
    }
}
