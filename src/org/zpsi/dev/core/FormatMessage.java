package org.zpsi.dev.core;

import org.bukkit.ChatColor;

public class FormatMessage {

    private Main plugin;

    public FormatMessage(Main instance) {
        this.plugin = instance;
    }

    String prefix = ChatColor.translateAlternateColorCodes('&', plugin.config.getString("Formatting.Prefix"));

    public String error(Integer code) {

        String errorPrefix = ChatColor.translateAlternateColorCodes('&', plugin.config.getString("Formatting.Error prefix"));
        String errorColor = ChatColor.translateAlternateColorCodes('&', plugin.config.getString("Formatting.Error color"));
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
        String infoPrefix = ChatColor.translateAlternateColorCodes('&', plugin.config.getString("Formatting.Info prefix"));
        String infoColor = ChatColor.translateAlternateColorCodes('&', plugin.config.getString("Formatting.Info color"));
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
        String msgPrefix = ChatColor.translateAlternateColorCodes('&', plugin.config.getString("Formatting.Message prefix"));
        String msgColor = ChatColor.translateAlternateColorCodes('&', plugin.config.getString("Formatting.Message color"));
        return prefix + msgPrefix + msgColor + msg;
    }


    public String helpPage(String topic, Integer page, Integer maxPages) {
        String header = ChatColor.DARK_AQUA + "Zutils help page for " + topic;
        String line = ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "-----------------------------------------------------";
        String pageNumbers;
        if (maxPages == 1) {
            pageNumbers = ChatColor.DARK_AQUA + "There are no other help pages for this topic.";
        } else {
            pageNumbers = ChatColor.DARK_AQUA + "Do /" + topic + " help ";
        }
        String[] commands = null;
        String[] usage = null;
        Boolean isPage = false;
        String fullPage = header + "\n" + line + "\n";
        if (topic.equalsIgnoreCase("core") && page == 1) {

            commands = new String[5];
            commands[0] = "core commands";
            commands[1] = "core info";
            commands[2] = "core reload";
            commands[3] = "core version";
            commands[4] = "core help";

            usage = new String[5];
            usage[0] = "View the list of all Zutils commands.";
            usage[1] = "View the information page for Zutils.";
            usage[2] = "Reload configuration files for Zutils.";
            usage[3] = "View the current Zutils version.";
            usage[4] = "View this page.";

            isPage = true;
        } else if (topic.equalsIgnoreCase("core-commands") && page == 1) {

            commands = new String[5];
            commands[0] = "core";
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
