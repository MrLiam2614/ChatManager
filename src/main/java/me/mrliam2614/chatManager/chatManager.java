package me.mrliam2614.chatManager;

import me.mrliam2614.FacilitisAPI.FacilitisAPI;
import me.mrliam2614.chatManager.commands.mainCMD;
import me.mrliam2614.chatManager.config.ConfigVariable;
import me.mrliam2614.chatManager.events.MessageSender;
import me.mrliam2614.chatManager.events.commandSend;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class chatManager extends JavaPlugin
        implements Listener {
    public FacilitisAPI facilitisAPI = (FacilitisAPI) Bukkit.getServer().getPluginManager().getPlugin("FacilitisAPI");

    ConsoleCommandSender console = getServer().getConsoleSender();

    public boolean chatMuted = false;

    public Object consoleMessage;
    public String Ver = "2.0.0";
    public int pluginID = 77583;
    private int mysqlReturn = 0;
    //public String Ver = "updating";

    public FileConfiguration MConfig;
    public String lang;
    public ArrayList<String> socialspyList = new ArrayList<>();
    public ConfigVariable configVariable;

    //MySql
    public String MySqlHost, MySqlPort, MySqlDatabase, MySqlUsername, MySqlPassword, MySqlTable;

    //OnEnable
    @Override
    public void onEnable() {
        facilitisAPI.messages.EnableMessage(this);
        this.saveDefaultConfig();

        lang = this.getConfig().getString("lang");
        facilitisAPI.config.generateConfig(this, "message_" + lang + ".yml");
        MConfig = facilitisAPI.config.getConfig(this, "message_" + lang + ".yml");

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

    public String color(String message){
        return facilitisAPI.strUtils.colored(message);
    }

    public String getPrefix(Player p) {
        String group = getGroup(p);
        String prefix = "";
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
            if(prefix == null)
                prefix = "";
            if(prefix.equalsIgnoreCase("null") | prefix.equalsIgnoreCase("none"))
                prefix = "";
        }
        return prefix;
    }

    public String getSuffix(Player p) {
        String suffix = "";
        String group = getGroup(p);
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
        if(suffix == null)
            suffix = "";
        if(suffix.equalsIgnoreCase("null") | suffix.equalsIgnoreCase("none"))
            suffix = "";
        return suffix;
    }

    public String getGroup(Player p) {
        String group = "";
        group = facilitisAPI.vault.getPermissions().getPrimaryGroup(p.getWorld().getName(), p);
        return group;
    }
}