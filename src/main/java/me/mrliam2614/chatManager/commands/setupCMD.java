package me.mrliam2614.chatManager.commands;

import me.mrliam2614.chatManager.chatManager;
import me.mrliam2614.chatManager.config.ConfigVariable;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class setupCMD implements Listener {
    public chatManager plugin;

    public setupCMD(chatManager plugin) {
        this.plugin = plugin;
    }

    public void prefix(CommandSender sender, String[] args) {
        if (ConfigVariable.MySqlEnable) {
            plugin.facilitisAPI.MySql.MySqlInsert(plugin, plugin.MySqlTable, "Group", args[2], "Prefix", args[3]);
            sender.sendMessage(ConfigVariable.prefixUpdated.replace("{GROUP}", args[2]).replace("{PREFIX}", plugin.color(args[3])));
        } else {
            this.plugin.getConfig().set("groups." + args[2] + ".prefix", args[3]);
            plugin.saveConfig();
            plugin.reloadConfig();
            sender.sendMessage(ConfigVariable.prefixUpdated.replace("{GROUP}", args[2]).replace("{PREFIX}", plugin.color(args[3])));
        }
    }

    public void suffix(CommandSender sender, String[] args) {
        if (ConfigVariable.MySqlEnable) {
            plugin.facilitisAPI.MySql.MySqlInsert(plugin, plugin.MySqlTable, "Group", args[2], "Suffix", args[3]);
            sender.sendMessage(ConfigVariable.suffixUpdated.replace("{GROUP}", args[2]).replace("{SUFFIX}", plugin.color(args[3])));
        } else {
            this.plugin.getConfig().set("groups." + args[2] + ".suffix", args[3]);
            plugin.saveConfig();
            plugin.reloadConfig();
            sender.sendMessage(ConfigVariable.suffixUpdated.replace("{GROUP}", args[2]).replace("{SUFFIX}", plugin.color(args[3])));
        }
    }

}