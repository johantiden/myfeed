package se.johantiden.myfeed.persistence;


import se.johantiden.myfeed.persistence.Subject.SubjectType;
import se.johantiden.myfeed.util.DocumentPredicates;
import se.johantiden.myfeed.util.JPredicates;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static se.johantiden.myfeed.persistence.Subject.ALL;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.BASE;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.CATEGORY;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.LOCAL;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.CONTINENT;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.COUNTRY;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.EVENT;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.FEED;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.ORGANIZATION;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.PERSON;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.SUBJECT;
import static se.johantiden.myfeed.persistence.Subject.SubjectType.SUB_FEED;

public final class SubjectClassifier {

    private static final Set<Subject> SUBJECTS = new HashSet<>();
    public static final boolean HIDE_SPORT = true;
    public static final boolean HIDE_CULTURE = true;
    public static final boolean HIDE_BAD = true;

    private SubjectClassifier() {}

    public static Set<Subject> getSubjects() {
        SUBJECTS.add(ALL);

        Subject fun = addWithoutExpression("#Fun", CATEGORY, ALL);
        Subject comics = addWithoutExpression("Comics", SUBJECT, fun);
        add("Ernie", l("serier/ernie"), SUBJECT, comics);

        add("Reddit", l("reddit"), FEED, ALL);
        Subject news = add("Nyheter", l("news.slashdot"), CATEGORY, ALL);
        Subject meToo = add("#metoo", l("metoo"), SUBJECT, news);

        add("Harvey Weinstein", l("Harvey Weinstein"), PERSON, meToo);

        add("Opinion",
                l(
                        "riktlinje",
                        "DEBATT",
                        "dn.se/asikt"
                ), CATEGORY, ALL);

        {
            Subject sjuk = add("Sjukdom & Hälsa", l("sjukdom"), SUBJECT, news);
            add("Influenza", l("Influenza"), SUBJECT, sjuk);
            add("Leukemi", l("leukemi"), SUBJECT, sjuk);
            add("Cancer", l("cancer"), SUBJECT, sjuk);
            add("Klimakterie", l("klimakterie"), SUBJECT, sjuk);
        }

        Subject tech = add("Tech", l(
                "tech ",
                "ycombinator",
                "Engadget"
        ), CATEGORY, ALL);

        {
            add("Science",
                    l(
                            "science",
                            "science.slashdot",
                            "phys.org",
                            "quantum",
                            "transistor",
                            "research",
                            "polymer",
                            "forskare",
                            "forskning",
                            "ultrasound"
                    ), CATEGORY, tech);

            add("Show HN", l("Show HN"), SUB_FEED, tech);


            Subject miljo = add("Miljö", l("klimat", "climate", "environment"), SUBJECT, news);
            add("Återvinning", l("återvinning"), SUBJECT, miljo);


            Subject internet = add("Internet",
                    l("internet", "World Wide Web"), SUBJECT, tech);
            add("Tim Berners-Lee", l("Tim Berners-Lee"), PERSON, internet);

            add("Linux",
                    l(
                            "linux",
                            "Ubuntu",
                            "Linus Torvalds",
                            "KDE"
                    ), SUBJECT, tech);

            add("Artificial Intelligence",
                    l(
                            "artificial intelligence",
                            "artificial-intelligence",
                            "robot",
                            "machine learning",
                            "neural net",
                            "cellular automata"
                    ), SUBJECT, tech);

            Subject virus = add("Virus",
                              l(
                                      "virus",
                                      "smittspridning",
                                      "smitta"
                              ), SUBJECT, news);

            add("Covid-19",
                l(
                    "corona",
                    "covid",
                    "sars-cov-2"
                ), SUBJECT, virus);


            add("Niklas Zennström", l("Zennström"), PERSON, tech);

            add("Software",
                    l("software", "programming", "programmer", "web framework",
                            "database", "computation", "clojure", "haskell",
                            "open source", "javascript", ".js", "developers.slashdot"), SUBJECT, tech);

            Subject facebook = add("Facebook", l("Facebook"), ORGANIZATION, tech);
            Subject zoom = add("Zoom", l("Zoom"), ORGANIZATION, tech);
            add("Cambridge Analytica", l("Cambridge Analytica"), ORGANIZATION, facebook, tech);

        }



        Subject sydAmerika = addWithoutExpression("Sydamerika", CONTINENT, news);

        Subject argentina = add("Argentina", l("Argentin"), COUNTRY, sydAmerika);
        add("Buenos Aires", l("Buenos Aires"), COUNTRY, argentina);
        add("Chile", l("Chile"), COUNTRY, sydAmerika);
        Subject honduras = add("Honduras", l("Hondura"), COUNTRY, sydAmerika);

        Subject brasilien = add("Brasilien", l("brasil","brazil").or(regex("Rio")), COUNTRY, sydAmerika);
        add("Jair Bolsonaro", l("bolsonaro"), PERSON, brasilien);
        add("Bolivia", l("Bolivia"), COUNTRY, sydAmerika);
        add("Guatemala", l("Guatemala"), COUNTRY, sydAmerika);

        Subject mexico = add("Mexiko", l("Mexico", "Mexiko"), COUNTRY, sydAmerika);
        add("Colombia", l("colombia"), COUNTRY, sydAmerika);

        add("El Salvador", l("El Salvador"), COUNTRY, sydAmerika);

        Subject afrika = add("Afrika", l("Afrika","Africa"), CONTINENT, news);

        add("Algeriet", l("algeria", "algeriet"), COUNTRY, afrika);
        add("Sudan", l("Sudan"), COUNTRY, afrika);
        add("Botswana", l("Botswana"), COUNTRY, afrika);
        add("Burkina Faso", l("Burkina Faso"), COUNTRY, afrika);
        add("Demokratiska Republiken Kongo", l("Democratic Republic of Congo","Demokratiska Republiken Kongo"), COUNTRY, afrika);
        add("Egypt", l("Egypt"), COUNTRY, afrika);
        add("Eritrea", l("eritrea"), COUNTRY, afrika);
        add("Elfenbenskusten", l("ivory coast","elfenbenfskusten"), COUNTRY, afrika);
        add("Etiopien", l("ethiopia","etiop"), COUNTRY, afrika);
        add("Gabon", l("Gabon"), COUNTRY, afrika);
        add("Kongo", l("Kongo"), COUNTRY, afrika);
        add("Libyen", l("libya","libyen"), COUNTRY, afrika);
        add("Nigeria", l("nigeria"), COUNTRY, afrika);
        add("Kamerun", l("kamerun","cameroon"), COUNTRY, afrika);
        Subject kenya = add("Kenya", l("Kenya"), COUNTRY, afrika);
        add("Nairobi", l("Nairobi"), LOCAL, kenya);
        add("Senegal", l("Senegal"), COUNTRY, afrika);
        add("Tunisien", l("tunisia"), COUNTRY, afrika);
        add("Uganda", l("uganda"), COUNTRY, afrika);
        add("Sydafrika", l("South Africa", "sydafrika"), COUNTRY, afrika);


        Subject europe = add("Europa", l("europe", "europa"), CONTINENT, news);
        add("#EU", l("European Union"), ORGANIZATION, europe);

        add("Georgien", l("Georgia", "Georgien"), COUNTRY, europe);
        {
            Subject turkiet = add("Turkiet", l("Turkiet", "turkey"), COUNTRY, europe);
            add("Erdogan", l("Erdogan"), PERSON, turkiet);
        }

        add("Finland", l("Finland"), COUNTRY, news);

        add("Grekland", l("grekland","grekisk","greece"), COUNTRY, europe);
        add("Cypern", l("cypern","cyprus","cypriot"), COUNTRY, europe);
        add("Frankrike", l("Frankrike","France","french","fransk"), COUNTRY, europe);

        add("Slovakien", l("Slovak"), COUNTRY, europe);
        add("Norge", l("norsk", "norge", "norway", "norwegian"), COUNTRY, europe);

        add("Malta", l("Malta","Maltese"), COUNTRY, europe);

        add("Lettland", l("lettland","latvia"), COUNTRY, europe);
        add("Bulgarien", l("bulgari"), COUNTRY, europe);
        add("Serbien", l("serbisk","serbien","serbia"), COUNTRY, europe);
        {
            Subject greatBritain = add("Storbritannien", l("Great Britain","Storbritannien","British", "U.K."), COUNTRY, europe);
            add("Tories", regex("Tories"), ORGANIZATION, greatBritain);
            add("Prince Harry", l("Prince Harry"), PERSON, greatBritain);

            add("Brexit", l("Brexit"), EVENT, greatBritain);
            add("Leicester", l("Leicester"), LOCAL, greatBritain);
            add("Theresa May", l("Theresa May"), PERSON, greatBritain);
            add("London", l("London"), LOCAL, greatBritain);
            add("Birmingham", l("Birmingham"), LOCAL, greatBritain);
        }


        {
            Subject venezuela = add("Venezuela", l("Venezuela"), COUNTRY, sydAmerika);
            add("Nicolás Maduro", l("Maduro"), PERSON, venezuela);
        }


        add("Ukraina", l("Ukrain"), COUNTRY, europe);
        add("Portugal", l("portugal"), COUNTRY, europe);

        add("Makedonien", l("macedoni", "Makedoni"), COUNTRY, europe);

        {

            Subject spanien = add("Spanien", l("Spanien", "Spain"), COUNTRY, europe);
            add("Barcelona", l("Barcelona"), LOCAL, spanien);
            add("Katalonien", l("Katalonien","Catala"), LOCAL, spanien);
            add("Mallorca", l("Mallorca"), LOCAL, spanien);
        }
        add("Italien", l("Italien","Italian","Italy"), COUNTRY, europe);
        add("Nederländerna", l("Nederländerna","Netherlands","Dutch","Holland","Holländ"), COUNTRY, europe);

        Subject tyskland = add("Tyskland", l("German","Tysk"), COUNTRY, europe);
        add("Angela Merkel", l("merkel"), PERSON, tyskland);

        Subject ungern = add("Ungern", l("Ungern","Hungary"), COUNTRY, europe);
        add("Bosnien", l("Bosnien","Bosnia"), COUNTRY, europe);
        add("Rumänien", l("rumän","romania"), COUNTRY, europe);
        Subject danmark = add("Danmark", l("danmark", "dansk", "denmark", "danish"), COUNTRY, europe);
        add("Köpenhamn", l("Köpenhamn", "Copenhagen"), LOCAL, danmark);
        add("Budapest", l("Budapest"), LOCAL, ungern);

        add("Polen", l("polen", "poland", "polish", "polsk"), COUNTRY, europe);


        {
            Subject sverige = add("Sverige",
                    l(
                            "sverige",
                            "swedish",
                            "sweden",
                            "svensk",
                            "TheLocal",
                            "inrikes",
                            "tågtrafik",
                            "kolmården"
                    ), COUNTRY, news);

            Subject inrikespolitik = add("Inrikespolitik",
                    l(
                            "Inrikespolitik",
                            "svensk politik",
                            "landsting",
                            "kommuner",
                            "justitieombudsman",
                            "Försäkringskassan"
                    ), SUBJECT, sverige);


            Subject fi = add("Feministiskt initiativ", l("Feministiskt initiativ"), ORGANIZATION, inrikespolitik);
            add("Gudrun Schyman", l("Gudrun Schyman"), PERSON, fi);

            add("Vänsterpartiet", l("Vänsterpartiet"), ORGANIZATION, inrikespolitik);
            Subject miljopartiet = add("Miljöpartiet", l("miljöpartiet"), ORGANIZATION, inrikespolitik);
            add("Gustav Fridolin", l("Fridolin"), PERSON, miljopartiet);

            add("Bildt", l("bildt"), PERSON, inrikespolitik);

            Subject alliansen = add("Alliansen", l("Alliansparti"), ORGANIZATION, inrikespolitik);

            Subject moderaterna = add("Moderatena", l("Moderaterna"), ORGANIZATION, inrikespolitik, alliansen);
            add("Ulf Kristersson", l("Ulf Kristersson"), PERSON, moderaterna);

            Subject centerpartiet = add("Centerpartiet", l("Centerpartiet"), ORGANIZATION, inrikespolitik, alliansen);
            add("Annie Lööf", l("Lööf"), PERSON, centerpartiet);

            Subject socialdemokraterna = add("Socialdemokraterna", l("socialdemokraterna"), ORGANIZATION, inrikespolitik);
            add("Stefan Löfven", l("Stefan Löfven"), PERSON, socialdemokraterna);

            Subject sverigedemokraterna = add("Sverigedemokraterna", l("Sverigedemokraterna"), ORGANIZATION, inrikespolitik);
            add("Jimmie Åkesson", l("Jimmie Åkesson"), PERSON, sverigedemokraterna);
            Subject talmannen = add("Talmannen", l("talmannen"), PERSON, inrikespolitik);
            add("Andreas Norlén", l("Andreas Norlén"), PERSON, talmannen, inrikespolitik);

            Subject stockholm = add("Stockholm",
                    l(
                            "Stockholm",
                            "dn.se/sthlm"
                    ), LOCAL, sverige);
            add("Arlanda", l("Arlanda"), LOCAL, stockholm);
            add("Bromma flygplats", l("Bromma flygplats"), LOCAL, stockholm);
            add("Kalix", l("Kalix"), LOCAL, sverige);
            add("Skövde", l("Skövde"), LOCAL, sverige);
            add("Malmö", l("Malmö"), LOCAL, sverige);
            add("Arvidsjaur", l("Arvidsjaur"), LOCAL, sverige);
            add("Göteborg", l("Göteborg", "Gothenburg"), LOCAL, sverige);

            add("SF-Bio", regex("SF.*[Bb]io"), ORGANIZATION, sverige);

            add("Studiemedel", l("CSN", "studiemedel", "studielån"), SUBJECT, sverige);


            {
                add("Nobelpriset", l("nobel"), SUBJECT, sverige);
                Subject svenskaAkademien = add("Svenska Akademien", l("Svenska Akademien", "Swedish Academy"), ORGANIZATION, sverige);
                add("Jean-Claude Arnault", l("Jean-Claude Arnault"), PERSON, svenskaAkademien, meToo);
            }
        }



        Subject asien = add("Asien", l("asien", "asia"), CONTINENT, news);

        add("Maldives", l("Maldives"), COUNTRY, asien);
        add("Thailand", l("thailand", "thailänd"), COUNTRY, asien);
        add("Sri Lanka", l("sri lank"), COUNTRY, asien);
        add("Kashmir", l("Kashmir"), COUNTRY, asien);
        add("Bhutan", l("Bhutan"), COUNTRY, asien);

        Subject myanmar = add("Myanmar", l("Myanmar"), COUNTRY, asien);
        add("Aung San Suu Kyi", l("Aung San Suu Kyi"), PERSON, myanmar);
        add("Rohingya", l("Rohingya"), SUBJECT, myanmar);

        Subject japan = add("Japan", l("Japan"), COUNTRY, asien);
        add("Shinzo Abe", l("Shinzo Abe"), PERSON, japan);
        add("Okinawa", l("Okinawa"), LOCAL, japan);
        add("Tokyo", l("Tokyo"), LOCAL, japan);

        add("Malaysia", l("malaysia"), COUNTRY, asien);

        Subject ryssland = add("Ryssland", l("Russia", "Ryssland", "rysk"), COUNTRY, asien);
        add("Moskva", l("Moscow", "Moskva"), LOCAL, ryssland);
        add("Vladimir Putin", l("Putin"), PERSON, ryssland);

        add("Indien", l("indien", "india"), COUNTRY, asien);
        add("Pakistan", l("pakistan"), COUNTRY, asien);
        add("Indonesien", l("indonesien", "indonesia"), COUNTRY, asien);

        add("Kina", l("Kina", "China", "kines", "chinee"), COUNTRY, asien);
        add("Taiwan", l("Taiwan"), COUNTRY, asien);

        Subject nordkorea = add("Nordkorea", l("nordkorea", "north korea"), COUNTRY, asien);
        add("Kim Jong-Un", l("Kim Jong-Un", "Kim Jong Un"), PERSON, nordkorea);

        Subject sydkorea = add("Sydkorea", l("sydkorea", "south korea"), COUNTRY, asien);
        add("Pyeongchang", l("Pyeongchang"), LOCAL, sydkorea);
        add("Moon Jae-in", l("moon jae-in"), PERSON, sydkorea);

        add("Singapore", l("Singapore"), COUNTRY, asien);


        Subject middleEast = add("Mellanöstern", l("mellanöstern", "middle east"), CONTINENT, news);
        Subject israel = add("Israel", l("Israel"), COUNTRY, middleEast);
        Subject palestina = add("Palestina", l("Palestin"), COUNTRY, middleEast);
        add("Gaza", l("Gaza"), LOCAL, israel, palestina);
        add("Libanon", l("libanon", "lebanon"), COUNTRY, middleEast);

        add("Afghanistan", l("Afghan", "Afgan"), COUNTRY, middleEast);


        Subject syrien = add("Syrien", l("syria", "syrien"), COUNTRY, middleEast);
        add("Idlib", l("Idlib"), LOCAL, syrien);

        add("Jordanien", l("jordan"), COUNTRY, middleEast);

        Subject saudiarabien = add("Saudiarabien", l("saudi"), COUNTRY, middleEast);
        add("Jamal Khashoggi", l("khashoggi"), EVENT, saudiarabien);

        add("Qatar", l("Qatar"), COUNTRY, middleEast);


        add("Yemen", l("Yemen", "jemen"), COUNTRY, middleEast);
        add("Iran", l("Iran"), COUNTRY, middleEast);
        add("Irak", l("iraq", "irak"), COUNTRY, middleEast);
        add("Kurder", l("kurd"), SUBJECT, middleEast);

        add("Förenade Arabemiraten", l("Förenade Arabemiraten", "UAE"), COUNTRY, middleEast);

        Subject oceanien = addWithoutExpression("Oceanien", CONTINENT, news);
        add("Mikronesien", l("Mikronesien", "micronesia"), COUNTRY, oceanien);
        add("Nauru", l("Nauru"), COUNTRY, oceanien);
        add("Australien", l("australia", "australien"), COUNTRY, oceanien);
        add("Nya Zealand", l("New Zealand", "Nya Zeeland"), COUNTRY, oceanien);



        Subject nordAmerika = addWithoutExpression("Nordamerika", CONTINENT, news);
        Subject usa = add("#USA", l("USA", "U.S.", "US election"), COUNTRY, nordAmerika);
        {
            add("Florida", l("Florida"), LOCAL, usa);
            add("Nevada", l("Nevada"), LOCAL, usa);
            Subject pittsburgh = add("Pittsburgh", l("Pittsburgh"), LOCAL, usa);
            add("Robert Bowers", l("Robert Bowers"), PERSON, pittsburgh);
            add("Kalifornien", l("kaliforni", "californi"), LOCAL, usa);
            add("Hawaii", l("hawaii"), LOCAL, usa);
            Subject arizona = add("Arizona", l("Arizona"), LOCAL, usa);
            add("Ohio", l("Ohio", "Cincinnati"), LOCAL, usa);
            add("Texas", l("Texas"), LOCAL, usa);

            Subject usaPolitik = add("USA-politik", l("USA-politik", "Planned Parenthood", "NAFTA"), SUBJECT, usa);

            add("Jeff Flake", l("Jeff Flake"), PERSON, usaPolitik, arizona);
            add("Brett Kavanaugh", l("Kavanaugh"), PERSON, usaPolitik);
            add("Bernie Sanders", l("Sanders"), PERSON, usaPolitik);
            add("Paul Ryan", l("Paul Ryan"), PERSON, usaPolitik);
            add("Mike Pompeo", l("pompeo"), PERSON, usaPolitik);

            add("Trump", l("Trump"), PERSON, usa);
            add("Bill Clinton", l("Bill Clinton"), PERSON, usa);
            add("Republikanerna", regex("washington.*republican", "GOP"), ORGANIZATION, usaPolitik);

            add("Obamacare", l("Obamacare"), SUBJECT, usaPolitik);
        }
        add("Kanada", l("Canada", "Kanada", "Kanadens", "Canadian"), COUNTRY, nordAmerika);
        add("Haiti", l("haiti"), COUNTRY, nordAmerika);


        {
            Subject weather = add("Weather & Disasters",
                    l(
                            "temperatur",
                            "varmare väder",
                            "kallare väder",
                            "snöfall",
                            "blötsnö",
                            "snömängd",
                            "meteorolog",
                            "väderinstitut",
                            "smhi",
                            "grader varmt",
                            "vinterdäck"
                    ), CATEGORY, ALL);

            add("Tsunami", l("tsunami"), SUBJECT, weather);
            add("Tyfon", l("tyfon", "typhoon"), SUBJECT, weather);
            add("Jordbävning", l("jordbävning", "earth quake", "earthquake"), SUBJECT, weather);
            add("Skogsbrand", l("skogsbrand"), SUBJECT, weather);
            add("Flooding", l("floodwater"), SUBJECT, weather);
            add("Jordskred", l("jordskred", "landslide"), SUBJECT, weather);
        }


        Subject violence = add("Våldsbrott",
                l(
                        "violence",
                        "Våldsbrott",
                        "våldtäkt",
                        "våldtagen",
                        "våld",
                        "skjutning",
                        "mord",
                        "murder",
                        "Död person"
                ), SUBJECT, news);

        add("Hatbrott",
                l(
                        "hatbrott",
                        "hate crime",
                        "racism",
                        "judehat",
                        "antisemitism",
                        "anti-semitic"
                ), SUBJECT, violence);

        Subject terrorism = add("Terrorism", l("terror"), SUBJECT, violence);
        add("Boko Haram", l("boko haram"), ORGANIZATION, terrorism);
        add("ISIL", l("islamic state").or(regex("ISIL", "ISIS")), ORGANIZATION, terrorism);

        add("United Nations", l("UN peacekeeper").or(regex("the UN")), ORGANIZATION, news);

        {
            Subject biz = add("Business", l("näringsliv", "dn.se/ekonomi", "aktie", "tillväxt"), CATEGORY, ALL);
            add("Elon Musk", l("Elon Musk"), PERSON, tech, biz);
            add("Tesla", l("Tesla"), ORGANIZATION, tech, biz);
            add("e-handel", l("e-handel"), SUBJECT, tech, biz);
            add("#BMW", l("BMW"), ORGANIZATION, tech, biz);
            add("Hubble", l("Hubble"), SUBJECT, tech);
            add("Nordnet", l("Nordnet"), ORGANIZATION, biz);

            add("Google", l("Google"), ORGANIZATION, biz, tech);
            add("Red Hat", l("Red Hat", "redhat"), ORGANIZATION, biz, tech);
            add("#IBM", regex("IBM"), ORGANIZATION, biz, tech);
            add("Warner", l("warner"), ORGANIZATION, biz);
            add("Nokia", l("nokia"), ORGANIZATION, biz);
            add("#SEB", regex("SEB"), ORGANIZATION, biz);
            add("Nordea", l("nordea"), ORGANIZATION, biz);
            add("Microsoft", l("Microsoft"), ORGANIZATION, biz, tech);
            add("Snapchat", l("Snapchat"), ORGANIZATION, biz, tech);

            Subject apple = add("Apple", l("Apple"), ORGANIZATION, biz, tech);
            add("Steve Jobs", l("Steve Jobs"), PERSON, apple);

            add("Amazon", l("Amazon"), ORGANIZATION, biz, tech);
        }

        {
            Subject sport = add("Sport", l(
                    "sport",
                    "Sportbladet",
                    "målchans",
                    "AIK",
                    "landslag",
                    "grand slam",
                    "OS-guld",
                    "VM-titel",
                    "världscup",
                    "50 meter fritt",
                    "guldstrid",
                    "SM-guld",
                    "VM-guld",
                    "OS-guld",
                    "avgörande mål",
                    "straffområde",
                    "olympic games"
            ), HIDE_SPORT, CATEGORY, ALL);

            add("Rallycross", l("rallycross"), SUBJECT, sport);
            add("Gymnastik", l("gymnastics", "gymnastik"), SUBJECT, sport);



            add("Fotboll", l(
                    "fotboll",
                    "Manchester United",
                    "soccer",
                    "mittback",
                    "Brommapojkarna",
                    "Paul Pogba",
                    "första halvlek",
                    "andra halvlek",
                    "zlatan",
                    "Nations League"
            ), SUBJECT, sport);


            Subject golf = add("Golf", l("golftävling", "Ryder Cup", "Henrik Stenson"), SUBJECT, sport);
            add("Tiger Woods", l("Tiger Woods"), PERSON, golf);

            add("Hockey", l("hockey"), SUBJECT, sport);
        }

        {
            Subject kultur = addWithoutExpression("Kultur", HIDE_CULTURE, CATEGORY, ALL);
            add("DN-Kultur&Nöje", l("kultur-noje"), SUBJECT, kultur);

            Subject books = add("Böcker", l("Böcker"), SUBJECT, kultur);
            add("DN-Bok", l("dnbok"), SUBJECT, books);

            add("Musik", l("klassisk rock"), SUBJECT, kultur);

            add("Konst", l("Andy Warhol"), PERSON, kultur);

            Subject celebrities = addWithoutExpression("Kändisar", SUBJECT, kultur);
            add("Bono", regex("U2.*Bono"), PERSON, celebrities);
            add("Tailor Swift", l("Taylor Swift"), PERSON, celebrities);
            add("Kanye West", l("Kanye West"), PERSON, celebrities);
            add("Freddie Mercury", l("Freddie Mercury"), PERSON, celebrities);

            add("Film", regex("film.*recension", "recension.*film"), SUBJECT, kultur);
        }

        {
            Subject equality = add("Jämställdhet", l("jämställdhet"), SUBJECT, news);
            add("HBTQ", l("hbtq", "homosexu"), SUBJECT, equality);
        }

        add("Interpol", l("Interpol"), ORGANIZATION, news);
        add("Flyktingar", l("flykting"), SUBJECT, news);

        add("Påven", l("pope", "Påve"), PERSON, news);
        add("Journalist", l("journalist"), SUBJECT, news);
        add("Amnesty", l("Amnesty"), ORGANIZATION, news);

        Subject fakeNews = add("Fake news", l("fake news"), SUBJECT, news);
        add("Fact Checker", l("Fact Checker"), SUB_FEED, fakeNews);

        add("Ledare", l("ledare"), CATEGORY, news);

        {
            Subject bad = addWithoutExpression("#Bad", HIDE_BAD, BASE, ALL);
            add("SVT::Snabbkollen", l("snabbkollen"), SUBJECT, bad);
            add("TheLocal::WordOfTheDay", l("word-of-the-day"), SUBJECT, bad);
            add("NYT::Your Briefing", regex("Your.*Briefing"), SUBJECT, bad);
            add("DN::webb-tv", l("webb-tv"), SUBJECT, bad);
            add("DN::mat-dryck", l("mat-dryck"), SUBJECT, bad);
            add("DN::Gratulerar", l("DN gratulerar"), SUBJECT, bad);
            add("DN::Minnesord", l("minnesord:"), SUBJECT, bad);
            add("DN::livsstil", l("www.dn.se/livsstil"), SUBJECT, bad);
            add("DN::nutidstestet", l("nutidstestet"), SUBJECT, bad);
            add("DN::motor", l("se/ekonomi/motor", "se/motor"), SUBJECT, bad);
            add("DN::insidan", l("dn.se/insidan"), SUBJECT, bad);
            add("SVD::PerfectGuide", l("Perfect Guide"), SUBJECT, bad);
            add("Så...", l(".se/sa-"), SUBJECT, bad);
            add("Engadget::Wirecutter", regex("engadget.*Wirecutter"), SUBJECT, bad);
        }


        add("Migrantkaravanen", l("migrantkaravan").or(regex("[Mm]exi.*[Bb]order","[Bb]order.*[Mm]exic")), EVENT, mexico, usa, honduras);
        return Collections.unmodifiableSet(SUBJECTS);
    }

