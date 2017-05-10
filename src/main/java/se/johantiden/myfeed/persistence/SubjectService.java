package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.controller.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SubjectService {

    public static final String NEWS = "News";
    public static final String BIZ = "Biz";
    public static final String TECH = "Tech";
    public static final String SPORT = "News";
    public static final String BAD = "Bad";
    public static final String ERROR = "Errors";
    public static final String FUN = "Fun";
    public static final String CULTURE = "Culture";

    public static final boolean REMOVE_BAD = true;
    public static final Subject UNMATCHED = new Subject("Unmatched", "Unmatched", "Unmatched", d -> true);
    private static final boolean CATCH_UNMATCHED_PER_FEED = true;

    public static final Predicate<Document> USA = has("usa").or(hasCaseSensitive("US")).or(has("trump")).or(has("senate")).or(has("congress")).or(hasCaseSensitive("FBI"));
    public static final Predicate<Document> RUSSIA = has("russia").or(has("ryssland")).or(has("rysk")).or(has("putin"));
    public static final Predicate<Document> SYRIA = has("syria").or(has("raqqa")).or(has("syrien"));
    public static final Predicate<Document> EGYPT = has("egypt");
    public static final Predicate<Document> LIBYA = has("libya");
    public static final Predicate<Document> NORTH_KOREA = has("north korea");
    public static final Predicate<Document> ISRAEL = has("israel");
    public static final Predicate<Document> PALESTINE = has("palestine");
    public static final Predicate<Document> SWEDEN = has("sweden").or(has("swedish"));
    public static final Predicate<Document> DAESH = hasCaseSensitive("IS ");

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

    private static Predicate<Document> isFromFeed(String feedName) {
        return d -> d.feed.name.equals(feedName);
    }

    private static Predicate<Document> anyCategoryEquals(String string) {
        if (!string.toLowerCase().equals(string)) {
            throw new IllegalArgumentException("Input must be lower case only!");
        }
        return d -> d.categories.stream().anyMatch(c -> c.name.equalsIgnoreCase(string));
    }

    private boolean add(String title, String tab, Predicate<Document> predicate) {
        return subjects.add(new Subject(title, title+tab, tab, predicate));
    }

    private static Predicate<Document> startsWith(String string) {
        return d -> d.text != null && d.text.toLowerCase().startsWith(string) ||
                            d.title != null && d.title.toLowerCase().startsWith(string);
    }

    private static Predicate<Document> startsWithCaseSensitive(String string) {
        return d -> d.text != null && d.text.startsWith(string) ||
                            d.title != null && d.title.startsWith(string);
    }

    private static Predicate<Document> has(String string) {
        String lowerCase = string.toLowerCase();
        if (!lowerCase.equals(string)) {
            throw new IllegalArgumentException("Input must be lower case only!");
        }
        return d -> d.text != null && d.text.toLowerCase().contains(string) ||
                            d.title != null && d.title.toLowerCase().contains(string) ||
                            d.categories.stream().anyMatch(c -> c.name.toLowerCase().contains(string));
    }

    private static Predicate<Document> hasCaseSensitive(String string) {
        return d -> d.text != null && d.text.contains(string) ||
                            d.title != null && d.title.contains(string) ||
                            d.categories.stream().anyMatch(c -> c.name.contains(string));
    }

    private void addSubjects() {
        add("encoding", ERROR, has("&#38;"));
        add("webb-tv", BAD, has("webb-tv"));
        add("Al Jazeera - NewsGrid", BAD, hasCaseSensitive("NewsGrid"));
        add("TheLocal (Clickbaity)", BAD, startsWith("these are").or(startsWithCaseSensitive("WATCH")).or(startsWith("this")));
        add("HackerNews - Hiring", BAD, isFromFeed("HackerNews").and(has("hiring")));
        add("Cars", BAD, anyCategoryEquals("motor").or(anyCategoryEquals("cars technica")));
        add("Årets (experimental)", BAD, has("årets"));
        add("Här är", BAD, startsWith("här är"));
        add("Surveys", BAD, startsWith("enkät"));
        add("BlackPeopleTwitter", BAD, anyCategoryEquals("blackpeopletwitter"));
        add("OldSchoolCool", BAD, anyCategoryEquals("oldschoolcool"));
        add("quityourbullshit", BAD, anyCategoryEquals("quityourbullshit"));

        add("Editorials", NEWS, anyCategoryEquals("ledare").or(has("your morning briefing")));
        add("Weather", NEWS, has("monsoon").or(has("snow")));
        add("Disasters", NEWS, has("flood"));
        add("AskReddit", FUN, isFromFeed("Reddit::r/AskReddit"));
        add("Mildly Interesting", FUN, anyCategoryEquals("mildlyinteresting"));
        add("Pics", FUN, anyCategoryEquals("pics"));
        add("Gifs", FUN, anyCategoryEquals("gifs"));
        add("Gaming", FUN, anyCategoryEquals("opposable thumbs").or(anyCategoryEquals("gaming")));
        add("Television", FUN, anyCategoryEquals("television"));
        add("Funny", FUN, anyCategoryEquals("funny"));
        add("Today I Learned", FUN, anyCategoryEquals("todayilearned"));
        add("Nottheonion", FUN, anyCategoryEquals("nottheonion"));
        add("Aww", FUN, anyCategoryEquals("aww"));

        add("Comics", FUN, isFromFeed("xkcd"));
        add("Tech Meetups", TECH, has("ars live"));
        add("Show HackerNews", TECH, startsWithCaseSensitive("Show HN:"));
        add("Climate", NEWS, has("climate").or(has("klimat")));
        add("Vaccination", NEWS, has("vaccin"));
        add("Blashpemy", NEWS, has("blasphemy"));
        add("Brexit", NEWS, has("brexit"));
        add("NATO", NEWS, has("nato"));
        add("Vinster i välfärden", NEWS, has("reepalu").or(has("vinsst").and(has("välfärd"))));
        add("Drottninggatan", NEWS, has("stockholm").and(has("terror")).or(has("drottninggatan")));
        add("Invandringspolitik", NEWS, SWEDEN.and(has("immigra").or(has("asylum")).or(has("refugee"))).or(has("invandring")).or(has("asylsökande")).or(has("nyanlända")));
        add("Skatteverket", NEWS, has("skatteverket"));
        add("Terrorism", NEWS, DAESH.or(has("taliban")).or(has("terror")));
        add("Crime", NEWS, has("prison").or(has("mördare")).or(has("mord")).or(has("bråk")).or(has("våldtäkt")).or(has("polisjakt")).or(has("polisinsats")).or(has("förhör")).or(has("död")));
        add("Sjukvård", NEWS, SWEDEN.and(has("hospital")).or(has("sjukvård")).or(has("läkar")));
        add("Union", NEWS, has("strejk").or(has("avtal").and(has("bemanning"))));
        add("Sport", SPORT, anyCategoryEquals("sport"));
        add("Football", SPORT, has("football").or(has("soccer")));
        add("Wall Street", BIZ, has("wall street"));
        add("Stockholmsbörsen", BIZ, has("stockholmsbörsen"));
        add("Polls", NEWS, has("opinionsmätning"));
        add("Traffic", NEWS, has("pendeltåg").or(has("fjärrtåg")).or(has("tågtrafik")));
        add("Politics", NEWS, has("minister"));
        add("Skolan", NEWS, has("skola"));

        add("Self-Driving Cars", TECH, has("self-driving"));
        add("Microsoft", TECH, has("microsoft"));
        add("Apple", TECH, has("apple"));
        add("Samsung", TECH, has("samsung"));
        add("Tesla", TECH, has("tesla"));
        add("Facebook", TECH, has("facebook"));
        add("Google", TECH, has("google"));
        add("Snapchat", TECH, has("snapchat"));
        add("Gear & Gadgets", TECH, anyCategoryEquals("gear & gadgets").or(has("qualcomm")));
        add("Science", TECH, anyCategoryEquals("scientific method"));
        add("Net Neutrality", TECH, has("net neutrality"));
        add("Artificial Intelligence", TECH, anyCategoryEquals("ai"));

        add("Reports", BIZ, has("report").and(has("quarter")).or(isFromFeed("ekonomi").or(isFromFeed("näringsliv")).and(has("rapport"))));

        addCountriesCross();
        addCountries();

        if (CATCH_UNMATCHED_PER_FEED) {
            add("Al Jazeera (Unmatched)", NEWS, isFromFeed("Al Jazeera"));
            add("TheLocal (Unmatched)", NEWS, isFromFeed("TheLocal"));
            add("New York Times (Unmatched)", NEWS, isFromFeed("New York Times :: World"));
            add("Ars Technica (Unmatched)", TECH, isFromFeed("Ars Technica"));
            add("Slashdot (Unmatched)", TECH, isFromFeed("Slashdot"));
            add("Reddit::top (Unmatched)", FUN, isFromFeed("Reddit::top"));
            add("Reddit::r/all (Unmatched)", FUN, isFromFeed("Reddit::r/all"));
            add("Reddit::r/worldnews (Unmatched)", NEWS, isFromFeed("Reddit::r/worldnews"));
            add("Dagens Nyheter - Nyheter (Unmatched)", NEWS, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("nyheter")));
            add("Dagens Nyheter - Ekonomi (Unmatched)", NEWS, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("ekonomi")));
            add("Dagens Nyheter - Stockholm (Unmatched)", NEWS, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("sthlm")));
            add("Dagens Nyheter - Debatt (Unmatched)", NEWS, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("debatt")));
            add("Svenska Dagbladet - Världen (Unmatched)", NEWS, isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("världen")));
            add("Svenska Dagbladet - Sverige (Unmatched)", NEWS, isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("sverige")));
            add("Svenska Dagbladet - Näringsliv (Unmatched)", BIZ, isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("näringsliv")));
        }
        add("HackerNews (Unmatched)", TECH, isFromFeed("HackerNews"));
        add("Svenska Dagbladet - Kultur (Unmatched)", CULTURE, isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("kultur")));
        add("Dagens Nyheter - Kultur & Nöje (Unmatched)", CULTURE, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("kultur-noje")));

        subjects.add(UNMATCHED);
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
        add("China", NEWS, has("china"));
        add("Egypt", NEWS, EGYPT);
        add("France", NEWS, has("france").or(has("french")).or(has("macron")).or(has("le pen")));
        add("Germany", NEWS, has("germany").or(has("tysk")));
        add("Iran", NEWS, has("iran"));
        add("Israel", NEWS, ISRAEL);
        add("Kambodja", NEWS, has("kambodja").or(has("hun sen")));
        add("Kosovo", NEWS, has("kosovo"));
        add("Libya", NEWS, LIBYA);
        add("North Korea", NEWS, NORTH_KOREA);
        add("Norge", NEWS, has("norge"));
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
