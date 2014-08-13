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

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import io.mazenmc.prisonrankup.enums.Message;
import io.mazenmc.prisonrankup.events.PlayerRankupEvent;
import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.objects.Command;
import io.mazenmc.prisonrankup.objects.PRPlayer;
import io.mazenmc.prisonrankup.utils.LangUtil;
import net.craftservers.prisonrankup.Models.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

import static io.mazenmc.prisonrankup.enums.PrisonRankupConfig.CONFIG;

public class Rankup extends Command {

    private static Rankup instance = new Rankup("rankup");

    private Rankup(String name) {
        super(name);
    }

    @Override
    public void onExecute(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        String[] strs = LangUtil.toColor("&3----------" + Message.PREFIX + "&r&3----------",
                "&6/rankup - &3Rankup to your wildest dreams!",
                "&6/rankup create [rank] [price] - &3Create ranks in-game!",
                "&6/rankup get [player] - &3Get player data!",
                "&6/rankup reload - &3Reload configuration files!",
                "&6/rankup set [player] [rank] - &3Set a player's rank!",
                "&6/rankup stats [rank] - &3Get a rank's statistics!",
                "&6/rankup update - &3Update plugin to the latest version!",
                "&3----------" + Message.PREFIX + "&r&3----------");

        sender.sendMessage(strs);
    }

    @Override
    public void onExecute(final Player player, org.bukkit.command.Command cmd, String label, String[] args) {
        if(args.length > 0) {
            onExecute((CommandSender) player, cmd, label, args);
            return;
        }

        PRPlayer pr = DataManager.getInstance().getPlayer(player.getName());

        if(pr.canRankup()) {

            pr.rankup();

            pr = DataManager.getInstance().getPlayer(player.getName());

            PlayerRankupEvent rankupEvent = new PlayerRankupEvent(pr);
            net.craftservers.prisonrankup.Utils.Events.PlayerRankupEvent oldRankupEvent =
                    new net.craftservers.prisonrankup.Utils.Events.PlayerRankupEvent(new net.craftservers.prisonrankup.Models.PRPlayer(pr.getName()), new Rank(pr.getCurrentRank().getName()));

            Bukkit.getPluginManager().callEvent(rankupEvent);
            Bukkit.getPluginManager().callEvent(oldRankupEvent);

            Bukkit.broadcastMessage(((Message.PREFIX.toString().equals("")) ? Message.RANKUP.toString() : Message.PREFIX.toString() + " " + Message.RANKUP)
                    .replaceAll("%player%", pr.getName()).replaceAll("%rank%", pr.getCurrentRank().getName()));
        }else{

            switch(pr.getReason()) {
                case NOHASMONEY:
                    //v3.1 start
                    DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(0);

                    player.sendMessage(Message.NOT_ENOUGH_MONEY.toString().replaceAll("%rank%", pr.getNextRank().getName())
                            .replaceAll("%price%", df.format(pr.getNextRank().getPrice().getValue() - pr.getBalance())));

                    //v3.1 end
                    break;

                case TIMENEEDWAIT:
                    String str;
                    double timeInterval = CONFIG.getDouble("Time Interval");

                    switch(CONFIG.getString("Time type").toLowerCase().charAt(0)) {
                        case 's':
                            str = timeInterval + " seconds!";
                            break;
                        case 'm':
                            str = (timeInterval / 60) + " minutes!";
                            break;
                        case 'h':
                            str = (timeInterval / 360) + " hours!";
                            break;
                        default:
                            player.sendMessage(ChatColor.DARK_RED + "Config has been defined incorrectly, please contact a server admin ASAP!");
                            return;
                    }

                    player.sendMessage(ChatColor.DARK_RED + "The required amount of time to rankup is " + str);

                    break;

                case HASMUCHRANK:
                    player.sendMessage(ChatColor.GOLD + "You're already the highest rank!");
                    break;

                case KTHXBAI:
                    player.sendMessage(ChatColor.GOLD + "What the fuck did you do to get this? You really fucked something up. I feel bad for you.. " +
                            "I am now going to tell you the description of computer science according to Wikipedia");

                    final String[] computerScience = ("Computer science is the scientific and practical approach to computation and its applications. It " +
                            "is the systematic study of the feasibility, structure, expression, and mechanization of the methodical procedures (or algorithms)" +
                            " that underlie the acquisition, representation, processing, storage, communication of, and access to information, whether such information" +
                            " is encoded as bits in a computer memory or transcribed in genes and protein structures in a biological cell. A computer scientist specializes" +
                            " in the theory of computation and the design of computational systems. Its subfields can be divided into a variety of theoretical and practical" +
                            " disciplines. Some fields, such as computational complexity theory (which explores the fundamental properties of Computational and intractable problems), " +
                            "are highly abstract, while fields such as computer graphics emphasize real-world visual applications. Still other fields focus on the challenges in implementing computation." +
                            " For example, programming language theory considers various approaches to the description of computation, whilst the study of computer programming itself investigates various aspects" +
                            " of the use of programming language and complex systems. Human-computer interaction considers the challenges in making computers and computations useful, usable, and universally accessible" +
                            " to humans.").split("\\.");


                    for(int i = 0; i < computerScience.length; i++) {
                        final String s = computerScience[i];

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.sendMessage(ChatColor.AQUA + s + ".");
                            }
                        }.runTaskLater(PrisonRankupPlugin.getInstance(), 200L * i);
                    }

                    break;
            }
        }
    }

    public static Rankup getInstance() {
        return instance;
    }

}