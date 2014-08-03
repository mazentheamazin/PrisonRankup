package io.mazenmc.prisonrankup.utils;

public final class StringUtil {

    /**
     * Builds a string that is safe to be used in a loop
     * @param objects Objects you wish to build into a string
     * @return Builded String
     */
    public static String buildString(Object... objects) {
        StringBuilder sb = new StringBuilder();

        for(Object o : objects) {
            sb.append(o);
        }

        return sb.toString();
    }
}
