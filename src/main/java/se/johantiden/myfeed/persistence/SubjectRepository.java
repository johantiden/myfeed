package se.johantiden.myfeed.persistence;

import se.johantiden.myfeed.controller.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SubjectRepository {

    private static final String NEWS = "News";
    private static final String BIZ = "Biz";
    private static final String TECH = "Tech";
    private static final String BAD = "Bad";
    private static final String FUN = "Fun";
    private static final String CULTURE = "Culture";

    private final List<Subject> subjects;

    public SubjectRepository() {
        subjects = createSubjects();
    }

    private static List<Subject> createSubjects() {
        ArrayList<Subject> s = new ArrayList<>();

        add(s, "Trump", NEWS, textContainsIgnoreCase("trump").or(textContainsIgnoreCase("the_donald")));
        add(s, "Macron", NEWS, textContainsIgnoreCase("macron"));
        add(s, "Syria", NEWS, textContains("Syria"));
        add(s, "Palestine vs Israel", "PvI", NEWS, textContains("Palestinian")
                                                    .or(textContains("Israeli"))
                                                    .or(textContains("Hamas")));
        add(s, "Egypt vs Libya", NEWS, textContains("Egypt").or(textContains("Libya")));
        add(s, "TheLocal (Unmatched)", NEWS, isFromFeed("TheLocal"));
        add(s, "Crises", NEWS, textContains("kris"));
        add(s, "Disasters", "disasters_v2", NEWS, textContainsIgnoreCase("floods").or(textContainsIgnoreCase("översvämning")));
        add(s, "Violence", NEWS, textContainsIgnoreCase("våldtäkt")
                                         .or(textContainsIgnoreCase("man död")
                                         .or(textContainsIgnoreCase("kvinna död")
                                                     .or(anyCategoryEquals("Accidents and Safety")))));

        add(s, "Stockholm", "sthml", NEWS, textContains("Stockholm").or(anyCategoryEquals("sthlm")));
        add(s, "Sex", "sex", NEWS, textContainsIgnoreCase("sexliv"));
        add(s, "Högskoleprovet", NEWS, textContainsIgnoreCase("högskoleprov"));
        add(s, "Fake News", NEWS, textContainsIgnoreCase("fake news").or(textContainsIgnoreCase("alternative facts")));
        add(s, "Elections", NEWS, textContainsIgnoreCase("väljer").and(textContainsIgnoreCase("president")));
        add(s, "Vinster i välfärden", NEWS, textContainsIgnoreCase("vinst").and(textContainsIgnoreCase("välfärd")));
        add(s, "Lottning i skolan", NEWS, textContainsIgnoreCase("skol").and(textContainsIgnoreCase("lott")));
        add(s, "Deaths", NEWS, anyCategoryEquals("Deaths (Obituaries)").or(anyCategoryEquals("Funerals and Memorials")));
        add(s, "North Korea", NEWS, textContains("Nordkorea").or(textContains("North Korea")));
        add(s, "Terrorism", NEWS, textContains("Boko Haram").or(textContainsIgnoreCase("car bomb")));
        add(s, "USA", "usa_v2", NEWS, textContains("Indiana").or(textContainsIgnoreCase("inreseförbud")));
        add(s, "Russia", NEWS, textContains("Russia"));
        add(s, "China", NEWS, textContains("China"));
        add(s, "Venezuela", NEWS, textContains("Venezuela").or(textContains("Maduro")));
        add(s, "Marine Le Pen", NEWS, textContains("Marine Le Pen"));
        add(s, "Kevin", NEWS, textContains("Kevin").and(textContains("Arvika")));
        add(s, "Ledare & Sammanfattningar", NEWS, anyCategoryEqualsIgnoreCase("ledare"));
        add(s, "Baby", NEWS, textContainsIgnoreCase("spädbarn"));
        add(s, "Refugees", NEWS, textContainsIgnoreCase("refugees").or(textContainsIgnoreCase("flykting")));
        add(s, "Misc News", NEWS, textContainsIgnoreCase("politiker").or(textContainsIgnoreCase("polis")).or(textContainsIgnoreCase("police")));

        add(s, "Apple", TECH, textContains("Apple"));
        add(s, "Google", TECH, textContains("Google").or(anyCategoryEquals("Google")));
        add(s, "Comcast", TECH, textContains("Comcast"));
        add(s, "Facebook", TECH, textContains("Facebook"));
        add(s, "Samsung", TECH, textContainsIgnoreCase("samsung group"));
        add(s, "Microsoft", TECH, textContainsIgnoreCase("microsoft"));
        add(s, "IBM", TECH, anyCategoryEquals("ibm"));
        add(s, "Artificial Intelligence", TECH, textContainsIgnoreCase("artificial intelligence"));
        add(s, "Internet", TECH, textContainsIgnoreCase("internet"));
        add(s, "Scientific Method", TECH, anyCategoryEquals("Scientific Method"));
        add(s, "Ask Slashdot", TECH, textContains("Ask Slashdot"));
        add(s, "Security", "Security_v3", TECH, anyCategoryEquals("malware").or(anyCategoryEquals("passwords")).or(textContainsIgnoreCase("denial-of-service")));
        add(s, "Misc Tech", TECH, isFromFeed("HackerNews").and(textContainsIgnoreCase("hiring").negate()).and(textContainsIgnoreCase("Show HN").negate())
                                          .or(anyCategoryEquals("Law & Disorder")
                                                      .or(anyCategoryEquals("internet"))));
        add(s, "Show HN", TECH, textContains("Show HN"));
        add(s, "Privacy", TECH, anyCategoryEquals("privacy"));

        add(s, "Music", CULTURE, anyCategoryEquals("Pop and Rock Music"));
        add(s, "Movies", CULTURE, anyCategoryEquals("movies"));
        add(s, "Art", CULTURE, anyCategoryEquals("Art"));
        add(s, "DN::Kultur & Nöje", CULTURE, anyCategoryEquals("kultur-noje"));

        add(s, "Misc Business", BIZ, anyCategoryEquals("Business"));
        add(s, "Affärsbragd", BIZ, textContains("Affärsbragd"));
        add(s, "Loans", BIZ, anyCategoryEquals("Näringsliv").and(textContainsIgnoreCase("lån")));
        add(s, "Union", BIZ, anyCategoryEquals("Näringsliv").and(textContainsIgnoreCase("fack")));
        add(s, "Pension", BIZ, anyCategoryEquals("Näringsliv").and(textContainsIgnoreCase("pension")));
        add(s, "Your boss (test)", BIZ, anyCategoryEquals("business").and(textContainsIgnoreCase("boss")));
        add(s, "Basic Income", BIZ, textContainsIgnoreCase("basic income"));

        add(s, "Reddit::Aww", FUN, anyCategoryEquals("aww"));
        add(s, "Reddit::Pics", FUN, anyCategoryEquals("pics"));
        add(s, "Reddit::Today I Learned", FUN, anyCategoryEquals("todayilearned"));
        add(s, "Reddit::Funny", FUN, anyCategoryEquals("funny"));
        add(s, "Reddit::Mildly Interesting", FUN, anyCategoryEquals("mildlyinteresting"));
        add(s, "Reddit::Gaming", FUN, anyCategoryEquals("gaming"));
        add(s, "Reddit::Gifs", FUN, anyCategoryEquals("gifs"));
        add(s, "Reddit::Oddly Satisfying", FUN, anyCategoryEquals("oddlysatisfying"));
        add(s, "Reddit::Educational Gifs", FUN, anyCategoryEquals("educationalgifs"));

        add(s, "Sport", "Sport_v2", BAD, anyCategoryEquals("sport").or(anyCategoryEquals("Sport")));
        add(s, "Reddit::Jokes", BAD, anyCategoryEquals("Jokes"));
        add(s, "Reddit Clickbaity", BAD, textContainsIgnoreCase("-- number"));
        add(s, "Reddit::MURICA", BAD, anyCategoryEquals("MURICA"));
        add(s, "Reddit::meirl", BAD, anyCategoryEquals("meirl"));
        add(s, "Food", BAD, anyCategoryEquals("mat-dryck").or(anyCategoryEqualsIgnoreCase("food")));
        add(s, "Cars", BAD, anyCategoryEquals("Cars Technica"));
        add(s, "Al Jazeera::News Grid", BAD, textContains("NewsGrid"));
        add(s, "DN::Webb-TV", BAD, anyCategoryEquals("webb-tv"));
        add(s, "Ars Technica::The Multiverse", BAD, isFromFeed("Ars Technica").and(anyCategoryEquals("The Multiverse")));
        add(s, "HackerNews::hiring", BAD, isFromFeed("HackerNews").and(textContainsIgnoreCase("hiring")));
        return s;
    }


    private static Predicate<Document> isFromFeed(String feedName) {
        return d -> d.feed.name.equals(feedName);
    }

    private static Predicate<Document> anyCategoryEquals(String string) {
        return d -> d.categories.stream().anyMatch(c -> c.name.equals(string));
    }

    private static Predicate<Document> anyCategoryEqualsIgnoreCase(String string) {
        return d -> d.categories.stream().anyMatch(c -> c.name.equalsIgnoreCase(string));
    }

    private static boolean add(ArrayList<Subject> s, String title, String tab, Predicate<Document> predicate) {
        return s.add(new Subject(title, title, tab, predicate));
    }

    private static boolean add(ArrayList<Subject> s, String title, String key, String tab, Predicate<Document> predicate) {
        return s.add(new Subject(title, key, tab, predicate));
    }


    private static Predicate<Document> textContainsIgnoreCase(String string) {
        return d -> d.text != null && d.text.toLowerCase().contains(string) ||
                d.title != null && d.title.toLowerCase().contains(string) ||
                d.categories.stream().anyMatch(c -> c.name.toLowerCase().contains(string));
    }

    private static Predicate<Document> textContains(String string) {
        return d -> d.text != null && d.text.contains(string) ||
                d.title != null && d.title.contains(string) ||
                d.categories.stream().anyMatch(c -> c.name.contains(string));

    }

    public List<Subject> getAllSubjects() {
        return subjects;
    }
}