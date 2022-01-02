package me.mrliam2614.commands;

import me.mrliam2614.ChatManager;
import me.mrliam2614.config.ConfigVariable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class CommandMuteChat implements Listener {
    public ChatManager plugin;

    public CommandMuteChat(ChatManager plugin) {
        this.plugin = plugin;
    }

    public void mute(CommandSender sender) {
        if (plugin.chatMuted) {
            plugin.chatMuted = false;
            Bukkit.getServer().broadcastMessage(ConfigVariable.chatUnmuted);
        } else if (!plugin.chatMuted) {
            plugin.chatMuted = true;
            Bukkit.getServer().broadcastMessage(ConfigVariable.chatMuted);
        }
    }

}