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

import io.mazenmc.prisonrankup.managers.DataManager;
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

public class Set extends SubCommand {

    private static Set instance = new Set();

    public Set() {
        super("rankup");
    }

    @Override
    public void onExecute(Player player, Command cmd, String label, String[] args) {
        onExecute((CommandSender) player, cmd, label, args);
    }

    @Override
    public void onExecute(CommandSender sender, Command cmd, String label, String[] args) {
        // Check if the player has the permission to use this command
        if(!(sender.hasPermission("prisonrankup.set"))) {
            sender.sendMessage(LangUtil.error("You do not have permission for this command!"));
            return;
        }

        // Check if the sender has provided correct number of arguments
        if(CommandUtil.checkArgs(2, args, sender)) {
            return;
        }

        // Get the PRPlayer instance, if not found inform sender of that
        PRPlayer player = DataManager.getInstance().getPlayer(args[0]);

        if(CommandUtil.notNull(player, LangUtil.error("ERROR: User " + args[0] + " has not joined the server nor manually entered in the data.yml"),
                sender)) {
            return;
        }

        // Get the Rank instance, if not found inform the sender of that
        Rank rank = RankManager.getInstance().getRank(args[1]);

        if(CommandUtil.notNull(player, LangUtil.error("ERROR: Rank " + args[1] + " does not exist!"), sender)) {
            return;
        }

        // Set the players rank
        player.setRank(rank);

        sender.sendMessage(ChatColor.GOLD + "Successfully set players rank");
    }
}
