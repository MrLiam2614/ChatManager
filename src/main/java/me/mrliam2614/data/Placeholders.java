package me.mrliam2614.data;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mrliam2614.ChatManager;
import org.bukkit.entity.Player;

import javax.crypto.spec.PSource;
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
            }else if(type.equalsIgnoreCase("color")){
                moment = plugin.groupHandler.getGroup(group).getChatColor();
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

    public String getPrefix(List<String> groups){
        StringBuilder prefix = getGroupValues(groups, "prefix");
        return prefix.toString();
    }

    public String getSuffix(List<String> groups) {
        StringBuilder suffix = getGroupValues(groups, "suffix");
        return suffix.toString();
    }

    public String getColor(Player p) {
        List<String> groups = getGroup(p);
        StringBuilder color = getGroupValues(groups, "color");
        return color.toString();
    }
    public String getColor(List<String> groups) {
        StringBuilder color = getGroupValues(groups, "color");
        return color.toString();
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
            //Pass PAPI Placeholders
            format = PlaceholderAPI.setPlaceholders(player, format);
        }
        String m1 = msg.substring(0, 1).toUpperCase();
        String message = m1 + msg.substring(1);

        format = repalce(format, "{player}", player.getName());
        format = repalce(format, "{displayname}", player.getDisplayName());
        format = repalce(format, "{prefix}", plugin.placeholders.getPrefix(player));
        format = repalce(format, "{suffix}", plugin.placeholders.getSuffix(player));
        format = repalce(format, "{message}", plugin.placeholders.getColor(player) +
                message.replaceAll("%", "%%"));

        return format;
    }

    private String repalce(String src, String target, String replacement) {
        StringBuilder stringBuilder = new StringBuilder(src);
        StringBuilder stringBuilderLower = new StringBuilder(src.toLowerCase());
        String searchString = target.toLowerCase();

        int index = 0;
        while ((index = stringBuilderLower.indexOf(searchString, index)) != -1) {
            stringBuilder.replace(index, index + searchString.length(), replacement);
            stringBuilderLower.replace(index, index + searchString.length(), replacement);
            index += replacement.length();
        }

        stringBuilderLower.setLength(0);
        stringBuilderLower.trimToSize();
        stringBuilderLower = null;

        return stringBuilder.toString();
    }

}
