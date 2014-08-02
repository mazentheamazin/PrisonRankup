package io.mazenmc.prisonrankup.objects;

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import io.mazenmc.prisonrankup.enums.Untitled;
import io.mazenmc.prisonrankup.managers.UUIDManager;
import io.mazenmc.prisonrankup.managers.VaultManager;
import io.mazenmc.prisonrankup.utils.UUIDUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

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
            profile.set("rank", getInstance().getRank(0).getName());
            profile.set("name", name);
        }else{
            profile = DATA.getConfigurationSection("users." + id);
        }

        setRank(getInstance().getRank(profile.getString("rank")));
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
        setRank(nextRank);

        VaultManager vaultManager = VaultManager.getInstance();

        vaultManager.getEconomy().withdrawPlayer(offlinePlayer, nextRank.getPrice().getValue());

        vaultManager.getPermission().playerRemoveGroup(null, offlinePlayer, currentRank.getName());
        vaultManager.getPermission().playerAddGroup(null, offlinePlayer, nextRank.getName());
    }

    /**
     * Returns if this player can rankup
     * @return If said player can rankup
     */
    public boolean canRankup() {
        return (VaultManager.getInstance().getEconomy().getBalance(offlinePlayer) >= nextRank.getPrice().getValue()) || (currentRank == nextRank);
    }

    /**
     * Gets why a player can't rankup
     * @return Why a player can't rankup
     */
    public Untitled getReason() {
       if(VaultManager.getInstance().getEconomy().getBalance(offlinePlayer) >= nextRank.getPrice().getValue()) {
           return Untitled.NOHASMONEY;
       }else if(currentRank == nextRank) {
           return Untitled.HASMUCHRANK;
       }else{
           return Untitled.KTHXBAI;
       }
    }

    public ConfigurationSection getSection() {
        return profile;
    }

    /**
     * Sets the players rank
     * @param rank The rank you wish to set the player to
     */
    public void setRank(Rank rank) {
        // Get the current rank and index of said rank
        currentRank = getInstance().getRank(profile.getString("rank"));
        int crIndex = getInstance().indexOf(currentRank);

        // This will set his next rank to his current one if he has the last rank
        if(crIndex == (getInstance().getRanks().size()-1)) {
            nextRank = currentRank;

            return;
        }

        nextRank = getInstance().getRank(crIndex + 1);
    }

    /**
     * Gets the balance of the player from Vault
     *
     * @return Player's balance
     */
    public double getBalance() {
        return VaultManager.getInstance().getEconomy().getBalance(offlinePlayer);
    }

}
