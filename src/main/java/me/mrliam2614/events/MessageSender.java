package me.mrliam2614.events;

import me.mrliam2614.ChatManager;
import me.mrliam2614.config.ConfigVariable;
import me.mrliam2614.reflection.Sounds;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class MessageSender implements Listener {
    public ChatManager plugin;

    public MessageSender(ChatManager plugin) {
        this.plugin = plugin;
    }

    private String msg;

    @SuppressWarnings("static-access")
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
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

        String format = ConfigVariable.messageFormat;

        format = plugin.placeholders.passPlaceholders(format, p, "{message}");

        if (containPlayer(msg)) {
            msg = replacePlayer(format, msg, p);
        }

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

    private String replacePlayer(String format, String msg, Player sender) {
        String beforeMessage = format.substring(0, format.indexOf("{message}"));
        String lastColor = ChatColor.getLastColors(ChatColor.translateAlternateColorCodes('&', beforeMessage)).replace('&', 'x');

        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (msg.toLowerCase().contains(p.getName().toLowerCase()) && !p.getName().equalsIgnoreCase(sender.getName())) {
                msg = msg.replaceAll("(?i)" + p.getName(), "§c" + p.getName() + lastColor);
                sendAdvise(p);
            }
        }
        return msg;
    }

    private void sendAdvise(Player p) {
        Sounds.pingPlayer(p);
    }
}