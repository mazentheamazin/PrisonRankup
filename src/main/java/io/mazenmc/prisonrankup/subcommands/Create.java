package io.mazenmc.prisonrankup.subcommands;

import io.mazenmc.prisonrankup.enums.Message;
import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.managers.RankManager;
import io.mazenmc.prisonrankup.objects.SubCommand;
import io.mazenmc.prisonrankup.utils.CommandUtil;
import io.mazenmc.prisonrankup.utils.LangUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Create extends SubCommand {

    private static Create instance = new Create();

    private Create() {
        super("create");
    }

    @Override
    public void onExecute(Player player, Command cmd, String label, String[] args) {
        onExecute((CommandSender) player, cmd, label, args);
    }

    @Override
    public void onExecute(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("prisonrankup.create"))) {
            sender.sendMessage(LangUtil.error("You do not have permission for this command!"));
        }

        if(CommandUtil.checkArgs(2, args, sender)) {
            return;
        }

        double price;
        String name;

        name = args[0];

        try{
            price = Double.parseDouble(args[1]);
        }catch(NumberFormatException ex) {
            sender.sendMessage(LangUtil.error("Invalid Syntax: Price provided is not a number!"));
            return;
        }

        List<String> configList = PrisonRankupConfig.CONFIG.getStringList("groups");

        configList.add(configList.size(), name + ':' + price);

        PrisonRankupConfig.CONFIG.set("groups", configList);
        PrisonRankupConfig.CONFIG.save();
        RankManager.getInstance().update();

        sender.sendMessage("" + Message.PREFIX + ChatColor.GOLD + "Successfully created rank " + name);
    }
}
