package io.mazenmc.prisonrankup;

import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.managers.DataManager;
import io.mazenmc.prisonrankup.managers.Manager;
import io.mazenmc.prisonrankup.managers.UUIDManager;
import io.mazenmc.prisonrankup.utils.ClassFinder;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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

        // Register listeners
        try{
            for(Class<?> cls : ClassFinder.find("io.mazenmc.prisonrankup.listeners", Listener.class, this)) {
                getServer().getPluginManager().registerEvents(cls.asSubclass(Listener.class).newInstance(), this);
            }

        }catch(Exception ex) {
            getLogger().log(Level.SEVERE, "Unable to register listeners, disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);

            return;
        }

        // Register subcommands
        try{
            for(Class<?> cls : ClassFinder.find("io.mazenmc.prisonrankup.subcommands", Object.class, this)) {
                cls.newInstance();
            }
        }catch(Exception ex) {
            getLogger().log(Level.SEVERE, "Unable to register default subcommands, disabling plugin...");
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
}
