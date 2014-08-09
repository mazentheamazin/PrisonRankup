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

package io.mazenmc.prisonrankup.commands;

import io.mazenmc.prisonrankup.enums.Message;
import io.mazenmc.prisonrankup.managers.RankManager;
import io.mazenmc.prisonrankup.objects.Command;
import io.mazenmc.prisonrankup.objects.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ranks extends Command {

    private static Ranks instance = new Ranks("ranks");

    private Ranks(String name) {
        super(name);
    }

    @Override
    public void onExecute(Player player, org.bukkit.command.Command cmd, String label, String[] args) {
        //Execute as a command sender, as no player specific methods need to be ran
        onExecute((CommandSender) player, cmd, label, args);
    }

    @Override
    public void onExecute(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        sender.sendMessage(ChatColor.GOLD + "All ranks:");

        String ranksFormat = Message.RANKS_FORMAT.toString();

        for(Rank rank : RankManager.getInstance().getRanks()) {
            sender.sendMessage(ranksFormat.replaceAll("%rank%", rank.getName()).replaceAll("%price%", rank.getPrice().toString()));
        }
    }

    public static Ranks getInstance() {
        return instance;
    }
}
