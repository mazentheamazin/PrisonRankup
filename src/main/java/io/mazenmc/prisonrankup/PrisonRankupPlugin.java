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

package io.mazenmc.prisonrankup;

import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.managers.CommandManager;
import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.managers.Manager;
import io.mazenmc.prisonrankup.managers.UUIDManager;
import io.mazenmc.prisonrankup.objects.Command;
import io.mazenmc.prisonrankup.objects.SubCommand;
import io.mazenmc.prisonrankup.utils.ClassFinder;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.logging.Level;

public class PrisonRankupPlugin extends JavaPlugin{

    /* Plugin instance */
    private static PrisonRankupPlugin instance;

    public void onEnable() {
        // Define instances
        instance = this;

        // Save default configurations
        PrisonRankupConfig.DATA.saveDefaultConfig();
        PrisonRankupConfig.CONFIG.saveDefaultConfig();

        // Register managers
        try{
            //initiate classes
            ClassFinder.find("io.mazenmc.prisonrankup.managers", Manager.class, this);
        }catch(Exception ex) {
            getLogger().log(Level.SEVERE, "Unable to register managers, disabling plugin...", ex);
            getServer().getPluginManager().disablePlugin(this);

            return;
        }

        // Register listeners
        try{
            for(Class<?> cls : ClassFinder.find("io.mazenmc.prisonrankup.listeners", Listener.class, this)) {
                getServer().getPluginManager().registerEvents(cls.asSubclass(Listener.class).newInstance(), this);
            }

        }catch(Exception ex) {
            getLogger().log(Level.SEVERE, "Unable to register listeners, disabling plugin...", ex);
            getServer().getPluginManager().disablePlugin(this);

            return;
        }

        // Register commands
        try{
            for(Class<? extends Command> cls : ClassFinder.find("io.mazenmc.prisonrankup.commands", Command.class, this)) {
                Command cmd = (Command) cls.getMethod("getInstance").invoke(null);

                CommandManager.getInstance().registerCommand(cmd);
            }
        }catch(Exception ex) {
            getLogger().log(Level.SEVERE, "Unable to register default commands, disabling plugin...", ex);
            getServer().getPluginManager().disablePlugin(this);

            return;
        }

        // Register subcommands
        try{
            for(Class<? extends SubCommand> cls : ClassFinder.find("io.mazenmc.prisonrankup.subcommands", SubCommand.class, this)) {
                Field instance = cls.getDeclaredField("instance");

                instance.setAccessible(true);

                SubCommand subCommand = (SubCommand) instance.get(null);

                CommandManager.getInstance().registerSubCommand(subCommand.getCommandName(), subCommand);
            }
        }catch(Exception ex) {
            getLogger().log(Level.SEVERE, "Unable to register default subcommands, disabling plugin...", ex);
            getServer().getPluginManager().disablePlugin(this);

            return;
        }

    }

    public void onDisable() {
        // Save all data
        DataManager.getInstance().save();
        UUIDManager.getInstance().save();

        // Avoid memory leaks
        cleanup();
        instance = null;
    }

    private void cleanup() {
        try{
            for(Class<? extends Manager> cls : ClassFinder.find("io.mazenmc.prisonrankup.managers", Manager.class, this)) {
                Manager manager = (Manager) cls.getMethod("getInstance").invoke(null);

                manager.cleanup();
            }
        }catch(Exception ignored) {}
    }

    /**
     * Singleton for PrisonRankup's plugin instance
     * @return PrisonRankup's plugin instance
     */
    public static PrisonRankupPlugin getInstance() {
        return instance;
    }

    public static void log(String message) {
        getInstance().getLogger().info(message);
    }

    public File getFile0() {
        return getFile();
    }
}
