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

    public void update() {
        ranks.clear();

        for(String s : PrisonRankupConfig.CONFIG.getStringList("groups")) {
            ranks.add(new Rank(s.split(" ")[0]));
        }
    }

    public List<Rank> getRanks() {
        return Collections.unmodifiableList(ranks);
    }

    public Rank getRank(String name) {
        for(Rank rank : ranks) {
            if(rank.getName().equals(name))
                return rank;
        }

        return null;
    }

    public Rank getRank(int index) {
        return ranks.get(index);
    }

    public int indexOf(Rank rank) {
        return ranks.indexOf(rank);
    }

    @Override
    public void cleanup() {
        instance = null;
    }

    public static RankManager getInstance() {
        return instance;
    }
}
