package io.mazenmc.prisonrankup.events;

import io.mazenmc.prisonrankup.objects.PRPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerRankupEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private PRPlayer player;

    public PlayerRankupEvent(PRPlayer player) {
        this.player = player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public PRPlayer getPlayer() {
        return player;
    }
}
