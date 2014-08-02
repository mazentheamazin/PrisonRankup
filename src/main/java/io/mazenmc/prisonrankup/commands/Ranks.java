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
