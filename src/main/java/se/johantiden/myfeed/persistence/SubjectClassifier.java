package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.util.DocumentPredicates;
import se.johantiden.myfeed.util.JPredicates;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static se.johantiden.myfeed.persistence.Subject.ROOT;

public class SubjectClassifier {

    private static final Set<Subject> SUBJECTS = new HashSet<>();
    public static final boolean HIDE_SPORT = true;
    public static final boolean HIDE_CULTURE = true;
    public static final boolean HIDE_BAD = true;

    public static Set<Subject> getSubjects() {
        SUBJECTS.add(ROOT);

        Subject fun = addWithoutExpression("#Fun", ROOT);
        Subject comics = addWithoutExpression("Comics", fun);
        add("xkcd", "xkcd", comics);
        add("Ernie", "serier/ernie", comics);

        Subject reddit = add("Reddit", "reddit.com", fun);
        add("r/funny", "reddit.com/r/funny", reddit);



        Subject news = addWithoutExpression("Nyheter", ROOT);
        Subject meToo = add("#metoo", "metoo", news);

        add("Harvey Weinstein", "Harvey Weinstein", meToo);

        Subject opinion = addWithoutExpression("Opinion", news);
        addInvisible("riktlinje", opinion);
        addInvisible("DEBATT", opinion);
        addInvisible("dn.se/asikt", opinion);


        {
            Subject sjuk = add("Sjukdom & Hälsa", "sjukdom", news);
            add("Influenza", "Influenza", sjuk);
            add("Leukemi", "leukemi", sjuk);
            add("Cancer", "cancer", sjuk);
            add("Klimakterie", "klimakterie", sjuk);
        }

        Subject tech = add("Tech", "tech ", ROOT);
        {
            {
                Subject science = add("Science", "science", tech);
                addInvisible("phys.org", science);
                addInvisible("quantum", science);
                addInvisible("transistor", science);
                addInvisible("research", science);
                addInvisible("polymer", science);
                addInvisible("forskare", science);
                addInvisible("forskning", science);
                add("Ultraljud", "ultrasound", science);
            }

            add("Show HN", "Show HN", tech);


            Subject miljo = add("Mijlö", l("klimat", "climate"), news);
            addInvisible("environment", miljo);
            add("Återvinning", "återvinning", miljo);


            Subject internet = add("Internet", "internet", tech);
            Subject linux = add("Linux", "linux", tech);
            addInvisible("Ubuntu", linux);
            addInvisible("Linus Torvalds", linux);
            addInvisible("KDE", linux);
            Subject ai = add("Artificial Intelligence", l("artificial intelligence","artificial-intelligence"), tech);
            addInvisible("robot", ai);
            Subject machineLearning = add("Machine Learning", "machine learning", ai);
            add("Neural Networks", "neural net", machineLearning);
            add("Cellular Automata", "cellular automata", machineLearning);

            add("Niklas Zennström", "Zennström", tech);
            addInvisible("ycombinator", tech);

            Subject software = add("Software", "software", tech);
            addInvisible("programming", software);
            addInvisible("programmer", software);
            addInvisible("web framework", software);
            addInvisible("database", software);
            addInvisible("computation", software);
            addInvisible("clojure", software);
            addInvisible("haskell", software);
            addInvisible("open source", software);
            addInvisible("Javascript", l("javascript",".js"), software);

            addInvisible("World Wide Web", internet);
            addInvisible("Engadget", tech);
            Subject facebook = add("Facebook", "Facebook", tech);
            add("Cambridge Analytica", "Cambridge Analytica", facebook, tech);

            add("Tim Berners-Lee", "Tim Berners-Lee", internet);

            addInvisible("science.slashdot", tech);
            addInvisible("news.slashdot", news);
            addInvisible("developers.slashdot", software);
        }



        {


            {
                Subject sydAmerika = addWithoutExpression("Sydamerika", news);

                Subject argentina = add("Argentina", "Argentin", sydAmerika);
                add("Buenos Aires", "Buenos Aires", argentina);
                add("Chile", "Chile", sydAmerika);
                add("Honduras", "Hondura", sydAmerika);

                Subject brasilien = add("Brasilien", l("brasil","brazil"), sydAmerika);
                add("Jair Bolsonaro", "bolsonaro", brasilien);
                add("Bolivia", "Bolivia", sydAmerika);
                add("Guatemala", "Guatemala", sydAmerika);

                add("Mexiko", l("Mexico","Mexiko"), sydAmerika);
                add("Colombia", "colombia", sydAmerika);

                add("El Salvador", "El Salvador", sydAmerika);
            }

            {
                Subject afrika = add("Afrika", l("Afrika","Africa"), news);

                add("Algeriet", l("algeria", "algeriet"), afrika);
                add("Sudan", "Sudan", afrika);
                add("Botswana", "Botswana", afrika);
                add("Burkina Faso", "Burkina Faso", afrika);
                add("Demokratiska Republiken Kongo", l("Democratic Republic of Congo","Demokratiska Republiken Kongo"), afrika);
                add("Egypt", "Egypt", afrika);
                add("Eritrea", "eritrea", afrika);
                add("Elfenbenskusten", l("ivory coast","elfenbenfskusten"), afrika);
                add("Etiopien", l("ethiopia","etiop"), afrika);
                add("Gabon", "Gabon", afrika);
                add("Kongo", "Kongo", afrika);
                add("Libyen", l("libya","libyen"), afrika);
                add("Nigeria", "nigeria", afrika);
                add("Kamerun", l("kamerun","cameroon"), afrika);
                Subject kenya = add("Kenya", "Kenya", afrika); add("Nairobi", "Nairobi", kenya);
                add("Senegal", "Senegal", afrika);
                add("Tunisien", "tunisia", afrika);
                add("Uganda", "uganda", afrika);
            }


            {
                Subject europe = add("Europa", l("europe", "europa"), news);
                add("#EU", "European Union", europe);

                add("Georgien", l("Georgia", "Georgien"), europe);
                {
                    Subject turkiet = add("Turkiet", "Turkiet", europe);
                    add("Erdogan", "Erdogan", turkiet);
                    addInvisible("Turkey", turkiet);
                }

                add("Grekland", l("grekland","grekisk","greece"), europe);
                add("Cypern", l("cypern","cyprus","cypriot"), europe);
                add("Frankrike", l("Frankrike","France","french","fransk"), europe);

                add("Slovakien", "Slovak", europe);
                add("Norge", l("norsk", "norge", "norway", "norwegian"), europe);

                add("Malta", l("Malta","Maltese"), europe);

                add("Lettland", l("lettland","latvia"), europe);
                add("Bulgarien", "bulgari", europe);
                add("Serbien", l("serbisk","serbien","serbia"), europe);
                {
                    Subject greatBritain = add("Storbritannien", l("Great Britain","Storbritannien","British"), europe);
                    addRegex("Tories", "Tories", greatBritain);
                    add("Prince Harry", "Prince Harry", greatBritain);
                    addInvisible("U.K.", greatBritain);

                    add("Brexit", "Brexit", greatBritain);
                    add("Leicester", "Leicester", greatBritain);
                    add("Theresa May", "Theresa May", greatBritain);
                    add("London", "London", greatBritain);
                    add("Birmingham", "Birmingham", greatBritain);
                }

                add("Ukraina", "Ukrain", europe);
                add("Portugal", "portugal", europe);

                Subject makedonien = add("Makedonien", "macedoni", europe);
                addInvisible("Makedoni", makedonien);

                {

                    Subject spanien = add("Spanien", "Spanien", europe);
                    addInvisible("Spain", spanien);
                    add("Barcelona", "Barcelona", spanien);
                    add("Katalonien", l("Katalonien","Catala"), spanien);
                    add("Mallorca", "Mallorca", spanien);
                }
                add("Italien", l("Italien","Italian","Italy"), europe);
                add("Nederländerna", l("Nederländerna","Netherlands","Dutch","Holland","Holländ"), europe);

                Subject tyskland = add("Tyskland", l("German","Tysk"), europe);
                add("Angela Merkel", "merkel", tyskland);

                Subject ungern = add("Ungern", l("Ungern","Hungary"), europe);
                add("Bosnien", l("Bosnien","Bosnia"), europe);
                add("Rumänien", l("rumän","romania"), europe);
                Subject danmark = add("Danmark", l("danmark", "dansk", "denmark", "danish"), europe);
                add("Budapest", "Budapest", ungern);
                add("Köpenhamn", l("Köpenhamn", "Copenhagen"), danmark);

                add("Polen", l("polen", "poland", "polish", "polsk"), europe);


                {
                    Subject sverige = add("Sverige", l("sverige", "swedish", "sweden"), news);
                    addInvisible("svensk", sverige);
                    addInvisible("TheLocal", sverige);
                    Subject inrikespolitik = add("Inrikespolitik", "Inrikespolitik", sverige);
                    addInvisible("svensk politik", inrikespolitik);

                    Subject fi = add("Feministiskt initiativ", "Feministiskt initiativ", inrikespolitik);
                    add("Gudrun Schyman", "Gudrun Schyman", fi);

                    add("Vänsterpartiet", "Vänsterpartiet", inrikespolitik);
                    Subject miljopartiet = add("Miljöpartiet", "miljöpartiet", inrikespolitik);
                    add("Gustav Fridolin", "Fridolin", miljopartiet);

                    add("Bildt", "bildt", inrikespolitik);

                    Subject alliansen = add("Alliansen", "Alliansparti", inrikespolitik);

                    Subject moderaterna = add("Moderatena", "Moderaterna", inrikespolitik, alliansen);
                    add("Ulf Kristersson", "Ulf Kristersson", moderaterna);

                    Subject centerpartiet = add("Centerpartiet", "Centerpartiet", inrikespolitik, alliansen);
                    add("Annie Lööf", "Lööf", centerpartiet);

                    Subject socialdemokraterna = add("Socialdemokraterna", "socialdemokraterna", inrikespolitik);
                    add("Stefan Löfven", "Stefan Löfven", socialdemokraterna);

                    Subject sverigedemokraterna = add("Sverigedemokraterna", "Sverigedemokraterna", inrikespolitik);
                    add("Jimmie Åkesson", "Jimmie Åkesson", sverigedemokraterna);
                    Subject talmannen = add("Talmannen", "talmannen", inrikespolitik);
                    add("Andreas Norlén", "Andreas Norlén", talmannen, inrikespolitik);
                    addInvisible("landsting", inrikespolitik);
                    addInvisible("kommuner", inrikespolitik);
                    addInvisible("justitieombudsman", inrikespolitik);
                    addInvisible("Försäkringskassan", inrikespolitik);

                    addInvisible("inrikes", sverige);
                    addInvisible("tågtrafik", sverige);
                    addInvisible("kolmården", sverige);

                    Subject stockholm = add("Stockholm", "Stockholm", sverige);
                    addInvisible("Arlanda", stockholm);
                    add("Skövde", "Skövde", sverige);
                    add("Malmö", "Malmö", sverige);
                    add("Arvidsjaur", "Arvidsjaur", sverige);
                    add("Göteborg", l("Göteborg", "Gothenburg"), sverige);

                    addRegex("SF-Bio", "SF.*[Bb]io", sverige);

                    add("Studiemedel", l("CSN", "studiemedel", "studielån"), sverige);


                    {
                        Subject nobelpriset = add("Nobelpriset", "nobel", sverige);
                        addInvisible("nobel pri", nobelpriset);
                        Subject svenskaAkademien = add("Svenska Akademien", l("Svenska Akademien", "Swedish Academy"), sverige);
                        add("Jean-Claude Arnault", "Jean-Claude Arnault", svenskaAkademien, meToo);
                    }
                }

            }


            {
                Subject asien = add("Asien", l("asien", "asia"), news);

                add("Maldives", "Maldives", asien);
                add("Thailand", l("thailand", "thailänd"), asien);
                add("Sri Lanka", "sri lank", asien);
                add("Kashmir", "Kashmir", asien);

                Subject myanmar = add("Myanmar", "Myanmar", asien);
                add("Aung San Suu Kyi", "Aung San Suu Kyi", myanmar);
                add("Rohingya", "Rohingya", myanmar);

                Subject japan = add("Japan", "Japan", asien);
                add("Shinzo Abe", "Shinzo Abe", japan);
                add("Okinawa", "Okinawa", japan);
                add("Tokyo", "Tokyo", japan);

                add("Malaysia", "malaysia", asien);

                Subject ryssland = add("Ryssland", l("Russia", "Ryssland", "rysk"), asien);
                add("Moskva", l("Moscow", "Moskva"), ryssland);
                add("Vladimir Putin", "Putin", ryssland);
                add("Indien", l("indien", "india"), asien);
                add("Pakistan", "pakistan", asien);
                add("Indonesien", l("indonesien", "indonesia"), asien);

                add("Kina", l("Kina", "China", "kines", "chinee"), asien);

                Subject nordkorea = add("Nordkorea", l("nordkorea", "north korea"), asien);
                add("Kim Jong-Un", l("Kim Jong-Un", "Kim Jong Un"), nordkorea);

                Subject sydkorea = add("Sydkorea", l("sydkorea", "south korea"), asien);
                add("Pyeongchang", "Pyeongchang", sydkorea);
                add("Moon Jae-in", "moon jae-in", sydkorea);

                add("Singapore", "Singapore", asien);
            }


            {
                Subject middleEast = add("Mellanöstern", l("mellanöstern", "middle east"), news);
                Subject israel = add("Israel", "Israel", middleEast);
                Subject palestina = add("Palestina", "Palestin", middleEast);
                add("Gaza", "Gaza", israel, palestina);
                add("Libanon", l("libanon", "lebanon"), middleEast);

                add("Afghanistan", l("Afghan", "Afgan"), middleEast);


                Subject syrien = add("Syrien", l("syria", "syrien"), middleEast);
                add("Idlib", "Idlib", syrien);

                add("Jordanien", "jordan", middleEast);

                Subject saudiarabien = add("Saudiarabien", "saudi", middleEast);
                add("Jamal Khashoggi", "khashoggi", saudiarabien);

                add("Qatar", "Qatar", middleEast);


                add("Yemen", "Yemen", middleEast);
                add("Iran", "Iran", middleEast);
                add("Irak", l("iraq", "irak"), middleEast);
                add("Kurder", "kurd", middleEast);


                Subject uae = add("Förenade Arabemiraten", "Förenade Arabemiraten", middleEast);
                addInvisible("UAE", uae);
            }

            {
                Subject oceanien = addWithoutExpression("Oceanien", news);
                add("Mikronesien", l("Mikronesien", "micronesia"), oceanien);
                add("Nauru", "Nauru", oceanien);
                add("Australien", l("australia", "australien"), oceanien);
                add("Nya Zealand", l("New Zealand", "Nya Zeeland"), oceanien);

            }


            {
                Subject nordAmerika = addWithoutExpression("Nordamerika", news);
                {
                    Subject usa = add("#USA", l("USA", "U.S.", "US election"), nordAmerika);
                    add("Florida", "Florida", usa);
                    Subject pittsburgh = add("Pittsburgh", "Pittsburgh", usa);
                    add("Robert Bowers", "Robert Bowers", pittsburgh);
                    add("Kalifornien", l("kaliforni", "californi"), usa);
                    add("Hawaii", "hawaii", usa);
                    Subject arizona = add("Arizona", "Arizona", usa);
                    add("Ohio", l("Ohio", "Cincinnati"), usa);
                    add("Texas", "Texas", usa);

                    Subject usaPolitik = add("USA-politik", "USA-politik", usa);
                    addInvisible("Planned Parenthood", usaPolitik);

                    add("Jeff Flake", "Jeff Flake", usaPolitik, arizona);
                    add("Brett Kavanaugh", "Kavanaugh", usaPolitik);
                    add("Bernie Sanders", "Sanders", usaPolitik);
                    add("Paul Ryan", "Paul Ryan", usaPolitik);
                    add("Mike Pompeo", "pompeo", usaPolitik);

                    add("Trump", "Trump", usa);
                    add("Bill Clinton", "Bill Clinton", usa);
                    add("NAFTA", "NAFTA", usaPolitik);
                    Subject usRepuplicans = addRegex("Republikanerna", "washington.*republican", usaPolitik);
                    addInvisible("GOP", usRepuplicans);

                    add("Obamacare", "Obamacare", usaPolitik);
                }

                add("Kanada", l("Canada", "Kanada", "Kanadens", "Canadian"), nordAmerika);
                add("Haiti", "haiti", nordAmerika);

            }
        }

        {
            Subject weather = addWithoutExpression("Weather & Disasters", ROOT);
            addInvisible("temperatur", weather);
            addInvisible("varmare väder", weather);
            addInvisible("kallare väder", weather);
            addInvisible("snöfall", weather);
            addInvisible("blötsnö", weather);
            addInvisible("snömängd", weather);
            addInvisible("meteorolog", weather);
            addInvisible("väderinstitut", weather);
            addInvisible("smhi", weather);
            addInvisible("grader varmt", weather);
            addInvisible("vinterdäck", weather);
            add("Tsunami", "tsunami", weather);
            add("Tyfon", l("tyfon", "typhoon"), weather);
            add("Jordbävning", l("jordbävning", "earth quake", "earthquake"), weather);
            add("Skogsbrand", "skogsbrand", weather);
            add("Flooding", "floodwater", weather);
            add("Jordskred", l("jordskred", "landslide"), weather);
        }


        Subject violence = add("Våldsbrott", l("violence", "Våldsbrott"), news);
        add("Mord", l("mord", "murder"), violence);
        addInvisible("Död person", violence);
        addInvisible("våldtäkt", violence);
        Subject hateCrime = add("Hatbrott", l("hatbrott", "hate crime"), violence);
        addInvisible("racism", hateCrime);
        addInvisible("judehat", hateCrime);
        addInvisible("antisemitism", hateCrime);
        addInvisible("anti-semitic", hateCrime);
        Subject terrorism = add("Terrorism", "terror", violence);
        add("Boko Haram", "boko haram", terrorism);
        Subject isil = add("ISIL", l("islamic state"), terrorism);
        addInvisibleRegex("ISIL", isil);
        addInvisibleRegex("ISIS", isil);

        Subject un = add("United Nations", "UN peacekeeper", news);
        addInvisibleRegex("the UN", un);

        {
            Subject biz = addWithoutExpression("Business", ROOT);
            add("Elon Musk", "Elon Musk", tech, biz);
            add("Tesla", "Tesla", tech, biz);
            add("e-handel", "e-handel", tech, biz);
            add("aktie", "aktie", biz);
            add("tillväxt", "tillväxt", biz);
            add("#BMW", "BMW", tech, biz);
            add("Hubble", "Hubble", tech);
            add("Nordnet", "Nordnet", biz);
            add("Ekonomi", "dn.se/ekonomi", biz);

            add("Google", "Google", biz, tech);
            addRegex("#IBM", "IBM", biz, tech);
            add("Warner", "warner", biz);
            add("Nokia", "nokia", biz);
            addRegex("#SEB", "SEB", biz);
            add("Nordea", "nordea", biz);
            add("Microsoft", "Microsoft", biz, tech);
            add("Snapchat", "Snapchat", biz, tech);

            Subject apple = add("Apple", "Apple", biz, tech);
            add("Steve Jobs", "Steve Jobs", apple);

            add("Amazon", "Amazon", biz, tech);
        }


        {
            Subject sport = add("Sport", "sport", HIDE_SPORT, ROOT);
            {
                addInvisible("Sportbladet", sport);
                addInvisible("målchans", sport);
                add("#AIK", "AIK", sport);
                add("landslag", "landslag", sport);
                add("Grand slam", "grand slam", sport);
                add("OS-guld", "OS-guld", sport);
                addInvisible("VM-titel", sport);
                addInvisible("världscup", sport);
                addInvisible("50 meter fritt", sport);
                addInvisible("guldstrid", sport);
                addInvisible("SM-guld", sport);
                addInvisible("VM-guld", sport);
                addInvisible("OS-guld", sport);
                addInvisible("avgörande mål", sport);
                addInvisible("straffområde", sport);
                addInvisible("olympic games", sport);
                add("Rallycross", "rallycross", sport);
                add("Gymnastik", l("gymnastics", "gymnastik"), sport);
            }



            {
                Subject fotboll = add("Fotboll", "fotboll", sport);
                addInvisible("Manchester United", fotboll);
                addInvisible("soccer", fotboll);
                addInvisible("mittback", fotboll);
                addInvisible("Brommapojkarna", fotboll);
                addInvisible("Paul Pogba", fotboll);
                addInvisible("första halvlek", fotboll);
                addInvisible("andra halvlek", fotboll);
                add("Zlatan", "zlatan", fotboll);
                addInvisible("Nations League", fotboll);

            }

            Subject golf = add("Golf", l("golftävling", "Ryder Cup", "Henrik Stenson"), sport);
            add("Tiger Woods", "Tiger Woods", golf);

            add("Hockey", "hockey", sport);
        }

        {
            Subject kultur = addWithoutExpression("Kultur", HIDE_CULTURE, ROOT);
            add("DN-Kultur&Nöje", "kultur-noje", kultur);

            Subject books = add("Böcker", "Böcker", kultur);
            add("DN-Bok", "dnbok", books);

            Subject music = addWithoutExpression("Musik", kultur);
            addInvisible("klassisk rock", music);

            Subject konst = addWithoutExpression("Konst", kultur);
            add("Andy Warhol", "Andy Warhol", konst);

            Subject celebrities = addWithoutExpression("Kändisar", kultur);
            addRegex("Bono", "U2.*Bono", celebrities);
            add("Tailor Swift", "Taylor Swift", celebrities);
            add("Kanye West", "Kanye West", celebrities);

            Subject film = addWithoutExpression("Film", kultur);
            addInvisibleRegex("film.*recension", film);
            addInvisibleRegex("recension.*film", film);
        }

        {
            Subject equality = add("Jämställdhet", "jämställdhet", news);
            Subject hbtq = add("HBTQ", "hbtq", equality);
            addInvisible("homosexu", hbtq);
        }

        add("Interpol", "Interpol", news);
        add("Flyktingar", "flykting", news);

        add("Påven", l("pope", "Påve"), news);
        add("Journalist", "journalist", news);
        add("Amnesty", "Amnesty", news);

        Subject fakeNews = add("Fake news", "fake news", news);
        add("Fact Checker", "Fact Checker", fakeNews);


        {
            Subject bad = addWithoutExpression("#Bad", HIDE_BAD, ROOT);
            add("SVT::Snabbkollen", "snabbkollen", bad);
            add("TheLocal::WordOfTheDay", "word-of-the-day", bad);
            addRegex("NYT::Your Briefing", "Your.*Briefing", bad);
            add("DN::webb-tv", "webb-tv", bad);
            add("DN::mat-dryck", "mat-dryck", bad);
            add("DN::Gratulerar", "DN gratulerar", bad);
            add("DN::Minnesord", "minnesord:", bad);
            add("DN::livsstil", "www.dn.se/livsstil", bad);
            add("DN::nutidstestet", "nutidstestet", bad);
            add("DN::motor", l("se/ekonomi/motor", "se/motor"), bad);
            addRegex("Engadget::Wirecutter", "engadget.*Wirecutter", bad);
        }


        return Collections.unmodifiableSet(SUBJECTS);
    }

