package io.mazenmc.prisonrankup.utils;

import org.bukkit.ChatColor;

public final class LangUtil {

    public static String toColor(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String[] toColor(String... strings) {
        String[] newStr = new String[strings.length];

        for(int i = 0; i < strings.length; i++) {
            newStr[i] = toColor(strings[i]);
        }

        return newStr;
    }

    public static String error(String input) {
        return ChatColor.DARK_RED + input;
    }
}
