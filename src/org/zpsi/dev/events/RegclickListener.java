package org.zpsi.dev.events;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.zpsi.dev.Main;
import org.zpsi.dev.advanced.RegclickCommand;
import org.zpsi.dev.chat.ChatManager;
import org.zpsi.dev.files.RegclickManager;
import org.bukkit.entity.Player;

public class RegclickListener implements Listener {

    private Main plugin;
    private FileConfiguration config;
    private ChatManager cManager;
    private RegclickManager rManager;
    private RegclickCommand rCommand;

    public RegclickListener(Main instance) {
        this.plugin = instance;
        this.config = plugin.getConfig();
        this.cManager = plugin.getcManager();
        this.rManager = plugin.getrManager();
        this.rCommand = plugin.getrCommand();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        if (rCommand.playerInteractCheck) {
            Location loc = event.getClickedBlock().getLocation();
            Action action = event.getAction();
            Player player = event.getPlayer();
            switch (rCommand.todo) {
                case 1: {
                    rCommand.startAdd(loc, action, player);
                    break;
                }
                case 2: {
                    rCommand.remove(loc);
                    break;
                }
                case 3: {
                    rCommand.info(loc);
                    break;
                }
                default: {
                    player.sendMessage(cManager.error(5));
                    break;
                }
            }
            rCommand.setPlayerInteractCheck(false);
            event.setCancelled(true);
        } else {
            Integer id = rManager.getID(event.getClickedBlock().getLocation());
            if (id != null && rManager.getAction(id) == event.getAction() && rCommand.canRun(id, event.getPlayer())){
                event.getPlayer().performCommand(rManager.getCommand(id));
                event.setCancelled(true);
            }
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void PlayerChatEvent(AsyncPlayerChatEvent event) {
        if (rCommand.playerChatCheck){
            int buffer = Integer.parseInt(event.getMessage());
            rCommand.addBuffer(buffer);
            rCommand.setPlayerChatCheck(false);
            event.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.LOW)
    public void PlayerCommandEvent(PlayerCommandPreprocessEvent event){
        rCommand.addCommand(event.getMessage());
        rCommand.setPlayerCommandCheck(false);
        event.setCancelled(true);
    }
}
