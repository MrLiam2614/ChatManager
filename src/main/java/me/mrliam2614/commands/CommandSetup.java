package me.mrliam2614.commands;

import me.mrliam2614.ChatManager;
import me.mrliam2614.config.ConfigVariable;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class CommandSetup implements Listener {
    public ChatManager plugin;

    public CommandSetup(ChatManager plugin) {
        this.plugin = plugin;
    }

    public void prefix(CommandSender sender, String[] args) {
        StringBuilder prefixValue = new StringBuilder(args[3]);
        for (int arg = 4; arg < args.length; arg++) {
            prefixValue.append(" ").append(args[arg]);
        }
        String prefixString = prefixValue.toString();
        if (ConfigVariable.MySqlEnable) {
            plugin.facilitisAPI.MySql.MySqlInsert(plugin, plugin.MySqlTable, "Group", args[2], "Prefix", prefixString);
            sender.sendMessage(ConfigVariable.prefixUpdated.replace("{GROUP}", args[2]).replace("{PREFIX}", plugin.color(prefixString)));
        } else {
            this.plugin.getConfig().set("groups." + args[2] + ".prefix", args[3]);
            plugin.saveConfig();
            plugin.reloadConfig();
            sender.sendMessage(ConfigVariable.prefixUpdated.replace("{GROUP}", args[2]).replace("{PREFIX}", plugin.color(prefixString)));
        }
    }

    public void suffix(CommandSender sender, String[] args) {
        StringBuilder suffixValue = new StringBuilder(args[3]);
        for (int arg = 4; arg < args.length; arg++) {
            suffixValue.append(" ").append(args[arg]);
        }
        String suffixString = suffixValue.toString();
        if (ConfigVariable.MySqlEnable) {
            plugin.facilitisAPI.MySql.MySqlInsert(plugin, plugin.MySqlTable, "Group", args[2], "Suffix", suffixString);
            sender.sendMessage(ConfigVariable.suffixUpdated.replace("{GROUP}", args[2]).replace("{SUFFIX}", plugin.color(suffixString)));
        } else {
            this.plugin.getConfig().set("groups." + args[2] + ".suffix", args[3]);
            plugin.saveConfig();
            plugin.reloadConfig();
            sender.sendMessage(ConfigVariable.suffixUpdated.replace("{GROUP}", args[2]).replace("{SUFFIX}", plugin.color(suffixString)));
        }
    }

    public void color(CommandSender sender, String[] args) {
        String group = args[2];
        String colorValue = args[3];
        if (ConfigVariable.MySqlEnable) {
            plugin.facilitisAPI.MySql.MySqlInsert(plugin, plugin.MySqlTable, "Group", group, "ChatColor", colorValue);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', colorValue+"This is the new default color"));
        } else {
            this.plugin.getConfig().set("groups." + group + ".suffix", colorValue);
            plugin.saveConfig();
            plugin.reloadConfig();
        }
    }

}