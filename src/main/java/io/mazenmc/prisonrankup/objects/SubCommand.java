package io.mazenmc.prisonrankup.objects;

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class SubCommand implements CommandExecutor {

    private static Map<String, String> commandHelp = new HashMap<>();

    protected String command;

    public SubCommand(String command) {
        this.command = command;

        PrisonRankupPlugin.getInstance().getCommand(command).setExecutor(this);
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
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(cmd.getPermission() != null && !(cs.hasPermission(cmd.getPermission()))) {
            cs.sendMessage(ChatColor.DARK_RED + "You do not have permission to run this command!");
            return true;
        }

        String[] newArgs = new String[args.length - 1];

        if(args.length < 1) {
            if(commandHelp.containsKey(cmd.getName())) {
                cs.sendMessage(commandHelp.get(cmd.getName()));
            }

            return true;
        }else{
            System.arraycopy(args, 1, newArgs, 0, (args.length - 1));
        }

        if(cs instanceof Player) {
            onExecute((Player) cs, cmd, label, newArgs);
        }else{
            onExecute(cs, cmd, label, newArgs);
        }

        return false;
    }

    public static void addHelp(String command, String... help) {
        commandHelp.put(command, StringUtils.join(help, "\n"));
    }
}
