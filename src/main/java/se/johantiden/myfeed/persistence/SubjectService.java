package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.controller.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static se.johantiden.myfeed.util.DocumentPredicates.anyCategoryEquals;
import static se.johantiden.myfeed.util.DocumentPredicates.has;
import static se.johantiden.myfeed.util.DocumentPredicates.hasCaseSensitive;
import static se.johantiden.myfeed.util.DocumentPredicates.isFromFeed;
import static se.johantiden.myfeed.util.DocumentPredicates.startsWith;
import static se.johantiden.myfeed.util.DocumentPredicates.startsWithCaseSensitive;

public class SubjectService {

    public static final String NEWS = "News";
    public static final String BIZ = "Biz";
    public static final String TECH = "Tech";
    public static final String SPORT = "Sport";
    public static final String BAD = "Bad";
    public static final String ERROR = "Errors";
    public static final String FUN = "Fun";
    public static final String CULTURE = "Culture";

    public static final String UNMATCHED_TAB = "Unmatched";
    public static final Subject UNMATCHED = new Subject("Unmatched", UNMATCHED_TAB, "Unmatched", d -> true);
    public static final boolean REMOVE_BAD = false;
    private static final boolean CATCH_UNMATCHED_PER_FEED = false;

    public static final Predicate<Document> USA = has("usa").or(hasCaseSensitive("US")).or(has("trump")).or(has("senate")).or(has("congress")).or(hasCaseSensitive("FBI"));
    public static final Predicate<Document> RUSSIA = has("russia").or(has("ryssland")).or(has("rysk")).or(has("putin"));
    public static final Predicate<Document> SYRIA = has("syria").or(has("raqqa")).or(has("syrien"));
    public static final Predicate<Document> EGYPT = has("egypt");
    public static final Predicate<Document> LIBYA = has("libya");
    public static final Predicate<Document> NORTH_KOREA = has("north korea").or(has("nordkorea"));
    public static final Predicate<Document> ISRAEL = has("israel");
    public static final Predicate<Document> PALESTINE = has("palestine");
    public static final Predicate<Document> SWEDEN = has("sweden").or(has("swedish"));
    public static final Predicate<Document> DAESH = hasCaseSensitive("IS ");
    public static final Predicate<Document> BIZ_CATEGORIES = anyCategoryEquals("näringsliv").or(anyCategoryEquals("ekonomi"));
    public static final Predicate<Document> CULTURE_CATEGORIES = anyCategoryEquals("film").or(anyCategoryEquals("kultur-noje")).or(anyCategoryEquals("kultur")).or(has("movies"));

    private final List<Subject> subjects;

    public SubjectService() {
        subjects = new ArrayList<>();
        addSubjects();
    }

    public Subject getSubjectFor(Document document) {
        return getAllSubjects().stream()
                       .filter(s -> s.test(document))
                       .findFirst()
                       .orElseThrow(() -> new IllegalStateException("Could not match document to any subject."));
    }

    private boolean add(String title, String tab, Predicate<Document> predicate) {
        return subjects.add(new Subject(title, title+tab, tab, predicate));
    }

