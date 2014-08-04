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
