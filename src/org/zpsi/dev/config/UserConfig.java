package org.zpsi.dev.config;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.zpsi.dev.Main;

import java.io.File;

public class UserConfig {
    Plugin plugin;

    public UserConfig(Main plugin) {
        this.plugin = plugin;
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            File file = new File(plugin.getDataFolder(), "userdata.yml");
            if (!file.exists()) {
                plugin.getDataFolder().createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initUser(Player p) {
    }

    public void delUser(Player p) {

    }

    public void loadSettings() {

    }


}
