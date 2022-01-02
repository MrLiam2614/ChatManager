package me.mrliam2614.events;


import me.mrliam2614.ChatManager;
import me.mrliam2614.config.ConfigVariable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandSend implements Listener {

    private ChatManager plugin;

    public CommandSend(ChatManager plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandSent(PlayerCommandPreprocessEvent e) {
        Player pl = e.getPlayer();
        String name = pl.getName();
        String command = e.getMessage();

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.hasPermission("chatmanager.socialspy")) {
                if (!pl.hasPermission("chatmanager.socialspy.bypass")) {
                    if (plugin.socialspyList.contains(p.getName())) {
                        String message = plugin.color(ConfigVariable.socialSpy.replace("{PLAYER}", name).replace("{COMMAND}", command));
                        p.sendMessage(message);
                    }
                }
            }
            if (p.hasPermission("chatmanager.alertcmd")) {
                if (ConfigVariable.cmdAlertEnabled) {
                    if (!pl.hasPermission("chatmanager.alertcmd.bypass")) {
                        if (ConfigVariable.cmdAlert.contains(command.split("/")[1].split(" ")[0].split(":")[0])) {
                            String message = plugin.color(ConfigVariable.commandAlert.replace("{PLAYER}", name).replace("{COMMAND}", command));
                            p.sendMessage(message);
                            String plMessage = plugin.color(ConfigVariable.cancelledCommand);
                            pl.sendMessage(plMessage);
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }

    }

}