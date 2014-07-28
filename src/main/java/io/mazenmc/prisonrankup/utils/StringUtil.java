package io.mazenmc.prisonrankup.utils;

public final class StringUtil {

    public static String buildString(Object... objects) {
        StringBuilder sb = new StringBuilder();

        for(Object o : objects) {
            sb.append(o);
        }

        return sb.toString();
    }
}
