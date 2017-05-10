package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.controller.Subject;
import se.johantiden.myfeed.util.JPredicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SubjectService {

    private static final String NEWS = "News";
    private static final String BIZ = "Biz";
    private static final String TECH = "Tech";
    private static final String SPORT = "News";
    private static final String BAD = "Bad";
    private static final String FUN = "Fun";
    private static final String CULTURE = "Culture";
    private static final String ENTERTAINMENT = "Entertainment";


    private static final boolean DEBUG = true;
    public static final Subject UNMATCHED = new Subject("Unmatched", "Unmatched", "Unmatched", d -> true);
    private static final boolean UNMATCHED_PER_FEED = true;


    public static final Predicate<Document> USA = has("usa").or(hasCaseSensitive(" US ")).or(has("trump"));
    public static final Predicate<Document> RUSSIA = has("russia");
    public static final Predicate<Document> SYRIA = has("syria").or(has("raqqa"));
    public static final Predicate<Document> EGYPT = has("egypt");
    public static final Predicate<Document> LIBYA = has("libya");
    public static final Predicate<Document> NORTH_KOREA = has("north korea");
    public static final Predicate<Document> ISRAEL = has("israel");
    public static final Predicate<Document> PALESTINE = has("palestine");
    public static final Predicate<Document> SWEDEN = has("sweden").or(has("swedish"));

    private final List<Subject> subjects;

    public SubjectService() {
        subjects = createSubjects();
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
        if (DEBUG) {
            if (!string.toLowerCase().equals(string)) {
                throw new IllegalArgumentException("Input must be lower case only!");
            }
        }
        return d -> d.categories.stream().anyMatch(c -> c.name.equalsIgnoreCase(string));
    }

    private static boolean add(ArrayList<Subject> s, String title, String tab, Predicate<Document> predicate) {
        return s.add(new Subject(title, title+tab, tab, predicate));
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
        if (DEBUG) {
            String lowerCase = string.toLowerCase();
            if (!lowerCase.equals(string)) {
                throw new IllegalArgumentException("Input must be lower case only!");
            }
        }
        return d -> d.text != null && d.text.toLowerCase().contains(string) ||
                            d.title != null && d.title.toLowerCase().contains(string) ||
                            d.categories.stream().anyMatch(c -> c.name.toLowerCase().contains(string));
    }

    private static Predicate<? super Document> hasCaseSensitive(String string) {
        return d -> d.text != null && d.text.contains(string) ||
                            d.title != null && d.title.contains(string) ||
                            d.categories.stream().anyMatch(c -> c.name.contains(string));
    }

    private static List<Subject> createSubjects() {
        ArrayList<Subject> s = new ArrayList<>();
        add(s, "webb-tv", BAD, has("webb-tv"));
        add(s, "Al Jazeera :: NewsGrid", BAD, has("newsgrid"));
        add(s, "TheLocal (Clickbaity)", BAD, startsWith("these are").or(startsWithCaseSensitive("WATCH")).or(startsWith("this")));

        add(s, "Editorials", NEWS, anyCategoryEquals("ledare").or(has("your morning briefing")));
        add(s, "Weather", NEWS, has("monsoon").or(has("snow")));

        add(s, "Comics", FUN, isFromFeed("xkcd"));

        add(s, "Climate", NEWS, has("climate"));
        add(s, "Vaccination", NEWS, has("vaccin"));
        add(s, "Blashpemy", NEWS, has("blasphemy"));
        add(s, "Brexit", NEWS, has("brexit"));
        add(s, "NATO", NEWS, has("nato"));
        add(s, "Drottninggatan", NEWS, has("stockholm").and(has("terror")).or(has("drottninggatan")));
        add(s, "Invandringspolitik", NEWS, SWEDEN.and(has("immigra").or(has("asylum")).or(has("refugee"))).or(has("invandring")));
        add(s, "Sjukvård", NEWS, SWEDEN.and(has("hospital")));
        add(s, "Terrorism", NEWS, has("taliban").or(has("terror")));
        add(s, "Crime", NEWS, has("prison"));

        addCountriesCross(s);
        addCountries(s);

        add(s, "Sport", SPORT, anyCategoryEquals("sport"));
        add(s, "Football", SPORT, has("football").or(has("soccer")));

        add(s, "AskReddit", FUN, isFromFeed("Reddit::r/AskReddit"));

        add(s, "Ars Technica (Unmatched)", TECH, isFromFeed("Ars Technica"));
        add(s, "HackerNews (Unmatched)", TECH, isFromFeed("HackerNews"));
        add(s, "Slashdot (Unmatched)", TECH, isFromFeed("Slashdot"));
        add(s, "Reddit::top (Unmatched)", FUN, isFromFeed("Reddit::top"));
        add(s, "Reddit::r/worldnews (Unmatched)", NEWS, isFromFeed("Reddit::r/worldnews"));
        add(s, "Reddit::r/all (Unmatched)", FUN, isFromFeed("Reddit::r/all"));
        add(s, "Dagens Nyheter - Nyheter (Unmatched)", NEWS, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("nyheter")));
        add(s, "Dagens Nyheter - Ekonomi (Unmatched)", NEWS, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("ekonomi")));
        add(s, "Dagens Nyheter - Kultur & Nöje (Unmatched)", NEWS, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("kultur-noje")));
        add(s, "Dagens Nyheter - Stockholm (Unmatched)", NEWS, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("sthlm")));
        add(s, "Dagens Nyheter - Debatt (Unmatched)", NEWS, isFromFeed("Dagens Nyheter").and(anyCategoryEquals("debatt")));
        add(s, "Svenska Dagbladet - Världen (Unmatched)", NEWS, isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("världen")));
        add(s, "Svenska Dagbladet - Sverige (Unmatched)", NEWS, isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("sverige")));
        add(s, "Svenska Dagbladet - Kultur (Unmatched)", NEWS, isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("kultur")));
        add(s, "Svenska Dagbladet - Näringsliv (Unmatched)", BIZ, isFromFeed("Svenska Dagbladet").and(anyCategoryEquals("näringsliv")));


        if (UNMATCHED_PER_FEED) {
            add(s, "Al Jazeera (Unmatched)", NEWS, isFromFeed("Al Jazeera"));
            add(s, "TheLocal (Unmatched)", NEWS, isFromFeed("TheLocal"));
            add(s, "New York Times (Unmatched)", NEWS, isFromFeed("New York Times :: World"));
        }

        s.add(UNMATCHED);
        return s;
    }

    private static void addCountriesCross(ArrayList<Subject> s) {
        add(s, "Egypt vs Libya", NEWS, EGYPT.and(LIBYA));
        add(s, "USA vs Russia", NEWS, USA.and(RUSSIA));
        add(s, "USA vs Syria", NEWS, USA.and(SYRIA));
        add(s, "USA vs North Korea", NEWS, USA.and(NORTH_KOREA));
        add(s, "Israel vs Palestine", NEWS, ISRAEL.and(PALESTINE));
    }

    private static void addCountries(ArrayList<Subject> s) {
        add(s, "Bangladesh", NEWS, has("bangladesh").or(has("nooruddin ahmed")));
        add(s, "Bosnia", NEWS, has("bosnia"));
        add(s, "Brazil", NEWS, has("brazil"));
        add(s, "China", NEWS, has("china"));
        add(s, "Egypt", NEWS, EGYPT);
        add(s, "France", NEWS, has("france").or(has("french")).or(has("macron")).or(has("le pen")));
        add(s, "Germany", NEWS, has("germany"));
        add(s, "Iran", NEWS, has("iran"));
        add(s, "Israel", NEWS, ISRAEL);
        add(s, "Kambodja", NEWS, has("kambodja").or(has("hun sen")));
        add(s, "Kosovo", NEWS, has("kosovo"));
        add(s, "Libya", NEWS, LIBYA);
        add(s, "North Korea", NEWS, NORTH_KOREA);
        add(s, "Palestine", NEWS, PALESTINE);
        add(s, "Poland", NEWS, has("poland"));
        add(s, "Russia", NEWS, RUSSIA);
        add(s, "Slovakia", NEWS, has("slovakia"));
        add(s, "South Korea", NEWS, has("south korea"));
        add(s, "South Africa", NEWS, has("south africa").or(has("stellenbosch")));
        add(s, "Syria", NEWS, SYRIA);
        add(s, "Tunisia", NEWS, has("tunisia"));
        add(s, "USA", NEWS, USA);
        add(s, "Yemen", NEWS, has("yemen"));
    }


    public List<Subject> getAllSubjects() {
        return subjects;
    }

}
