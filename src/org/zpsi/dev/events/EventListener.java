package org.zpsi.dev.events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.entity.Player;
import org.apache.commons.lang3.StringUtils;
import org.zpsi.dev.chat.ChatManager;
import org.zpsi.dev.Main;

import java.util.List;
import java.util.Set;
import java.lang.Math;

public class EventListener implements Listener {
    private Main plugin;
    private FileConfiguration config;
    private ChatManager cManager;

    public EventListener(Main instance) {
        this.plugin = instance;
        this.config = plugin.getConfig();
        this.cManager = plugin.getcManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        String eventPrefix = args[0];
        Player player = event.getPlayer();
        boolean run = false;
        int index = 0;
        if (config.isConfigurationSection("Custom messages")) {
            Set<String> keys = config.getConfigurationSection("Custom messages").getKeys(false);
            for (String s : keys) {
                String sCommand = "/".concat(s);
                if (sCommand.equalsIgnoreCase(eventPrefix)) {
                    List<String> messages = config.getStringList("Custom messages." + s);
                    if (args.length > 1) {
                        int temp = Integer.parseInt(args[1]) - 1;
                        if (StringUtils.isNumeric(args[1]) && temp >= 0 && temp < messages.size()) {
                            index = temp;
                            run = true;
                        } else {
                            player.sendMessage(cManager.error(1));
                        }
                    } else {
                        index = (int) (Math.random() * (messages.size()));
                        run = true;
                    }
                    if (run) {
                        player.chat(messages.get(index));
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

}