    private static Predicate<Document> regex(String... regexesOr) {
        return JPredicates.or(Arrays.stream(regexesOr).map(DocumentPredicates::matches).collect(Collectors.toList()));
    }

    private static Predicate<Document> l(String... expressions) {
        List<Predicate<Document>> predicates = Arrays.stream(expressions).map(DocumentPredicates::has).collect(Collectors.toList());
        return JPredicates.or(predicates);
    }

    private static Subject add(String name, Predicate<Document> predicate, SubjectType type, Subject... parents) {
        return add(name, predicate, false, true, true, type, parents);
    }

    private static Subject addWithoutExpression(String name, boolean hide, SubjectType type, Subject... parents) {
        return add(name, d -> false, hide, type, parents);
    }

    public static Subject addWithoutExpression(String name, SubjectType type, Subject... parents) {
        return add(name, d -> false, type, parents);
    }

    private static Subject add(String name, Predicate<Document> predicate, boolean hide, SubjectType type, Subject... parents) {
        boolean isHashTag = true;
        boolean showAsTab = true;
        return add(name, predicate, hide, isHashTag, showAsTab, type, parents);
    }

    private static Subject add(String name, @Nonnull Predicate<Document> predicate, boolean hide, boolean isHashTag, boolean showAsTab, SubjectType type, Subject... parents) {
        int size = SUBJECTS.size();
        Subject subject = new Subject(Arrays.asList(parents), name, type, predicate, hide, isHashTag, showAsTab);
        SUBJECTS.add(subject);
        if (size == SUBJECTS.size()) {
            throw new IllegalStateException("Duplicate detected! " + name);
        }
        return subject;
    }
}
