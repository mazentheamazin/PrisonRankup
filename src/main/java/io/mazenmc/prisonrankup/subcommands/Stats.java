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
import io.mazenmc.prisonrankup.managers.RankManager;
import io.mazenmc.prisonrankup.objects.PRPlayer;
import io.mazenmc.prisonrankup.objects.Rank;
import io.mazenmc.prisonrankup.objects.SubCommand;
import io.mazenmc.prisonrankup.utils.CommandUtil;
import io.mazenmc.prisonrankup.utils.LangUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Stats extends SubCommand {

    private static Stats instance = new Stats();

    private Stats() {
        super("rankup");
    }

    @Override
    public void onExecute(Player player, Command cmd, String label, String[] args) {
        onExecute((CommandSender) player, cmd, label, args);
    }

    @Override
    public void onExecute(CommandSender sender, Command cmd, String label, String[] args) {
        // Check if the player has the permission to run the command
        if(!(sender.hasPermission("prisonrankup.stats"))) {
            sender.sendMessage(LangUtil.error("You do not have permission for this command"));
            return;
        }

        // Check if the player has provided sufficient arguments
        if(CommandUtil.checkArgs(1, args, sender)) {
            return;
        }

        // Get the rank, if null inform the sender and exit the command
        Rank rank = RankManager.getInstance().getRank(args[0]);

        if(CommandUtil.notNull(rank, LangUtil.error("ERROR: Rank provided does not exist"), sender)) {
            return;
        }

        //Provide player with statistics found

        List<PRPlayer> players = rank.getPlayers();
        StringBuilder stringBuilder = new StringBuilder();

        sender.sendMessage(ChatColor.GREEN + "----------" + ChatColor.translateAlternateColorCodes('&', Message.PREFIX.toString().replaceAll(" ", "") + "---------"));
        sender.sendMessage(ChatColor.AQUA + rank.getName() + "'s Stats:");
        sender.sendMessage(ChatColor.AQUA + "Price: " + rank.getPrice().toString());
        sender.sendMessage(ChatColor.AQUA + "Player's in rank: ");

        stringBuilder.append(ChatColor.GOLD);

        for(int i = 0; i < players.size(); i++) {
            //TODO: Make clickable player profiles
            stringBuilder.append(players.get(i).getName());
            stringBuilder.append(", ");

            if((i % 4) == 0) {
                stringBuilder.append("\n");
                stringBuilder.append(ChatColor.GOLD);
            }
        }

        sender.sendMessage(stringBuilder.toString());

        sender.sendMessage(ChatColor.GREEN + "----------" + ChatColor.translateAlternateColorCodes('&', Message.PREFIX.toString().replaceAll(" ", "") + "---------"));
    }
}
