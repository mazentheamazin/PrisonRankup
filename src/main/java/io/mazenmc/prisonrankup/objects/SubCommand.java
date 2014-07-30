package io.mazenmc.prisonrankup.objects;

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand implements CommandExecutor {

    protected String command;

    public SubCommand(String command) {
        this.command = command;

        PrisonRankupPlugin.getInstance();
    }

    public SubCommand() {
        this("rankup");
    }

    public abstract void onExecute(CommandSender sender, Command cmd, String label, String[] args);

    public abstract void onExecute(Player player, Command cmd, String label, String[] args);

    public String getCommand() {
        return command;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] strings) {
        return false;
    }
}
