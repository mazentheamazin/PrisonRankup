package io.mazenmc.prisonrankup.managers;

import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.utils.StringUtil;
import io.mazenmc.prisonrankup.utils.UUIDUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import static io.mazenmc.prisonrankup.PrisonRankupPlugin.getInstance;
import static io.mazenmc.prisonrankup.PrisonRankupPlugin.log;

public class UUIDManager extends Manager {

    private static UUIDManager instance = new UUIDManager();

    private Map<UUID, String> uuidData = new HashMap<>();
    private JSONParser jsonParser = new JSONParser();
    private PrisonRankupConfig dataConfig = PrisonRankupConfig.DATA;


    private UUIDManager() {
        update();
    }

    /**
     * Method used to update memory data. SHOULD ONLY BE CALLED AT STARTUP!
     */
    public void update() {
        log("Updating all UUID data, logging for security reasons...");

        for(String s : dataConfig.getConfigurationSection("users").getKeys(false)) {
            ConfigurationSection userSection;
            UUID uuid = UUIDUtil.stringToID(s);

            //Try and get the name from config
            if((userSection = dataConfig.getConfigurationSection(StringUtil.buildString("users.", s))).contains("name")) {
                uuidData.put(uuid, userSection.getString("name"));

                continue;
            }

            /* Either 2 things happened: The player has joined when the server was running < v3.0 or he hasn't joined the server
               We're first going to try and get his UUID from his OfflinePlayer profile                                           */
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

            if(offlinePlayer.hasPlayedBefore()) {
                uuidData.put(uuid, offlinePlayer.getName());

                continue;
            }

            // Now we're for sure he hasn't joined the server yet, we will have to resort to connecting to mojangs servers
            log(StringUtil.buildString("Player with UUID ", uuid, " was unable to be found on Bukkit nor PrisonRankup's databases... ",
                    "Connecting to Mojang's servers to retrieve required data"));

            // NOTE: Not running this async due to that it should be called at startup, and this data may be required before it is unable to be retrieved
            try{
                //Define the URL and open the connection
                URL url = new URL(StringUtil.buildString("https://sessionserver.mojang.com/session/minecraft/profile/", uuid.toString()));
                URLConnection connection = url.openConnection();

                //Read the contents of the said page and parse them into a JSONArray
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JSONArray array = (JSONArray) jsonParser.parse(reader);

                for(JSONObject object : )
            }catch(Exception ex) {
                getInstance().getLogger().log(Level.SEVERE, StringUtil.buildString("Unable to retrieve a player name by the UUID of ", uuid.toString(),
                        " due to ", ex.getMessage()), ex);
            }
        }
    }

    public void save() {
        // TODO: Save all new data
    }
}
