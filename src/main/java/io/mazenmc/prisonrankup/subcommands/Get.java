package io.mazenmc.prisonrankup.subcommands;

import io.mazenmc.prisonrankup.enums.Message;
import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.objects.PRPlayer;
import io.mazenmc.prisonrankup.objects.SubCommand;
import io.mazenmc.prisonrankup.utils.CommandUtil;
import io.mazenmc.prisonrankup.utils.LangUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Get extends SubCommand {

    private static Get instance = new Get();

    public Get() {
        super("get");
    }

    @Override
    public void onExecute(Player player, Command cmd, String label, String[] args) {
        onExecute((CommandSender) player, cmd, label, args);
    }

    @Override
    public void onExecute(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("prisonrankup.get"))) {
            sender.sendMessage(LangUtil.error("You do not have permission for this command!"));
            return;
        }

        if(CommandUtil.checkArgs(1, args, sender)) {
            return;
        }

        PRPlayer player = DataManager.getInstance().getPlayer(args[0]);

        if(CommandUtil.notNull(player, LangUtil.error("ERROR: User " + args[0] + " has not joined the server nor manually entered in the data.yml"),
                sender)) {
            return;
        }

        sender.sendMessage(ChatColor.GREEN + "----------" + ChatColor.translateAlternateColorCodes('&', Message.PREFIX.toString().replaceAll(" ", "") +
                "---------"));

        sender.sendMessage(ChatColor.AQUA + player.getName() + "'s Profile:");
        sender.sendMessage(ChatColor.AQUA + "Balance: " + player.getBalance());
        sender.sendMessage(ChatColor.AQUA + "Current rank: " + player.getCurrentRank().getName());
        sender.sendMessage(ChatColor.AQUA + "Next rank: " + player.getNextRank().getName());
        sender.sendMessage(ChatColor.AQUA + "UUID: " + player.getID().toString());

        sender.sendMessage(ChatColor.GREEN + "----------" + ChatColor.translateAlternateColorCodes('&', Message.PREFIX.toString().replaceAll(" ", "") +
                "---------"));
    }
}
