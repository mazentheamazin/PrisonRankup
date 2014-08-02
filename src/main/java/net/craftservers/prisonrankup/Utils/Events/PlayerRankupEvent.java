package net.craftservers.prisonrankup.Utils.Events;

import net.craftservers.prisonrankup.Models.PRPlayer;
import net.craftservers.prisonrankup.Models.Rank;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Deprecated
public class PlayerRankupEvent extends Event{

    private static final HandlerList handlers = new HandlerList();
    private PRPlayer player;
    private Rank oldRank;

    @Deprecated
    public PlayerRankupEvent(PRPlayer player, Rank oldRank) {
        this.player = player;
        this.oldRank = oldRank;
    }

    @Deprecated
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Deprecated
    public PRPlayer getPlayer() {return player;}

    @Deprecated
    public Rank getOldRank() {return oldRank;}

    @Deprecated
    public Rank getNewRank() {return getPlayer().getCurrentRank();}
}
