package io.mazenmc.prisonrankup.listeners;

import io.mazenmc.prisonrankup.events.GroupAddEvent;
import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.managers.RankManager;
import io.mazenmc.prisonrankup.objects.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GroupAddListener implements Listener {

    @EventHandler
    public void onGroupAdd(GroupAddEvent event) {
        Rank rank = null;

        for(Rank rnk : RankManager.getInstance().getRanks()) {
            if(rnk.getName().equalsIgnoreCase(event.getNewGroup()))
                rank = rnk;
        }

        if(rank == null) {
            return;
        }

        DataManager.getInstance().getPlayer(event.getTarget().getName()).setRank(rank);
    }
}
