package org.zpsi.dev.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.zpsi.dev.core.Main;

import java.util.Arrays;
import java.util.List;

public class Config {

    private Main plugin;

    public Config(Main instance) {
        this.plugin = instance;
    }

    FileConfiguration config = this.plugin.getConfig();

    public void configDefaults() {

        config.addDefault("Formatting.Prefix", "&8[Zutils] &r");
        config.addDefault("Formatting.Error prefix", "&cError: &r");
        config.addDefault("Formatting.Error color", "&c");
        config.addDefault("Formatting.Info prefix", "&aInfo: &r");
        config.addDefault("Formatting.Info color", "&a");
        config.addDefault("Formatting.Message prefix", "");
        config.addDefault("Formatting.Message color", "&b");
        List<String> zpsi = Arrays.asList("Zpsi smells", "bad");
        config.addDefault("Custom messages.zpsi", zpsi);
        config.options().copyDefaults(true);
        this.plugin.saveConfig();
    }

}
