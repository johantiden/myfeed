package se.johantiden.myfeed.util;

public class JString {
    public static boolean containsIgnoreCase(String fullString, String substring) {
        if (fullString == null) {
            return false;
        }

        return fullString.toLowerCase().contains(substring.toLowerCase());

    }
}
