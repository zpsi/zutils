package org.zpsi.dev.advanced;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.zpsi.dev.Main;
import org.zpsi.dev.chat.ChatManager;
import org.zpsi.dev.files.RegclickManager;
import org.bukkit.Location;

public class RegclickCommand {
    private Main plugin;
    private RegclickManager rManager;
    private ChatManager cManager;

    public boolean playerInteractCheck = false;
    public boolean playerChatCheck = false;
    public boolean playerCommandCheck = false;

    public CommandSender sender;

    public Location loc;
    public Action action;
    public Player player;
    public Integer todo;
    public Integer buffer;
    public String cmd;

    public RegclickCommand(Main instance) {
        this.plugin = instance;
        this.rManager = plugin.getrManager();
        this.cManager = plugin.getcManager();
    }

    public void clearValues() {

        playerInteractCheck = false;
        playerChatCheck = false;
        playerCommandCheck = false;
        sender = null;
        loc = null;
        action = null;
        player = null;
        todo = null;
        buffer = null;
        cmd = null;
    }

    public void setPlayerInteractCheck(boolean isEnabled) {
        this.playerInteractCheck = isEnabled;
    }

    public void setPlayerChatCheck(boolean isEnabled) {
        this.playerChatCheck = isEnabled;
    }

    public void setPlayerCommandCheck(boolean isEnabled) {
        this.playerCommandCheck = isEnabled;
    }

    public void setCommandSender(CommandSender sender) {
        this.sender = sender;
    }

    public void init(int todo) {
        this.playerInteractCheck = true;
        this.todo = todo;
    }

    public void remove(Location loc) {

    }

    public void startAdd(Location loc, Action action, Player player) {
        sender.sendMessage(cManager.msg("Regclick event initialized at " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " with action " + action.toString() + "."));
        this.loc = loc;
        this.action = action;
        this.player = player;
        getBuffer();
    }

    public void getBuffer() {
        sender.sendMessage(cManager.msg("What should the buffer be (in seconds)?"));
        this.playerChatCheck = true;
    }

    public void addBuffer(int buffer) {
        sender.sendMessage(cManager.msg("Buffer created with value " + buffer + "."));
        this.buffer = buffer;
        getCommand();
    }

    public void getCommand() {
        sender.sendMessage(cManager.msg("What should be run?"));
        this.playerCommandCheck = true;
    }

    public void addCommand(String cmd) {
        sender.sendMessage(cManager.msg(cmd + " will be run when this event is triggered."));
        this.cmd = cmd;
        finalizeAdd();
    }

    public void finalizeAdd() {
        Integer id = rManager.regInit();
        rManager.addPlayer(id, player);
        rManager.addLocation(id, loc);
        rManager.addAction(id, action);
        rManager.addCommand(id, cmd);
        rManager.addBuffer(id, buffer);
    }

    public void info(Location loc) {

    }

    public boolean canRun(Integer id, Player player){
        return false;
    }

}
