package Utils;

public class StringManipulation {

    public static String expandUsername(String username) {
        return username.replace(".", " ");
    }
    public static String condensUsername(String username) {
        return username.replace(" ", ".");
    }
}
