package se.johantiden.myfeed.persistence;

import java.util.Set;
import java.util.TreeSet;

import static se.johantiden.myfeed.persistence.DocumentClassifier.*;

public class SubjectClassifier {

    public static final String GAMING = "Gaming";
    public static final String UTVECKLING = "Utveckling";

    public static Set<SubjectRule> getDefaultSubjectRules() {
        TreeSet<SubjectRule> s = new TreeSet<>(SubjectRule.COMPARATOR);
        add(s, EKONOMI, "ekonomi");
        add(s, EKONOMI, "näringsliv");
        add(s, EKONOMI, "[Oo]ljepris");
        add(s, EKONOMI, "[Oo]ljelager");
        add(s, EKONOMI, "[Bb]örs");
        add(s, EKONOMI, "[Aa]ktie");

        add(s, "Ledare", "[Ll]edare");
        add(s, "Ledare", "LEDARE");
        add(s, "Sport", "[Ss]port");
        add(s, "Sport", "[Ff]otboll");
        add(s, "Sport", "[Ff]ootball");
        add(s, YOUR_BRIEFING, "[Yy]our.+[Bb]riefing");
        add(s, "Tim Berners-Lee", "Tim Berners\\-Lee");

        add(s, "Arkitektur", "Architecht");
        add(s, "Arkitektur", "Arkitekt");

        add(s, TYSKLAND, "[Gg]erman");
        add(s, TYSKLAND, "[Tt]ysk");
        add(s, TYSKLAND, "Brandenburg");
        add(s, TYSKLAND, "merkel");
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

        add(s, EUROVISION, EUROVISION);


        add(s, "FBI");
        add(s, SPOTIFY);
        add(s, MICROSOFT);
        add(s, "Verizon");
        add(s, "Uber");
        add(s, "Samsung");
        add(s, "Apple");
        add(s, "Apple", "iOS");
        add(s, "Apple", "iPad");
        add(s, "Apple", "iPhone");
        add(s, FACEBOOK);
        add(s, "Zuckerberg");
        add(s, "HTC");
        add(s, FACEBOOK, "Zuckerberg");
        add(s, FINGERPRINT);
        add(s, "Klarna");
        add(s, "Tesla");
        add(s, "Scania");
        add(s, "LinkedIn", "Linked[Ii]n");
        add(s, "Nintendo");
        add(s, "Amazon");
        add(s, GAMING, "Nintendo");
        add(s, "Google");
        add(s, "H&M");
        add(s, "HP");

        add(s, "Net Neutrality", "Net Neutrality");
        add(s, "Blockchain", "[Bb]lock.*chain");
        add(s, "Blockchain", "[Bb]lockkedja");

        add(s, "Science", "[Ss]cience");
        add(s, "Science", "[Ss]cientific");
        add(s, "Science", "MIT");
        add(s, "Science", "[Rr]esearch");
        add(s, "Science", "arxiv\\.org");
        add(s, "Science", "[Aa]stronom");

        add(s, "Drone", "[Dd]rone");

        add(s, "Blog", "[Bb]log");

        add(s, DocumentClassifier.UTVECKLING, "[Mm]emory leak");
        add(s, DocumentClassifier.UTVECKLING, "Haskell");
        add(s, DocumentClassifier.UTVECKLING, "Pascal");

        add(s, MUSEUM);
        add(s, "Musik", "[Mm]usik");
        add(s, "Musik", "[Hh]iphop");
        add(s, "Musik", "[Mm]usician");
        add(s, "Musik", "[Mm]usiker");
        add(s, "Konst", "[Kk]onstnär");
        add(s, "Konst", "[Gg]uitar");
        add(s, BÖCKER, "[Ff]författare");
        add(s, "Film/TV", "[Dd]ramaserie");
        add(s, "Mode/Kläder", "[Yy]tterplagg");

        add(s, "Naturen", "[Ff]orest");

        add(s, "Terror", "[Tt]error");
        add(s, "Ebola", "Ebola");
        add(s, "Kolera", "Kolera");
        add(s, "Kolera", "Cholera");

        add(s, IT_SÄKERHET, "IT-attack");
        add(s, IT_SÄKERHET, "[Rr]ansomware");
        add(s, IT_SÄKERHET, "[Cc]yberattack");
        add(s, IT_SÄKERHET, "[Mm]alware");
        add(s, IT_SÄKERHET, "[Ww]ana[Cc]ry");
        add(s, IT_SÄKERHET, "[Ww]anna[Cc]ry");
        add(s, IT_SÄKERHET, "[Hh]acker[^N]");
        add(s, IT_SÄKERHET, "[Hh]acking");
        add(s, IT_SÄKERHET, "IT-utpressning");
        add(s, IT_SÄKERHET, "IT-angrepp");
        add(s, IT_SÄKERHET, "[Ii]nternet [Ss]ecurity");
        add(s, IT_SÄKERHET, "[Ee]ternalblue");
        add(s, IT_SÄKERHET, "[Bb]otnet");
        add(s, IT_SÄKERHET, " [Hh]ack ");

        add(s, "Brexit", "Brexit");

        add(s, "Feministiskt Initiativ", "Feministiskt [Ii]nitiativ");
        add(s, VÄNSTERPARTIET);
        add(s, MILJÖPARTIET);
        add(s, SOCIALDEMOKRATERNA);
        add(s, CENTERPARTIET);
        add(s, LIBERALERNA);
        add(s, KRISTDEMOKRATERNA);
        add(s, KRISTDEMOKRATERNA, "KD[ \\.]");
        add(s, MODERATERNA);
        add(s, "Alliansen", "[Aa]lliansen");
        add(s, "Regeringen", "[Rr]egeringen");
        add(s, "Borgerlig", "[Bb]orgerlig");
        add(s, SVERIGEMOKRATERNA);
        add(s, SVERIGEMOKRATERNA, "SD[ \\.]");

        add(s, "Pope Francis");
        add(s, "Debatt", "[Dd]ebatt");

        add(s, "Mecca", "Mecca");
        add(s, "Mecca", "Hajj");
        add(s, "Mecca", "[Aa]l-[Aa]qsa");

        add(s, "Foliehatt", "CCTV");

        add(s, "Kvinnor", REGEX_KVINNOR_MÄN);
        add(s, "Män", REGEX_KVINNOR_MÄN);

        add(s, HIRING, "[Hh]iring.*HackerNews");
        add(s, IDAGSIDAN);

        add(s, HISTORIA, "historian");
        add(s, HISTORIA, "1500");
        add(s, HISTORIA, "1600");
        add(s, HISTORIA, "1700");
        add(s, HISTORIA, "1800");

        add(s, MAT, "[Rr]ecipe:.*TheLocal");
        add(s, MAT, "mat-dryck");
        add(s, MAT, "Restaurants");

        add(s, "Daesh", "Daesh");
        add(s, "Daesh", "Islamic State");
        add(s, "Daesh", "ISIL");
        add(s, "Daesh", "ISIS");
        add(s, "Daesh", "([Tt]error.*IS)|(IS.*[Tt]error)");

        add(s, "Taliban", "Taliban");

        add(s, GAMING, "[Gg]aming");
        add(s, GAMING, "[Gg]ames");
        add(s, "Cars", "cars technica");
        add(s, NEWS_GRID);
        add(s, WEBB_TV);
        add(s, LEAGUEOFLEGENDS);
        add(s, DEALMASTER);
        add(s, "Dödsfälla", "[Dd]ödsfälla");
        add(s, "Motor", "[Mm]otor");
        add(s, "Serier", "[Ss]erier");

        add(s, HÄR_ÄR, "– här är");
        add(s, HÄR_ÄR, "- här är");
        add(s, HÄR_ÄR, HÄR_ÄR);

        add(s, "Uutiset", UUTISET);
        add(s, "VIDEO", "^VIDEO");

        add(s, "Naturkatastrof", "[Jj]ordbävning");
        add(s, "Naturkatastrof", "[Ee]arthquake");
        add(s, "Naturkatastrof", "[Ll]andslide");
        add(s, "Naturkatastrof", "[Mm]udslide");
        add(s, "Naturkatastrof", "[Oo]rkan");

        add(s, "Talaq", "[Tt]alaq");

        add(s, FRÅGESPORT, "fragesport");
        add(s, JUNIOR);
        add(s, NUTIDSTESTET);
        add(s, PERFECT_GUIDE);
        add(s, RESOR);
        add(s, I_AM_A, "IAmA");
        add(s, BLACK_PEOPLE_TWITTER);
        add(s, THE_DENNIS);

        add(s, NUMBER_OF_PEOPLE, "-- number");
        add(s, NUMBER_OF_PEOPLE, "--number");

        add(s, DocumentClassifier.UTVECKLING, "[Pp]rogramming");

        add(s, "Solförmörkelse", "[Ee]clipse");
        add(s, "Solförmörkelse", "[Ss]olförmörkelse");

        add(s, "Big Ben", "Big Ben");
        add(s, "Så...", "Så.* du");
        add(s, "USA", "San Francisco");
        add(s, "San Francisco", "San Francisco");
        add(s, "London", "Buckingham Palace");
        add(s, "London", "London");
        add(s, STORBRITANNIEN, "Buckingham Palace");

        add(s, "YouTube", "[Yy]ou[Tt]ube");
        add(s, "USA", "Massachusetts");
        add(s, "USA", "Cleveland");
        add(s, "USA", "Texas");
        add(s, "Vanuatu");
        add(s, "Örebro");
        add(s, INRIKES, "Örebro");
        add(s, "Nike");
        add(s, GAMING, "Dungeons and Dragons");

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
        add(s, USA, "Obama");

        add(s, "Trump", "Trump");
        add(s, USA, "Trump");

        add(s, "Ebba Busch Thor", "Busch Thor");
        add(s, KRISTDEMOKRATERNA, "Busch Thor");
        add(s, INRIKES, "Busch Thor");

        add(s, "Alice Bah Kuhnke", "Alice Bah Kuhnke");
        add(s, MILJÖPARTIET, "Alice Bah Kuhnke");
        add(s, INRIKES, "Alice Bah Kuhnke");

        add(s, "Mugabe", "Mugabe");
        add(s, SYDAFRIKA, "Mugabe");
        add(s, AFRIKA, "Mugabe");

        add(s, "Jonas Sjöstedt", "Jonas Sjöstedt");
        add(s, VÄNSTERPARTIET, "Jonas Sjöstedt");
        add(s, INRIKES, "Jonas Sjöstedt");

        add(s, "Annie Lööf", "Annie Lööf");
        add(s, CENTERPARTIET, "Annie Lööf");
        add(s, INRIKES, "Annie Lööf");

        add(s, "Stefan Löfven", "Löfven");
        add(s, SOCIALDEMOKRATERNA, "Löfven");
        add(s, INRIKES, "Löfven");

        add(s, "Jimmie Åkesson", "Jimmie Åkesson");
        add(s, SOCIALDEMOKRATERNA, "Jimmie Åkesson");
        add(s, INRIKES, "Jimmie Åkesson");

        add(s, "Jan Björklund", "Björklund");
        add(s, LIBERALERNA, "Björklund");
        add(s, INRIKES, "Björklund");

        add(s, "Kinberg Batra");
        add(s, MODERATERNA, "Kinberg Batra");

        add(s, "Leo Varadkar", "Varadkar");
        add(s, IRLAND, "Varadkar");

        add(s, "Rodrigo Duterte", "Duterte");
        add(s, FILIPPINERNA, "Duterte");

        add(s, "Vladimir Putin", "Putin");
        add(s, RYSSLAND, "Putin");

        add(s, "Malala Yousafzai", "Malala");

        add(s, "Kim Wall", "Kim Wall");

        add(s, "Theresa May", "Theresa May");
        add(s, STORBRITANNIEN, "Theresa May");

        add(s, "Hassan Rouhani", "Rouhani");
        add(s, "Hassan Rouhani", "Rohani");
        add(s, "Iran", "Rouhani");
        add(s, "Iran", "Rohani");

        add(s, "Marcellus Williams");
        add(s, USA, "Marcellus Williams");

        add(s, "Therese Johaug", "Johaug");
    }

