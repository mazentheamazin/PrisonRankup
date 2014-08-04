package io.mazenmc.prisonrankup.managers;

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.objects.PRPlayer;
import io.mazenmc.prisonrankup.objects.Rank;
import io.mazenmc.prisonrankup.utils.UUIDUtil;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataManager extends Manager{

    private static DataManager instance = new DataManager();

    private PrisonRankupConfig dataConfig = PrisonRankupConfig.DATA;
    private List<PRPlayer> players = new ArrayList<>();

    private DataManager() {
        update();
    }

    /**
     * Updates all information about players from data.yml. All changes will be lost.
     */
    public void update() {
        if(dataConfig.getConfigurationSection("users") == null) {
            dataConfig.createSection("users");
        }

        for(String s : dataConfig.getConfigurationSection("users").getKeys(false)) {
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
        }
    }

    /**
     * Gets a player from the list by his name
     * @param name Name of said player
     * @return Found PRPlayer object
     */
    public PRPlayer getPlayer(String name) {
        for(PRPlayer player : players) {
            if(player.getName().startsWith(name)) {
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
        for(PRPlayer player : new ArrayList<>(players)) {
            if(player.getName().equals(ne.getName()))
                players.remove(player);
        }

        players.add(ne);

        TimeManager.getInstance().update(ne);
    }

    @Override
    public void cleanup() {
        instance = null;

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
