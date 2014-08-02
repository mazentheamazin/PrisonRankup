package io.mazenmc.prisonrankup.events;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import static io.mazenmc.prisonrankup.managers.VaultManager.getInstance;

public class GroupAddEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private CommandSender sender;
    private OfflinePlayer target;
    private String newGroup;
    private String oldGroup;

    public GroupAddEvent(Player sender, OfflinePlayer target, String newGroup) {
        this.sender = sender;
        this.target = target;
        this.newGroup = newGroup;
        oldGroup = getInstance().getPermission().getPrimaryGroup(Bukkit.getPlayer(this.target.getUniqueId()));
    }

    public GroupAddEvent(Player sender, String target, String newGroup) {
        this.sender = sender;
        this.target = Bukkit.getOfflinePlayer(target);
        this.newGroup = newGroup;
        oldGroup = getInstance().getPermission().getPrimaryGroup(Bukkit.getPlayer(this.target.getUniqueId()));
    }

    public GroupAddEvent(CommandSender sender, String target, String newGroup) {
        this.sender = sender;
        this.target = Bukkit.getOfflinePlayer(target);
        this.newGroup = newGroup;
    }

    public GroupAddEvent(CommandSender sender, OfflinePlayer target, String newGroup) {
        this.sender = sender;
        this.target = target;
        this.newGroup = newGroup;
        oldGroup = getInstance().getPermission().getPrimaryGroup(Bukkit.getPlayer(this.target.getUniqueId()));
    }

    public CommandSender getSender() {
        return sender;
    }

    public OfflinePlayer getTarget() {
        return target;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getNewGroup() {
        return newGroup;
    }

    public String getOldGroup() {
        return oldGroup;
    }

}
