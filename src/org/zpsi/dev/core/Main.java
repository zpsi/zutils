package org.zpsi.dev.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.zpsi.dev.config.Config;
import org.zpsi.dev.events.EventListener;

//Testing

public final class Main extends JavaPlugin {

    FileConfiguration config = getConfig();
    Config configClass = new Config(this);

    public FormatMessage formatMessage(){
        FormatMessage formatMessage = new FormatMessage(this);
        return formatMessage;
    }

    FormatMessage formatMessage = formatMessage();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        configClass.configDefaults();
    }

    @Override
    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("core")) {

            if (args.length < 1 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
                sender.sendMessage(formatMessage.helpPage("core", 1, 1));
            } else if (args[0].equalsIgnoreCase("commands") || args[0].equalsIgnoreCase("cmds") || args[0].equalsIgnoreCase("cmd") || args[0].equalsIgnoreCase("c")) {
                sender.sendMessage(formatMessage.helpPage("core-commands", 1, 1));
            } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
                reloadConfig();
                sender.sendMessage(formatMessage.info(0, null));
            } else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("v")) {
                sender.sendMessage(formatMessage.info(null, "Zutils is version Alpha v0.1"));
            } else {
                sender.sendMessage(formatMessage.error(0));
            }

            return true;
        } else if (cmd.getName().equalsIgnoreCase("zchat")) {

            sender.sendMessage(formatMessage.error(0));
            return true;
        }

        return false;
    }
}

