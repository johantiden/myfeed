package se.johantiden.myfeed.persistence;


import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static se.johantiden.myfeed.persistence.Subject.ROOT;

public class SubjectClassifier {

    private static Set<Subject> subjects = new HashSet<>();

    public static Set<Subject> getSubjects() {
        subjects.add(ROOT);

        Subject fun = addWithoutExpression("Fun", ROOT);
        add("xkcd", "xkcd", fun);



        Subject news = addWithoutExpression("Nyheter", ROOT);


        Subject nordkorea = add("Nordkorea", "Nordkorea", news);
        Subject kim = add("Kim Jong-Un", "Kim Jong-Un", nordkorea);

        Subject singapore = add("Singapore", "Singapore", news);

        Subject usa = add("USA", "USA", news);
        Subject florida = add("Florida", "Florida", usa);
        Subject arizona = add("Arizona", "Arizona", usa);

        Subject usaPolitik = add("USA-politik", "USA-politik", news, usa);
        add("Jeff Flake", "Jeff Flake", usaPolitik, arizona);
        add("Brett Kavanaugh", "Brett Kavanaugh", usaPolitik);

        Subject trump = add("Trump", "Trump", news, usa);


        Subject sverige = add("Sverige", "Sverige", news);
        Subject inrikespolitik = add("Inrikespolitik", "Inrikespolitik", sverige);
        addInvisible("[Ss]vensk politik", inrikespolitik);
        Subject centerpartiet = add("Centerpartiet", "Centerpartiet", inrikespolitik);
        add("Annie Lööf", "Annie Lööf", centerpartiet);

        Subject tech = addWithoutExpression("Tech", ROOT);
        Subject science = addWithoutExpression("Science", tech);
        addInvisible("[Pp]hys\\.org", science);
        Subject internet = add("Internet", "Internet", tech);
        Subject software = add("Software", "[Ss]oftware", tech);
        Subject ai = add("Artificial Intelligence", "[Aa]rtificial[ \\-][Ii]ntelligence", tech);
        addInvisible("[Rr]obot", ai);
        addInvisible("[Mm]achine [Ll]earning", ai);

        addInvisible("[Pp]rogramming", software);
        addInvisible("[Pp]rogrammer", software);
        add("Open Source", "Open Source", software);
        addInvisible("World Wide Web", internet);
        addInvisible("Engadget", tech);
        Subject facebook = add("Facebook", "Facebook", tech);
        add("Cambridge Analytica", "Cambridge Analytica", facebook, tech);
        addInvisible("[Pp]olymer", science);
        add("Tim Berners-Lee", "Tim Berners-Lee", internet);

        Subject greatBritain = add("Great Britain", "Great Britain", news);
        addInvisible("U\\.K\\.", greatBritain);
        Subject brexit = add("Brexit", "Brexit", news, greatBritain);

        Subject apple = add("Apple", "Apple", tech);
        add("Steve Jobs", "Steve Jobs", apple);
        Subject amazon = add("Amazon", "Amazon", tech);
        add("Alexa", "Alexa", tech, amazon, ai);

        Subject google = add("Google", "Goole", tech);


        Subject africa = add("Africa", "Africa", news);
        Subject egypt = add("Egypt", "Egypt", africa);
        Subject turkiet = add("Turkiet", "Turkiet", news);
        addInvisible("Turkey", turkiet);
        Subject grekland = add("Grekland", "Grekland", news);
        addInvisible("Greece", grekland);

        add("Amnesty", "Amnesty", news);
        add("Influenza", "Influenza", science);

        Subject nobelpriset = add("Nobelpriset", "[Nn]obelpriset", news);
        addInvisible("[Nn]obel pri", nobelpriset);

        Subject ukraina = add("Ukraina", "Ukrain", news);
        Subject qatar = add("Qatar", "Qatar", news);
        Subject saudi = add("Saudi Arabia", "Saudi Arabi", news);
        Subject uae = add("Förenade Arabemiraten", "Förenade Arabemiraten", news);
        addInvisible("UAE", uae);
        Subject indien = add("Indien", "Indien", news);
        addInvisible("India", indien);

        Subject indonesien = add("Indonesien", "Indonesien", news);
        addInvisible("Indonesia", indonesien);

        Subject weather = addWithoutExpression("Weather", ROOT);
        add("Tsunami", "[Tt]sunami", weather);


        Subject biz = addWithoutExpression("Biz", ROOT);
        add("Elon Musk", "Elon Musk", tech, biz);
        add("Tesla", "Tesla", tech, biz);





        Subject sport = addWithoutExpression("Sport", ROOT);
        Subject hockey = add("Hockey", "[Hh]ockey", sport);

        boolean hideCulture = false;
        Subject kultur = addWithoutExpression("Kultur", hideCulture, ROOT);
        addInvisible("kultur\\-noje", kultur);
        Subject music = addWithoutExpression("Musik", kultur);
        addInvisible("klassisk rock", music);

        return subjects;
    }

    private static Subject addWithoutExpression(String name, boolean hide, Subject... parents) {
        return add(name, null, hide, true, parents);
    }

    private static Subject addInvisible(String expression, Subject... parents) {
        return add("invisible:"+expression, expression, false, false, parents);
    }

    public static Subject addWithoutExpression(String name, Subject... parents) {
        return add(name, null, false, true, parents);
    }

    public static Subject add(String name, @Nullable String expression, Subject... parents) {
        return add(name, expression, false, true, parents);
    }

    private static Subject add(String name, @Nullable String expression, boolean hide, boolean isHashTag, Subject... parents) {
        int size = subjects.size();
        Subject subject = new Subject(Arrays.asList(parents), name, expression, hide, isHashTag);
        subjects.add(subject);
        if (size == subjects.size()) {
            throw new IllegalStateException("Duplicate detected! " + name + ", " + expression);
        }
        return subject;
    }
}
