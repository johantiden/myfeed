package se.johantiden.myfeed.persistence;


import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static se.johantiden.myfeed.persistence.Subject.ROOT;

public class SubjectClassifier {

    private static final Set<Subject> SUBJECTS = new HashSet<>();
    public static final boolean HIDE_SPORT = false;
    public static final boolean HIDE_CULTURE = false;
    public static final boolean HIDE_BAD = false;

    public static Set<Subject> getSubjects() {
        SUBJECTS.add(ROOT);

        Subject fun = addWithoutExpression("#Fun", ROOT);
        Subject comics = addWithoutExpression("Comics", fun);
        add("xkcd", "xkcd", comics);
        add("Ernie", "serier/ernie", comics);

        Subject reddit = add("Reddit", "reddit\\.com", fun);
        add("r/funny", "reddit\\.com/r/funny", reddit);



        Subject news = addWithoutExpression("Nyheter", ROOT);
        Subject meToo = add("#metoo", "[Mm]e[Tt]oo", news);


        {
            Subject usa = add("#USA", "USA|U\\.S\\.", news);
            add("Florida", "Florida", usa);
            add("Kalifornien", "[KkCc]aliforni", usa);
            add("Hawaii", "[Hh]awaii", usa);
            Subject arizona = add("Arizona", "Arizona", usa);
            add("Ohio", "Ohio|Cincinnati", usa);
            add("Texas", "Texas", usa);
            add("New York", "New York", usa);

            Subject usaPolitik = add("USA-politik", "USA-politik", news, usa);
            add("Jeff Flake", "Jeff Flake", usaPolitik, arizona);
            add("Brett Kavanaugh", "Kavanaugh", usaPolitik);
            add("Paul Ryan", "Paul Ryan", usaPolitik);
            add("Mike Pompeo", "[Pp]ompeo", usaPolitik);

            add("Trump", "Trump", usa);
            add("Bill Clinton", "Bill Clinton", usa);
            add("NAFTA", "NAFTA", usaPolitik);
            add("Republikanerna", "washington.*[Rr]epublican", usaPolitik);
            add("Obamacare", "Obamacare", usaPolitik);
        }

        {
            Subject sverige = add("Sverige", "[Ss]verige|[Ss]wedish|[Ss]weden", news);
            addInvisible("[Ss]vensk", sverige);
            addInvisible("TheLocal", sverige);
            Subject inrikespolitik = add("Inrikespolitik", "Inrikespolitik", sverige);
            addInvisible("[Ss]vensk politik", inrikespolitik);
            Subject alliansen = add("Alliansen", "Alliansparti", inrikespolitik);
            Subject centerpartiet = add("Centerpartiet", "Centerpartiet", inrikespolitik, alliansen);
            Subject moderaterna = add("Moderatena", "Moderaterna", inrikespolitik, alliansen);
            add("Ulf Kristersson", "Ulf Kristersson", moderaterna);
            add("Annie Lööf", "Annie Lööf", centerpartiet);
            Subject socialdemokraterna = add("Socialdemokraterna", "[Ss]ocialdemokraterna", inrikespolitik);
            add("Stefan Löfven", "Stefan Löfven", socialdemokraterna);
            Subject sverigedemokraterna = add("Sverigedemokraterna", "Sverigedemokraterna", inrikespolitik);
            add("Jimmie Åkesson", "Jimmie Åkesson", sverigedemokraterna);
            Subject talmannen = add("Talmannen", "[Tt]almannen", inrikespolitik);
            add("Andreas Norlén", "Andreas Norlén", talmannen, inrikespolitik);
            addInvisible("[Ll]andsting", inrikespolitik);
            addInvisible("[Kk]ommuner", inrikespolitik);
            addInvisible("[Jj]ustitieombudsman", inrikespolitik);
            addInvisible("Försäkringskassan", inrikespolitik);

            addInvisible("[Ii]nrikes", sverige);
            addInvisible("[Tt]ågtrafik", sverige);
            addInvisible("[Kk]olmården", sverige);

            add("Stockholm", "Stockholm", sverige);
            add("Skövde", "Skövde", sverige);
            add("Malmö", "Malmö", sverige);
            add("Göteborg", "Göteborg|Gothenburg", sverige);

            add("Studiemedel", "CSN|[Ss]tudiemedel|[Ss]tudielån", sverige);


            {
                Subject nobelpriset = add("Nobelpriset", "[Nn]obel", sverige);
                addInvisible("[Nn]obel pri", nobelpriset);
                Subject svenskaAkademien = add("Svenska Akademien", "Svenska Akademien|Swedish Academy", sverige);
                add("Jean-Claude Arnault", "Jean-Claude Arnault", svenskaAkademien, meToo);
            }
        }

        Subject opinion = addWithoutExpression("Opinion", news);
        addInvisible("[Rr]iktlinje", opinion);
        addInvisible("DEBATT", opinion);

        Subject equality = add("Jämställdhet", "[Jj]ämställdhet", news);
        Subject hbtq = add("HBTQ", "hbtq", equality);
        addInvisible("[Hh]omosexu", hbtq);

        add("Flyktingar", "flykting", news);


        Subject tech = addWithoutExpression("Tech", ROOT);
        {
            {
                Subject science = addWithoutExpression("Science", tech);
                addInvisible("[Pp]hys\\.org", science);
                addInvisible("[Qq]uantum", science);
                addInvisible("[Tt]ransistor", science);
                addInvisible("[Rr]esearch", science);
                addInvisible("[Pp]olymer", science);
                addInvisible("[Ff]orskare", science);
                addInvisible("[Ff]orskning", science);
                add("Influenza", "Influenza", science);
                add("Leukemi", "[Ll]eukemi", science);
                add("Ultraljud", "[Uu]ltrasound", science);
            }

            Subject internet = add("Internet", "[Ii]nternet", tech);
            Subject linux = add("Linux", "[Ll]inux", tech);
            addInvisible("Ubuntu", linux);
            addInvisible("KDE", linux);
            Subject ai = add("Artificial Intelligence", "[Aa]rtificial[ \\-][Ii]ntelligence", tech);
            addInvisible("[Rr]obot", ai);
            Subject machineLearning = add("Machine Learning", "[Mm]achine [Ll]earning", ai);
            add("Neural Networks", "[Nn]eural [Nn]et", machineLearning);
            add("Cellular Automata", "[Cc]ellular [Aa]utomata", machineLearning);

            add("Niklas Zennström", "Zennström", tech);
            addInvisible("HackerNews", tech);
            addInvisible("Slashdot", tech);

            Subject software = add("Software", "[Ss]oftware", tech);
            addInvisible("[Pp]rogramming", software);
            addInvisible("[Pp]rogrammer", software);
            addInvisible("[Ww]eb [Ff]ramework", software);
            addInvisible("[Dd]atabase", software);
            addInvisible("[Cc]lojure", software);
            addInvisible("[Hh]askell", software);
            addInvisible("[Oo]pen [Ss]ource", software);
            addInvisible("[Jj]ava[Ss]cript|\\.js", software);

            addInvisible("World Wide Web", internet);
            addInvisible("Engadget", tech);
            Subject facebook = add("Facebook", "Facebook", tech);
            add("Cambridge Analytica", "Cambridge Analytica", facebook, tech);

            add("Tim Berners-Lee", "Tim Berners-Lee", internet);
        }

        add("Klimat", "[Kk]limat|[Cc]limate", news);


        Subject terrorism = add("Terrorism", "[T]error", news);
        add("Boko Haram", "[Bb]oko [Hh]aram", terrorism);
        add("ISIL", "ISIL|ISIS|[Ii]slamic [Ss]state", terrorism);


        add("Påven", "[Pp]ope|Påve", news);
        add("Journalist", "[Jj]ournalist", news);
        add("Amnesty", "Amnesty", news);


        {
            add("Ukraina", "Ukrain", news);
            add("Qatar", "Qatar", news);
            Subject makedonien = add("Makedonien", "[Mm]acedoni", news);
            addInvisible("Makedoni", makedonien);
            add("Yemen", "Yemen", news);
            add("Iran", "Iran", news);
            add("Irak", "[Ii]ra[kq]", news);
            add("Kurder", "[Kk]urd", news);

            Subject brasilien = add("Brasilien", "[Bb]ra[sz]il", news);
            add("Jair Bolsonaro", "[Bb]olsonaro", brasilien);
            Subject uae = add("Förenade Arabemiraten", "Förenade Arabemiraten", news);
            addInvisible("UAE", uae);
            Subject indien = add("Indien", "Indien", news);
            addInvisible("India", indien);

            Subject indonesien = add("Indonesien", "Indonesien", news);
            addInvisible("Indonesia", indonesien);

            Subject spanien = add("Spanien", "Spanien", news);
            addInvisible("Spain", spanien);
            add("Barcelona", "Barcelona", spanien);
            add("Katalonien", "Katalonien|Catala", spanien);

            Subject myanmar = add("Myanmar", "Myanmar", news);
            add("Aung San Suu Kyi", "Aung San Suu Kyi", myanmar);
            add("Rohingya", "Rohingya", myanmar);

            add("Frankrike", "Frankrike|France|[Ff]rench|[Ff]ransk", news);

            Subject argentina = add("Argentina", "[Aa]rgentin", news);
            add("Buenos Aires", "Buenos Aires", argentina);

            {
                Subject afrika = add("Afrika", "Afrika|Africa", news);
                add("Egypt", "Egypt", afrika);
                add("Botswana", "Botswana", news, afrika);
                Subject kenya = add("Kenya", "Kenya", afrika);
                add("Nairobi", "Nairobi", kenya);
                add("Burkina Faso", "Burkina Faso", afrika);
                add("Tunisien", "[Tt]unisia", afrika);
                add("Nigeria", "[Nn]igeria", afrika);
                add("Libyen", "[Ll]ibya|[Ll]ibyen", afrika);
                add("Kamerun", "[Kk]amerun|[Cc]ameroon", afrika);

            }
            add("Maldives", "Maldives", news);

            {
                Subject turkiet = add("Turkiet", "Turkiet", news);
                add("Erdogan", "Erdogan", turkiet);
                addInvisible("Turkey", turkiet);
            }

            add("Grekland", "[Gg]rekland|[Gg]rekisk|[Gg]reece", news);
            add("Cypern", "[Cc]ypern|[Cc]yprus|[Cc]ypriot", news);

            Subject israel = add("Israel", "Israel", news);
            Subject palestina = add("Palestina", "Palestin", news);
            add("Gaza", "Gaza", israel, palestina);
            add("Libanon", "[Ll][ei]banon", news);

            add("Thailand", "[Tt]hail[aä]nd", news);

            add("Gabon", "Gabon", news);
            add("Slovakien", "Slovak", news);

            Subject japan = add("Japan", "Japan", news);
            add("Okinawa", "Okinawa", japan);
            add("Tokyo", "Tokyo", japan);

            add("Malta", "Malta|Maltese", news);

            add("Afghanistan", "Afghan|Afgan", news);

            Subject ryssland = add("Ryssland", "Russia|Ryssland|[Rr]ysk", news);
            add("Moskva", "Moscow|Moskva", ryssland);
            add("Vladimir Putin", "Putin", ryssland);

            add("Lettland", "[Ll]ettland|[Ll]atvia", news);
            add("Bulgarien", "[Bb]ulgari", news);

            Subject syrien = add("Syrien", "[Ss]yria|[Ss]yrien", news);
            add("Idlib", "Idlib", syrien);

            add("Saudiarabien", "[Ss]audi", news);

            Subject greatBritain = add("Storbritannien", "Great Britain|Storbritannien|British", news);
            add("Tories", "Tories", greatBritain);
            addInvisible("U\\.K\\.", greatBritain);

            add("Brexit", "Brexit", news, greatBritain);
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


            Subject ungern = add("Ungern", "Ungern|Hungary", news);
            add("Budapest", "Budapest", ungern);
            add("Bosnien", "Bosnien|Bosnia", news);
            add("Rumänien", "[Rr]umän|[Rr]omania", news);
            Subject danmark = add("Danmark", "[Dd]anmark|[Dd]ansk|[Dd]enmark|[Dd]anish", news);
            add("Köpenhamn", "Köpenhamn|Copenhagen", danmark);
            add("Nauru", "Nauru", news);
            add("Guatemala", "Guatemala", news);

            add("Mexiko", "Mexico|Mexiko", news);

            add("El Salvador", "El Salvador", news);
            add("Kina", "Kina|China", news);

            Subject nordkorea = add("Nordkorea", "Nordkorea", news);
            add("Kim Jong-Un", "Kim Jong-Un", nordkorea);

            Subject sydkorea = add("Sydkorea", "Sydkorea", news);
            add("Pyeongchang", "Pyeongchang", sydkorea);

            add("Singapore", "Singapore", news);
            add("Portugal", "[Pp]ortugal", news);

            Subject europa = add("Europa", "[Ee]urope", news);
            add("#EU", "European Union", news, europa);
        }

        {
            Subject weather = addWithoutExpression("Weather", ROOT);
            addInvisible("[Tt]emperatur", weather);
            add("Tsunami", "[Tt]sunami", weather);
            add("Tyfon", "[Tt]yfon|[Tt]yphoon", weather);
            add("Jordbävning", "[Jj]ordbävning|[Ee]arth [Qq]uake", weather);
            add("Skogsbrand", "[Ss]kogsbrand", weather);
        }
        {
            Subject biz = addWithoutExpression("Business", ROOT);
            add("Elon Musk", "Elon Musk", tech, biz);
            add("Tesla", "Tesla", tech, biz);
            add("#BMW", "BMW", tech, biz);
            add("Ekonomi", "dn\\.se/ekonomi", biz);

            add("Google", "Google", biz, tech);
            add("Microsoft", "Microsoft", biz, tech);
            add("Snapchat", "Snapchat", biz, tech);

            Subject apple = add("Apple", "Apple", biz, tech);
            add("Steve Jobs", "Steve Jobs", apple);

            add("Amazon", "Amazon", biz, tech);
        }

        add("Interpol", "Interpol", news);

        {
            Subject sport = addWithoutExpression("Sport", HIDE_SPORT, ROOT);
            addInvisible("Sportbladet", sport);
            add("Hockey", "[Hh]ockey", sport);
            Subject fotboll = add("Fotboll", "[Ff]otboll", sport);
            add("#AIK", "AIK", sport);
            addInvisible("Brommapojkarna", fotboll);
            Subject golf = add("Golf", "[Gg]olftävling|Ryder Cup|Henrik Stenson", sport);
            add("Tiger Woods", "Tiger Woods", golf);
            add("Grand slam", "[Gg]rand slam", sport);
            add("OS-guld", "OS\\-guld", sport);
            addInvisible("VM\\-titel", sport);
            addInvisible("[Vv]ärldscup", sport);
            addInvisible("50 meter fritt", sport);
            addInvisible("[Gg]uldstrid", sport);
            addInvisible("SM-guld", sport);
            addInvisible("VM-guld", sport);
            addInvisible("OS-guld", sport);
            addInvisible("[Oo]lympic [Gg]ames", sport);
            add("Rallycross", "[Rr]allycross", sport);
        }
        {
            Subject kultur = addWithoutExpression("Kultur", HIDE_CULTURE, ROOT);
            add("DN-Kultur&Nöje", "kultur\\-noje", kultur);

            Subject books = add("Böcker", "Böcker", kultur);
            add("DN-Bok", "dnbok", books);

            Subject music = addWithoutExpression("Musik", kultur);
            addInvisible("klassisk rock", music);

            Subject konst = addWithoutExpression("Konst", kultur);
            add("Andy Warhol", "Andy Warhol", konst);

            Subject celebrities = addWithoutExpression("Kändisar", kultur);
            add("Bono", "U2.*Bono", celebrities);
        }

        Subject bad = addWithoutExpression("#Bad", HIDE_BAD, ROOT);
        add("SVT::Snabbkollen", "svt.*snabbkollen", bad);
        add("TheLocal::WordOfTheDay", "thelocal.*word-of-the-day", bad);
        add("HackerNews::Show HN", "Show HN", bad);
        add("NYT::Your Briefing", "Your.*Briefing", bad);
        add("DN::webb-tv", "webb-tv", bad);
        add("Engadget::Wirecutter", "[Ee]ngadget.*Wirecutter", bad);



        return SUBJECTS;
    }

    private static Subject addWithoutExpression(String name, boolean hide, Subject... parents) {
        boolean hashTag = true;
        boolean showAsTab = true;
        return add(name, null, hide, hashTag, showAsTab, parents);
    }

    private static void addInvisible(String expression, Subject... parents) {
        boolean hide = false;
        boolean isHashTag = false;
        boolean showAsTab = false;
        add("invisible:" + expression, expression, hide, isHashTag, showAsTab, parents);
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
        int size = SUBJECTS.size();
        Subject subject = new Subject(Arrays.asList(parents), name, expression, hide, isHashTag, showAsTab);
        SUBJECTS.add(subject);
        if (size == SUBJECTS.size()) {
            throw new IllegalStateException("Duplicate detected! " + name + ", " + expression);
        }
        return subject;
    }
}
