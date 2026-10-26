
package utils;


public class Validation {
     public static boolean isValidCollection(String collection) {

        return collection.equalsIgnoreCase("Game")
                || collection.equalsIgnoreCase("Movie")
                || collection.equalsIgnoreCase("Music");
    }

    public static boolean isValidType(String type) {

        return type.equalsIgnoreCase("Audio")
                || type.equalsIgnoreCase("Video");
    }

    public static boolean isValidPrice(double price) {
        return price >= 0;
    }

    public static boolean isValidYear(int year) {
        return year >= 1900 && year <= 2100;
    }
}

