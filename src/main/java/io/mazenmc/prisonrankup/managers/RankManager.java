package io.mazenmc.prisonrankup.managers;

import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.objects.Rank;

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
            ranks.add(new Rank(s.split(" ")[0]));
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
