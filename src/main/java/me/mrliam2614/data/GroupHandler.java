package me.mrliam2614.data;

import me.mrliam2614.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupHandler {
    private final ChatManager plugin;
    private List<Group> groupList;

    public GroupHandler(ChatManager plugin) {
        groupList = new ArrayList<>();
        this.plugin = plugin;

        fetchData();
    }

    public void update() {
        groupList.clear();
        fetchData();
    }

    private void fetchData() {
        if(plugin.MySqlEnable) {
            ResultSet results = plugin.facilitisAPI.MySql.MySqlGetAll(plugin, plugin.MySqlTable);
            while(true){
                try {
                    if (!results.next()) break;
                    String name = results.getString("Group");
                    String suffix = results.getString("Suffix");
                    String prefix = results.getString("Prefix");
                    Group group = new Group(prefix, suffix, name);
                    groupList.add(group);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                results.getStatement().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            FileConfiguration fileConfiguration = plugin.getConfig();
            for(String group : plugin.getConfig().getConfigurationSection("groups").getKeys(false)) {
                String groupPath = "groups." + group;

                String prefix = plugin.getConfig().getString(groupPath + ".prefix");
                String suffix = plugin.getConfig().getString(groupPath + ".suffix");

                Group toAdd = new Group(prefix, suffix, group);
                groupList.add(toAdd);
            }
        }
    }

    public Group getGroup(String groupName){
        return groupList.stream().filter(g -> g.getGroupName().equalsIgnoreCase(groupName)).findAny().orElse(null);
    }

    public List<Group> getGroupList(){
        return groupList;
    }
}
