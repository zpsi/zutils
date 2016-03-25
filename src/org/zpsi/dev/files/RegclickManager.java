package org.zpsi.dev.files;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;
import org.zpsi.dev.Main;

import java.io.File;
import java.io.Reader;
import java.util.Set;
import java.util.logging.Level;

public class RegclickManager {

    private Main plugin;
    FileConfiguration config;
    File file;

    public RegclickManager(Main instance) {
        this.plugin = instance;
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            this.file = new File(plugin.getDataFolder(), "regclick.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            this.config = YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveConfig() {
        if (config == null || file == null) {
            return;
        }
        try {
            config.save(file);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + file, ex);
        }
    }

    public Integer regInit(){
        Set<String> ids = config.getKeys(false);
        Integer id = null;
        if (ids.isEmpty()){
            id = 1;
        } else {
            for (String i : ids) {
                if (config.getConfigurationSection(i).getKeys(true) == null){
                    id = Integer.parseInt(i);
                    break;
                } else {
                    id = Integer.parseInt(i) + 1;
                }
            }
        }
        config.createSection(id.toString());
        saveConfig();
        return id;
    }

    public void regRemove(Integer id){
        config.set(id.toString(), null);
        saveConfig();
    }

    public void getID(Location loc){

    }

    public boolean exists(Integer id){

        return false;
    }

    public void storeLoc(Location loc){

    }

}