    private static void addPlaces(Set<SubjectRule> s) {

        add(s, "Libyen", "Libyen");
        add(s, "Libyen", "Libya");
        add(s, AFRIKA, "Libyen");
        add(s, AFRIKA, "Libya");

        add(s, AFRIKA, "Afrika");
        add(s, AFRIKA, "Africa");

        add(s, "Lesotho", "Lesotho");
        add(s, AFRIKA, "Lesotho");

        add(s, "Marocko", "Marocko");
        add(s, AFRIKA, "Marocko");
        add(s, "Marocko", "Morocc");
        add(s, AFRIKA, "Morocc");

        add(s, "Tunisien", "Tunis");
        add(s, AFRIKA, "Tunis");

        add(s, "Angola", "Angola");
        add(s, AFRIKA, "Angola");

        add(s, "Kongo-Kinshasa", "Kongo-Kinshasa");
        add(s, AFRIKA, "Kongo-Kinshasa");

        add(s, "Elfenbenskusten", "Elfenbenskusten");
        add(s, AFRIKA, "Elfenbenskusten");
        add(s, "Elfenbenskusten", "Ivory Coast");
        add(s, AFRIKA, "Ivory Coast");
        add(s, "Elfenbenskusten", "Ivorian");
        add(s, AFRIKA, "Ivorian");

        add(s, "Uganda", "Uganda");
        add(s, AFRIKA, "Uganda");

        add(s, "Kamerun", "Kamerun");
        add(s, AFRIKA, "Kamerun");
        add(s, "Kamerun", "Cameroon");
        add(s, AFRIKA, "Cameroon");

        add(s, "Albanien", "Albanien");
        add(s, "Albanien", "Albania");

        add(s, "Barcelona", "Barcelona");

        add(s, "Bosnien", "Bosnien");
        add(s, "Bosnien", "Bosnia");

        add(s, "Belgien", "Belgien");
        add(s, "Belgien", "Belgium");
        add(s, "Bryssel", "Bryssel");
        add(s, "Belgien", "Bryssel");
        add(s, "Bryssel", "Brussels");
        add(s, "Belgien", "Brussels");


        add(s, "Brasilien", "Brasilien");
        add(s, "Brasilien", "Brazil");

        add(s, "Chile", "Chile");

        add(s, "Egypten", "Egypt");


        add(s, "Irak", "Iraq");
        add(s, "Irak", "Irak");
        add(s, "Irak", "Mosul");

        add(s, "Indien", "Indien");
        add(s, "Indien", "India");

        add(s, "Oman", "Oman");

        add(s, "Nepal", "Nepal");

        add(s, "Syrien", "Syrien");
        add(s, "Syrien", "Syria");
        add(s, "Syrien", "[Ss]yrisk");
        add(s, "Syrien", "Syrier");
        add(s, "Syrien", "Damascus");
        add(s, "Syrien", "Damaskus");
        add(s, "Raqqa", "Raqqa");
        add(s, "Syrien", "Raqqa");

        add(s, "Venezuela", "Venezuela");
        add(s, "Venezuela", "Maduro");
        add(s, "Nicolás Maduro", "Maduro");

        add(s, "Nordkorea", "Nordkorea");
        add(s, "Nordkorea", "North Korea");
        add(s, "Nordkorea", "N Korea");
        add(s, "Nordkorea", "Kim Jong-un");
        add(s, "Kim Jong-un", "Kim Jong-un");

        add(s, "Sydkorea", "Sydkorea");
        add(s, "Sydkorea", "South Korea");
        add(s, "Sydkorea", "Seoul");
        add(s, "Seoul", "Seoul");
        add(s, "Sydkorea", "Moon Jae-in");
        add(s, "Moon Jae-in", "Moon Jae-in");

        add(s, "Myanmar", "Myanmar");
        add(s, "Myanmar", "Burma");
        add(s, "Myanmar", "Aung San Suu Kyi");
        add(s, "Aung San Suu Kyi", "Aung San Suu Kyi");

        add(s, "Iran", "Iran");

        add(s, "Kina", "Kina");
        add(s, "Kina", "China");
        add(s, "Kina", "Xi Jinping");
        add(s, "Kina", "[Kk]ines");
        add(s, "Xi Jinping", "Xi Jinping");

        add(s, "Kuba", "Cuba");
        add(s, "Kuba", "Kuba");

        add(s, "Spanien", "Spanien");
        add(s, "Spanien", "Spain");
        add(s, "Spanien", "Spanish");
        add(s, "Spanien", "Barcelona");
        add(s, "Katalonien", "Katalan");
        add(s, "Katalonien", "Katalon");
        add(s, "Spanien", "Katalan");
        add(s, "Spanien", "Katalon");

        add(s, "Italien", "Italien");
        add(s, "Italien", "Itali");
        add(s, "Italien", "Italy");

        add(s, "Sierra Leone", "Sierra Leone");
        add(s, AFRIKA, "Sierra Leone");
        add(s, "Sierra Leone", "Freetown");
        add(s, AFRIKA, "Freetown");

        add(s, "Singapore", "Singapore");

        add(s, "Somalien", "Somali");

        add(s, "Yingluck Shinawatra", "Yingluck");
        add(s, "Thailand", "Yingluck");
        add(s, "Thailand", "Thailand");

        add(s, "Hong Kong", "Hong Kong");

        add(s, "Jemen", "Jemen");
        add(s, "Jemen", "Yemen");

        add(s, "Kenya", "Kenya");

        add(s, "Kuwait", "Kuwait");

        add(s, "Danmark", "Danmark");
        add(s, "Danmark", "Denmark");
        add(s, "Danmark", "Köpenhamn");
        add(s, "Danmark", "Copenhagen");
        add(s, "Köpenhamn", "Köpenhamn");
        add(s, "Köpenhamn", "Copenhagen");

        add(s, "Bangladesh", "Bangladesh");

        add(s, "Malaysia", "Malaysia");

        add(s, "Frankrike", "Frankrike");
        add(s, "Frankrike", "France");
        add(s, "Frankrike", "[Ff]ransk");
        add(s, "Frankrike", "French");
        add(s, "Frankrike", "Paris");
        add(s, "Paris", "Paris");

        add(s, "Australien", "Australien");
        add(s, "Australien", "Australia");
        add(s, "Australien", "Sydney");

        add(s, "Nya Zeeland", "Nya Zeeland");
        add(s, "Nya Zeeland", "New Zealand");

        add(s, "Nederländerna", "Nederländerna");
        add(s, "Nederländerna", "Netherlands");
        add(s, "Nederländerna", "Dutch");

        add(s, "Tjeckien", "Tjeckien");
        add(s, "Tjeckien", "Tjeckisk");
        add(s, "Tjeckien", "Czech");

        add(s, "USA", "USA");
        add(s, "USA", "US");
        add(s, "USA", "FBI");
        add(s, "USA", "U\\.S\\.");
        add(s, "USA", "Orlando");
        add(s, "USA", "California");
        add(s, "Charlottesville", "Charlottesville");
        add(s, "USA", "Charlottesville");
        add(s, "Washington", "Washington");
        add(s, "Washington", "[Vv]ita huset");
        add(s, "USA", "[Vv]ita huset");
        add(s, "USA", "Washington");

        add(s, "Libanon", "Libanon");
        add(s, "Libanon", "Libanes");
        add(s, "Libanon", "Lebanon");
        add(s, "Libanon", "Lebanese");

        add(s, "EU", "EU");
        add(s, "EU", "E\\.U\\.");
        add(s, "EU", "European Union");

        add(s, "United Nations", "United Nations");
        add(s, "United Nations", "U\\.N\\.");

        add(s, "Uruguay");

        add(s, "Europa", "Europa");
        add(s, "Europa", "Europe");

        add(s, "Finland", "Finland");
        add(s, "Finland", "Åbo");
        add(s, "Finland", "Turku");
        add(s, "Åbo", "Åbo");
        add(s, "Åbo", "Turku");

        add(s, "Grekland", "Grekland");
        add(s, "Grekland", "Greek");
        add(s, "Grekland", "Greece");
        add(s, "Grekland", "Grek");

        add(s, "Israel", "Israel");
        add(s, "Israel", "West Bank");

        add(s, "Malmö", "Malmö");

        add(s, "Mexiko", "Mexico");
        add(s, "Mexiko", "Mexiko");
        add(s, "Mexiko", "Mexican");
        add(s, "Tijuana", "Tijuana");
        add(s, "Mexiko", "Tijuana");

        add(s, "Nigeria", "Nigeria");

        add(s, "Norge", "Norge");
        add(s, "Norge", "Norway");
        add(s, "Norge", "[Nn]orska");

        add(s, "Qatar", "Qatar");

        add(s, "Dubai", "Dubai");
        add(s, "United Arab Emirates", "Dubai");
        add(s, "United Arab Emirates", "United Arab Emirates");
        add(s, "United Arab Emirates", "UAE");

        add(s, "Saudiarabien", "Saudiarabien");
        add(s, "Saudiarabien", "Saudi Arabia");

        add(s, SYDAFRIKA, SYDAFRIKA);
        add(s, SYDAFRIKA, "South Africa");
        add(s, AFRIKA, "South Africa");
        add(s, AFRIKA, SYDAFRIKA);

        add(s, "Tanzania", "Tanzania");
        add(s, AFRIKA, "Tanzania");

        add(s, "Sydsudan", "Sydsudan");
        add(s, "Sydsudan", "South Sudan");

        add(s, "Taiwan", "Taiwan");

        add(s, "Turkiet", "Turkiet");
        add(s, "Turkiet", "Turkey");
        add(s, "Turkiet", "Turkish");
        add(s, "Turkiet", "Recep Tayyip Erdogan");
        add(s, "Turkiet", "Istanbul");

        add(s, "Ukraina", "Ukrain");
        add(s, "Ukraina", "Kiev");

        add(s, "Österrike", "Österrike");
        add(s, "Österrike", "Austria");

        add(s, "Schweiz", "Schweiz");

        add(s, "Ungern", "Ungern");
        add(s, "Ungern", "Hungary");

        add(s, "Portugal", "Portugal");

        add(s, "Japan", "Japan");

        add(s, "Argentina", "Argentin");

        add(s, RYSSLAND, RYSSLAND);
        add(s, RYSSLAND, "Russia");
        add(s, RYSSLAND, "[Rr]ysk");
        add(s, RYSSLAND, "Moskva");
        add(s, RYSSLAND, "Kreml");

        add(s, "Kanada", "Kanada");
        add(s, "Kanada", "Canada");
        add(s, "Kanada", "Canadian");
        add(s, "Kanada", "Kanaden");

        add(s, "Palestina", "Palestin");

        add(s, "Afghanistan", "Afghan");
        add(s, "Afghanistan", "Afganistan");
        add(s, "Afghanistan", "Kabul");

        add(s, "Pakistan", "Pakistan");

        add(s, "Baloch", "Baloch");
        add(s, "Pakistan", "Baloch");
        add(s, "Afghanistan", "Baloch");


        add(s, FILIPPINERNA, FILIPPINERNA);
        add(s, FILIPPINERNA, "Manila");
        add(s, FILIPPINERNA, "Philippines");

        add(s, IRLAND, IRLAND);
        add(s, IRLAND, "Irish");
        add(s, IRLAND, "Ireland");

        add(s, "Tibet", "Tibet");

        add(s, "Montenegro", "Montenegro");

        add(s, STORBRITANNIEN, STORBRITANNIEN);
        add(s, STORBRITANNIEN, "Manchester");
        add(s, "Manchester", "Manchester");
        add(s, STORBRITANNIEN, "London");
        add(s, STORBRITANNIEN, "England");
        add(s, STORBRITANNIEN, "Britain");
        add(s, STORBRITANNIEN, "Scotland");
        add(s, STORBRITANNIEN, "British");

        addSweden(s);
    }

