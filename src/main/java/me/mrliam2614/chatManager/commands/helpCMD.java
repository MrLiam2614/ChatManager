package me.mrliam2614.chatManager.commands;

import me.mrliam2614.chatManager.chatManager;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class helpCMD implements Listener {
    public chatManager plugin;

    public helpCMD(chatManager plugin) {
        this.plugin = plugin;
    }

    public void help(CommandSender sender) {
        sender.sendMessage("\n§7Chat Manager Help");
        sender.sendMessage("§7Alias for §a/chatmanager §7is §c/cm");
        sender.sendMessage("§a/chatmanager help §7- §6see commands list");
        sender.sendMessage("§a/chatmanager reload §7- §6reload plugin's config");
        sender.sendMessage("§a/chatmanager setup [prefix - suffix] [group] [prefix] §7- §6Setup your config file from the game!");
    }

}