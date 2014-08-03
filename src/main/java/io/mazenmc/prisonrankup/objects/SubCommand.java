package io.mazenmc.prisonrankup.objects;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    private String cmdName;

    protected SubCommand(String cmdName) {
        this.cmdName = cmdName;
    }

    public String getCommandName() {
        return cmdName;
    }

    public abstract void onExecute(CommandSender sender, Command cmd, String label, String[] args);

    public abstract void onExecute(Player player, Command cmd, String label, String[] args);

}
