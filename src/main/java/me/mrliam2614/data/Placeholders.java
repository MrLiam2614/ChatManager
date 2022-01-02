package me.mrliam2614.data;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mrliam2614.ChatManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Placeholders {
    private final ChatManager plugin;

    public Placeholders(ChatManager plugin){
        this.plugin = plugin;
    }

    private StringBuilder getGroupValues(List<String> groups, String type){
        String moment = "";
        StringBuilder request = new StringBuilder();
        for (String group : groups) {
            if (request.length() != 0 && group.equalsIgnoreCase(plugin.getConfig().getString("defaultGroup"))) {
                continue;
            }
            if (request.length() != 0 && plugin.getConfig().getBoolean("singlePrefix")) {
                break;
            }
            if (plugin.groupHandler.getGroup(group) == null) {
                group = "default";
            }
            if(type.equalsIgnoreCase("prefix")){
                moment = plugin.groupHandler.getGroup(group).getPrefix();
            }else if(type.equalsIgnoreCase("suffix")){
                moment = plugin.groupHandler.getGroup(group).getSuffix();
            }
            if (moment != null && !moment.equalsIgnoreCase("null") && !moment.equalsIgnoreCase("none")) {
                request.append(moment);
            }
        }
        return request;
    }



    public String getPrefix(Player p) {
        List<String> groups = getGroup(p);
        StringBuilder prefix = getGroupValues(groups, "prefix");
        return prefix.toString();
    }

    public String getSuffix(Player p) {
        List<String> groups = getGroup(p);
        StringBuilder suffix = getGroupValues(groups, "suffix");
        return suffix.toString();
    }

    public String getPrefix(String... groups){
        StringBuilder prefix = getGroupValues(Arrays.asList(groups), "prefix");
        return prefix.toString();
    }

    public String getSuffix(String... groups) {
        StringBuilder suffix = getGroupValues(Arrays.asList(groups), "suffix");
        return suffix.toString();
    }

    public List<String> getGroup(Player p) {
        List<String> group = new ArrayList<>(Arrays.asList(plugin.facilitisAPI.vault.getPermissions().getPlayerGroups(p)));
        if (group.contains(plugin.facilitisAPI.vault.getPermissions().getPrimaryGroup(p))) {
            group.remove(plugin.facilitisAPI.vault.getPermissions().getPrimaryGroup(p));
        }
        group.add(0, plugin.facilitisAPI.vault.getPermissions().getPrimaryGroup(p));
        return group;
    }

    public String passPlaceholders(String format, Player player, String msg){
        if(plugin.isPlaceholderAPI){
            System.out.println("placeholder OK");
            //Pass PAPI Placeholders
            format = PlaceholderAPI.setPlaceholders(player, format);
            System.out.println(format);
        }
        format = format.toLowerCase()
                .replace("{player}", player.getName())
                .replace("{displayname}", player.getDisplayName())
                .replace("{message}",msg.replaceAll("%", "%%"))
                .replace("{prefix}", plugin.placeholders.getPrefix(player))
                .replace("{suffix}",plugin.placeholders.getSuffix(player));
        return format;
    }
}
