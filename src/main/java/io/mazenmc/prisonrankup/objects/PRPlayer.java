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

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import io.mazenmc.prisonrankup.enums.Untitled;
import io.mazenmc.prisonrankup.managers.*;
import io.mazenmc.prisonrankup.utils.UUIDUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.mazenmc.prisonrankup.enums.PrisonRankupConfig.CONFIG;
import static io.mazenmc.prisonrankup.enums.PrisonRankupConfig.DATA;
import static io.mazenmc.prisonrankup.managers.RankManager.getInstance;

public class PRPlayer {

    /* PrisonRankup Variables */
    private Rank currentRank;
    private Rank nextRank;

    /* Player data */
    private ConfigurationSection profile;
    private String name;
    private String id;
    private OfflinePlayer offlinePlayer;

    /* API data */
    private Map<String, Object> apiData = new HashMap<>();

    /**
     * Constructor for a new PrisonRankup player
     * @param name Name of player
     */
    public PRPlayer(String name) {
        // First, we need to define the all objects
        this.name = name;
        this.id = UUIDUtil.idToString(UUIDManager.getInstance().getUUID(name));
        offlinePlayer = Bukkit.getPlayer(name);

        // If the player is not online get the offline player async
        if(offlinePlayer == null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    offlinePlayer = Bukkit.getOfflinePlayer(UUIDUtil.stringToID(id));
                }
            }.runTaskAsynchronously(PrisonRankupPlugin.getInstance());
        }

        if(!exists()) {
            // Create the section for the users profile
            DATA.createSection("users." + id);
            profile = DATA.getConfigurationSection("users." + id);

            // Set the data
            String rank = "";

            if(CONFIG.getBoolean("Transfer ranks to profile") && offlinePlayer != null) {
                for(String s : VaultManager.getInstance().getPermission().getPlayerGroups(null, offlinePlayer)) {
                    if(RankManager.getInstance().isRank(s))
                        rank = s;
                }
            }

            profile.set("rank", (rank.equals("")) ? getInstance().getRank(0).getName() : rank);
            profile.set("name", name);
        }else{
            profile = DATA.getConfigurationSection("users." + id);
        }

        setRank(getInstance().getRank(profile.getString("rank")));

        /* Time to load API data */
        for(String s : getSection().getKeys(false)) {
            // Making sure we don't load PrisonRankup's data
            if(s.equals("rank") || s.equals("name")) {
                continue;
            }

            // Add the data to the hashmap
            apiData.put(s, getSection().get(s));
        }
    }

    /**
     * Will return if exists in-config
     * @return If player exists in-config
     */
    public boolean exists() {
        return DATA.contains("users." + id);
    }

    /**
     * Gets the current rank of the player
     * @return Current rank
     */
    public Rank getCurrentRank() {
        return currentRank;
    }

    /**
     * Gets the next rank of the player
     * @return Next rank
     */
    public Rank getNextRank() {
        return nextRank;
    }

    /**
     * Gets the name of the player
     * @return Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the String representation of players UUID
     * @return Player's UUID
     */
    public String getStringID() {
        return id;
    }

    /**
     * Gets the player's UUID
     * @return Player's UUID
     */
    public UUID getID() {
        return UUIDUtil.stringToID(id);
    }

    /*                                       v3.1 Start                                                               */

    /**
     * Gets value of API data from key
     * @param key key used for said API data
     * @return Found value
     */
    public Object get(String key) {
        return apiData.get(key);
    }

    /**
     * Gets value of API data from key
     * @param key key used for said API data
     * @return Found value
     */
    public String getString(String key) {
        return (String) get(key);
    }

    /**
     * Gets value of API data from key
     * @param key key used for said API data
     * @return Found value
     */
    public int getInt(String key) {
        return (Integer) get(key);
    }

    /**
     * Gets value of API data from key
     * @param key key used for said API data
     * @return Found value
     */
    public double getDouble(String key) {
        return (Double) get(key);
    }

    /**
     * Gets value of API data from key
     * @param key key used for said API data
     * @return Found value
     */
    public byte getByte(String key) {
        return (Byte) get(key);
    }

    /*                                           v3.1 End                                                             */

    /**
     * Gets the Offline player object of said player
     * @return Player's UUID
     */
    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    /**
     * Makes the player rankup
     */
    public void rankup() {
        VaultManager vaultManager = VaultManager.getInstance();

        vaultManager.getEconomy().withdrawPlayer(offlinePlayer, nextRank.getPrice().getValue());

        vaultManager.getPermission().playerRemoveGroup(null, offlinePlayer, currentRank.getName());
        vaultManager.getPermission().playerAddGroup(null, offlinePlayer, nextRank.getName());

        setRank(nextRank);
    }

    /**
     * Returns if this player can rankup
     * @return If said player can rankup
     */
    public boolean canRankup() {
        return (VaultManager.getInstance().getEconomy().getBalance(offlinePlayer) >= nextRank.getPrice().getValue()) && (currentRank != nextRank) &&
                !(TimeManager.getInstance().isOnTimer(this));
    }

    /**
     * Gets why a player can't rankup
     * @return Why a player can't rankup
     */
    public Untitled getReason() {
         if(TimeManager.getInstance().isOnTimer(this)) {
             return Untitled.TIMENEEDWAIT;
         }else if(VaultManager.getInstance().getEconomy().getBalance(offlinePlayer) < nextRank.getPrice().getValue()) {
             return Untitled.NOHASMONEY;
         }else if(currentRank == nextRank) {
             return Untitled.HASMUCHRANK;
         }else{
             return Untitled.KTHXBAI;
         }
    }

    /**
     * Gets the section in config that belongs to said player
     * @return Said section
     */
    public ConfigurationSection getSection() {
        return profile;
    }

    /**
     * Sets the players rank
     * @param rank The rank you wish to set the player to
     */
    public void setRank(Rank rank) {
        // Get the current rank and index of said rank
        currentRank = rank;
        int crIndex = getInstance().indexOf(currentRank);

        // This will set his next rank to his current one if he has the last rank otherwise work as usual.
        if(crIndex == (getInstance().getRanks().size()-1)) {
            nextRank = currentRank;
        }else{
            nextRank = getInstance().getRank(crIndex + 1);
        }

        DataManager.getInstance().updatePlayer(this);
    }

    /*                                       v3.1 Start                                                               */

    /**
     * Add API data
     * @param key Key you wish to use for said API data
     * @param value Value for said API data
     */
    public void put(String key, Object value) {
        // Make sure somebody doesn't add data with a key that already exists
        if(apiData.containsKey(key))
            throw new IllegalArgumentException(key + " is an already used API key");

        apiData.put(key, value);
    }

    /**
     * Remove API data
     * @param key Key you wish to use to remove said API data
     */
    public void remove(String key) {
        // Make sure somebody doesn't remove data which doesn't exist
        if(!(apiData.containsKey(key)))
            throw new IllegalArgumentException("API data with the key " + key + " doesn't exist!");

        // Replace the data to null, bukkit will remove the data by itself
        replace(key, null);
    }

    /**
     * Replace API data
     * @param key Key you wish to use for said API data
     * @param newValue New value to set the data to
     */
    public void replace(String key, Object newValue) {
        // Make sure the data exists
        if(!(apiData.containsKey(key)))
            throw new IllegalArgumentException("API data with key " + key + " doesn't exist!");

        // Replace the data
        apiData.remove(key);
        apiData.put(key, newValue);
    }

    /**
     * Gets API data
     * @return API data
     */
    public Map<String, Object> getData() {
        return Collections.unmodifiableMap(apiData);
    }

    /*                                           v3.1 End                                                             */

    /**
     * Gets the balance of the player from Vault
     * @return Player's balance
     */
    public double getBalance() {
        return VaultManager.getInstance().getEconomy().getBalance(offlinePlayer);
    }

}
