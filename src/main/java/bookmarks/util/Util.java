package bookmarks.util;

import java.util.Optional;

public class Util {
    public static <T> T replaceIfNotNull(T original, T target){
        return Optional.ofNullable(target).orElse(original);
    }

    public static void sleep(long delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
