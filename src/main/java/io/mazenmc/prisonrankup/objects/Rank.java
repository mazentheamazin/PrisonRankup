package io.mazenmc.prisonrankup.objects;

import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.exceptions.RankNotFoundException;
import io.mazenmc.prisonrankup.managers.RankManager;

public class Rank {

    private String name;
    private Price price;

    public Rank(String name) {
        Rank tmpRank;

        if((tmpRank = RankManager.getInstance().getRank(name)) != null) {
            this.name = tmpRank.getName();
            this.price = tmpRank.getPrice();

            return;
        }

        this.name = name;

        for(String s : PrisonRankupConfig.CONFIG.getStringList("groups")) {
            if(s.startsWith(name) && s.contains(":") && s.split(":").length == 1)
                this.price = new Price(s.split(":")[1]);
        }

        if(price == null) {
            throw new RankNotFoundException(this);
        }
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
