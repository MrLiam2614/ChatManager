package me.mrliam2614.events;

import me.mrliam2614.chatManager;
import me.mrliam2614.config.ConfigVariable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class MessageSender implements Listener {
    public chatManager plugin;

    public MessageSender(chatManager plugin) {
        this.plugin = plugin;
    }

    private String msg;

    @SuppressWarnings("static-access")
    @EventHandler
    public void chatMessage(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        msg = e.getMessage();

        if (p.hasPermission("chatManager.colors")) {
            msg = plugin.color(msg);
        }

        e.setFormat(plugin.color(ConfigVariable.messageFormat.replace("{PLAYER}", p.getName()).replace("{PREFIX}", plugin.getPrefix(p)).replace("{SUFFIX}", plugin.getSuffix(p)).replace("{MESSAGE}", msg)));
        if (plugin.chatMuted) {
            if (!p.hasPermission("chatmanager.mutebypass")) {
                e.setCancelled(true);
            }
        }
    }
}