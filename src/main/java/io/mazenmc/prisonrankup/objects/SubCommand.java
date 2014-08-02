package io.mazenmc.prisonrankup.objects;

import io.mazenmc.prisonrankup.managers.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    public SubCommand(String cmdName) {
        CommandManager.getInstance().registerSubCommand(cmdName, this);
    }

    public abstract void onExecute(CommandSender sender, Command cmd, String label, String[] args);

    public abstract void onExecute(Player player, Command cmd, String label, String[] args);

}
