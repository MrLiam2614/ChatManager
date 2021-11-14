package me.mrliam2614;

import me.mrliam2614.commands.mainCMD;
import me.mrliam2614.config.ConfigVariable;
import me.mrliam2614.events.MessageSender;
import me.mrliam2614.events.commandSend;
import me.mrliam2614.config.FConfig;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class chatManager extends JavaPlugin implements Listener {
    public FacilitisAPI facilitisAPI;

    ConsoleCommandSender console = getServer().getConsoleSender();

    public boolean chatMuted = false;

    private int mysqlReturn = 0;
    //public String Ver = "updating";

    public FConfig MConfig;
    public String lang;
    public ArrayList<String> socialspyList = new ArrayList<>();
    public ConfigVariable configVariable;

    //MySql
    public String MySqlHost, MySqlPort, MySqlDatabase, MySqlUsername, MySqlPassword, MySqlTable;

    //OnEnable
    @Override
    public void onEnable() {
        facilitisAPI = FacilitisAPI.getInstance();
        facilitisAPI.messages.EnableMessage(this);
        this.saveDefaultConfig();

        lang = this.getConfig().getString("lang");
        MConfig = new FConfig(this, "message_" + lang + ".yml");

        this.getServer().getPluginManager().registerEvents(this, this);
        reloadConfig();
        configVariable = new ConfigVariable(this);

        //MySql
        String MySqlTableColumns = "(`Prefix` text NULL,`Group` text NULL,`Suffix` text NULL)";
        String MySqlDefaultData = "(`Prefix`, `Group`, `Suffix`) VALUES ('&7[&6Default&7]', 'default', '&7[&6Suffix&7]')";

        MySqlHost = ConfigVariable.MySqlHost;
        MySqlPort = ConfigVariable.MySqlPort;
        MySqlDatabase = ConfigVariable.MySqlDatabase;
        MySqlUsername = ConfigVariable.MySqlUsername;
        MySqlPassword = ConfigVariable.MySqlPassword;
        MySqlTable = ConfigVariable.MySqlTablePrefix + "chatmanager";
        boolean MySqlEnable = ConfigVariable.MySqlEnable;

        if (MySqlEnable) {
            mysqlReturn = facilitisAPI.MySql.MySqlConnect(this, MySqlUsername, MySqlPassword, MySqlHost, MySqlPort, MySqlDatabase, MySqlTable, MySqlTableColumns, MySqlDefaultData);
            if (mysqlReturn == 1)
                facilitisAPI.console.sendMessage(this, "&aConnected to the mysql Succesfully!", "info");
            if (mysqlReturn == 2)
                facilitisAPI.console.sendMessage(this, "&aMySql table not found... Table created Succesfully!", "info");
            if (mysqlReturn == 0)
                facilitisAPI.console.sendMessage(this, "&cThere was a problem during the connection, please check your login data!", "error");
        }

        //EndOfMySql


        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new MessageSender(this), this);
        pm.registerEvents(new commandSend(this), this);

        getCommand("mutechat").setExecutor(new mainCMD(this));
        getCommand("chatmanager").setExecutor(new mainCMD(this));
        getCommand("socialspy").setExecutor(new mainCMD(this));

        facilitisAPI.vault.Start();
    }

    //OnDisable
    @Override
    public void onDisable() {
        facilitisAPI.messages.DisableMessage(this);
    }

    public String color(String message) {
        return facilitisAPI.strUtils.colored(message);
    }

    public String getPrefix(Player p) {
        String prefix = "";
        String groups[] = getGroup(p);
        for (String group : groups) {
            if (prefix == "" | prefix == null) {
                if (ConfigVariable.MySqlEnable) {
                    if (facilitisAPI.MySql.MySqlGet(this, MySqlTable, "Group", group, "Group") == "") {
                        group = "default";
                    }
                    prefix = facilitisAPI.MySql.MySqlGet(this, MySqlTable, "Group", group, "Prefix");
                } else {
                    if (this.getConfig().getString("groups." + group) == null) {
                        group = "default";
                    }
                    prefix = this.getConfig().getString("groups." + group + ".prefix");
                    if (prefix == null)
                        prefix = "";
                    if (prefix.equalsIgnoreCase("null") | prefix.equalsIgnoreCase("none"))
                        prefix = "";
                }
            }
        }
        return prefix;
    }

    public String getSuffix(Player p) {
        String suffix = "";
        String groups[] = getGroup(p);
        for (String group : groups) {
            if (suffix == "" | suffix == null) {
                if (ConfigVariable.MySqlEnable) {
                    if (facilitisAPI.MySql.MySqlGet(this, MySqlTable, "Group", group, "Group") == "") {
                        group = "default";
                    }
                    suffix = facilitisAPI.MySql.MySqlGet(this, MySqlTable, "Group", group, "Suffix");
                } else {
                    if (this.getConfig().getString("groups." + group) == null) {
                        group = "default";
                    }
                    suffix = this.getConfig().getString("groups." + group + ".suffix");
                }
                if (suffix == null)
                    suffix = "";
                if (suffix.equalsIgnoreCase("null") | suffix.equalsIgnoreCase("none"))
                    suffix = "";
            }
        }
        return suffix;
    }

    public String[] getGroup(Player p) {
        String[] group;
        group = facilitisAPI.vault.getPermissions().getPlayerGroups(p);
        return group;
    }
}