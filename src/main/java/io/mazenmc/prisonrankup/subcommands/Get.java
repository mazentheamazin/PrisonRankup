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

package io.mazenmc.prisonrankup.subcommands;

import io.mazenmc.prisonrankup.enums.Message;
import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.objects.PRPlayer;
import io.mazenmc.prisonrankup.objects.SubCommand;
import io.mazenmc.prisonrankup.utils.CommandUtil;
import io.mazenmc.prisonrankup.utils.LangUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Get extends SubCommand {

    private static Get instance = new Get();

    public Get() {
        super("rankup");
    }

    @Override
    public void onExecute(Player player, Command cmd, String label, String[] args) {
        onExecute((CommandSender) player, cmd, label, args);
    }

    @Override
    public void onExecute(CommandSender sender, Command cmd, String label, String[] args) {
        // Check if the sender has the permission to run this command
        if(!(sender.hasPermission("prisonrankup.get"))) {
            sender.sendMessage(LangUtil.error("You do not have permission for this command!"));
            return;
        }

        // Check if the sender has provided sufficient arguments
        if(CommandUtil.checkArgs(1, args, sender)) {
            return;
        }

        // Get the player from stored data, if null inform the sender and exit the method
        PRPlayer player = DataManager.getInstance().getPlayer(args[0]);

        if(CommandUtil.notNull(player, LangUtil.error("ERROR: User " + args[0] + " has not joined the server nor manually entered in the data.yml"),
                sender)) {
            return;
        }

        // Provide the sender with found data

        sender.sendMessage(ChatColor.GREEN + "----------" + ChatColor.translateAlternateColorCodes('&', Message.PREFIX.toString().replaceAll(" ", "") +
                "---------"));

        sender.sendMessage(ChatColor.AQUA + player.getName() + "'s Profile:");
        sender.sendMessage(ChatColor.AQUA + "Balance: " + player.getBalance());

        sender.sendMessage(ChatColor.AQUA + "Current rank: " + player.getCurrentRank().getName());
        sender.sendMessage(ChatColor.AQUA + "Next rank: " + player.getNextRank().getName());

        sender.sendMessage(ChatColor.AQUA + "UUID: " + player.getID().toString());

        sender.sendMessage(ChatColor.GREEN + "----------" + ChatColor.translateAlternateColorCodes('&', Message.PREFIX.toString().replaceAll(" ", "") +
                "---------"));
    }
}
