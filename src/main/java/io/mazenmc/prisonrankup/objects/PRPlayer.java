package io.mazenmc.prisonrankup.objects;

import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PRPlayer {

    private Rank currentRank;
    private Rank nextRank;

    private String name;
    private String id;
    private OfflinePlayer offlinePlayer;

    public PRPlayer(String name) {
        //First, we need to define the all objects
        this.name = name;
        //
    }
}
