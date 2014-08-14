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

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.events.PlayerRankupEvent;
import io.mazenmc.prisonrankup.managers.TimeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class RankupListener implements Listener{

    @EventHandler
    public void onRankup(PlayerRankupEvent event) {
        if(PrisonRankupConfig.CONFIG.getBoolean("Interval on all ranks")) {

            long time = 0L;
            int i = PrisonRankupConfig.CONFIG.getInt("Time Interval");

            switch(PrisonRankupConfig.CONFIG.getString("Time Type").toLowerCase().charAt(0)) {
                case 's':
                    time += i;
                    break;

                case 'm':
                    time += i * 60;
                    break;

                case 'h':
                    time += i * 360;
                    break;

                default:
                    PrisonRankupPlugin.log("Time type has been defined incorrectly!");
                    return;
            }

            TimeManager.getInstance().addPlayer(event.getPlayer(), time);
        }
    }
}
