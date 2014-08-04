package io.mazenmc.prisonrankup.objects;

import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.exceptions.RankNotFoundException;
import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.managers.RankManager;

import java.util.ArrayList;
import java.util.List;

public class Rank {

    private String name;
    private Price price;

    /**
     * This constuctor shouldn't be used due to RankManager#getRank(String)
     * @param name
     */
    public Rank(String name) {
        this.name = name;

        for(String s : PrisonRankupConfig.CONFIG.getStringList("groups")) {
            if(s.startsWith(name) && s.contains(":") && s.split(":").length >= 2)
                this.price = new Price(s.split(":")[1]);
        }

        if(price == null) {
            throw new RankNotFoundException(this);
        }
    }

    /**
     * Get the name of the rank
     * @return Rank's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the rank
     * @return Rank's price
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Gets all players that are in this group
     * @return Rank's players
     */
    public List<PRPlayer> getPlayers() {
        List<PRPlayer> players = new ArrayList<>();

        for(PRPlayer player : DataManager.getInstance().getPlayers()) {
            if(player.getCurrentRank().equals(this))
                players.add(player);
        }

        return players;
    }
}
