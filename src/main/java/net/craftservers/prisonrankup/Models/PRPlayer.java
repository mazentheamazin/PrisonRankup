package net.craftservers.prisonrankup.Models;

import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.managers.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Deprecated
public class PRPlayer {

    private io.mazenmc.prisonrankup.objects.PRPlayer player;

    @Deprecated
    public PRPlayer(String name) {
        player = DataManager.getInstance().getPlayer(name);
    }

    @Deprecated
    public boolean exists() {
        return player.exists();
    }

    @Deprecated
    public void create() {
        // Method is useless due to that it is non-existant
    }

    @Deprecated
    public Rank getNextRank() {
        return new Rank(player.getNextRank().getName());
    }

    @Deprecated
    public String getStringUUID() {
        return player.getStringID();
    }

    @Deprecated
    public Rank getCurrentRank() {
        return new Rank(player.getCurrentRank().getName());
    }

    @Deprecated
    public Player getPlayer() {
        return Bukkit.getPlayer(player.getName());
    }

    @Deprecated
    public PRPlayer rankup() {
        player.rankup();

        return this;
    }

    @Deprecated
    public boolean canRankup() {
        return player.canRankup();
    }

    @Deprecated
    public PRPlayer setRank(Rank rank) {
        player.setRank(RankManager.getInstance().getRank(rank.getName()));

        return this;
    }

}