    private void addSubjects() {
        add("encoding", ERROR, has("&#").and(has(";")));
        add("webb-tv", BAD, has("webb-tv"));
        add("mat-dryck", BAD, has("mat-dryck"));
        add("Al Jazeera - NewsGrid", BAD, hasCaseSensitive("NewsGrid"));
        add("TheLocal (Non-news)", BAD, startsWith("these are").or(startsWithCaseSensitive("WATCH")).or(startsWith("this")).or(has("book")).or(has("rate")));
        add("HackerNews - Hiring", BAD, isFromFeed("HackerNews").and(has("hiring")));
        add("Cars", BAD, anyCategoryEquals("motor").or(anyCategoryEquals("cars technica")));
        add("Årets (experimental)", BAD, startsWithCaseSensitive("Årets"));
        add("Här är", BAD, startsWith("här är"));
        add("Surveys", BAD, startsWith("enkät"));
        add("BlackPeopleTwitter", BAD, anyCategoryEquals("blackpeopletwitter"));
        add("OldSchoolCool", BAD, anyCategoryEquals("oldschoolcool"));
        add("quityourbullshit", BAD, anyCategoryEquals("quityourbullshit"));
        add("Paywalled", BAD, d -> d.isPaywalled);

        add("Editorials", NEWS, anyCategoryEquals("ledare").or(has("your morning briefing")));
        add("Weather", NEWS, has("monsoon").or(has("snow")).or(has("temperatur")).or(has("minus").and(has("grader"))));
        add("Disasters", NEWS, has("flood"));
        add("AskReddit", FUN, isFromFeed("Reddit::r/AskReddit"));
        add("Mildly Interesting", FUN, anyCategoryEquals("mildlyinteresting"));
        add("Pics", FUN, anyCategoryEquals("pics"));
        add("Videos", FUN, anyCategoryEquals("gifs").or(anyCategoryEquals("videos")));
        add("Gaming", FUN, anyCategoryEquals("opposable thumbs").or(anyCategoryEquals("gaming")));
        add("Television", FUN, anyCategoryEquals("television"));
        add("Funny", FUN, anyCategoryEquals("funny"));
        add("Today I Learned", FUN, anyCategoryEquals("todayilearned"));
        add("Nottheonion", FUN, anyCategoryEquals("nottheonion"));
        add("Aww", FUN, anyCategoryEquals("aww"));
        add("Cringe", FUN, anyCategoryEquals("cringe"));

        add("Football", SPORT, has("football").or(has("soccer")).or(has("fotboll")));
        add("Sport", SPORT, anyCategoryEquals("sport"));

        add("Comics", FUN, isFromFeed("xkcd"));
        add("Tech Meetups", TECH, has("ars live"));
        add("Show HackerNews", TECH, startsWithCaseSensitive("Show HN:"));
        add("Climate", NEWS, has("climate").or(has("klimat")).or(has("fjäril")));
        add("Vaccination", NEWS, has("vaccin"));
        add("Blashpemy", NEWS, has("blasphemy"));
        add("Brexit", NEWS, has("brexit"));
        add("NATO", NEWS, has("nato"));
        add("Vinster i välfärden", NEWS, has("reepalu").or(has("vinsst").and(has("välfärd"))));
        add("Drottninggatan", NEWS, has("stockholm").and(has("terror")).or(has("drottninggatan")));
        add("Invandringspolitik", NEWS, SWEDEN.and(has("immigra").or(has("asylum")).or(has("refugee"))).or(has("invandring")).or(has("asylsökande")).or(has("nyanlända")));
        add("Skatteverket", NEWS, has("skatteverket"));
        add("Health", NEWS, SWEDEN.and(has("hospital")).or(has("sjukvård")).or(has("läkar")).or(has("bakterie")).or(has("bacteria")).or(has("smitta")));
        add("Union", BIZ, has("strejk").or(has("avtal").and(has("bemanning"))));
        add("Wall Street", BIZ, has("wall street"));
        add("Europe", BIZ, BIZ_CATEGORIES.and(hasCaseSensitive("EU")));
        add("Stockholmsbörsen", BIZ, has("stockholmsbörsen"));
        add("Polls", NEWS, has("opinionsmätning"));
        add("Traffic", NEWS, has("pendeltåg").or(has("fjärrtåg")).or(has("trafik")).or(hasCaseSensitive("SJ").and(has("tåg"))).or(has("strömlöst tåg")));
        add("Skolan", NEWS, has("skola"));
        add("Terrorism", NEWS, DAESH.or(has("taliban")).or(has("terror")));

        add("Eurovision", CULTURE, has("eurovision").or(has("schlager")));
        add("Documentaries", CULTURE, has("documentar").or(has("dokumentär")));
        add("History", CULTURE, has("1600").or(has("1700")).or(has("1800")));

        add("Self-Driving Cars", TECH, has("self-driving"));
        add("Microsoft", TECH, has("microsoft"));
        add("Apple", TECH, has("apple"));
        add("Samsung", TECH, has("samsung"));
        add("Tesla", TECH, has("tesla"));
        add("Facebook", TECH, has("facebook"));
        add("Google", TECH, has("google"));
        add("Snapchat", TECH, has("snapchat"));
        add("Netflix", TECH, has("netflix"));
        add("Uber", TECH, hasCaseSensitive("Uber"));
        add("Gear & Gadgets", TECH, anyCategoryEquals("gear & gadgets").or(has("qualcomm")));
        add("Science", TECH, anyCategoryEquals("scientific method"));
        add("Net Neutrality", TECH, has("net neutrality"));
        add("Artificial Intelligence", TECH, anyCategoryEquals("ai"));

        add("Reports", BIZ, has("report").and(has("quarter")).or(BIZ_CATEGORIES.and(has("rapport"))));
        add("Movies", CULTURE, anyCategoryEquals("film").or(anyCategoryEquals("movies")).or(CULTURE_CATEGORIES.and(hasCaseSensitive("Cannes"))));
        add("Books", CULTURE, anyCategoryEquals("litteratur"));
        add("Music", CULTURE, CULTURE_CATEGORIES.and(has("hiphop")));

        addCountriesCross();
        addCountries();

        add("Economy", BIZ, BIZ_CATEGORIES.and(has("inflation").or(has("räntepolitik")).or(has("utlåning")).or(has("valuta")).or(has("pris"))));
        add("Politics", NEWS, has("minister"));
        add("Crime", NEWS, has("prison").or(has("mördare")).or(has("mord")).or(has("bråk")).or(has("våldtäkt")).or(has("polisjakt")).or(has("polisinsats")).or(has("förhör")).or(has("död")).or(has("övergrepp")).or(has("ofreda")).or(has("misshand")).or(has("shooter")).or(has("fängelse")).or(has("våld mot tjänsteman")));
        add("Accidents", NEWS, has("skada").or(has("krock")));

        add("Al Jazeera (Unmatched)", unmatchedOr(NEWS), isFromFeed("Al Jazeera"));
        add("TheLocal (Unmatched)", unmatchedOr(NEWS), isFromFeed("TheLocal"));
        add("New York Times (Unmatched)", unmatchedOr(NEWS), isFromFeed("New York Times :: World"));
        add("Ars Technica (Unmatched)", unmatchedOr(TECH), isFromFeed("Ars Technica"));
        add("Slashdot (Unmatched)", unmatchedOr(TECH), isFromFeed("Slashdot"));
        add("Reddit::top (Unmatched)", unmatchedOr(FUN), isFromFeed("Reddit::top"));
        add("Reddit::r/all (Unmatched)", unmatchedOr(FUN), isFromFeed("Reddit::r/all"));
        add("Reddit::r/worldnews (Unmatched)", unmatchedOr(NEWS), isFromFeed("Reddit::r/worldnews"));
        add("Dagens Nyheter - Nyheter (Unmatched)", unmatchedOr(NEWS), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("nyheter")));
        add("Dagens Nyheter - Ekonomi (Unmatched)", unmatchedOr(NEWS), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("ekonomi")));
        add("Dagens Nyheter - Stockholm (Unmatched)", unmatchedOr(NEWS), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("sthlm")));
        add("Dagens Nyheter - Debatt (Unmatched)", unmatchedOr(NEWS), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("debatt")));
        add("Svenska Dagbladet - Världen (Unmatched)", unmatchedOr(NEWS), isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("världen")));
        add("Svenska Dagbladet - Sverige (Unmatched)", unmatchedOr(NEWS), isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("sverige")));
        add("Svenska Dagbladet - Näringsliv (Unmatched)", unmatchedOr(BIZ), isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("näringsliv")));
        add("SVT Nyheter (Unmatched)", unmatchedOr(NEWS), isFromFeed("SVT Nyheter"));
        add("HackerNews (Unmatched)", unmatchedOr(TECH), isFromFeed("HackerNews"));
        add("Svenska Dagbladet - Kultur (Unmatched)", unmatchedOr(CULTURE), isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("kultur")));
        add("Dagens Nyheter - Kultur & Nöje (Unmatched)", unmatchedOr(CULTURE), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("kultur-noje")));

        subjects.add(UNMATCHED);
    }

    private String unmatchedOr(String tab) {
        return CATCH_UNMATCHED_PER_FEED ? tab : UNMATCHED_TAB;
    }

    private void addCountriesCross() {
        add("Egypt vs Libya", NEWS, EGYPT.and(LIBYA));
        add("USA vs Russia", NEWS, USA.and(RUSSIA));
        add("USA vs Syria", NEWS, USA.and(SYRIA));
        add("USA vs North Korea", NEWS, USA.and(NORTH_KOREA));
        add("Israel vs Palestine", NEWS, ISRAEL.and(PALESTINE));
    }

    private void addCountries() {
        add("Bangladesh", NEWS, has("bangladesh").or(has("nooruddin ahmed")));
        add("Bosnia", NEWS, has("bosnia"));
        add("Brazil", NEWS, has("brazil"));
        add("Canada", NEWS, has("canada").or(has("british columbia")));
        add("China", NEWS, has("china"));
        add("Egypt", NEWS, EGYPT);
        add("France", NEWS, has("france").or(has("french")).or(has("macron")).or(has("le pen")).or(has("fransk")));
        add("Germany", NEWS, has("germany").or(has("tysk")));
        add("Iran", NEWS, has("iran"));
        add("Israel", NEWS, ISRAEL);
        add("Kambodja", NEWS, has("kambodja").or(has("hun sen")));
        add("Kosovo", NEWS, has("kosovo"));
        add("Libya", NEWS, LIBYA);
        add("North Korea", NEWS, NORTH_KOREA);
        add("Norge", NEWS, has("norge").or(has("norsk")));
        add("Palestine", NEWS, PALESTINE);
        add("Poland", NEWS, has("poland"));
        add("Russia", NEWS, RUSSIA);
        add("Slovakia", NEWS, has("slovakia"));
        add("South Korea", NEWS, has("south korea"));
        add("South Africa", NEWS, has("south africa").or(has("stellenbosch")));
        add("Syria", NEWS, SYRIA);
        add("Tunisia", NEWS, has("tunisia"));
        add("USA", NEWS, USA);
        add("Yemen", NEWS, has("yemen"));
    }


    public List<Subject> getAllSubjects() {
        return subjects;
    }

}
