package org.zpsi.dev.chat;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.zpsi.dev.Main;

public class ChatManager {

    private Main plugin;
    private FileConfiguration config;
    private String prefix;

    public ChatManager(Main instance){
        this.plugin = instance.getInstance();
        config = plugin.getConfig();
        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("Formatting.Prefix"));
    }

    public String error(Integer code) {

        String errorPrefix = ChatColor.translateAlternateColorCodes('&', config.getString("Formatting.Error prefix"));
        String errorColor = ChatColor.translateAlternateColorCodes('&', config.getString("Formatting.Error color"));
        String errorMessage = prefix + errorPrefix + errorColor;
        switch (code) {
            case 0: {
                errorMessage += "Incorrect usage.";
                break;
            }
            case 1: {
                errorMessage += "Could not find the target.";
                break;
            }
            case 2: {
                errorMessage += "Invalid target.";
                break;
            }
            case 3: {
                errorMessage += "Player is offline.";
                break;
            }
            case 4: {
                errorMessage += "This command can not be run from console.";
                break;
            }
            case 5: {
                errorMessage += "Something went wrong, you shouldn't be seeing this.";
                break;
            }
            case 6: {
                errorMessage += "Insufficient permissions.";
                break;
            }
            case 7: {
                errorMessage += "Does not exist.";
                break;
            }
            case 8: {
                errorMessage += "Help page not found.";
                break;
            }
            default: {
                errorMessage += "Unexpected error code.";
            }
        }
        return errorMessage;
    }

    public String info(Integer code, String msg) {
        String infoPrefix = ChatColor.translateAlternateColorCodes('&', config.getString("Formatting.Info prefix"));
        String infoColor = ChatColor.translateAlternateColorCodes('&', config.getString("Formatting.Info color"));
        String infoMessage = prefix + infoPrefix + infoColor;
        if (code == null) {
            infoMessage += msg;
        } else {
            switch (code) {
                case 0: {
                    infoMessage += "Zutils has been reloaded.";
                    break;
                }
                default: {
                    infoMessage += "Unexpected info code.";
                }
            }
        }
        return infoMessage;
    }

    public String msg(String msg) {
        String msgPrefix = ChatColor.translateAlternateColorCodes('&', config.getString("Formatting.Message prefix"));
        String msgColor = ChatColor.translateAlternateColorCodes('&', config.getString("Formatting.Message color"));
        return prefix + msgPrefix + msgColor + msg;
    }


    public String helpPage(String topic, Integer page) {
        String header = ChatColor.DARK_AQUA + "Zutils help page for " + topic;
        String line = ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------";
        String pageNumbers;
        int maxPages = 1;
        String[] commands = null;
        String[] usage = null;
        Boolean isPage = false;
        if (topic.equalsIgnoreCase("main") || topic.equalsIgnoreCase("zutils") || topic.equalsIgnoreCase("z")) {
            maxPages = 1;
            header =  ChatColor.DARK_AQUA + "Zutils main help page.";
            switch(page){
                case 1 : {
                    commands = new String[5];
                    commands[0] = "zutils commands";
                    commands[1] = "zutils info";
                    commands[2] = "zutils reload";
                    commands[3] = "zutils version";
                    commands[4] = "zutils help";

                    usage = new String[5];
                    usage[0] = "View the list of all Zutils commands.";
                    usage[1] = "View the information page for Zutils.";
                    usage[2] = "Reload configuration files for Zutils.";
                    usage[3] = "View the current Zutils version.";
                    usage[4] = "View this page.";
                    isPage = true;
                    break;
                }
                default : {
                    isPage = false;
                }
            }
        } else if (topic.equalsIgnoreCase("commands") || topic.equalsIgnoreCase("cmds") || topic.equalsIgnoreCase("cmd")) {
            header =  ChatColor.DARK_AQUA + "Zutils commands.";
            switch(page) {
                case 1 : {
                    maxPages = 1;
                    commands = new String[5];
                    commands[0] = "zutils";
                    commands[1] = "<cmsgName>";
                    commands[2] = "zchat";
                    commands[3] = "regclick";
                    commands[4] = "start";

                    usage = new String[5];
                    usage[0] = "Main command for Zutils.";
                    usage[1] = "Run a config-defined chat message.";
                    usage[2] = "Request a private chat with another player.";
                    usage[3] = "Register an action to a block-interact event.";
                    usage[4] = "Teleport to a random safe location.";
                    isPage = true;
                    break;
                }
                default : {
                    isPage = false;
                }
            }
        }
        String fullPage = header + "\n" + line + "\n";
        if (maxPages == 1) {
            pageNumbers = ChatColor.DARK_AQUA + "There are no other help pages for this topic.";
        } else if (page == maxPages) {
            pageNumbers = ChatColor.DARK_AQUA + "You have reached the last page for this topic.";
        } else {
            pageNumbers = ChatColor.DARK_AQUA + "Do /zutils help " + topic + " " + page + 1 + " to get to the next page.";
        }
        if (isPage) {
            for (int i = 0; i < commands.length && i < usage.length; i++) {
                fullPage += ChatColor.AQUA + "/" + commands[i] + ": " + ChatColor.GRAY + usage[i] + "\n";
            }
            if (page < maxPages) {
                pageNumbers += (page + 1) + " to get to the next page.";
            } else if (page == maxPages && maxPages != 1) {
                pageNumbers += (page - 1) + " to get to the previous page.";
            }
            fullPage += line + "\n" + pageNumbers;
            return fullPage;
        }
        return error(8);
    }

}
