package io.mazenmc.prisonrankup.utils;

import org.bukkit.ChatColor;

public class LangUtil {

    public static String toColor(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String error(String input) {
        return ChatColor.DARK_RED + input;
    }
}
