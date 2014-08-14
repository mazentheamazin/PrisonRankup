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

package io.mazenmc.prisonrankup.managers;

import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.objects.Rank;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankManager extends Manager{

    private static RankManager instance = new RankManager();

    private List<Rank> ranks = new ArrayList<>();

    private RankManager() {
        update();
    }

    /**
     * Updates data from config
     */
    public void update() {
        ranks.clear();

        for(String s : PrisonRankupConfig.CONFIG.getStringList("groups")) {
            ranks.add(new Rank(s.split(":")[0]));
        }
    }

    /**
     * Returns all ranks defined in config
     * @return All rnaks defined in config
     */
    public List<Rank> getRanks() {
        return Collections.unmodifiableList(ranks);
    }

    /**
     * Gets the rank from name
     * @param name Name you wish to get said rank from
     * @return Rank found
     */
    public Rank getRank(String name) {
        for(Rank rank : ranks) {
            if(rank.getName().equals(name))
                return rank;
        }

        return null;
    }

    /**
     * Gets the rank from index on list
     * @param index Index you wish to get said rank from
     * @return Rank found
     */
    public Rank getRank(int index) {
        return ranks.get(index);
    }

    /**
     * Gets the index of rank from the list
     * @param rank Rank you wish to get the index of
     * @return Index of rank
     */
    public int indexOf(Rank rank) {
        return ranks.indexOf(rank);
    }

    public boolean isRank(String rank) {
        for(Rank rnk : ranks) {
            if(rnk.getName().equals(rank))
                return true;
        }

        return false;
    }

    @Override
    public void cleanup() {
        instance = null;
    }

    /**
     * Returns an instance of said object
     * @return Instance of object
     */
    public static RankManager getInstance() {
        return instance;
    }
}
