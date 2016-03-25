package org.zpsi.dev;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.zpsi.dev.advanced.RegclickCommand;
import org.zpsi.dev.events.RegclickListener;
import org.zpsi.dev.files.RegclickManager;
import org.zpsi.dev.files.UserManager;
import org.zpsi.dev.events.EventListener;
import org.zpsi.dev.chat.ChatManager;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public final class Main extends JavaPlugin {

    private static Main instance;
    private FileConfiguration config;
    private ChatManager cManager;
    private UserManager uManager;
    private RegclickManager rManager;
    private RegclickCommand rCommand;

    @Override
    public void onEnable() {
        instance = this;

        this.config = getConfig();
        this.cManager = new ChatManager(this);
        this.uManager = new UserManager(this);
        this.rManager = new RegclickManager(this);
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getServer().getPluginManager().registerEvents(new RegclickListener(this), this);
        configDefaults();
    }

    public ChatManager getcManager() {
        return cManager;
    }

    public UserManager getuManager() {
        return uManager;
    }

    public RegclickManager getrManager() {
        return rManager;
    }

    public RegclickCommand getrCommand() {
        return rCommand;
    }

    public Main getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("zutils")) {
            if (!commandIsEnabled("zutils") && sender instanceof Player) {
                sender.sendMessage(cManager.error(9));
                return true;
            }
            if (args.length < 1 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
                if (!sender.hasPermission("zutils.*") && !sender.isOp()) {
                    if (!sender.hasPermission("zutils.help")) {
                        sender.sendMessage(cManager.error(6));
                        return true;
                    }
                }
                if (args.length < 2 || args[1].equalsIgnoreCase("main")) {
                    sender.sendMessage(cManager.helpPage("main", 1));
                } else if (args.length == 3 && StringUtils.isNumeric(args[2])) {
                    sender.sendMessage(cManager.helpPage(args[1].toLowerCase(), Integer.parseInt(args[2])));
                } else {
                    sender.sendMessage(cManager.helpPage(args[1], 1));
                }

            } else if (args[0].equalsIgnoreCase("commands") || args[0].equalsIgnoreCase("cmds") || args[0].equalsIgnoreCase("cmd") || args[0].equalsIgnoreCase("c")) {
                sender.sendMessage(cManager.helpPage("commands", 1));
            } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("r")) {
                reloadConfig();
                this.config = getConfig();
                sender.sendMessage(cManager.info(0, null));
            } else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver") || args[0].equalsIgnoreCase("v")) {
                sender.sendMessage(cManager.info(null, "Zutils is in version Alpha v0.1"));
            } else {
                sender.sendMessage(cManager.error(0));
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("zchat")) {
            if (!commandIsEnabled("zchat") && sender instanceof Player) {
                sender.sendMessage(cManager.error(9));
                return true;
            }
            if (!sender.hasPermission("zutils.*") && !sender.isOp()) {
                if (!sender.hasPermission("zutils.zchat")) {
                    sender.sendMessage(cManager.error(6));
                    return true;
                }
            }
            sender.sendMessage(cManager.error(0));
            return true;
        } else if (cmd.getName().equalsIgnoreCase("regclick")) {
            if (!commandIsEnabled("regclick") && sender instanceof Player) {
                sender.sendMessage(cManager.error(9));
                return true;
            }
            if (!sender.hasPermission("zutils.*") && !sender.isOp()) {
                if (!sender.hasPermission("zutils.regclick.*")) {
                    if (!sender.hasPermission("zutils.regclick.add")) {
                        sender.sendMessage(cManager.error(6));
                        return true;
                    }
                    if (!sender.hasPermission("zutils.regclick.remove")) {
                        sender.sendMessage(cManager.error(6));
                        return true;
                    }
                    if (!sender.hasPermission("zutils.regclick.info")) {
                        sender.sendMessage(cManager.error(6));
                        return true;
                    }
                    if (!sender.hasPermission("zutils.regclick.help")) {
                        sender.sendMessage(cManager.error(6));
                        return true;
                    }
                }
            }
            rCommand.setCommandSender(sender);
            if (args.length < 1 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
                if (!sender.hasPermission("zutils.*") && !sender.isOp()) {
                    if (!sender.hasPermission("zutils.regclick.*")) {
                        if (!sender.hasPermission("zutils.regclick.help")) {
                            sender.sendMessage(cManager.error(6));
                            return true;
                        }
                    }
                }
                sender.sendMessage(cManager.helpPage("regclick", 1));
                return true;
            } else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {
                if (!sender.hasPermission("zutils.*") && !sender.isOp()) {
                    if (!sender.hasPermission("zutils.regclick.*")) {
                        if (!sender.hasPermission("zutils.regclick.add")) {
                            sender.sendMessage(cManager.error(6));
                            return true;
                        }
                    }
                }
                this.rCommand = new RegclickCommand(this);
                rCommand.init(1);
                return true;
            } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("d")) {
                if (!sender.hasPermission("zutils.*") && !sender.isOp()) {
                    if (!sender.hasPermission("zutils.regclick.*")) {
                        if (!sender.hasPermission("zutils.regclick.remove")) {
                            sender.sendMessage(cManager.error(6));
                            return true;
                        }
                    }
                }
                this.rCommand = new RegclickCommand(this);
                rCommand.init(2);
                return true;
            } else if (args[0].equalsIgnoreCase("information") || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("i")) {
                if (!sender.hasPermission("zutils.*") && !sender.isOp()) {
                    if (!sender.hasPermission("zutils.regclick.*")) {
                        if (!sender.hasPermission("zutils.regclick.info")) {
                            sender.sendMessage(cManager.error(6));
                            return true;
                        }
                    }
                }
                this.rCommand = new RegclickCommand(this);
                rCommand.init(3);
                return true;
            } else if (args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("cancel") || args[0].equalsIgnoreCase("end")) {
                if (!sender.hasPermission("zutils.*") && !sender.isOp()) {
                    if (!sender.hasPermission("zutils.regclick.*")) {
                        if (!sender.hasPermission("zutils.regclick.stop")) {
                            rCommand.clearValues();
                            sender.sendMessage(cManager.msg("Event creation canceled."));
                            return true;
                        }
                    }
                }
                this.rCommand = new RegclickCommand(this);
                rCommand.init(3);
                return true;
            }
            sender.sendMessage(cManager.error(5));
            return true;
        }
        return false;
    }

    public boolean commandIsEnabled(String cmd) {
        List<String> enabledCommands = config.getStringList("Enabled commands");
        for (String s : enabledCommands) {
            if (s.equalsIgnoreCase(cmd)) {
                return true;
            }
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
        List<String> enabledCommands = Arrays.asList("zutils", "help", "zchat", "regclick", "cmsg");
        config.addDefault("Enabled commands", enabledCommands);
        List<String> zpsi = Arrays.asList("Zpsi smells", "bad");
        config.addDefault("Custom messages.zpsi", zpsi);
        config.options().copyDefaults(true);
        saveConfig();
    }
}

