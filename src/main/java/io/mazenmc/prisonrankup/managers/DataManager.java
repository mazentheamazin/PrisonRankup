package io.mazenmc.prisonrankup.managers;

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

    public void update() {
        for(String s : dataConfig.getConfigurationSection("users").getKeys(false)) {
            addPlayer(UUIDManager.getInstance().getName(UUIDUtil.stringToID(s)));
        }
    }

    public void addPlayer(String name) {
        players.add(new PRPlayer(name));
    }

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

    public PRPlayer getPlayer(String name) {
        for(PRPlayer player : players) {
            if(player.getName().startsWith(name))
                return player;
        }

        return null;
    }

    public boolean contains(UUID id) {
        for(PRPlayer player : players) {
            if(player.getID().equals(id))
                return true;
        }

        return false;
    }

    public List<PRPlayer> getPlayers() {
        return players;
    }

    @Override
    public void cleanup() {
        instance = null;

        save();
    }

    public static DataManager getInstance() {
        return instance;
    }
}
