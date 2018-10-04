package se.johantiden.myfeed.persistence;


import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static se.johantiden.myfeed.persistence.Subject.ROOT;

public class SubjectClassifier {

    private static Set<Subject> subjects = new HashSet<>();
    public static final boolean HIDE_SPORT = false;
    public static final boolean HIDE_CULTURE = false;
    public static final boolean HIDE_BAD = false;

    public static Set<Subject> getSubjects() {
        subjects.add(ROOT);

        Subject fun = addWithoutExpression("#Fun", ROOT);
        Subject comics = addWithoutExpression("Comics", fun);
        add("xkcd", "xkcd", comics);
        add("Ernie", "serier/ernie", comics);

        Subject reddit = add("Reddit", "reddit\\.com", fun);
        Subject rFunny = add("r/funny", "reddit\\.com/r/funny", reddit);



        Subject news = addWithoutExpression("Nyheter", ROOT);
        Subject meToo = add("#metoo", "#metoo", news);


        Subject nordkorea = add("Nordkorea", "Nordkorea", news);
        Subject kim = add("Kim Jong-Un", "Kim Jong-Un", nordkorea);

        Subject sydkorea = add("Sydkorea", "Sydkorea", news);
        add("Pyeongchang", "Pyeongchang", sydkorea);

        Subject singapore = add("Singapore", "Singapore", news);

        Subject usa = add("#USA", "USA|U\\.S\\.", news);
        Subject florida = add("Florida", "Florida", usa);
        Subject california = add("Kalifornien", "[KkCc]aliforni", usa);
        Subject hawaii = add("Hawaii", "[Hh]awaii", usa);
        Subject arizona = add("Arizona", "Arizona", usa);
        Subject ohio = add("Ohio", "Ohio|Cincinnati", usa);
        Subject texas = add("Texas", "Texas", usa);

        Subject mexiko = add("Mexiko", "Mexico|Mexiko", news);

        Subject elSalvador = add("El Salvador", "El Salvador", news);
        Subject kina = add("Kina", "Kina|China", news);

        Subject usaPolitik = add("USA-politik", "USA-politik", news, usa);
        add("Jeff Flake", "Jeff Flake", usaPolitik, arizona);
        add("Brett Kavanaugh", "Kavanaugh", usaPolitik,meToo);
        add("Paul Ryan", "Paul Ryan", usaPolitik);

        Subject trump = add("Trump", "Trump", usa);
        add("Bill Clinton", "Bill Clinton", usa);
        add("NAFTA", "NAFTA", usaPolitik);
        add("Republikanerna", "washington.*[Rr]epublican", usaPolitik);
        add("Obamacare", "Obamacare", usaPolitik);


        Subject sverige = add("Sverige", "Sverige|Swedish|Sweden", news);
        addInvisible("[Ss]vensk", sverige);
        addInvisible("TheLocal", sverige);
        Subject inrikespolitik = add("Inrikespolitik", "Inrikespolitik", sverige);
        addInvisible("[Ss]vensk politik", inrikespolitik);
        Subject alliansen = add("Alliansen", "Alliansparti", inrikespolitik);
        Subject centerpartiet = add("Centerpartiet", "Centerpartiet", inrikespolitik,alliansen);
        Subject moderaterna = add("Moderatena", "Moderaterna", inrikespolitik, alliansen);
        add("Ulf Kristersson", "Ulf Kristersson", moderaterna);
        add("Annie Lööf", "Annie Lööf", centerpartiet);
        Subject sverigedemokraterna = add("Sverigedemokraterna", "Sverigedemokraterna", inrikespolitik);
        add("Jimmie Åkesson", "Jimmie Åkesson", sverigedemokraterna);
        Subject talmannen = add("Talmannen", "[Tt]almannen", inrikespolitik);
        add("Andreas Norlén", "Andreas Norlén", talmannen, inrikespolitik);
        addInvisible("[Ll]andsting", inrikespolitik);
        addInvisible("[Kk]ommuner", inrikespolitik);
        Subject inrikes = add("Inrikes", "[Ii]nrikes", sverige);
        addInvisible("[Tt]ågtrafik", inrikes);




        Subject danmark = add("Danmark", "[Dd]anmark|[Dd]ansk|[Dd]enmark|[Dd]anish", news);
        Subject copenhagen = add("Köpenhamn", "Köpenhamn|Copenhagen", danmark);

        add("Studiemedel", "CSN|[Ss]tudiemedel|[Ss]tudielån", sverige);

        Subject tech = addWithoutExpression("Tech", ROOT);

        Subject science = addWithoutExpression("Science", tech);
        addInvisible("[Pp]hys\\.org", science);
        addInvisible("[Qq]uantum", science);
        addInvisible("[Tt]ransistor", science);
        addInvisible("[Rr]esearch", science);
        addInvisible("[Pp]olymer", science);
        addInvisible("[Ff]orskare", science);
        addInvisible("[Ff]orskning", science);

        Subject internet = add("Internet", "[Ii]nternet", tech);
        Subject linux = add("Linux", "[Ll]inux", tech);
        addInvisible("Ubuntu", linux);
        Subject ai = add("Artificial Intelligence", "[Aa]rtificial[ \\-][Ii]ntelligence", tech);
        addInvisible("[Rr]obot", ai);
        Subject machineLearning = add("Machine Learning", "[Mm]achine [Ll]earning", ai);
        add("Neural Networks", "[Nn]eural [Nn]et", machineLearning);
        add("Cellular Automata", "[Cc]ellular [Aa]utomata", machineLearning);

        add("Niklas Zennström", "Zennström", tech);

        Subject software = add("Software", "[Ss]oftware", tech);
        addInvisible("[Pp]rogramming", software);
        addInvisible("[Pp]rogrammer", software);
        addInvisible("[Ww]eb [Ff]ramework", software);
        add("Open Source", "[Oo]pen [Ss]ource", software);
        add("Javascript", "[Jj]avascript|\\.js", software);

        Subject klimat = add("Klimat", "[Kk]limat|[Cc]limate", news);


        addInvisible("World Wide Web", internet);
        addInvisible("Engadget", tech);
        Subject facebook = add("Facebook", "Facebook", tech);
        add("Cambridge Analytica", "Cambridge Analytica", facebook, tech);

        add("Tim Berners-Lee", "Tim Berners-Lee", internet);

        Subject greatBritain = add("Storbritannien", "Great Britain|Storbritannien|British", news);
        add("Tories", "Tories", greatBritain);
        addInvisible("U\\.K\\.", greatBritain);
        Subject brexit = add("Brexit", "Brexit", news, greatBritain);
        add("Theresa May", "Theresa May", greatBritain);
        add("London", "London", greatBritain);
        add("Birmingham", "Birmingham", greatBritain);

        add("Mikronesien", "Mikronesien|[Mm]icronesia", news);

        add("Bolivia", "Bolivia", news);
        add("Senegal", "Senegal", news);
        add("Chile", "Chile", news);
        add("Italien", "Italien|Italian|Italy", news);
        add("Kanada", "Canada|Kanada|Kanadens|Canadian", news);
        add("Nederländerna", "Nederländerna|Netherlands|Dutch|Holland|Holländ", news);
        add("Tyskland", "German|Tysk", news);


        add("Pope Francis", "[Pp]ope.*Francis", news);

        Subject afrika = add("Afrika", "Afrika|Africa", news);
        Subject egypt = add("Egypt", "Egypt", afrika);
        add("Botswana", "Botswana", news, afrika);
        Subject kenya = add("Kenya", "Kenya", afrika);
        add("Nairobi", "Nairobi", kenya);
        Subject turkiet = add("Turkiet", "Turkiet", news);
        add("Erdogan", "Erdogan", turkiet);
        Subject maldives = add("Maldives", "Maldives", news);
        addInvisible("Turkey", turkiet);
        Subject grekland = add("Grekland", "Grekland", news);
        addInvisible("Greece", grekland);

        Subject israel = add("Israel", "Israel", news);
        Subject palestina = add("Palestina", "Palestin", news);
        add("Gaza", "Gaza", israel, palestina);

        add("Slovakien", "Slovak", news);

        Subject japan = add("Japan", "Japan", news);
        add("Okinawa", "Okinawa", japan);

        add("Malta", "Malta|Maltese", news);

        Subject afganistan = add("Afghanistan", "Afghan|Afgan", news);

        Subject ryssland = add("Ryssland", "Russia|Ryssland|[Rr]ysk", news);
        add("Vladimir Putin", "Putin", ryssland);


        add("Amnesty", "Amnesty", news);
        add("Influenza", "Influenza", science);
        add("Leukemi", "[Ll]eukemi", science);
        add("Ultraljud", "[Uu]ltrasound", science);

        Subject nobelpriset = add("Nobelpriset", "[Nn]obelpriset", sverige);
        addInvisible("[Nn]obel pri", nobelpriset);
        Subject svenskaAkademien = add("Svenska Akademien", "Svenska Akademien", nobelpriset, sverige);
        add("Jean-Claude Arnault", "Jean-Claude Arnault", svenskaAkademien, meToo);

        Subject ukraina = add("Ukraina", "Ukrain", news);
        Subject qatar = add("Qatar", "Qatar", news);
        Subject saudi = add("Saudi Arabia", "Saudi Arabi", news);
        Subject makedonien = add("Makedonien", "[Mm]acedoni", news);
        addInvisible("Makedoni", makedonien);
        Subject yemen = add("Yemen", "Yemen", news);
        Subject iran = add("Iran", "Iran", news);
        Subject irak = add("Irak", "Irak", news);
        addInvisible("Iraq", irak);
        Subject kurdish = add("Kurder", "[Kk]urd", news);

        Subject uae = add("Förenade Arabemiraten", "Förenade Arabemiraten", news);
        addInvisible("UAE", uae);
        Subject indien = add("Indien", "Indien", news);
        addInvisible("India", indien);

        Subject indonesien = add("Indonesien", "Indonesien", news);
        addInvisible("Indonesia", indonesien);

        Subject weather = addWithoutExpression("Weather", ROOT);
        add("Tsunami", "[Tt]sunami", weather);
        add("Tyfon", "[Tt]yfon|[Tt]yphoon", weather);

        Subject eu = add("#EU", "European Union", news);
        Subject europa = add("Europa", "[Ee]urope", news);

        Subject biz = addWithoutExpression("Business", ROOT);
        add("Elon Musk", "Elon Musk", tech, biz);
        add("Tesla", "Tesla", tech, biz);

        Subject google = add("Google", "Google", tech);
        Subject microsoft = add("Microsoft", "Microsoft", tech);

        Subject apple = add("Apple", "Apple", tech);
        add("Steve Jobs", "Steve Jobs", apple);
        Subject amazon = add("Amazon", "Amazon", tech);
        add("Alexa", "Alexa", tech, amazon, ai);


        Subject spanien = add("Spanien", "Spanien", news);
        addInvisible("Spain", spanien);
        add("Barcelona", "Barcelona", spanien);
        add("Katalonien", "Katalonien|Catala", spanien);

        Subject myanmar = add("Myanmar", "Myanmar", news);
        add("Aung San Suu Kyi", "Aung San Suu Kyi", myanmar);
        add("Rohingya", "Rohingya", myanmar);

        Subject frankrike = add("Frankrike", "Frankrike|France|[Ff]rench|[Ff]ransk", news);


        Subject sport = addWithoutExpression("Sport", HIDE_SPORT, ROOT);
        Subject hockey = add("Hockey", "[Hh]ockey", sport);
        Subject fotboll = add("Fotboll", "[Ff]otboll", sport);
        Subject golf = add("Golf", "[Gg]olftävling|Ryder Cup|Henrik Stenson", sport);
        add("Tiger Woods", "Tiger Woods", golf);
        add("Grand slam", "[Gg]rand slam", sport);
        add("OS-guld", "OS\\-guld", sport);
        addInvisible("VM\\-titel", sport);
        add("Rallycross", "[Rr]allycross", sport);

        Subject kultur = addWithoutExpression("Kultur", HIDE_CULTURE, ROOT);
        addInvisible("kultur\\-noje", kultur);
        Subject music = addWithoutExpression("Musik", kultur);
        addInvisible("klassisk rock", music);


        Subject bad = addWithoutExpression("#Bad", HIDE_BAD, ROOT);
        add("SVT::Snabbkollen", "svt.*snabbkollen", bad);
        add("TheLocal::WordOfTheDay", "thelocal.*word-of-the-day", bad);
        add("Show HN", "Show HN", bad);

        Subject celebrities = addWithoutExpression("Kändisar", kultur);
        add("Bono", "U2.*Bono", celebrities);


        return subjects;
    }

