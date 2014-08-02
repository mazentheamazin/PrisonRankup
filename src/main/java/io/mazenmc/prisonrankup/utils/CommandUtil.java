package io.mazenmc.prisonrankup.utils;

import org.bukkit.command.CommandSender;

public class CommandUtil {

    public static boolean checkArgs(int length, String[] args, CommandSender sender) {
        if(args.length < length) {
            sender.sendMessage(LangUtil.error("Invalid syntax, please refer to command help for more information"));
        }

        return args.length < length;
    }

    public static boolean notNull(Object obj, String message, CommandSender sender) {
        if(obj == null) {
            sender.sendMessage(LangUtil.toColor(message));
        }

        return obj == null;
    }
}
