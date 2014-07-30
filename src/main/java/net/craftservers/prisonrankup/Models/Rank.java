package net.craftservers.prisonrankup.Models;

import io.mazenmc.prisonrankup.managers.RankManager;
import io.mazenmc.prisonrankup.objects.PRPlayer;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class Rank {

    private io.mazenmc.prisonrankup.objects.Rank rank;

    @Deprecated
    public Rank(String name) {
        rank = RankManager.getInstance().getRank(name);
    }

    @Deprecated
    public String getName() {
        return rank.getName();
    }

    @Deprecated
    public double getPrice() {
        return rank.getPrice().getValue();
    }

    @Deprecated
    public String getPriceString() {
        return rank.getPrice().toString();
    }

    @Deprecated
    public List<String> getPlayersInRank() {
        List<PRPlayer> players = rank.getPlayers();
        List<String> names = new ArrayList<>();

        for(PRPlayer player : players) {
            names.add(player.getName());
        }

        return names;
    }
}
