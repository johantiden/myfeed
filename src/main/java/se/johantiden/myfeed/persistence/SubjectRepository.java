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
    private static final String ENTERTAINMENT = "Entertainment";

    private static final boolean DEBUG = true;

    private final List<Subject> subjects;

    public SubjectRepository() {
        subjects = createSubjects();
    }

    private static List<Subject> createSubjects() {
        ArrayList<Subject> s = new ArrayList<>();

        add(s, "Trump", NEWS, has("trump").or(has("the_donald")));
        add(s, "Macron", NEWS, has("macron"));
        add(s, "Syria", NEWS, has("syria"));
        add(s, "Palestine vs Israel", NEWS, has("palestinian")
                                                    .or(has("israeli"))
                                                    .or(has("hamas")));
        add(s, "Egypt vs Libya", NEWS, has("egypt").or(has("libya")));
        add(s, "USA vs Syria", NEWS, has("usa").or(has("trump")).and(has("syria")));
        add(s, "USA vs Russia", NEWS, has("usa").or(has("trump")).and(has("russia").or(has("ryssland"))));
        add(s, "TheLocal (Unmatched)", NEWS, isFromFeed("TheLocal"));
        add(s, "Crises", NEWS, has("kris").and(anyCategoryEquals("Näringsliv").negate()));
        add(s, "Disasters", NEWS, has("floods").or(has("översvämning")));
        add(s, "Crime", NEWS,
                has("våldtäkt")
                    .or(has("man död").or(has("kvinna död")
                                                  .or(anyCategoryEquals("Accidents and Safety"))
                                                  .or(has("sexköp"))
                                                  .or(has("stöld"))
                                                  .or(has("murder"))
                                                  .or(has("mordmisstänkt"))
                                                  .or(has("mörda")))));

        add(s, "Stockholm", NEWS, has("stockholm").or(anyCategoryEquals("sthlm")).and(has("börs").negate()));
        add(s, "Arbogamordet", NEWS, has("arboga").and(has("mord")));
        add(s, "Högskoleprovet", NEWS, has("högskoleprov"));
        add(s, "Fake News", NEWS, has("fake news").or(has("alternative facts")));
        add(s, "Elections", NEWS, has("väljer").and(has("president")));
        add(s, "Vinster i välfärden", NEWS, has("vinst").and(has("välfärd")));
        add(s, "Lottning i skolan", NEWS, has("skol").and(has("lott")));
        add(s, "Deaths", NEWS, anyCategoryEquals("Deaths (Obituaries)").or(anyCategoryEquals("Funerals and Memorials")));
        add(s, "North Korea", NEWS, has("nordkorea").or(has("north korea")));
        add(s, "Terrorism", NEWS, has("boko haram").or(has("car bomb")).or(has("terrorism")));
        add(s, "USA", NEWS, has("indiana")
                .or(has("inreseförbud"))
                .or(has("republican")));
        add(s, "Russia", NEWS, has("russia"));
        add(s, "China", NEWS, has("china").or(has("communist party")));
        add(s, "Venezuela", NEWS, has("venezuela").or(has("maduro")));
        add(s, "Marine Le Pen", NEWS, has("marine le pen"));
        add(s, "Kevin", NEWS, has("kevin").and(has("arvika").or(has("mord")).or(has("åklagare"))));
        add(s, "Ledare & Sammanfattningar", NEWS, anyCategoryEqualsIgnoreCase("ledare"));
        add(s, "Baby", NEWS, has("spädbarn"));
        add(s, "Refugees", NEWS, has("refugees").or(has("flykting")));
        add(s, "Misc News", NEWS, has("politiker").or(has("polis")).or(has("police")));
        add(s, "Political Movements", NEWS, anyCategoryEquals("esist"));
        add(s, "Norway", NEWS, has("norway"));
        add(s, "Kambodja", NEWS, has("kambodja").or(has("hun sen")));
        add(s, "Philippines", NEWS, has("philippines").or(has("duterte")));
        add(s, "Daesh", NEWS, has("isil").or(has("duterte")));
        add(s, "Discrimination", NEWS, has("racism").or(has("discrimination")));
        add(s, "South Korea", NEWS, has("south korea").and(has("vote").negate().and(has("election").negate())));
        add(s, "South Korean Election", NEWS, has("south korea").and(has("vote").or(has("election"))));
        add(s, "France", NEWS, has("france").or(has("fransk").or(has("french")).or(has("frankr"))));
        add(s, "Debate", NEWS, anyCategoryEquals("Debatt"));
        add(s, "Drottninggatan", NEWS, has("drottninggatan").and(has("terror")));
        add(s, "Toxic", NEWS, has("gift").and(has("utsläpp")));
        add(s, "Fox Hunt", NEWS, has("rävjakt"));
        add(s, "Holocaust", NEWS, has("holocaust"));
        add(s, "Bosnia", NEWS, has("bosnia"));
        add(s, "Weather", NEWS, has("monsoon").or(has("snö")));
        add(s, "FIFA Corruption", NEWS, has("fifa").and(has("corruption")));
        add(s, "Public Sector", NEWS, has("vårdcentral"));

        add(s, "Apple", TECH, has("apple"));
        add(s, "Google", TECH, has("google").or(anyCategoryEquals("Google")));
        add(s, "Comcast", TECH, has("comcast"));
        add(s, "Facebook", TECH, has("facebook"));
        add(s, "Samsung", TECH, has("samsung group"));
        add(s, "Microsoft", TECH, has("microsoft"));
        add(s, "IBM", TECH, anyCategoryEquals("ibm"));
        add(s, "Gadgets", TECH, anyCategoryEquals("Gear & Gadgets"));
        add(s, "Artificial Intelligence", TECH, has("artificial intelligence"));
        add(s, "Internet", TECH, has("internet"));
        add(s, "Ask Slashdot", TECH, has("ask slashdot"));
        add(s, "Security", TECH, anyCategoryEquals("malware").or(anyCategoryEquals("passwords")).or(has("denial-of-service")));
        add(s, "Misc Tech", TECH, isFromFeed("HackerNews").and(has("hiring").negate()).and(has("show hn").negate())
                                          .or(anyCategoryEquals("Law & Disorder")
                                                      .or(anyCategoryEquals("internet"))));
        add(s, "Show HN", TECH, has("show hn"));
        add(s, "Privacy", TECH, anyCategoryEquals("privacy"));
        add(s, "Science", TECH, anyCategoryEquals("science").or(anyCategoryEquals("Scientific Method")));

        add(s, "Music", CULTURE, anyCategoryEquals("Pop and Rock Music"));
        add(s, "Movies", CULTURE, anyCategoryEquals("movies"));
        add(s, "Art", CULTURE, anyCategoryEquals("Art"));
        add(s, "DN::Kultur & Nöje", CULTURE, anyCategoryEquals("kultur-noje"));
        add(s, "SVD::Kultur", CULTURE, anyCategoryEquals("Kultur"));
        add(s, "Books", CULTURE, anyCategoryEquals("familj").and(has("bok")));

        add(s, "Sex", ENTERTAINMENT, has("sexliv"));

        add(s, "Misc Business", BIZ, anyCategoryEquals("Business"));
        add(s, "Affärsbragd", BIZ, has("affärsbragd"));
        add(s, "Loans", BIZ, anyCategoryEquals("Näringsliv").and(has("lån")));
        add(s, "Union", BIZ, anyCategoryEquals("Näringsliv").and(has("fack").or(has("strejk"))));
        add(s, "Pension", BIZ, anyCategoryEquals("Näringsliv").and(has("pension")));
        add(s, "Your boss (test)", BIZ, anyCategoryEquals("business").and(has("boss")));
        add(s, "Basic Income", BIZ, has("basic income"));
        add(s, "Asia", BIZ, anyCategoryEquals("ekonomi").and(has("asia").or(has("asien"))));
        add(s, "Medicine", BIZ, anyCategoryEquals("Näringsliv").and(has("läkemedel")));
        add(s, "Press Releases", BIZ, anyCategoryEquals("Näringsliv").and(has("pressmeddelande")));
        add(s, "Stockholm", BIZ, anyCategoryEquals("Näringsliv").and(has("stockholm")));
        add(s, "Riksbanken", BIZ, anyCategoryEquals("ekonomi").and(has("riksbanken")));
        add(s, "Fraud", BIZ, anyCategoryEquals("Näringsliv").and(has("bedrägeri")));
        add(s, "Scandals", BIZ, anyCategoryEquals("Näringsliv").and(has("skandal")));
        add(s, "Nordea till Danmark", BIZ, has("nordea").and(has("danmark")));
        add(s, "Food Services", BIZ, anyCategoryEquals("ekonomi").and(has("foodora").or(has("ubereats")).or(has("uber eats"))));

        add(s, "Reddit::Aww", FUN, anyCategoryEquals("aww"));
        add(s, "Reddit::Pics", FUN, anyCategoryEquals("pics"));
        add(s, "Reddit::Today I Learned", FUN, anyCategoryEquals("todayilearned"));
        add(s, "Reddit::Funny", FUN, anyCategoryEquals("funny"));
        add(s, "Reddit::Mildly Interesting", FUN, anyCategoryEquals("mildlyinteresting"));
        add(s, "Reddit::Gaming", FUN, anyCategoryEquals("gaming"));
        add(s, "Reddit::Gifs", FUN, anyCategoryEquals("gifs"));
        add(s, "Reddit::Oddly Satisfying", FUN, anyCategoryEquals("oddlysatisfying"));
        add(s, "Reddit::Educational Gifs", FUN, anyCategoryEquals("educationalgifs"));
        add(s, "Reddit::Not The Onion", FUN, anyCategoryEquals("nottheonion"));
        add(s, "Reddit::Photoshop Battles", FUN, anyCategoryEquals("photoshopbattles"));
        add(s, "Reddit::Interesting As Fuck", FUN, anyCategoryEquals("interestingasfuck"));
        add(s, "Reddit::Europe", FUN, feedContains("Reddit").and(anyCategoryEquals("europe")));
        add(s, "Reddit::Videos", FUN, anyCategoryEquals("videos"));
        add(s, "John Oliver", FUN, has("john oliver"));
        add(s, "Comics", FUN, anyCategoryEquals("serier").or(isFromFeed("xkcd")));

        add(s, "Sport", BAD, anyCategoryEquals("sport").or(anyCategoryEquals("Sport")));
        add(s, "Reddit::Jokes", BAD, anyCategoryEquals("Jokes"));
        add(s, "Reddit Clickbaity", BAD, has("-- number"));
        add(s, "Reddit::MURICA", BAD, anyCategoryEquals("MURICA"));
        add(s, "Reddit::meirl", BAD, anyCategoryEquals("meirl"));
        add(s, "Reddit::wholesomememes", BAD, anyCategoryEquals("wholesomememes"));
        add(s, "Food", BAD, anyCategoryEquals("mat-dryck").or(anyCategoryEqualsIgnoreCase("food")));
        add(s, "Cars", BAD, anyCategoryEquals("Cars Technica"));
        add(s, "Al Jazeera::News Grid", BAD, has("newsgrid"));
        add(s, "DN::Webb-TV", BAD, anyCategoryEquals("webb-tv"));
        add(s, "Ars Technica::The Multiverse", BAD, isFromFeed("Ars Technica").and(anyCategoryEquals("The Multiverse")));
        add(s, "HackerNews::hiring", BAD, isFromFeed("HackerNews").and(has("hiring")));
        add(s, "Economy - Warning (clickbaity)", BAD, anyCategoryEquals("Näringsliv").and(has("varning")));

        return s;
    }

    private static Predicate<Document> feedContains(String string) {
        return d -> d.feed.name.contains(string);
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
        return s.add(new Subject(title, title+tab, tab, predicate));
    }

    private static Predicate<Document> has(String string) {
        if (DEBUG) {
            if (!string.toLowerCase().equals(string)) {
                throw new IllegalArgumentException("Input must be lower case only!");
            }
        }
        return d -> d.text != null && d.text.toLowerCase().contains(string) ||
                d.title != null && d.title.toLowerCase().contains(string) ||
                d.categories.stream().anyMatch(c -> c.name.toLowerCase().contains(string));
    }

    public List<Subject> getAllSubjects() {
        return subjects;
    }
}
