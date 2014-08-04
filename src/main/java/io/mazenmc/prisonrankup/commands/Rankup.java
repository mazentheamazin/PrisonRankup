package io.mazenmc.prisonrankup.commands;

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import io.mazenmc.prisonrankup.enums.Message;
import io.mazenmc.prisonrankup.events.PlayerRankupEvent;
import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.objects.Command;
import io.mazenmc.prisonrankup.objects.PRPlayer;
import net.craftservers.prisonrankup.Models.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static io.mazenmc.prisonrankup.enums.PrisonRankupConfig.CONFIG;

public class Rankup extends Command {

    private static Rankup instance = new Rankup("rankup");

    private Rankup(String name) {
        super(name);
    }

    @Override
    public void onExecute(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        sender.sendMessage(ChatColor.DARK_RED + "Only players can run this command!");
    }

    @Override
    public void onExecute(final Player player, org.bukkit.command.Command cmd, String label, String[] args) {
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
                    player.sendMessage(Message.NOT_ENOUGH_MONEY.toString().replaceAll("%rank%", pr.getNextRank().getName())
                            .replaceAll("%price%", pr.getNextRank().getPrice().toString()));
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