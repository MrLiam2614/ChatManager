package me.mrliam2614.commands;

import me.mrliam2614.ChatManager;
import me.mrliam2614.config.ConfigVariable;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandGetExample {
    private final ChatManager plugin;

    public CommandGetExample(ChatManager plugin) {
        this.plugin = plugin;
    }

    public void sendExample(Player player, List<String> groupList) {
        if (groupList.size() == 0) {
            groupList.add("default");
        }
        String format = ConfigVariable.messageFormat;
        String msg = "This is a &6Test &aMessage&c!";

        format = format.toLowerCase()
                .replace("{player}", "TestUser")
                .replace("{displayname}", "TestUser-DisplayName")
                .replace("{message}", plugin.placeholders.getColor(groupList) + msg.replaceAll("%", "%%"))
                .replace("{prefix}", plugin.placeholders.getPrefix(groupList))
                .replace("{suffix}", plugin.placeholders.getSuffix(groupList));

        format = plugin.placeholders.passPlaceholders(format, player, msg);

        player.sendMessage(plugin.color(format));
    }
}
