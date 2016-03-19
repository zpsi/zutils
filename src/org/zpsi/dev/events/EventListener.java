package org.zpsi.dev.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.entity.Player;
import org.apache.commons.lang3.StringUtils;
import org.zpsi.dev.core.Main;

import java.util.List;
import java.util.Set;
import java.lang.Math;

public class EventListener implements Listener {
    private Main plugin;

    public EventListener(Main instance) {
        this.plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split(" ");
        String eventPrefix = args[0];
        Player player = event.getPlayer();
        boolean run = false;
        int index = 0;
        if (plugin.config.isConfigurationSection("Custom messages")) {
            Set<String> keys = plugin.config.getConfigurationSection("Custom messages").getKeys(false);
            for (String s : keys) {
                String sCommand = "/".concat(s);
                if (sCommand.equalsIgnoreCase(eventPrefix)) {
                    List<String> messages = plugin.config.getStringList("Custom messages." + s);
                    if (args.length > 1) {
                        int temp = Integer.parseInt(args[1]) - 1;
                        if (StringUtils.isNumeric(args[1]) && temp >= 0 && temp < messages.size()) {
                            index = temp;
                            run = true;
                        } else {
                            player.sendMessage(plugin.error(1));
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
