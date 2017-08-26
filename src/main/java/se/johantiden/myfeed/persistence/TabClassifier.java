package se.johantiden.myfeed.persistence;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static se.johantiden.myfeed.persistence.DocumentClassifier.*;

public class TabClassifier {
    public static Set<TabRule> getDefaultTabRules() {
        Set<TabRule> s = new HashSet<>();

        boolean hideErrors = false;
        add(s, ERROR, "&#", hideErrors);

        boolean hideWeather = true;
        add(s, VÄDER, VÄDER, hideWeather);

        boolean hideSports = true;
        add(s, SPORT, "[Ss]port", hideSports);

        boolean hideCulture = true;
        add(s, CULTURE, "kultur", hideCulture);
        add(s, CULTURE, "kultur-noje", hideCulture);
        add(s, CULTURE, "dnbok", hideCulture);

        add(s, NEWS, "aljazeera.*news", false);
        add(s, NEWS, "aljazeera.*insidestory", false);
        add(s, NEWS, "aljazeera.*opinion", false);
        add(s, NEWS, "aljazeera.*features", false);
        add(s, NEWS, "aljazeera.*indepth", false);
        add(s, NEWS, "aljazeera.*programmes", false);
        add(s, NEWS, "aljazeera.*focus", false);
        add(s, NEWS, "svt.*nyheter", false);
        add(s, NEWS, "Dagens Nyheter.*nyheter", false);
        add(s, NEWS, "Dagens Nyheter.*varlden", false);
        add(s, NEWS, "Dagens Nyheter.*sverige", false);
        add(s, NEWS, "Dagens Nyheter.*debatt", false);
        add(s, NEWS, "Dagens Nyheter.*sthlm", false);
        add(s, NEWS, "Dagens Nyheter.*ledare", false);
        add(s, NEWS, STOCKHOLM, false);
        add(s, NEWS, "TheLocal", false);
        add(s, NEWS, "Reuters - World", false);
        add(s, NEWS, "Los Angeles Times - World", false);
        add(s, NEWS, "Washington Post - The Fact Checker", false);
        add(s, NEWS, "Washington Post - WorldViews", false);
        add(s, NEWS, "worldnews", false);
        add(s, NEWS, "New York Times - World", false);
        add(s, NEWS, "Reddit.*politics", false);
        add(s, NEWS, "Reddit.*news", false);
        add(s, NEWS, "Reddit.*worldnews", false);

        add(s, TECH, "Slashdot", false);
        add(s, TECH, "HackerNews", false);
        add(s, TECH, "Engadget", false);
        add(s, TECH, "Science", false);
        add(s, TECH, "ProgrammerHumor", false);
        add(s, TECH, "Ars Technica.*tech-policy", false);
        add(s, TECH, "Ars Technica.*gadgets", false);
        add(s, TECH, "Ars Technica.*gaming", false);
        add(s, TECH, "Ars Technica.*facebook", false);
        add(s, TECH, "Ars Technica.*opposable thumbs", false);
        add(s, TECH, "Ars Technica.*the-multiverse", false);
        add(s, TECH, "Ars Technica.*Technology Lab", false);
        add(s, TECH, "Ars Technica.*Ministry of Innovation", false);
        add(s, TECH, "Reddit.*technology", false);
        add(s, TECH, NETFLIX, false);
        add(s, TECH, SPOTIFY, false);
        add(s, TECH, MICROSOFT, false);
        add(s, TECH, FACEBOOK, false);
        add(s, TECH, IT_SÄKERHET, false);
        add(s, TECH, FORSKNING, false);
        add(s, TECH, UTVECKLING, false);
        add(s, TECH, "LinkedIn", false);

        add(s, FUN, "AskReddit", false);
        add(s, FUN, "Reddit.*gaming", false);
        add(s, FUN, "Reddit.*pics", false);
        add(s, FUN, "Reddit.*gifs", false);
        add(s, FUN, "Reddit.*funny", false);
        add(s, FUN, "Reddit.*PoliticalHumor", false);
        add(s, FUN, "Reddit.*mildlyinteresting", false);
        add(s, FUN, "Reddit.*Design", false);
        add(s, FUN, "Reddit.*aww", false);
        add(s, FUN, "Reddit.*sports", false);
        add(s, FUN, "Reddit.*music", false);
        add(s, FUN, "Reddit.*videos", false);
        add(s, FUN, "Reddit.*todayilearned", false);
        add(s, FUN, "Reddit.*NatureIsFuckingLit", false);
        add(s, FUN, "Reddit.*nottheonion", false);
        add(s, FUN, "Reddit.*MarchAgainstTrump", false);
        add(s, FUN, "Reddit.*Showerthoughts", false);
        add(s, FUN, "Reddit.*photoshopbattles", false);
        add(s, FUN, "Reddit.*oddlysatisfying", false);
        add(s, FUN, "Reddit.*space", false);
        add(s, FUN, "Reddit.*mildlyinfuriating", false);
        add(s, FUN, "Reddit.*TrumpCriticizesTrump", false);
        add(s, FUN, "xkcd", false);

        add(s, BIZ, "näringsliv", false);
        add(s, BIZ, "investerar", false);
        add(s, BIZ, "Klarna", false);
        add(s, BIZ, "Tesla", false);
        add(s, BIZ, "Scania", false);
        add(s, BIZ, "börsraket", false);
        add(s, BIZ, "New York Times - World.*business", false);
        add(s, BIZ, "Slashdot.*business", false);
        add(s, BIZ, "Breakit", false);
        add(s, BIZ, EKONOMI, false);
        add(s, BIZ, FINGERPRINT, false);

        boolean hideBad = true;
        add(s, BAD, YOUR_BRIEFING, hideBad);
        add(s, BAD, HIRING, hideBad);
        add(s, BAD, WEBB_TV, hideBad);
        add(s, BAD, SERIER, hideBad);
        add(s, BAD, CARS, hideBad);
        add(s, BAD, DEALMASTER, hideBad);
        add(s, BAD, LEAGUEOFLEGENDS, hideBad);
        add(s, BAD, IDAGSIDAN, hideBad);
        add(s, BAD, MAT, hideBad);
        add(s, BAD, NEWS_GRID, hideBad);
        add(s, BAD, FRÅGESPORT, hideBad);
        add(s, BAD, JUNIOR, hideBad);
        add(s, BAD, PERFECT_GUIDE, hideBad);
        add(s, BAD, I_AM_A, hideBad);
        add(s, BAD, BLACK_PEOPLE_TWITTER, hideBad);
        add(s, BAD, THE_DENNIS, hideBad);
        add(s, BAD, NUMBER_OF_PEOPLE, hideBad);
        add(s, BAD, NUTIDSTESTET, hideBad);
        add(s, BAD, RESOR, hideBad);
        add(s, BAD, UUTISET, hideBad);
        add(s, BAD, HÄR_ÄR, hideBad);
        add(s, BAD, "Dagens Industri.*/bil/", hideBad);
        add(s, BAD, "interestingasfuck", hideBad);
        add(s, BAD, "Jokes", hideBad);
        add(s, BAD, "OldSchoolCool", hideBad);
        add(s, BAD, "GetMotivated", hideBad);
        add(s, BAD, "2meirl4meirl", hideBad);
        add(s, BAD, "thisismylifenow", hideBad);
        add(s, BAD, "facepalm", hideBad);
        add(s, BAD, "VIDEO:", hideBad);
        add(s, BAD, "Dagens Nyheter.*blogg", hideBad);
        add(s, BAD, "Så\\.\\.\\.", hideBad);
        add(s, "Bad", "EarthPorn", hideBad);
        add(s, "Bad", "MemeEconomy", hideBad);
        add(s, "Bad", "dankmemes", hideBad);
        add(s, "Bad", "bestof", hideBad);
        add(s, "Bad", "wholesomememes", hideBad);
        add(s, "Bad", "Wirecutter", hideBad);
        add(s, "Bad", "Week in pictures", hideBad);
        add(s, "Bad", "Minnesord", hideBad);
        add(s, "Bad", "Arkitektur", hideBad);
        add(s, "Bad", "rarepuppers", hideBad);
        add(s, "Bad", "Medan du sov", hideBad);
        add(s, "Bad", "DN gratulerar", hideBad);
        add(s, "Bad", "neoliberal", hideBad);
        add(s, "Bad", "me_irl", hideBad);
        add(s, "Bad", "Mode/Kläder", hideBad);

        add(s, "No Subjects", "subjects:\\[\\]", false);

        return s;
    }

    private static void add(Collection<TabRule> tabs, String name, String expression, boolean hide) {
        tabs.add(new TabRule(name, expression, hide));
    }
}
