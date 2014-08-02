package io.mazenmc.prisonrankup.subcommands;

import io.mazenmc.prisonrankup.enums.Message;
import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.managers.RankManager;
import io.mazenmc.prisonrankup.objects.SubCommand;
import io.mazenmc.prisonrankup.utils.LangUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends SubCommand {

    private static Reload instance = new Reload();

    public Reload() {
        super("reload");
    }

    @Override
    public void onExecute(Player player, Command cmd, String label, String[] args) {
        onExecute((CommandSender) player, cmd, label, args);
    }

    @Override
    public void onExecute(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender.hasPermission("prisonrankup.reload"))) {
            sender.sendMessage(LangUtil.error("You do not have permission to use this command!"));
            return;
        }

        PrisonRankupConfig.CONFIG.reload();
        RankManager.getInstance().update();
        Message.refresh();

        sender.sendMessage(Message.PREFIX + "Successfully reloaded plugin!");
    }
}
