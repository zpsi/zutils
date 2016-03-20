package org.zpsi.dev;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.zpsi.dev.events.EventListener;
import org.zpsi.dev.chat.ChatManager;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public final class Main extends JavaPlugin {

    private static Main instance;
    private FileConfiguration config;
    private ChatManager cManager;

    @Override
    public void onEnable() {
        instance = this;

        this.config = getConfig();
        this.cManager = new ChatManager(this);
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        configDefaults();
    }

    public ChatManager getcManager(){
        return cManager;
    }

    public Main getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("zutils")) {

            if (args.length < 1 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
                if (args.length < 2 || args[1].equalsIgnoreCase("main")){
                    sender.sendMessage(cManager.helpPage("main", 1));
                } else if (args.length == 3 && StringUtils.isNumeric(args[2])) {
                    sender.sendMessage(cManager.helpPage(args[1].toLowerCase(), Integer.parseInt(args[2])));
                }
                else {
                    sender.sendMessage(cManager.helpPage(args[1], 1));
                }

            } else if (args[0].equalsIgnoreCase("commands") || args[0].equalsIgnoreCase("cmds") || args[0].equalsIgnoreCase("cmd") || args[0].equalsIgnoreCase("c")) {
                sender.sendMessage(cManager.helpPage("commands", 1));
            } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
                reloadConfig();
                sender.sendMessage(cManager.info(0, null));
            } else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("v")) {
                sender.sendMessage(cManager.info(null, "Zutils is version Alpha v0.1"));
            }
            else {
                sender.sendMessage(cManager.error(0));
            }

            return true;
        } else if (cmd.getName().equalsIgnoreCase("zchat")) {

            sender.sendMessage(cManager.error(0));
            return true;
        }

        return false;
    }

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
        saveConfig();
    }
}

