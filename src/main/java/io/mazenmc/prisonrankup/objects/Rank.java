/**
 PrisonRankup, the most feature-packed rankup plugin out there.
 Copyright (C) 2014 Mazen K.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.mazenmc.prisonrankup.objects;

import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.exceptions.RankNotFoundException;
import io.mazenmc.prisonrankup.managers.DataManager;

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
