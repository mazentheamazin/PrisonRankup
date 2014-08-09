/**
 PrisonRankup, the most feature-packed rankup plugin out there.
 Copyright (C) 2014 Mazen K.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
