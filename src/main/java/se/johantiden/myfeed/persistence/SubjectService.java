package se.johantiden.myfeed.persistence;

import org.apache.bcel.generic.NEW;
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
    public static final String ENTERTAINMENT = "Entertainment";

    public static final String UNMATCHED_TAB = "Unmatched";
    public static final Subject UNMATCHED = new Subject("Unmatched", UNMATCHED_TAB, "Unmatched", d -> true);
    public static final boolean REMOVE_BAD = true;
    private static final boolean CATCH_UNMATCHED_PER_FEED = true;

    public static final Predicate<Document> USA = has("usa").or(hasCaseSensitive("US")).or(hasCaseSensitive("U.S.")).or(has("trump")).or(has("senate")).or(has("congress")).or(hasCaseSensitive("FBI"));
    public static final Predicate<Document> RUSSIA = has("russia").or(has("ryssland")).or(has("rysk")).or(has("putin"));
    public static final Predicate<Document> SYRIA = has("syria").or(has("raqqa")).or(has("syrien"));
    public static final Predicate<Document> EGYPT = has("egypt");
    public static final Predicate<Document> LIBYA = has("libya");
    public static final Predicate<Document> NORTH_KOREA = has("north korea").or(has("nordkorea"));
    public static final Predicate<Document> ISRAEL = has("israel");
    public static final Predicate<Document> PALESTINE = has("palestine").or(has("palestinian"));
    public static final Predicate<Document> SWEDEN = has("sweden").or(has("swedish"));
    public static final Predicate<Document> DAESH = hasCaseSensitive("IS ");
    public static final Predicate<Document> BIZ_CATEGORIES = anyCategoryEquals("näringsliv").or(anyCategoryEquals("ekonomi"));
    public static final Predicate<Document> CULTURE_CATEGORIES = anyCategoryEquals("film").or(anyCategoryEquals("kultur-noje")).or(anyCategoryEquals("kultur")).or(anyCategoryEquals("movies").or(anyCategoryEquals("dnbok")));
    public static final Predicate<Document> SPORT_CATEGORY = anyCategoryEquals("sport");

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
        add("I dag (experimental)", BAD, startsWithCaseSensitive("I dag"));
        add("Här är", BAD, has("här är"));
        add("Surveys", BAD, startsWith("enkät"));
        add("BlackPeopleTwitter", BAD, anyCategoryEquals("blackpeopletwitter"));
        add("OldSchoolCool", BAD, anyCategoryEquals("oldschoolcool"));
        add("quityourbullshit", BAD, anyCategoryEquals("quityourbullshit"));
        add("Advice Animals", BAD, anyCategoryEquals("adviceanimals"));
        add("News as Video :(", BAD, isFromFeed("Svenska Dagbladet").and(startsWithCaseSensitive("VIDEO")));
        add("Turism", BAD, has("turist"));
        add("Finnish", BAD, d -> d.pageUrl.contains("uutiset"));
        add("<-- Number of people that X (vote bait)", BAD, has("-- number").or(has("--number")));
        add("Blondinbella", BAD, has("blondinbella"));
        add("Paywalled", BAD, d -> d.isPaywalled);

        add("Ledare", NEWS, anyCategoryEquals("ledare").or(has("your morning briefing")));
        add("Väder", NEWS, has("monsoon").or(has("snow")).or(has("temperatur")).or(has("minus").and(has("grader")).or(has("storm"))));
        add("Katastrofer", NEWS, has("flood").or(has("skogsbrand")).or(has("earthquake")).or(has("jordbävning")));
        add("AskReddit", FUN, isFromFeed("Reddit::r/AskReddit"));
        add("Mildly Interesting", FUN, anyCategoryEquals("mildlyinteresting"));
        add("Pics", FUN, anyCategoryEquals("pics"));
        add("Videos", FUN, anyCategoryEquals("gifs").or(anyCategoryEquals("videos")));
        add("Gaming", FUN, anyCategoryEquals("opposable thumbs").or(anyCategoryEquals("gaming")));
        add("Funny", FUN, anyCategoryEquals("funny"));
        add("9gag", FUN, isFromFeed("9gag"));
        add("Today I Learned", FUN, anyCategoryEquals("todayilearned"));
        add("Nottheonion", FUN, anyCategoryEquals("nottheonion"));
        add("Aww", FUN, anyCategoryEquals("aww"));
        add("Cringe", FUN, anyCategoryEquals("cringe"));
        add("Oddly Satisfying", FUN, anyCategoryEquals("oddlysatisfying"));

        add("Fotboll", SPORT, has("football").or(has("soccer")).or(has("fotboll")).or(hasCaseSensitive("U21")).or(has("allsvensk")).or(has("zlatan")));
        add("Hockey", SPORT, hasCaseSensitive("SHL").or(hasCaseSensitive("NHL")).or(has("tre kronor")));
        add("Golf", SPORT, SPORT_CATEGORY.and(has("golf")));
        add("Volleyboll", SPORT, SPORT_CATEGORY.and(has("volleyboll")));
        add("Sport", SPORT, SPORT_CATEGORY.or(has("lagkapten")).or(has("guif")));

        add("Serier", FUN, isFromFeed("xkcd"));
        add("Security", TECH, anyCategoryEquals("botnet").or(has("keylogg")).or(isFromFeed("HackerNews").and(has("exploit"))));
        add("Tech Meetups", TECH, has("ars live"));
        add("Show HN", TECH, startsWithCaseSensitive("Show HN:"));
        add("Ask HN", TECH, startsWithCaseSensitive("Ask HN:"));
        add("Klimatet", NEWS, has("climate").or(has("klimat")).or(has("fjäril")));
        add("Vaccination", NEWS, has("vaccin"));
        add("Blashpemy", NEWS, has("blasphemy"));
        add("Brexit", NEWS, has("brexit"));
        add("NATO", NEWS, has("nato"));
        add("Vinster i välfärden", NEWS, has("reepalu").or(has("vinsst").and(has("välfärd"))));
        add("Drottninggatan", NEWS, has("stockholm").and(has("terror")).or(has("drottninggatan")));
        add("Invandring", NEWS, SWEDEN.and(has("immigra").or(has("asylum")).or(has("refugee"))).or(has("invandring")).or(has("asylsökande")).or(has("nyanlända")).or(has("flykting")));
        add("Skatteverket", NEWS, has("skatteverket"));
        add("Arbete", BIZ, has("strejk").or(has("avtal").and(has("bemanning"))).or(has("arbetsmiljö")));
        add("Wall Street", BIZ, has("wall street"));
        add("Stockholmsbörsen", BIZ, has("stockholmsbörsen"));
        add("Opinionsmätningar", NEWS, has("opinionsmätning"));
        add("Trafik", NEWS, has("pendeltåg").or(has("fjärrtåg")).or(has("trafik")).or(hasCaseSensitive("SJ").and(has("tåg"))).or(has("strömlöst tåg")));
        add("Terrorism", NEWS, DAESH.or(has("taliban")).or(has("terror")).or(has("jihad")));

        add("Eurovision", CULTURE, has("eurovision").or(has("schlager")));

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

        add("Rapporter", BIZ, has("report").and(has("quarter")).or(BIZ_CATEGORIES.and(has("rapport"))));
        add("Film", CULTURE, anyCategoryEquals("film").or(anyCategoryEquals("movies")).or(CULTURE_CATEGORIES.and(hasCaseSensitive("Cannes").or(has("film")))).or(has("documentar")).or(has("dokumentär")));
        add("Litteratur", CULTURE, anyCategoryEquals("litteratur").or(anyCategoryEquals("dnbok")));
        add("Musik", CULTURE, anyCategoryEquals("musik").or(CULTURE_CATEGORIES.and(has("hiphop").or(has("jazz")).or(has("artist")).or(has("bandet")).or(has("låtar")))));
        add("Konst", CULTURE, CULTURE_CATEGORIES.and(has("konstnär")));
        add("History", CULTURE, has("1600").or(has("1700")).or(has("1800")));
        add("Television", CULTURE, anyCategoryEquals("television").or(CULTURE_CATEGORIES.and(has("avsnitt"))));
        add("Svenska Dagbladet - Kultur", unmatchedOr(CULTURE), isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("kultur")));
        add("Dagens Nyheter - Kultur & Nöje", unmatchedOr(CULTURE), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("kultur-noje")));
        add("Kultur", unmatchedOr(CULTURE), CULTURE_CATEGORIES);

        // No specific topics matched. Cluster by country instead.

        addCountriesCross();
        addCountries();

        // Only sweden should be left at this point. Create some very general topics.

        add("Finans", BIZ, BIZ_CATEGORIES.and(has("aktie")));
        add("Ekonomi", BIZ, BIZ_CATEGORIES.and(has("inflation").or(has("räntepolitik")).or(has("utlåning")).or(has("valuta")).or(has("pris")).or(has("kritiserade bolag")).or(has("pension")).or(hasCaseSensitive("NIX"))));
        add("Politik", NEWS, has("minister").or(has("regering")).or(has("ungdomsförbund")).or(has("kommunalråd")).or(has("partistämma")).or(SWEDEN.and(has("democrats").or(has("centre")))));
        add("Brott", NEWS,
                has("prison").or(has("mördare")).or(has("mord")).or(has("bråk")).or(has("våldtäkt"))
                        .or(has("polisjakt")).or(has("polisinsats")).or(has("förhör")).or(has("död"))
                        .or(has("övergrepp")).or(has("ofreda")).or(has("misshand")).or(has("shooter"))
                        .or(has("fängelse")).or(has("våld mot tjänsteman")).or(has("permission").and(has("avvek")))
                        .or(has("sköt").and(has("ihjäl"))).or(has("hotade")).or(has("narkotika")));
        add("Olyckor", NEWS, has("olycka").or(has("skada")).or(has("krock")).or(has("påkörd")).or(has("störta")).or(has("kört in i")));
        add("Sjukvård & Hälsa", NEWS,
                   SWEDEN.and(has("hospital")).or(has("sjukvård")).or(has("läkar")).or(has("bakterie"))
                           .or(has("bacteria")).or(has("smitta")).or(has("epidemi")).or(has("sjukhus"))
                           .or(has("läkemedel")));
        add("Skolan", NEWS, has("skola").or(has("skolres")));
        add("Inrikes", NEWS, has("grundvatten"));
        add("Europa", BIZ, BIZ_CATEGORIES.and(hasCaseSensitive("EU")));

        // We failed to classify, Just cluster by source.

        add("HackerNews", TECH, isFromFeed("HackerNews"));

        add("Al Jazeera", unmatchedOr(NEWS), isFromFeed("Al Jazeera"));
        add("TheLocal", unmatchedOr(NEWS), isFromFeed("TheLocal"));
        add("New York Times", unmatchedOr(NEWS), isFromFeed("New York Times :: World"));
        add("Ars Technica", unmatchedOr(TECH), isFromFeed("Ars Technica"));
        add("Slashdot", unmatchedOr(TECH), isFromFeed("Slashdot"));
        add("Reddit::top", unmatchedOr(FUN), isFromFeed("Reddit::top"));
        add("Reddit::r/all", unmatchedOr(FUN), isFromFeed("Reddit::r/all"));
        add("Reddit::r/worldnews", unmatchedOr(NEWS), isFromFeed("Reddit::r/worldnews"));
        add("Dagens Nyheter - Nyheter", unmatchedOr(NEWS), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("nyheter")));
        add("Dagens Nyheter - Ekonomi", unmatchedOr(NEWS), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("ekonomi")));
        add("Dagens Nyheter - Stockholm", unmatchedOr(NEWS), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("sthlm")));
        add("Dagens Nyheter - Debatt", unmatchedOr(NEWS), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("debatt")));
        add("Dagens Nyheter - Insidan", unmatchedOr(ENTERTAINMENT), isFromFeed("Dagens Nyheter").and(anyCategoryEquals("insidan")));
        add("Svenska Dagbladet - Världen", unmatchedOr(NEWS), isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("världen")));
        add("Svenska Dagbladet - Sverige", unmatchedOr(NEWS), isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("sverige")));
        add("Svenska Dagbladet - Näringsliv", unmatchedOr(BIZ), isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("näringsliv")));
        add("SVT Nyheter", unmatchedOr(NEWS), isFromFeed("SVT Nyheter"));

        subjects.add(UNMATCHED);
    }

    private String unmatchedOr(String tab) {
        return CATCH_UNMATCHED_PER_FEED ? tab : UNMATCHED_TAB;
    }

    private void addCountriesCross() {
        add("Egypt and Libya", NEWS, EGYPT.and(LIBYA));
        add("USA and Russia", NEWS, USA.and(RUSSIA));
        add("USA and Syria", NEWS, USA.and(SYRIA));
        add("USA and North Korea", NEWS, USA.and(NORTH_KOREA));
        add("Israel and Palestine", NEWS, ISRAEL.and(PALESTINE));
    }

    private void addCountries() {
        add("Australia", NEWS, has("australia"));
        add("Bangladesh", NEWS, has("bangladesh").or(has("nooruddin ahmed")));
        add("Bosnia", NEWS, has("bosnia"));
        add("Brazil", NEWS, has("brazil"));
        add("Canada", NEWS, has("canada").or(has("kanada")).or(has("british columbia")));
        add("China", NEWS, has("china"));
        add("Colombia", NEWS, has("colombia").and(has("district of col").negate()));
        add("Egypt", NEWS, EGYPT);
        add("France", NEWS, has("france").or(has("french")).or(has("macron")).or(has("le pen")).or(has("fransk")));
        add("Germany", NEWS, has("germany").or(has("tysk")));
        add("Great Britain", NEWS, hasCaseSensitive("UK").or(has("britain")).or(has("british")).or(has("england")));
        add("India", NEWS, has("india ").or(anyCategoryEquals("india")));
        add("Iran", NEWS, has("iran"));
        add("Israel", NEWS, ISRAEL);
        add("Kambodja", NEWS, has("kambodja").or(has("hun sen")));
        add("Kosovo", NEWS, has("kosovo"));
        add("Libya", NEWS, LIBYA);
        add("Myanmar", NEWS, has("myanmar").or(has("burma")));
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
        add("Venezuela", NEWS, has("venezuela"));
        add("Yemen", NEWS, has("yemen"));
        add("Zimbabwe", NEWS, has("zimbabwe").or(has("mugabe")));
    }


    public List<Subject> getAllSubjects() {
        return subjects;
    }

}