    private static Subject addWithoutExpression(String name, boolean hide, Subject... parents) {
        boolean hashTag = true;
        boolean showAsTab = true;
        return add(name, null, hide, hashTag, showAsTab, parents);
    }

    private static Subject addInvisible(String expression, Subject... parents) {
        boolean hide = false;
        boolean isHashTag = false;
        boolean showAsTab = false;
        return add("invisible:"+expression, expression, hide, isHashTag, showAsTab, parents);
    }

    public static Subject addWithoutExpression(String name, Subject... parents) {
        boolean hide = false;
        boolean isHashTag = true;
        boolean showAsTab = true;
        return add(name, null, hide, isHashTag, showAsTab, parents);
    }

    public static Subject add(String name, @Nullable String expression, Subject... parents) {
        boolean hide = false;
        boolean isHashTag = true;
        boolean showAsTab = true;
        return add(name, expression, hide, isHashTag, showAsTab, parents);
    }

    private static Subject add(String name, @Nullable String expression, boolean hide, boolean isHashTag, boolean showAsTab, Subject... parents) {
        int size = subjects.size();
        Subject subject = new Subject(Arrays.asList(parents), name, expression, hide, isHashTag, showAsTab);
        subjects.add(subject);
        if (size == subjects.size()) {
            throw new IllegalStateException("Duplicate detected! " + name + ", " + expression);
        }
        return subject;
    }
}