    private static Subject addRegex(String name, String regex, Subject... parents) {
        return add(name, DocumentPredicates.matches(regex), false, true, true, parents);
    }

    private static Subject add(String name, Predicate<Document> predicate, Subject... parents) {
        return add(name, predicate, false, true, true, parents);
    }

    private static Predicate<Document> l(String... expressions) {
        List<Predicate<Document>> predicates = Arrays.stream(expressions).map(DocumentPredicates::has).collect(Collectors.toList());
        return JPredicates.or(predicates);
    }

    private static Subject add(String name, String expression, boolean hideSport, Subject... parents) {
        boolean hashTag = true;
        boolean showAsTab = true;
        return add(name, DocumentPredicates.has(expression), hideSport, hashTag, showAsTab, parents);
    }

    private static Subject addWithoutExpression(String name, boolean hide, Subject... parents) {
        boolean hashTag = true;
        boolean showAsTab = true;
        return add(name, d -> false, hide, hashTag, showAsTab, parents);
    }

    private static Subject addInvisible(String name, Predicate<Document> predicate, Subject... parents) {
        boolean hide = false;
        boolean isHashTag = false;
        boolean showAsTab = false;
        return add("invisible:"+name, predicate, hide, isHashTag, showAsTab, parents);
    }

    private static Subject addInvisibleRegex(String regex, Subject... parents) {
        return addInvisible(regex, DocumentPredicates.matches(regex), parents);
    }

    private static Subject addInvisible(String expression, Subject... parents) {
        return addInvisible(expression, DocumentPredicates.has(expression), parents);
    }

    public static Subject addWithoutExpression(String name, Subject... parents) {
        boolean hide = false;
        boolean isHashTag = true;
        boolean showAsTab = true;
        return add(name, d -> false, hide, isHashTag, showAsTab, parents);
    }

    public static Subject add(String name, @Nullable String expression, Subject... parents) {
        boolean hide = false;
        boolean isHashTag = true;
        boolean showAsTab = true;
        return add(name, DocumentPredicates.has(expression), hide, isHashTag, showAsTab, parents);
    }

    private static Subject add(String name, @Nonnull Predicate<Document> predicate, boolean hide, boolean isHashTag, boolean showAsTab, Subject... parents) {
        int size = SUBJECTS.size();
        Subject subject = new Subject(Arrays.asList(parents), name, predicate, hide, isHashTag, showAsTab);
        SUBJECTS.add(subject);
        if (size == SUBJECTS.size()) {
            throw new IllegalStateException("Duplicate detected! " + name);
        }
        return subject;
    }
}
