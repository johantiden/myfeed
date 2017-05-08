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

    private final List<Subject> subjects;

    public SubjectRepository() {
        subjects = createSubjects();
    }

    private static List<Subject> createSubjects() {
        ArrayList<Subject> s = new ArrayList<>();

        add(s, "Trump", NEWS, d -> textContains(d, "trump") || textContains(d, "Trump"));
        add(s, "Macron", NEWS, d -> textContains(d, "macron") || textContains(d, "Macron"));
        add(s, "Syria", NEWS, d -> textContains(d, "Syria"));
        add(s, "Palestine vs Israel", NEWS, d -> textContains(d, "Palestinian") || textContains(d, "Israeli"));
        add(s, "TheLocal (Unmatched)", NEWS, d -> isFromFeed(d, "TheLocal"));

        add(s, "Apple", TECH, d -> textContains(d, "Apple"));
        add(s, "Scientific Method", TECH, d -> anyCategoryEquals(d, "Scientific Method"));
        add(s, "Security", TECH, d -> anyCategoryEquals(d, "Malware") ||
            anyCategoryEquals(d, "Passwords"));
        add(s, "Misc Tech", TECH, d -> isFromFeed(d, "HackerNews"));

        add(s, "Misc Business", BIZ, d -> anyCategoryEquals(d, "Business"));

        add(s, "Aww", FUN, d -> anyCategoryEquals(d, "Aww"));

        add(s, "Sport", BAD, d -> anyCategoryEquals(d, "sport"));
        add(s, "Reddit::Jokes", BAD, d -> anyCategoryEquals(d, "Jokes"));

        return s;
    }

    private static boolean isFromFeed(Document d, String feedName) {
        return d.feed.name.equals(feedName);
    }

    private static boolean anyCategoryEquals(Document d, String string) {
        return d.categories.stream().anyMatch(c -> c.name.equals(string));
    }

    private static boolean add(ArrayList<Subject> s, String title, String tab, Predicate<Document> predicate) {
        return s.add(new Subject(title, tab, predicate));
    }

    private static boolean textContains(Document d, String string) {
        return d.text != null && d.text.contains(string) ||
                d.title != null && d.title.contains(string);
    }

    public List<Subject> getAllSubjects() {
        return subjects;
    }
}
