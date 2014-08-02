package io.mazenmc.prisonrankup.objects;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Command {

    private String name;

    protected Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void onExecute(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args);

    public abstract void onExecute(Player player, org.bukkit.command.Command cmd, String label, String[] args);

}
