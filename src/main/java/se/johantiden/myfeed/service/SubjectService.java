package se.johantiden.myfeed.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import se.johantiden.myfeed.classification.DocumentMatcher;
import se.johantiden.myfeed.persistence.Document;
import se.johantiden.myfeed.persistence.DocumentClassifier;
import se.johantiden.myfeed.persistence.SubjectRule;
import se.johantiden.myfeed.persistence.SubjectRuleRepository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SubjectService {

    @Autowired
    SubjectRuleRepository subjectRuleRepository;

    @Autowired
    DocumentService documentService;

    public static Set<SubjectRule> getDefaultSubjectRules() {
        TreeSet<SubjectRule> s = new TreeSet<>(SubjectRule.COMPARATOR);
        add(s, DocumentClassifier.EKONOMI, "ekonomi");
        add(s, DocumentClassifier.EKONOMI, "näringsliv");
        add(s, "Ledare", "ledare");
        add(s, "Sport", "sport");
        add(s, "Briefing", "[Yy]our.+[Bb]riefing");
        add(s, "Tim Berners-Lee", "Tim Berners\\-Lee");

        add(s, DocumentClassifier.TYSKLAND, "[Gg]erman");
        add(s, DocumentClassifier.TYSKLAND, "[Tt]ysk");
        add(s, DocumentClassifier.TYSKLAND, "Brandenburg");
        add(s, DocumentClassifier.TYSKLAND, "merkel");
        add(s, "Angela Merkel", "merkel");

        add(s, "Nazism", "Hitler");
        add(s, "Nazism", "Nazi");

        add(s, "Twitter", "[Tt]witter");
        add(s, "Twitter", "[Tt]weet");

        add(s, "Macron", "Macron");

        addPlaces(s);
        addPeople(s);

        add(s, "Turist", "[Tt]urist");

        add(s, "Netflix", "Netflix");
        add(s, "Boko Haram", "Boko Haram");

        add(s, "Klimat", "[Kk]limat");
        add(s, "Klimat", "[Cc]limate");

        add(s, "Väder", " [Vv]äder");
        add(s, "Väder", " [Vv]ädret");

        add(s, DocumentClassifier.EUROVISION, DocumentClassifier.EUROVISION);


        add(s, "FBI");
        add(s, DocumentClassifier.SPOTIFY);
        add(s, "Microsoft");
        add(s, "Verizon");
        add(s, "Uber");
        add(s, "Samsung");
        add(s, "Apple");
        add(s, "Facebook");
        add(s, "Nintendo");
        add(s, "Gaming", "Nintendo");
        add(s, "Google");
        add(s, "H&M");
        add(s, "HP");

        add(s, "Net Neutrality", "Net Neutrality");
        add(s, "Blockchain", "[Bb]lock.*chain");

        add(s, DocumentClassifier.MUSEUM);
        add(s, "Musik", "[Mm]usik");
        add(s, "Musik", "[Hh]iphop");
        add(s, "Konst", "[Kk]onstnär");
        add(s, DocumentClassifier.BÖCKER, "[Ff]författare");
        add(s, "Film/TV", "[Dd]ramaserie");

        add(s, "Terror", "[Tt]error");
        add(s, "TT", "TT");
        add(s, "Ebola", "Ebola");
        add(s, "Kolera", "Kolera");
        add(s, "Kolera", "Cholera");

        add(s, DocumentClassifier.IT_SÄKERHET, "IT-attack");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Rr]ansomware");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Cc]yberattack");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Mm]alware");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Ww]ana[Cc]ry");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Ww]anna[Cc]ry");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Hh]acker");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Hh]acking");
        add(s, DocumentClassifier.IT_SÄKERHET, "IT-utpressning");
        add(s, DocumentClassifier.IT_SÄKERHET, "IT-angrepp");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Ii]nternet [Ss]ecurity");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Ee]ternalblue");
        add(s, DocumentClassifier.IT_SÄKERHET, "[Bb]otnet");

        add(s, "Brexit", "Brexit");

        add(s, "Feministiskt Initiativ", "Feministiskt [Ii]nitiativ");
        add(s, DocumentClassifier.VÄNSTERPARTIET);
        add(s, DocumentClassifier.MILJÖPARTIET);
        add(s, DocumentClassifier.SOCIALDEMOKRATERNA);
        add(s, DocumentClassifier.CENTERPARTIET);
        add(s, DocumentClassifier.LIBERALERNA);
        add(s, DocumentClassifier.KRISTDEMOKRATERNA);
        add(s, DocumentClassifier.KRISTDEMOKRATERNA, "KD[ \\.]");
        add(s, DocumentClassifier.MODERATERNA);
        add(s, DocumentClassifier.SVERIGEMOKRATERNA);
        add(s, DocumentClassifier.SVERIGEMOKRATERNA, "SD[ \\.]");

        add(s, "Pope Francis");
        add(s, "Debatt", "[Dd]ebatt");

        add(s, "Mecca", "Mecca");
        add(s, "Mecca", "Hajj");
        add(s, "Mecca", "[Aa]l-[Aa]qsa");

        add(s, "Foliehatt", "CCTV");

        add(s, "Kvinnor", DocumentClassifier.REGEX_KVINNOR_MÄN);
        add(s, "Män", DocumentClassifier.REGEX_KVINNOR_MÄN);

        add(s, DocumentClassifier.HIRING, "[Hh]iring.*HackerNews");
        add(s, DocumentClassifier.IDAGSIDAN);

        add(s, DocumentClassifier.HISTORIA, "historian");
        add(s, DocumentClassifier.HISTORIA, "1500");
        add(s, DocumentClassifier.HISTORIA, "1600");
        add(s, DocumentClassifier.HISTORIA, "1700");
        add(s, DocumentClassifier.HISTORIA, "1800");

        add(s, DocumentClassifier.MAT, "[Rr]ecipe:.*TheLocal");
        add(s, DocumentClassifier.MAT, "mat-dryck");
        add(s, DocumentClassifier.MAT, "Restaurants");

        add(s, "Daesh", "Daesh");
        add(s, "Daesh", "Islamic State");
        add(s, "Daesh", "ISIL");
        add(s, "Daesh", "ISIS");
        add(s, "Daesh", "([Tt]error.*IS)|(IS.*[Tt]error)");

        add(s, "Taliban", "Taliban");

        add(s, "Gaming", "[Gg]aming");
        add(s, "Gaming", "[Gg]ames");
        add(s, "Cars", "cars technica");
        add(s, DocumentClassifier.NEWS_GRID);
        add(s, DocumentClassifier.WEBB_TV);
        add(s, DocumentClassifier.LEAGUEOFLEGENDS);
        add(s, DocumentClassifier.DEALMASTER);
        add(s, "Dödsfälla", "[Dd]ödsfälla");
        add(s, "Motor", "[Mm]otor");
        add(s, "Serier", "[Ss]erier");

        add(s, "Här är", "– här är");
        add(s, "Här är", "- här är");
        add(s, "Här är", "Här är");

        add(s, "Uutiset", DocumentClassifier.UUTISET);
        add(s, "VIDEO", "^VIDEO");

        add(s, "Naturkatastrof", "[Jj]ordbävning");
        add(s, "Naturkatastrof", "[Ee]arthquake");
        add(s, "Naturkatastrof", "[Ll]andslide");
        add(s, "Naturkatastrof", "[Mm]udslide");

        add(s, "Talaq", "[Tt]alaq");

        add(s, DocumentClassifier.FRÅGESPORT, "fragesport");
        add(s, DocumentClassifier.JUNIOR);
        add(s, DocumentClassifier.NUTIDSTESTET);
        add(s, DocumentClassifier.PERFECT_GUIDE);
        add(s, DocumentClassifier.RESOR);
        add(s, DocumentClassifier.I_AM_A, "IAmA");
        add(s, DocumentClassifier.BLACK_PEOPLE_TWITTER);
        add(s, DocumentClassifier.THE_DENNIS);

        add(s, DocumentClassifier.NUMBER_OF_PEOPLE, "-- number");
        add(s, DocumentClassifier.NUMBER_OF_PEOPLE, "--number");

        return s;
    }

    public static void add(Set<SubjectRule> set, String name, String expression) {
        int size = set.size();
        set.add(new SubjectRule(name, expression));
        if (size == set.size()) {
            throw new IllegalStateException("Duplicate detected! " + name + ", " + expression);
        }
    }

    public static void add(Set<SubjectRule> set, String name) {
        add(set, name, name);
    }

    private static void addPeople(Set<SubjectRule> s) {

        add(s, "Obama", "Obama");
        add(s, DocumentClassifier.USA, "Obama");

        add(s, "Trump", "Trump");
        add(s, DocumentClassifier.USA, "Trump");

        add(s, "Ebba Busch Thor", "Busch Thor");
        add(s, DocumentClassifier.KRISTDEMOKRATERNA, "Busch Thor");
        add(s, DocumentClassifier.INRIKES, "Busch Thor");

        add(s, "Alice Bah Kuhnke", "Alice Bah Kuhnke");
        add(s, DocumentClassifier.MILJÖPARTIET, "Alice Bah Kuhnke");
        add(s, DocumentClassifier.INRIKES, "Alice Bah Kuhnke");

        add(s, "Mugabe", "Mugabe");
        add(s, DocumentClassifier.SYDAFRIKA, "Mugabe");
        add(s, DocumentClassifier.AFRIKA, "Mugabe");

        add(s, "Jonas Sjöstedt", "Jonas Sjöstedt");
        add(s, DocumentClassifier.VÄNSTERPARTIET, "Jonas Sjöstedt");
        add(s, DocumentClassifier.INRIKES, "Jonas Sjöstedt");

        add(s, "Annie Lööf", "Annie Lööf");
        add(s, DocumentClassifier.CENTERPARTIET, "Annie Lööf");
        add(s, DocumentClassifier.INRIKES, "Annie Lööf");

        add(s, "Stefan Löfven", "Löfven");
        add(s, DocumentClassifier.SOCIALDEMOKRATERNA, "Löfven");
        add(s, DocumentClassifier.INRIKES, "Löfven");

        add(s, "Jimmie Åkesson", "Jimmie Åkesson");
        add(s, DocumentClassifier.SOCIALDEMOKRATERNA, "Jimmie Åkesson");
        add(s, DocumentClassifier.INRIKES, "Jimmie Åkesson");

        add(s, "Jan Björklund", "Björklund");
        add(s, DocumentClassifier.LIBERALERNA, "Björklund");
        add(s, DocumentClassifier.INRIKES, "Björklund");

        add(s, "Kinberg Batra");
        add(s, DocumentClassifier.MODERATERNA, "Kinberg Batra");

        add(s, "Leo Varadkar", "Varadkar");
        add(s, DocumentClassifier.IRLAND, "Varadkar");

        add(s, "Rodrigo Duterte", "Duterte");
        add(s, DocumentClassifier.FILIPPINERNA, "Duterte");

        add(s, "Vladimir Putin", "Putin");
        add(s, DocumentClassifier.RYSSLAND, "Putin");

        add(s, "Malala Yousafzai", "Malala");

        add(s, "Kim Wall", "Kim Wall");

        add(s, "Theresa May", "Theresa May");
        add(s, DocumentClassifier.STORBRITANNIEN, "Theresa May");

        add(s, "Hassan Rouhani", "Rouhani");
        add(s, "Hassan Rouhani", "Rohani");
        add(s, "Iran", "Rouhani");
        add(s, "Iran", "Rohani");

        add(s, "Marcellus Williams");
        add(s, DocumentClassifier.USA, "Marcellus Williams");

        add(s, "Therese Johaug", "Johaug");
    }

    private static void addPlaces(Set<SubjectRule> l) {

        add(l, "Libyen", "Libyen");
        add(l, "Libyen", "Libya");
        add(l, DocumentClassifier.AFRIKA, "Libyen");
        add(l, DocumentClassifier.AFRIKA, "Libya");

        add(l, DocumentClassifier.AFRIKA, "Afrika");
        add(l, DocumentClassifier.AFRIKA, "Africa");

        add(l, "Lesotho", "Lesotho");
        add(l, DocumentClassifier.AFRIKA, "Lesotho");

        add(l, "Marocko", "Marocko");
        add(l, DocumentClassifier.AFRIKA, "Marocko");
        add(l, "Marocko", "Morocc");
        add(l, DocumentClassifier.AFRIKA, "Morocc");

        add(l, "Tunisien", "Tunis");
        add(l, DocumentClassifier.AFRIKA, "Tunis");

        add(l, "Angola", "Angola");
        add(l, DocumentClassifier.AFRIKA, "Angola");

        add(l, "Kongo-Kinshasa", "Kongo-Kinshasa");
        add(l, DocumentClassifier.AFRIKA, "Kongo-Kinshasa");

        add(l, "Elfenbenskusten", "Elfenbenskusten");
        add(l, DocumentClassifier.AFRIKA, "Elfenbenskusten");
        add(l, "Elfenbenskusten", "Ivory Coast");
        add(l, DocumentClassifier.AFRIKA, "Ivory Coast");
        add(l, "Elfenbenskusten", "Ivorian");
        add(l, DocumentClassifier.AFRIKA, "Ivorian");

        add(l, "Uganda", "Uganda");
        add(l, DocumentClassifier.AFRIKA, "Uganda");

        add(l, "Kamerun", "Kamerun");
        add(l, DocumentClassifier.AFRIKA, "Kamerun");
        add(l, "Kamerun", "Cameroon");
        add(l, DocumentClassifier.AFRIKA, "Cameroon");

        add(l, "Albanien", "Albanien");
        add(l, "Albanien", "Albania");

        add(l, "Barcelona", "Barcelona");

        add(l, "Bosnien", "Bosnien");
        add(l, "Bosnien", "Bosnia");

        add(l, "Belgien", "Belgien");
        add(l, "Belgien", "Belgium");


        add(l, "Brasilien", "Brasilien");
        add(l, "Brasilien", "Brazil");

        add(l, "Chile", "Chile");

        add(l, "Egypten", "Egypt");


        add(l, "Irak", "Iraq");
        add(l, "Irak", "Irak");
        add(l, "Irak", "Mosul");

        add(l, "Indien", "Indien");
        add(l, "Indien", "India");

        add(l, "Oman", "Oman");

        add(l, "Nepal", "Nepal");

        add(l, "Syrien", "Syrien");
        add(l, "Syrien", "Syria");
        add(l, "Syrien", "[Ss]yrisk");
        add(l, "Syrien", "Syrier");
        add(l, "Syrien", "Damascus");
        add(l, "Syrien", "Damaskus");
        add(l, "Raqqa", "Raqqa");
        add(l, "Syrien", "Raqqa");

        add(l, "Venezuela", "Venezuela");
        add(l, "Venezuela", "Maduro");
        add(l, "Nicolás Maduro", "Maduro");

        add(l, "Nordkorea", "Nordkorea");
        add(l, "Nordkorea", "North Korea");
        add(l, "Nordkorea", "N Korea");
        add(l, "Nordkorea", "Kim Jong-un");
        add(l, "Kim Jong-un", "Kim Jong-un");

        add(l, "Sydkorea", "Sydkorea");
        add(l, "Sydkorea", "South Korea");
        add(l, "Sydkorea", "Seoul");
        add(l, "Seoul", "Seoul");
        add(l, "Sydkorea", "Moon Jae-in");
        add(l, "Moon Jae-in", "Moon Jae-in");

        add(l, "Myanmar", "Myanmar");
        add(l, "Myanmar", "Burma");
        add(l, "Myanmar", "Aung San Suu Kyi");
        add(l, "Aung San Suu Kyi", "Aung San Suu Kyi");

        add(l, "Iran", "Iran");

        add(l, "Kina", "Kina");
        add(l, "Kina", "China");
        add(l, "Kina", "Xi Jinping");
        add(l, "Kina", "[Kk]ines");
        add(l, "Xi Jinping", "Xi Jinping");

        add(l, "Kuba", "Cuba");
        add(l, "Kuba", "Kuba");

        add(l, "Spanien", "Spanien");
        add(l, "Spanien", "Spain");
        add(l, "Spanien", "Spanish");
        add(l, "Spanien", "Barcelona");

        add(l, "Italien", "Italien");
        add(l, "Italien", "Itali");
        add(l, "Italien", "Italy");

        add(l, "Sierra Leone", "Sierra Leone");
        add(l, DocumentClassifier.AFRIKA, "Sierra Leone");
        add(l, "Sierra Leone", "Freetown");
        add(l, DocumentClassifier.AFRIKA, "Freetown");

        add(l, "Singapore", "Singapore");

        add(l, "Hong Kong", "Hong Kong");

        add(l, "Jemen", "Jemen");
        add(l, "Jemen", "Yemen");

        add(l, "Kenya", "Kenya");

        add(l, "Kuwait", "Kuwait");

        add(l, "Danmark", "Danmark");
        add(l, "Danmark", "Denmark");
        add(l, "Danmark", "Köpenhamn");
        add(l, "Danmark", "Copenhagen");
        add(l, "Köpenhamn", "Köpenhamn");
        add(l, "Köpenhamn", "Copenhagen");

        add(l, "Bangladesh", "Bangladesh");

        add(l, "Malaysia", "Malaysia");

        add(l, "Frankrike", "Frankrike");
        add(l, "Frankrike", "France");
        add(l, "Frankrike", "[Ff]ransk");
        add(l, "Frankrike", "French");
        add(l, "Frankrike", "Paris");
        add(l, "Paris", "Paris");

        add(l, "Australia", "Australia");

        add(l, "Nederländerna", "Nederländerna");
        add(l, "Nederländerna", "Netherlands");
        add(l, "Nederländerna", "Dutch");

        add(l, "Tjeckien", "Tjeckien");
        add(l, "Tjeckien", "Tjeckisk");
        add(l, "Tjeckien", "Czech");

        add(l, "USA", "USA");
        add(l, "USA", "US");
        add(l, "USA", "FBI");
        add(l, "USA", "U\\.S\\.");
        add(l, "USA", "Orlando");
        add(l, "USA", "California");

        add(l, "Libanon", "Libanon");
        add(l, "Libanon", "Libanes");
        add(l, "Libanon", "Lebanon");
        add(l, "Libanon", "Lebanese");

        add(l, "EU", "EU");
        add(l, "EU", "E\\.U\\.");
        add(l, "EU", "European Union");

        add(l, "United Nations", "United Nations");
        add(l, "United Nations", "U\\.N\\.");

        add(l, "Europa", "Europa");
        add(l, "Europa", "Europe");

        add(l, "Finland", "Finland");

        add(l, "Grekland", "Grekland");
        add(l, "Grekland", "Greek");
        add(l, "Grekland", "Greece");
        add(l, "Grekland", "Grek");

        add(l, "Israel", "Israel");
        add(l, "Israel", "West Bank");

        add(l, "Malmö", "Malmö");

        add(l, "Mexiko", "Mexico");
        add(l, "Mexiko", "Mexiko");
        add(l, "Mexiko", "Mexican");
        add(l, "Tijuana", "Tijuana");
        add(l, "Mexiko", "Tijuana");

        add(l, "Nigeria", "Nigeria");

        add(l, "Norge", "Norge");
        add(l, "Norge", "Norway");
        add(l, "Norge", "[Nn]orska");

        add(l, "Qatar", "Qatar");

        add(l, "Dubai", "Dubai");
        add(l, "United Arab Emirates", "Dubai");
        add(l, "United Arab Emirates", "United Arab Emirates");
        add(l, "United Arab Emirates", "UAE");

        add(l, "Saudiarabien", "Saudiarabien");
        add(l, "Saudiarabien", "Saudi Arabia");

        add(l, DocumentClassifier.SYDAFRIKA, DocumentClassifier.SYDAFRIKA);
        add(l, DocumentClassifier.SYDAFRIKA, "South Africa");
        add(l, DocumentClassifier.AFRIKA, "South Africa");
        add(l, DocumentClassifier.AFRIKA, DocumentClassifier.SYDAFRIKA);

        add(l, "Tanzania", "Tanzania");
        add(l, DocumentClassifier.AFRIKA, "Tanzania");

        add(l, "Sydsudan", "Sydsudan");
        add(l, "Sydsudan", "South Sudan");

        add(l, "Taiwan", "Taiwan");

        add(l, "Turkiet", "Turkiet");
        add(l, "Turkiet", "Turkey");
        add(l, "Turkiet", "Turkish");
        add(l, "Turkiet", "Recep Tayyip Erdogan");
        add(l, "Turkiet", "Istanbul");

        add(l, "Ukraina", "Ukrain");
        add(l, "Ukraina", "Kiev");

        add(l, "Österrike", "Österrike");
        add(l, "Österrike", "Austria");

        add(l, "Schweiz", "Schweiz");

        add(l, "Ungern", "Ungern");
        add(l, "Ungern", "Hungary");

        add(l, "Portugal", "Portugal");

        add(l, "Japan", "Japan");

        add(l, "Argentina", "Argentin");

        add(l, DocumentClassifier.RYSSLAND, DocumentClassifier.RYSSLAND);
        add(l, DocumentClassifier.RYSSLAND, "Russia");
        add(l, DocumentClassifier.RYSSLAND, "[Rr]ysk");
        add(l, DocumentClassifier.RYSSLAND, "Moskva");
        add(l, DocumentClassifier.RYSSLAND, "Kreml");

        add(l, "Kanada", "Kanada");
        add(l, "Kanada", "Canada");
        add(l, "Kanada", "Canadian");
        add(l, "Kanada", "Kanaden");

        add(l, "Palestina", "Palestin");

        add(l, "Afghanistan", "Afghan");
        add(l, "Afghanistan", "Afganistan");
        add(l, "Afghanistan", "Kabul");

        add(l, "Pakistan", "Pakistan");

        add(l, "Baloch", "Baloch");
        add(l, "Pakistan", "Baloch");
        add(l, "Afghanistan", "Baloch");


        add(l, DocumentClassifier.FILIPPINERNA, DocumentClassifier.FILIPPINERNA);
        add(l, DocumentClassifier.FILIPPINERNA, "Manila");
        add(l, DocumentClassifier.FILIPPINERNA, "Philippines");

        add(l, DocumentClassifier.IRLAND, DocumentClassifier.IRLAND);
        add(l, DocumentClassifier.IRLAND, "Irish");
        add(l, DocumentClassifier.IRLAND, "Ireland");

        add(l, "Tibet", "Tibet");

        add(l, "Montenegro", "Montenegro");

        add(l, DocumentClassifier.STORBRITANNIEN, DocumentClassifier.STORBRITANNIEN);
        add(l, DocumentClassifier.STORBRITANNIEN, "Manchester");
        add(l, "Manchester", "Manchester");
        add(l, DocumentClassifier.STORBRITANNIEN, "London");
        add(l, DocumentClassifier.STORBRITANNIEN, "England");
        add(l, DocumentClassifier.STORBRITANNIEN, "Britain");
        add(l, DocumentClassifier.STORBRITANNIEN, "Scotland");
        add(l, DocumentClassifier.STORBRITANNIEN, "British");

        addSweden(l);
    }

    private static void addSweden(Set<SubjectRule> l) {

        add(l, "Gröna Lund", "Gröna Lund");
        add(l, DocumentClassifier.STOCKHOLM, "Gröna Lund");
        add(l, DocumentClassifier.INRIKES, "Gröna Lund");

        add(l, "Eskilstuna", "Eskilstuna");
        add(l, DocumentClassifier.INRIKES, "Eskilstuna");

        add(l, "Bromma flygplats", "Bromma flygplats");
        add(l, DocumentClassifier.INRIKES, "Bromma flygplats");

        add(l, "Kristianstad", "Kristianstad");
        add(l, DocumentClassifier.INRIKES, "Kristianstad");

        add(l, "Arboga", "Arboga");
        add(l, DocumentClassifier.INRIKES, "Arboga");

        add(l, DocumentClassifier.STOCKHOLM, "Stockholm");
        add(l, DocumentClassifier.INRIKES, "Stockholm");
        add(l, DocumentClassifier.STOCKHOLM, "sthlm");
        add(l, DocumentClassifier.INRIKES, "sthlm");

        add(l, DocumentClassifier.INRIKES, "Sverige");
        add(l, DocumentClassifier.INRIKES, "Swedish");
        add(l, DocumentClassifier.INRIKES, "Swede");

        add(l, "Umeå", "Umeå");
        add(l, DocumentClassifier.INRIKES, "Umeå");

        add(l, "Liseberg", "Liseberg");
        add(l, DocumentClassifier.GÖTEBORG, "Liseberg");
        add(l, DocumentClassifier.INRIKES, "Liseberg");

        add(l, DocumentClassifier.GÖTEBORG, DocumentClassifier.GÖTEBORG);
        add(l, DocumentClassifier.GÖTEBORG, "Gothenburg");

        add(l, "Strömsund", "Strömsund");
        add(l, DocumentClassifier.INRIKES, "Strömsund");

        add(l, "Norrköping", "Norrköping");
        add(l, DocumentClassifier.INRIKES, "Norrköping");

        add(l, "Östersund", "Östersund");
        add(l, DocumentClassifier.INRIKES, "Östersund");

        add(l, "Västervik", "Västervik");
        add(l, DocumentClassifier.INRIKES, "Västervik");

        add(l, "Katrineholm", "Katrineholm");
        add(l, DocumentClassifier.INRIKES, "Katrineholm");

        add(l, "Uppsala", "Uppsala");
        add(l, DocumentClassifier.INRIKES, "Uppsala");

        add(l, "Linköping", "Linköping");
        add(l, DocumentClassifier.INRIKES, "Linköping");
    }

    @PostConstruct
    public void postConstruct() {
        boolean isEmpty = !subjectRuleRepository.findAll().iterator().hasNext();
        if (isEmpty) {
            hackCreateDefaultSubjectRules();
        }
    }

    private void hackCreateDefaultSubjectRules() {

        Collection<SubjectRule> defaultSubjectRules = getDefaultSubjectRules();

        defaultSubjectRules.forEach(this::put);
    }

    public void put(SubjectRule subjectRule) {
        Pattern pattern = Pattern.compile(subjectRule.getExpression());

        Optional<SubjectRule> existing = subjectRuleRepository.findOneByNameAndExpression(subjectRule.getName(), subjectRule.getExpression());
        if (existing.isPresent() && !Objects.equals(subjectRule.getId(), existing.get().getId())) {
            throw new IllegalStateException("Subject Rule already present! " + subjectRule + " vs " + existing.get());
        }

        subjectRuleRepository.save(subjectRule);

        documentService.invalidateSubjects();
    }

    public void parseSubjectsFor(Document document) {

        document.setSubjectsParsed(true);

        List<SubjectRule> subjectRules = getAllSubectRules();

        Set<String> matchingSubjects = subjectRules.stream()
                .filter(r -> {
                    boolean match = r.isMatch(document);
                    return match;
                })
                .map(SubjectRule::getName)
                .collect(Collectors.toSet());

        hackAddSubredditAsSubject(matchingSubjects, document);


        document.getSubjects().clear();
        document.getSubjects().addAll(matchingSubjects);
        documentService.put(document);
    }

    private static void hackAddSubredditAsSubject(Set<String> matchingSubjects, Document document) {

        if (new DocumentMatcher(document).has("Reddit")) {
            String cat = document.getSourceCategories().get(0);
            matchingSubjects.add(cat);
        }

    }

    public List<SubjectRule> getAllSubectRules() {
        return Lists.newArrayList(subjectRuleRepository.findAll());
    }

    public Optional<SubjectRule> findSubjectRule(Long id) {
        SubjectRule subjectRule = subjectRuleRepository.findOne(id);
        return Optional.ofNullable(subjectRule);
    }
}
