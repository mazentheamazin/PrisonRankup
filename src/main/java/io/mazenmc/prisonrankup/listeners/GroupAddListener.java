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
