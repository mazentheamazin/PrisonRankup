package io.mazenmc.prisonrankup.utils;

import org.bukkit.command.CommandSender;

public final class CommandUtil {

    /**
     * Will check length of arguments and if not correct inform the sender of that
     * @param length Length of arguments you wish to check
     * @param args Arguments provided by the sender
     * @param sender The sender of the command
     * @return If the length of arguments is correct
     */
    public static boolean checkArgs(int length, String[] args, CommandSender sender) {
        if(args.length < length) {
            sender.sendMessage(LangUtil.error("Invalid syntax, please refer to command help for more information"));
        }

        return args.length < length;
    }

    /**
     * Will check if an object is null, if null inform the sender with your message
     * @param obj Object you wish to check
     * @param message Message you wish to send to the sender
     * @param sender The sender of the command
     * @return If the object is null
     */
    public static boolean notNull(Object obj, String message, CommandSender sender) {
        if(obj == null) {
            sender.sendMessage(LangUtil.toColor(message));
        }

        return obj == null;
    }
}
