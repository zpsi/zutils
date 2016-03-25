package org.zpsi.dev.files;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
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
                if (!exists(Integer.parseInt(i))){
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

    public Integer getID(Location loc){
        Integer id = null;
        Set<String> ids = config.getKeys(false);
        for (String i : ids){
            if(getLocation(Integer.parseInt(i)) == loc){
                id = Integer.parseInt(i);
                break;
            }
        }
        return id;
    }

    public boolean exists(Integer id){
        if (config.isConfigurationSection(id.toString())){
            return true;
        }
        return false;
    }

    public void addPlayer(Integer id, Player player){
        config.getConfigurationSection(id.toString()).set("Player", player);
        saveConfig();
    }

    public void addLocation(Integer id, Location loc){
        config.getConfigurationSection(id.toString()).set("Location", loc);
        saveConfig();
    }

    public void addAction(Integer id, Action action){
        config.getConfigurationSection(id.toString()).set("Action", action);
        saveConfig();
    }

    public void addBuffer(Integer id, Integer buffer){
        config.getConfigurationSection(id.toString()).set("Buffer", buffer);
        saveConfig();
    }

    public void addCommand(Integer id, String command){
        config.getConfigurationSection(id.toString()).set("Command", command);
        saveConfig();
    }

    public Location getLocation(Integer id){
        Location loc = (Location) config.getConfigurationSection(id.toString()).get("Location");
        return loc;
    }

    public Action getAction(Integer id){
        Action action = (Action) config.getConfigurationSection(id.toString()).get("Action");
        return action;
    }

    public Integer getBuffer(Integer id){
        Integer buffer = (Integer) config.getConfigurationSection(id.toString()).get("Buffer");
        return buffer;
    }

    public String getCommand(Integer id){
        String command = (String) config.getConfigurationSection(id.toString()).get("Command");
        return command;
    }
}
