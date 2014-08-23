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
import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.objects.PRPlayer;
import io.mazenmc.prisonrankup.objects.Rank;
import io.mazenmc.prisonrankup.utils.StringUtil;
import io.mazenmc.prisonrankup.utils.UUIDUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DataManager extends Manager{

    private static DataManager instance = new DataManager();

    private PrisonRankupConfig dataConfig = PrisonRankupConfig.DATA;
    private List<PRPlayer> players = new ArrayList<>();

    private DataManager() {
        new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTask(PrisonRankupPlugin.getInstance());
    }

    /**
     * Updates all information about players from data.yml. All changes will be lost.
     */
    public void update() {
        if(dataConfig.getConfigurationSection("users") == null) {
            dataConfig.createSection("users");
        }

        for(String s : dataConfig.getConfigurationSection("users").getKeys(false)) {
            String rank;

            // Update 'group' to 'rank'
            if((rank = dataConfig.getString(StringUtil.buildString("users.", s, ".group"))) != null) {
                dataConfig.set(StringUtil.buildString("users.", s, ".group"), null);
                dataConfig.set(StringUtil.buildString("users.", s, ".rank"), rank);
            }

            addPlayer(UUIDManager.getInstance().getName(UUIDUtil.stringToID(s)));
        }
    }

    /**
     * Add a player to the list
     * @param name Name of said player
     */
    public void addPlayer(String name) {
        players.add(new PRPlayer(name));
    }

    /**
     * Save all changes to the data.yml
     */
    public void save() {
        for(PRPlayer player : players) {
            // First make the variables for easy access
            Rank rank = player.getCurrentRank();
            String id = player.getStringID();
            ConfigurationSection section = player.getSection();

            /* Compare the variables to ones in config,
               if different; update them. */

            if(!(section.getString("rank").equals(rank.getName()))) {
                section.set("rank", rank.getName());
            }

            // v3.1 start

            // Save API data
            for(Map.Entry<String, Object> entry : player.getData().entrySet()) {
                section.set(entry.getKey(), entry.getValue());
            }

            // v3.1 end
        }
    }

    /**
     * Gets a player from the list by his name
     * @param name Name of said player
     * @return Found PRPlayer object
     */
    public PRPlayer getPlayer(String name) {
        for(PRPlayer player : players) {
            if(player.getName().equals(name)) {
                return player;
            }
        }

        return null;
    }

    /**
     * Returns if the list contains a UUID
     * @param id ID you wish to check
     * @return if the list contains a UUID
     */
    public boolean contains(UUID id) {
        for(PRPlayer player : players) {
            if(player.getID().equals(id))
                return true;
        }

        return false;
    }

    /**
     * Returns all the players on the list
     * @return All players on the list
     */
    public List<PRPlayer> getPlayers() {
        return players;
    }

    public void updatePlayer(PRPlayer ne) {
        remove(ne.getName());

        players.add(ne);

        TimeManager.getInstance().update(ne);
    }

    public void remove(String name) {
        for(PRPlayer p : new ArrayList<>(players)) {
            if(p.getName().equals(name))
                players.remove(p);
        }
    }

    @Override
    public void cleanup() {
        save();
    }

    /**
     * Returns an instance of said object
     * @return Instance of object
     */
    public static DataManager getInstance() {
        return instance;
    }
}
