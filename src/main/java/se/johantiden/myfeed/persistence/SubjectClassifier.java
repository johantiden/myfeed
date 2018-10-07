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

        Subject opinion = addWithoutExpression("Opinion", news);
        addInvisible("[Rr]iktlinje", opinion);
        addInvisible("DEBATT", opinion);



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


        {


            {
                Subject sydAmerika = addWithoutExpression("Sydamerika", news);

                Subject argentina = add("Argentina", "[Aa]rgentin", sydAmerika);
                add("Buenos Aires", "Buenos Aires", argentina);
                add("Chile", "Chile", sydAmerika);

                Subject brasilien = add("Brasilien", "[Bb]ra[sz]il", sydAmerika);
                add("Jair Bolsonaro", "[Bb]olsonaro", brasilien);
                add("Bolivia", "Bolivia", sydAmerika);
                add("Guatemala", "Guatemala", sydAmerika);

                add("Mexiko", "Mexico|Mexiko", sydAmerika);

                add("El Salvador", "El Salvador", sydAmerika);
            }

            {
                Subject afrika = add("Afrika", "Afrika|Africa", news);
                add("Senegal", "Senegal", afrika);

                add("Egypt", "Egypt", afrika);
                add("Botswana", "Botswana", afrika);
                Subject kenya = add("Kenya", "Kenya", afrika);
                add("Nairobi", "Nairobi", kenya);
                add("Burkina Faso", "Burkina Faso", afrika);
                add("Tunisien", "[Tt]unisia", afrika);
                add("Nigeria", "[Nn]igeria", afrika);
                add("Libyen", "[Ll]ibya|[Ll]ibyen", afrika);
                add("Kamerun", "[Kk]amerun|[Cc]ameroon", afrika);
                add("Gabon", "Gabon", afrika);
            }


            {
                Subject europe = add("Europa", "[Ee]urope", news);
                add("#EU", "European Union", europe);

                {
                    Subject turkiet = add("Turkiet", "Turkiet", europe);
                    add("Erdogan", "Erdogan", turkiet);
                    addInvisible("Turkey", turkiet);
                }

                add("Grekland", "[Gg]rekland|[Gg]rekisk|[Gg]reece", europe);
                add("Cypern", "[Cc]ypern|[Cc]yprus|[Cc]ypriot", europe);
                add("Frankrike", "Frankrike|France|[Ff]rench|[Ff]ransk", europe);

                add("Slovakien", "Slovak", europe);

                add("Malta", "Malta|Maltese", europe);

                add("Lettland", "[Ll]ettland|[Ll]atvia", europe);
                add("Bulgarien", "[Bb]ulgari", europe);
                add("Serbien", "[Ss]erbisk|[Ss]erbien|[Ss]erbia", europe);
                {
                    Subject greatBritain = add("Storbritannien", "Great Britain|Storbritannien|British", europe);
                    add("Tories", "Tories", greatBritain);
                    addInvisible("U\\.K\\.", greatBritain);

                    add("Brexit", "Brexit", news, greatBritain);
                    add("Theresa May", "Theresa May", greatBritain);
                    add("London", "London", greatBritain);
                    add("Birmingham", "Birmingham", greatBritain);
                }

                add("Ukraina", "Ukrain", europe);
                add("Portugal", "[Pp]ortugal", europe);

                Subject makedonien = add("Makedonien", "[Mm]acedoni", europe);
                addInvisible("Makedoni", makedonien);

                {

                    Subject spanien = add("Spanien", "Spanien", europe);
                    addInvisible("Spain", spanien);
                    add("Barcelona", "Barcelona", spanien);
                    add("Katalonien", "Katalonien|Catala", spanien);
                }
                add("Italien", "Italien|Italian|Italy", europe);
                add("Nederländerna", "Nederländerna|Netherlands|Dutch|Holland|Holländ", europe);
                add("Tyskland", "German|Tysk", europe);


                Subject ungern = add("Ungern", "Ungern|Hungary", europe);
                add("Bosnien", "Bosnien|Bosnia", europe);
                add("Rumänien", "[Rr]umän|[Rr]omania", europe);
                Subject danmark = add("Danmark", "[Dd]anmark|[Dd]ansk|[Dd]enmark|[Dd]anish", europe);
                add("Budapest", "Budapest", ungern);
                add("Köpenhamn", "Köpenhamn|Copenhagen", danmark);


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

            }


            {
                Subject asien = add("Asien", "[Aa]sien|[Aa]sia", news);

                add("Maldives", "Maldives", asien);
                add("Thailand", "[Tt]hail[aä]nd", asien);

                Subject myanmar = add("Myanmar", "Myanmar", asien);
                add("Aung San Suu Kyi", "Aung San Suu Kyi", myanmar);
                add("Rohingya", "Rohingya", myanmar);

                Subject japan = add("Japan", "Japan", asien);
                add("Okinawa", "Okinawa", japan);
                add("Tokyo", "Tokyo", japan);

                Subject ryssland = add("Ryssland", "Russia|Ryssland|[Rr]ysk", asien);
                add("Moskva", "Moscow|Moskva", ryssland);
                add("Vladimir Putin", "Putin", ryssland);
                add("Indien", "[Ii]ndien|[Ii]ndia", asien);
                add("Indonesien", "[Ii]ndonesien|[Ii]ndonesia", asien);

                add("Kina", "Kina|China", asien);

                Subject nordkorea = add("Nordkorea", "Nordkorea", asien);
                add("Kim Jong-Un", "Kim Jong-Un", nordkorea);

                Subject sydkorea = add("Sydkorea", "Sydkorea", asien);
                add("Pyeongchang", "Pyeongchang", sydkorea);

                add("Singapore", "Singapore", asien);
            }


            {
                Subject middleEast = addWithoutExpression("Mellanöstern", news);
                Subject israel = add("Israel", "Israel", middleEast);
                Subject palestina = add("Palestina", "Palestin", middleEast);
                add("Gaza", "Gaza", israel, palestina);
                add("Libanon", "[Ll][ei]banon", middleEast);

                add("Afghanistan", "Afghan|Afgan", middleEast);


                Subject syrien = add("Syrien", "[Ss]yria|[Ss]yrien", middleEast);
                add("Idlib", "Idlib", syrien);

                add("Saudiarabien", "[Ss]audi", middleEast);

                add("Qatar", "Qatar", middleEast);


                add("Yemen", "Yemen", middleEast);
                add("Iran", "Iran", middleEast);
                add("Irak", "[Ii]ra[kq]", middleEast);
                add("Kurder", "[Kk]urd", middleEast);


                Subject uae = add("Förenade Arabemiraten", "Förenade Arabemiraten", middleEast);
                addInvisible("UAE", uae);
            }

            {
                Subject oceanien = addWithoutExpression("Oceanien", news);
                add("Mikronesien", "Mikronesien|[Mm]icronesia", oceanien);
                add("Nauru", "Nauru", oceanien);

            }


            {
                Subject nordAmerika = addWithoutExpression("Nordamerika", news);
                {
                    Subject usa = add("#USA", "USA|U\\.S\\.", nordAmerika);
                    add("Florida", "Florida", usa);
                    add("Kalifornien", "[KkCc]aliforni", usa);
                    add("Hawaii", "[Hh]awaii", usa);
                    Subject arizona = add("Arizona", "Arizona", usa);
                    add("Ohio", "Ohio|Cincinnati", usa);
                    add("Texas", "Texas", usa);
                    add("New York", "New York", usa);

                    Subject usaPolitik = add("USA-politik", "USA-politik", usa);
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

                add("Kanada", "Canada|Kanada|Kanadens|Canadian", nordAmerika);

            }
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

        {
            Subject equality = add("Jämställdhet", "[Jj]ämställdhet", news);
            Subject hbtq = add("HBTQ", "hbtq", equality);
            addInvisible("[Hh]omosexu", hbtq);
        }

        add("Interpol", "Interpol", news);
        add("Flyktingar", "flykting", news);

        add("Klimat", "[Kk]limat|[Cc]limate", news);


        {
            Subject terrorism = add("Terrorism", "[T]error", news);
            add("Boko Haram", "[Bb]oko [Hh]aram", terrorism);
            add("ISIL", "ISIL|ISIS|[Ii]slamic [Ss]state", terrorism);
        }

        add("Påven", "[Pp]ope|Påve", news);
        add("Journalist", "[Jj]ournalist", news);
        add("Amnesty", "Amnesty", news);




        {
            Subject bad = addWithoutExpression("#Bad", HIDE_BAD, ROOT);
            add("SVT::Snabbkollen", "svt.*snabbkollen", bad);
            add("TheLocal::WordOfTheDay", "thelocal.*word-of-the-day", bad);
            add("HackerNews::Show HN", "Show HN", bad);
            add("NYT::Your Briefing", "Your.*Briefing", bad);
            add("DN::webb-tv", "webb-tv", bad);
            add("Engadget::Wirecutter", "[Ee]ngadget.*Wirecutter", bad);
        }


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
