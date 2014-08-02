package io.mazenmc.prisonrankup.commands;

import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.objects.Command;
import io.mazenmc.prisonrankup.objects.PRPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    public void onExecute(Player player, org.bukkit.command.Command cmd, String label, String[] args) {
        PRPlayer pr = DataManager.getInstance().getPlayer(player.getName());

        if(pr.canRankup()) {
            pr.rankup();

            //
        }else{
            player.sendMessage(ChatColor.DARK_RED + "You cannot rankup! You need " + (pr.getBalance() - pr.getNextRank().getPrice().getValue()));
        }
    }
}
