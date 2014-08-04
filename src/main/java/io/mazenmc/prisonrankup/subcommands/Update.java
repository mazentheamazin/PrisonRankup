package io.mazenmc.prisonrankup.subcommands;

import io.mazenmc.prisonrankup.managers.UpdaterManager;
import io.mazenmc.prisonrankup.objects.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Update extends SubCommand {

    private static Update instance = new Update();

    private Update() {
        super("rankup");
    }

    @Override
    public void onExecute(Player player, Command cmd, String label, String[] args) {
        onExecute((CommandSender) player, cmd, label, args);
    }

    @Override
    public void onExecute(CommandSender sender, Command cmd, String label, String[] args) {
        if(UpdaterManager.getInstance().isUpdateAvailable()) {
            sender.sendMessage(ChatColor.DARK_RED + "There is no update available!");
        }

        UpdaterManager.getInstance().update();

        sender.sendMessage(ChatColor.GOLD + "Updated PrisonRankup to the latest version");
        sender.sendMessage(ChatColor.GOLD + "Please restart for all changes to take effect");
    }

    public static Update getInstance() {
        return instance;
    }
}
