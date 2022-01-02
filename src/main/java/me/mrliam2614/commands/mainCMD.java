package me.mrliam2614.commands;

import me.mrliam2614.ChatManager;
import me.mrliam2614.config.ConfigVariable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class mainCMD implements CommandExecutor {
    public ChatManager plugin;
    private CommandMuteChat muteCMD;
    private CommandHelp helpCMD;
    private Socialspy socialspy;
    private CommandSetup setupCMD;

    public mainCMD(ChatManager plugin) {
        this.plugin = plugin;

        muteCMD = new CommandMuteChat(plugin);
        helpCMD = new CommandHelp(plugin);
        setupCMD = new CommandSetup(plugin);
        socialspy = new Socialspy(plugin);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mutechat")) {
            if (!sender.hasPermission("chatmanager.mutechat")) {
                sender.sendMessage(ConfigVariable.NoPermMSG);
            } else {
                muteCMD.mute(sender);
            }
        }
        if (command.getName().equalsIgnoreCase("socialspy")) {
            if (!sender.hasPermission("chatmanager.socialspy")) {
                sender.sendMessage(ConfigVariable.NoPermMSG);
            } else {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    socialspy.spy(player);
                } else {
                    sender.sendMessage(ConfigVariable.NoPermMSG);
                }
            }
        }
        if (command.getName().equalsIgnoreCase("chatmanager")) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help") || args.length > 4) {
                if (!sender.hasPermission("chatmanager.help")) {
                    sender.sendMessage(ConfigVariable.NoPermMSG);
                } else {
                    helpCMD.help(sender);
                }
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("chatmanager.reload")) {
                    sender.sendMessage(ConfigVariable.NoPermMSG);
                } else {
                    plugin.reloadConfig();
                    plugin.update();
                    sender.sendMessage(ConfigVariable.reload);
                }
            } else if (args[0].equalsIgnoreCase("socialspy")) {
                if (!sender.hasPermission("chatmanager.setup")) {
                    sender.sendMessage(ConfigVariable.NoPermMSG);
                } else {
                    if (args.length == 1) {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;
                            socialspy.spy(player);
                        } else {
                            sender.sendMessage(ConfigVariable.NoPermMSG);
                        }
                    } else {
                        helpCMD.help(sender);
                    }
                }
            } else if (args[0].equalsIgnoreCase("setup")) {
                if (!sender.hasPermission("chatmanager.setup")) {
                    sender.sendMessage(ConfigVariable.NoPermMSG);
                } else {
                    if (args.length == 4) {
                        if (args[1].equalsIgnoreCase("prefix")) {
                            setupCMD.prefix(sender, args);
                        } else if (args[1].equalsIgnoreCase("suffix")) {
                            setupCMD.suffix(sender, args);
                        }
                    } else {
                        helpCMD.help(sender);
                    }
                }
            } else {
                helpCMD.help(sender);
            }
        }
        return false;
    }
}