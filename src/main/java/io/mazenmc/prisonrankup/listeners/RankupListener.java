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
