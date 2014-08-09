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

package io.mazenmc.prisonrankup.enums;

import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.mazenmc.prisonrankup.PrisonRankupPlugin.getInstance;
import static io.mazenmc.prisonrankup.PrisonRankupPlugin.log;

public enum PrisonRankupConfig {

    CONFIG(new File(getInstance().getDataFolder(), "config.yml")),
    DATA(new File(getInstance().getDataFolder(), "data.yml"));

    private FileConfiguration config;
    private File file;

    private PrisonRankupConfig(File file) {
        this.config = YamlConfiguration.loadConfiguration(file);
        this.file = file;
    }

    public boolean isConfigurationSection(String path) {
        return config.isConfigurationSection(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return config.getConfigurationSection(path);
    }

    public boolean isColor(String path) {
        return config.isColor(path);
    }

    public Color getColor(String path, Color def) {
        return config.getColor(path, def);
    }

    public Color getColor(String path) {
        return config.getColor(path);
    }

    public boolean isItemStack(String path) {
        return config.isItemStack(path);
    }

    public ItemStack getItemStack(String path, ItemStack def) {
        return config.getItemStack(path, def);
    }

    public ItemStack getItemStack(String path) {
        return config.getItemStack(path);
    }

    public boolean isOfflinePlayer(String path) {
        return config.isOfflinePlayer(path);
    }

    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
        return config.getOfflinePlayer(path, def);
    }

    public OfflinePlayer getOfflinePlayer(String path) {
        return config.getOfflinePlayer(path);
    }

    public boolean isVector(String path) {
        return config.isVector(path);
    }

    public Vector getVector(String path, Vector def) {
        return config.getVector(path, def);
    }

    public Vector getVector(String path) {
        return config.getVector(path);
    }

    public List<Map<?, ?>> getMapList(String path) {
        return config.getMapList(path);
    }

    public List<Short> getShortList(String path) {
        return config.getShortList(path);
    }

    public List<Character> getCharacterList(String path) {
        return config.getCharacterList(path);
    }

    public List<Byte> getByteList(String path) {
        return config.getByteList(path);
    }

    public List<Long> getLongList(String path) {
        return config.getLongList(path);
    }

    public List<Float> getFloatList(String path) {
        return config.getFloatList(path);
    }

    public List<Double> getDoubleList(String path) {
        return config.getDoubleList(path);
    }

    public List<Boolean> getBooleanList(String path) {
        return config.getBooleanList(path);
    }

    public List<Integer> getIntegerList(String path) {
        return config.getIntegerList(path);
    }

    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public boolean isList(String path) {
        return config.isList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return config.getList(path, def);
    }

    public List<?> getList(String path) {
        return config.getList(path);
    }

    public boolean isLong(String path) {
        return config.isLong(path);
    }

    public long getLong(String path, long def) {
        return config.getLong(path, def);
    }

    public long getLong(String path) {
        return config.getLong(path);
    }

    public boolean isDouble(String path) {
        return config.isDouble(path);
    }

    public double getDouble(String path, double def) {
        return config.getDouble(path, def);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public boolean isBoolean(String path) {
        return config.isBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public boolean isInt(String path) {
        return config.isInt(path);
    }

    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public boolean isString(String path) {
        return config.isString(path);
    }

    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public ConfigurationSection createSection(String path, Map<?, ?> map) {
        return config.createSection(path, map);
    }

    public ConfigurationSection createSection(String path) {
        return config.createSection(path);
    }

    public Object get(String path, Object def) {
        return config.get(path, def);
    }

    public Object get(String path) {
        return config.get(path);
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public ConfigurationSection getDefaultSection() {
        return config.getDefaultSection();
    }

    public Configuration getRoot() {
        return config.getRoot();
    }

    public String getName() {
        return config.getName();
    }

    public String getCurrentPath() {
        return config.getCurrentPath();
    }

    public boolean isSet(String path) {
        return config.isSet(path);
    }

    public boolean contains(String path) {
        return config.contains(path);
    }

    public Map<String, Object> getValues(boolean deep) {
        return config.getValues(deep);
    }

    public Set<String> getKeys(boolean deep) {
        return config.getKeys(deep);
    }

    public ConfigurationSection getParent() {
        return config.getParent();
    }

    public Configuration getDefaults() {
        return config.getDefaults();
    }

    public void setDefaults(Configuration defaults) {
        config.setDefaults(defaults);
    }

    public void addDefaults(Configuration defaults) {
        config.addDefaults(defaults);
    }

    public void addDefaults(Map<String, Object> defaults) {
        config.addDefaults(defaults);
    }

    public void addDefault(String path, Object value) {
        config.addDefault(path, value);
    }

    public void save() {
        try{
            config.save(file);
        }catch(IOException ex) {
            log("Unable to save " + file.getName() + " due to " + ex.getMessage() + "... printing stacktrace");
            ex.printStackTrace();
        }
    }

    public void reload() {
        try{
            config.load(file);
        }catch(Exception ex) {
            log("Unable to reload " + file.getName() + " due to " + ex.getMessage() + "... printing stacktrace");
            ex.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if(!file.exists()) {
            getInstance().saveResource(file.getName(), false);
        }


        reload();
    }
}