    private static void addSweden(Set<SubjectRule> l) {

        add(l, "Gröna Lund", "Gröna Lund");
        add(l, STOCKHOLM, "Gröna Lund");
        add(l, INRIKES, "Gröna Lund");

        add(l, "Eskilstuna", "Eskilstuna");
        add(l, INRIKES, "Eskilstuna");

        add(l, "Bromma flygplats", "Bromma flygplats");
        add(l, INRIKES, "Bromma flygplats");

        add(l, "Kristianstad", "Kristianstad");
        add(l, INRIKES, "Kristianstad");

        add(l, "Arboga", "Arboga");
        add(l, INRIKES, "Arboga");

        add(l, STOCKHOLM, "Stockholm");
        add(l, INRIKES, "Stockholm");
        add(l, STOCKHOLM, "sthlm");
        add(l, INRIKES, "sthlm");

        add(l, INRIKES, "Sverige");
        add(l, INRIKES, "Swedish");
        add(l, INRIKES, "Swede");

        add(l, "Umeå", "Umeå");
        add(l, INRIKES, "Umeå");

        add(l, "Liseberg", "Liseberg");
        add(l, GÖTEBORG, "Liseberg");
        add(l, INRIKES, "Liseberg");

        add(l, GÖTEBORG, GÖTEBORG);
        add(l, GÖTEBORG, "Gothenburg");

        add(l, "Strömsund", "Strömsund");
        add(l, INRIKES, "Strömsund");

        add(l, "Norrköping", "Norrköping");
        add(l, INRIKES, "Norrköping");

        add(l, "Östersund", "Östersund");
        add(l, INRIKES, "Östersund");

        add(l, "Västervik", "Västervik");
        add(l, INRIKES, "Västervik");

        add(l, "Katrineholm", "Katrineholm");
        add(l, INRIKES, "Katrineholm");

        add(l, "Uppsala", "Uppsala");
        add(l, INRIKES, "Uppsala");

        add(l, "Linköping", "Linköping");
        add(l, INRIKES, "Linköping");
    }
}
