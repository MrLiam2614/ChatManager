package me.mrliam2614.chatManager.commands;

import me.mrliam2614.chatManager.chatManager;
import me.mrliam2614.chatManager.config.ConfigVariable;
import org.bukkit.entity.Player;

public class socialSpy {

    private chatManager plugin;

    public socialSpy(chatManager plugin) {
        this.plugin = plugin;
    }

    public void spy(Player player) {
        String pName = player.getName();
        System.out.println("Dio Cane " + pName);
        if (plugin.socialspyList.contains(pName)) {
            plugin.socialspyList.remove(pName);
            player.sendMessage(plugin.color(ConfigVariable.sspyDisabled));
        } else {
            plugin.socialspyList.add(pName);
            player.sendMessage(plugin.color(ConfigVariable.sspyEnabled));
        }
    }

}