package io.mazenmc.prisonrankup.listeners;

import io.mazenmc.prisonrankup.events.GroupAddEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class GAEListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0].replaceAll("/", "");
        String[] args = new String[event.getMessage().split(" ").length - 1];
        Player p = event.getPlayer();
        boolean argsExists = (args.length == 0);

        System.arraycopy(event.getMessage().split(" "), 1, args, 0, args.length);

        switch(command.toLowerCase()) {
            case "changeplayer":
                if(!argsExists) {
                    switch(args[0].toLowerCase()) {
                        case "addsub":
                            if(checkArgs(3, args)) {
                                if(p.hasPermission("droxperms.players")) {
                                    callEvent(p, args[1], args[2]);
                                }
                            }
                            break;
                        case "setgroup":
                            if(checkArgs(3, args)) {
                                if(p.hasPermission("droxperms.players")) {
                                    callEvent(p, args[1], args[2]);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                break;
            case "manuadd":
                if(!argsExists) {
                    if(checkArgs(2, args)) {
                        if(p.hasPermission("groupmanager.manuadd")) {
                            callEvent(p, args[0], args[1]);
                        }
                    }
                }
                break;
            case "manuaddsub":
                if(!argsExists) {
                    if(checkArgs(2, args)) {
                        if(p.hasPermission("groupmanager.manuaddsub")) {
                            callEvent(p, args[0], args[1]);
                        }
                    }
                }
                break;
            case "pex":
                if(!argsExists) {
                    switch(args[0].toLowerCase()) {
                        case "user":
                            if(checkArgs(5, args)) {
                                if(args[2].equalsIgnoreCase("group")) {
                                    if(args[3].equalsIgnoreCase("add") && p.hasPermission("permissions.manage.membership." + args[4])){
                                        callEvent(p, args[1], args[4]);
                                    }else if(args[3].equalsIgnoreCase("set") && p.hasPermission("permissions.manage.membership." + args[4])) {
                                        callEvent(p, args[1], args[4]);
                                    }
                                }
                            }
                    }
                }
                break;
            case "playeraddgroup":
                if(!argsExists) {
                    if(checkArgs(2, args)) {
                        if(p.hasPermission("overpermissions.playeraddgroup")) {
                            callEvent(p, args[0], args[1]);
                        }
                    }
                }
                break;
            case "permissions":
                if(!argsExists) {
                    if(checkArgs(4, args)) {
                        if(args[0].equalsIgnoreCase("player")) {
                            if(args[1].equalsIgnoreCase("setgroup")) {
                                if(p.hasPermission("permissions.player.setgroup") || p.hasPermission("zpermissions.player.*") || p.hasPermission("zpermissions.player.setgroup")) {
                                    callEvent(p, args[2], args[3]);
                                }
                            }else if(args[1].equalsIgnoreCase("addgroup")) {
                                if(p.hasPermission("permissions.player.setgroup") || p.hasPermission("zpermissions.player.*") || p.hasPermission("zpermissions.player.addgroup")) {
                                    callEvent(p, args[2], args[3]);
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    private boolean checkArgs(int i, String[] array) {
        return array.length >= i;
    }

    public void callEvent(Player p, String target, String newGroup) {
        Bukkit.getPluginManager().callEvent(new GroupAddEvent(p, target, newGroup));
    }
}
