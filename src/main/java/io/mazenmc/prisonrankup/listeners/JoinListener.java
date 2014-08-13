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

package io.mazenmc.prisonrankup.listeners;

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.managers.TimeManager;
import io.mazenmc.prisonrankup.managers.UUIDManager;
import io.mazenmc.prisonrankup.managers.UpdaterManager;
import io.mazenmc.prisonrankup.utils.UUIDUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        // Before we do anything I'm going to do something hacky ;)
        if(!(Bukkit.getOnlineMode())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try{
                        // Get the CraftPlayer class
                        String mcVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
                        Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + mcVersion + ".entity.CraftPlayer");

                        // Get the NMS EntityPlayer class
                        Method handle = craftPlayer.getMethod("getHandle");
                        Object nmsEntity = handle.invoke(event.getPlayer());
                        Class<?> entityPlayer = nmsEntity.getClass();

                        // If we've gotten this far, we can now get his UUID
                        UUID id = UUIDUtil.stringToID(UUIDManager.getInstance().getRepository().findProfilesByNames(event.getPlayer().getName())[0].getId());

                        // Now we're going to set his uuid
                        Field field = entityPlayer.getField("uniqueID");
                        field.set(nmsEntity, id);

                        // Continue the running the event

                        new JoinRunnable(event).runTask(PrisonRankupPlugin.getInstance());
                    }catch(Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.runTaskAsynchronously(PrisonRankupPlugin.getInstance());
        }else{
            new JoinRunnable(event).run();
        }
    }

    private class JoinRunnable extends BukkitRunnable {

        private PlayerJoinEvent event;

        private JoinRunnable(PlayerJoinEvent event) {
            this.event = event;
        }

        @Override
        public void run() {
            UUIDManager uuidManager = UUIDManager.getInstance();

            if(!(uuidManager.contains(event.getPlayer().getName())) &&
                    (uuidManager.getName(event.getPlayer().getUniqueId()) != null) ||
                    !(uuidManager.getName(event.getPlayer().getUniqueId()).equals(event.getPlayer().getName()))) {

                UUIDManager.getInstance().updateName(event.getPlayer().getUniqueId(), event.getPlayer().getName());
            }

            if(!DataManager.getInstance().contains(event.getPlayer().getUniqueId())) {
                DataManager.getInstance().addPlayer(event.getPlayer().getName());
            }

            if(PrisonRankupConfig.CONFIG.getBoolean("Timed Requirement")) {
                long seconds = 0L;
                int time = PrisonRankupConfig.CONFIG.getInt("Time Interval");

                switch(PrisonRankupConfig.CONFIG.getString("Time type").toLowerCase().charAt(0)) {
                    case 's':
                        seconds += time;
                        break;

                    case 'm':
                        seconds += time * 60;
                        break;

                    case 'h':
                        seconds += time * 360;
                        break;
                }

                TimeManager.getInstance().addPlayer(DataManager.getInstance().getPlayer(event.getPlayer().getName()), seconds);
            }

            if(UpdaterManager.getInstance().isUpdateAvailable() && event.getPlayer().hasPermission("prisonrankup.update")) {
                UpdaterManager um = UpdaterManager.getInstance();

                event.getPlayer().sendMessage(ChatColor.GOLD + "An update is available: " + um.getName() + ", a " +
                        um.getType() + " for " + um.getVersion() + " available at " + um.getVersion());
            }
        }
    }
}
