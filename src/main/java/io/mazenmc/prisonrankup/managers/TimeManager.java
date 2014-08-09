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

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import io.mazenmc.prisonrankup.objects.PRPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TimeManager extends Manager {

    private static TimeManager instance = new TimeManager();

    private List<PRPlayer> onTime = new ArrayList<>();

    private TimeManager() {}

    public void addPlayer(PRPlayer player, long time) {

        onTime.add(player);
        final String name = player.getName();

        new BukkitRunnable() {
            @Override
            public void run() {
                for(PRPlayer prPlayer : new ArrayList<>(onTime)) {
                    if(prPlayer.getName().equals(name)) {
                        onTime.remove(prPlayer);
                        return;
                    }
                }
            }
        }.runTaskLater(PrisonRankupPlugin.getInstance(), time);
    }

    public boolean isOnTimer(PRPlayer player) {
        return onTime.contains(player);
    }

    void update(PRPlayer ne) {
        boolean removed = false;

        for(PRPlayer prPlayer : new ArrayList<>(onTime)) {
            if(prPlayer.getName().equals(ne.getName())) {
                onTime.remove(prPlayer);
                removed = true;
            }
        }

        if(removed) {
            onTime.add(ne);
        }
    }

    @Override
    public void cleanup() {
        instance = null;
    }

    public static TimeManager getInstance() {
        return instance;
    }
}
