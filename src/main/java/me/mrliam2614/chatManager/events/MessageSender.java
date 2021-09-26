package me.mrliam2614.chatManager.events;

import me.mrliam2614.chatManager.chatManager;
import me.mrliam2614.chatManager.config.ConfigVariable;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.ServicePriority;


public class MessageSender implements Listener {
    public chatManager plugin;
    private Chat cprovider;

    public MessageSender(chatManager plugin) {
        this.plugin = plugin;
        Bukkit.getServicesManager().register(Chat.class, this.cprovider, this.plugin, ServicePriority.Highest);
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

        e.setFormat(plugin.color(ConfigVariable.messageFormat.replace("{GROUP}", plugin.getGroup(p)).replace("{PLAYER}", p.getName()).replace("{PREFIX}", plugin.getPrefix(p)).replace("{SUFFIX}", plugin.getSuffix(p)).replace("{MESSAGE}", msg)));
        if (plugin.chatMuted) {
            if (!p.hasPermission("chatmanager.mutebypass")) {
                e.setCancelled(true);
            }
        }
    }
}