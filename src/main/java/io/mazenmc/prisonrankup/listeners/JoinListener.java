package io.mazenmc.prisonrankup.listeners;

import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.managers.UUIDManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(!(UUIDManager.getInstance().contains(event.getPlayer().getName())) ||
                !(UUIDManager.getInstance().getName(event.getPlayer().getUniqueId()).equals(event.getPlayer().getName()))) {
            UUIDManager.getInstance().updateName(event.getPlayer().getUniqueId(), event.getPlayer().getName());
        }

        if(!DataManager.getInstance().contains(event.getPlayer().getUniqueId())) {
            DataManager.getInstance().addPlayer(event.getPlayer().getName());
        }
    }
}
