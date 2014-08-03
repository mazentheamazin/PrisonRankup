package io.mazenmc.prisonrankup.listeners;

import io.mazenmc.prisonrankup.enums.Message;
import io.mazenmc.prisonrankup.utils.LangUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        Action action = event.getAction();
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();

        if(action == Action.RIGHT_CLICK_BLOCK) {
            if((block.getType() == Material.WALL_SIGN) || (block.getType() == Material.SIGN_POST)) {
                Sign sign = (Sign) block.getState();

                if(sign.getLine(0).equalsIgnoreCase(LangUtil.toColor("&bPrison-Rankup"))){
                    player.performCommand("rankup");
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){

        Player player = event.getPlayer();
        Block block = event.getBlock();

        if((block.getType() == Material.WALL_SIGN) || (block.getType() == Material.SIGN_POST)) {
            Sign sign = (Sign) block.getState();

            if(sign.getLine(0).equalsIgnoreCase(LangUtil.toColor("&bPrison-Rankup"))) {
                if(!player.hasPermission("prisonrankup.sign")) {
                    player.sendMessage(Message.PREFIX + "" + ChatColor.RED + "" + ChatColor.BOLD + "You do not have permission to break this sign!");
                    event.setCancelled(true);
                }else{
                    player.sendMessage(Message.PREFIX + "" + ChatColor.GREEN + "Sign has been removed.");
                }
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event){

        Player player = event.getPlayer();

        if(event.getLine(0).equalsIgnoreCase("[PrisonRankup]")) {
            if(player.hasPermission("prisonrankup.sign")) {
                if(event.getLine(1).isEmpty() && event.getLine(2).isEmpty() && event.getLine(3).isEmpty()) {
                    event.setLine(0, ChatColor.translateAlternateColorCodes('&', "&bPrison-Rankup"));
                    event.setLine(1, ChatColor.GREEN + "Click Me to...");
                    event.setLine(2, ChatColor.GREEN + "Rankup!");

                    player.sendMessage(Message.PREFIX + "" + ChatColor.GREEN + "Sign has been created.");
                }else{
                    player.sendMessage(Message.PREFIX + "" + ChatColor.RED + "Please make sure that all lines below the first one are empty to create a sign!");
                }
            }else{
                player.sendMessage(Message.NO_PERMISSION + "");
            }
        }
    }

}
