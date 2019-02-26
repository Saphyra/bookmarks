package org.github.bookmarks.common.util;

import java.util.Optional;

public class Util {
    public static <T> T replaceIfNotNull(T original, T target){
        return Optional.ofNullable(target).orElse(original);
    }
}
