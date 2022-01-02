package me.mrliam2614.events;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mrliam2614.ChatManager;
import me.mrliam2614.config.ConfigVariable;
import me.mrliam2614.reflection.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class MessageSender implements Listener {
    public ChatManager plugin;

    public MessageSender(ChatManager plugin) {
        this.plugin = plugin;
    }

    private String msg;

    @SuppressWarnings("static-access")
    @EventHandler
    public void chatMessage(AsyncPlayerChatEvent e) {
        msg = e.getMessage();
        Player p = e.getPlayer();
        if (plugin.chatMuted) {
            if (!p.hasPermission("chatmanager.mutebypass")) {
                e.setCancelled(true);
            }
        }

        if (p.hasPermission("chatManager.colors")) {
            msg = plugin.color(msg);
        }

        if (containPlayer(msg)) {
            msg = replacePlayer(msg);
            sendAdvise(msg);
        }
        String format = ConfigVariable.messageFormat;

        format = plugin.placeholders.passPlaceholders(format, p, msg);

        e.setFormat(plugin.color(format));
    }

    private boolean containPlayer(String msg) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (msg.toLowerCase().contains(p.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private String replacePlayer(String msg) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (msg.toLowerCase().contains(p.getName().toLowerCase())) {
                msg = msg.replaceAll("(?i)" + p.getName(), "&c" + p.getName() + plugin.getConfig().getString("chatColor"));
            }
        }
        return msg;
    }

    private void sendAdvise(String msg) {
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (msg.toLowerCase().contains(p.getName().toLowerCase())) {
                Sounds.pingPlayer(p);
            }
        }
    }
}