package io.mazenmc.prisonrankup.managers;

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

public class VaultManager extends Manager{

    /* Class instance */
    private static VaultManager instance = new VaultManager();

    /* Vault class instances */
    private Permission permission;
    private Economy economy;
    private Chat chat;

    private VaultManager() {
        if(getPluginManager().getPlugin("Vault") == null) {
            PrisonRankupPlugin.log("Vault has not been found on the system, disabling...");

            getPluginManager().disablePlugin(PrisonRankupPlugin.getInstance());
            return;
        }

        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        if(chatProvider != null) {
            chat = chatProvider.getProvider();
        }
    }

    /**
     * Getter for the Permission service
     * @return Permission service
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Getter for the Economy service
     * @return Economy service
     */
    public Economy getEconomy() {
        return economy;
    }

    /**
     * Getter for the Chat service
     * @return Chat service
     */
    public Chat getChat() {
        return chat;
    }

    @Override
    public void cleanup() {
        instance = null;
    }

    public static VaultManager getInstance() {
        return instance;
    }
}
