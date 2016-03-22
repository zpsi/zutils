package org.zpsi.dev.files;

import org.zpsi.dev.Main;

import java.io.File;

public class RegclickManager {

    private Main plugin;

    public RegclickManager(Main instance) {
        this.plugin = instance;
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            File file = new File(plugin.getDataFolder(), "regclick.yml");
            if (!file.exists()) {
                plugin.getDataFolder().createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
