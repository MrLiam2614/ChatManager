package me.mrliam2614.commands;

import me.mrliam2614.ChatManager;
import me.mrliam2614.config.ConfigVariable;
import org.bukkit.entity.Player;

public class Socialspy {

    private ChatManager plugin;

    public Socialspy(ChatManager plugin) {
        this.plugin = plugin;
    }

    public void spy(Player player) {
        String pName = player.getName();
        if (plugin.socialspyList.contains(pName)) {
            plugin.socialspyList.remove(pName);
            player.sendMessage(plugin.color(ConfigVariable.sspyDisabled));
        } else {
            plugin.socialspyList.add(pName);
            player.sendMessage(plugin.color(ConfigVariable.sspyEnabled));
        }
    }

}