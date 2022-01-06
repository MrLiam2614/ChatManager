package me.mrliam2614;

import me.mrliam2614.commands.mainCMD;
import me.mrliam2614.config.ConfigVariable;
import me.mrliam2614.config.FConfig;
import me.mrliam2614.data.GroupHandler;
import me.mrliam2614.data.Placeholders;
import me.mrliam2614.events.CommandSend;
import me.mrliam2614.events.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class ChatManager extends JavaPlugin implements Listener {
    private static ChatManager instance;
    public Placeholders placeholders;
    public FacilitisAPI facilitisAPI;

    ConsoleCommandSender console = getServer().getConsoleSender();

    public boolean chatMuted = false;

    private int mysqlReturn = 0;
    //public String Ver = "updating";

    public FConfig MConfig;
    public String lang;
    public ArrayList<String> socialspyList = new ArrayList<>();
    public ConfigVariable configVariable;
    public GroupHandler groupHandler;

    public boolean isPlaceholderAPI = false;

    //A boolean
    public boolean MySqlEnable;

    //MySql
    public String MySqlHost, MySqlPort, MySqlDatabase, MySqlUsername, MySqlPassword, MySqlTable;

    //OnEnable
    @Override
    public void onEnable() {
        instance = this;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            isPlaceholderAPI = true;
        }
        facilitisAPI = FacilitisAPI.getInstance();
        facilitisAPI.messages.EnableMessage(this);
        this.saveDefaultConfig();

        lang = this.getConfig().getString("lang");
        MConfig = new FConfig(this, "message_" + lang + ".yml");

        this.getServer().getPluginManager().registerEvents(this, this);
        reloadConfig();
        configVariable = new ConfigVariable(this);

        //MySql
        String MySqlTableColumns = "(`Group` text NULL,`ChatColor` text NULL,`Prefix` text NULL,`Suffix` text NULL)";
        String MySqlDefaultData = "(`Prefix`, `Group`, `Suffix`, `ChatColor`) VALUES ('&7[&6Default&7]', 'default', '&7[&6Suffix&7]', '&f')";

        MySqlHost = ConfigVariable.MySqlHost;
        MySqlPort = ConfigVariable.MySqlPort;
        MySqlDatabase = ConfigVariable.MySqlDatabase;
        MySqlUsername = ConfigVariable.MySqlUsername;
        MySqlPassword = ConfigVariable.MySqlPassword;
        MySqlTable = ConfigVariable.MySqlTablePrefix + "chatmanager";
        MySqlEnable = ConfigVariable.MySqlEnable;

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
        pm.registerEvents(new CommandSend(this), this);

        getCommand("mutechat").setExecutor(new mainCMD(this));
        getCommand("chatmanager").setExecutor(new mainCMD(this));
        getCommand("socialspy").setExecutor(new mainCMD(this));

        facilitisAPI.vault.Start();
        groupHandler = new GroupHandler(this);
        placeholders = new Placeholders(this);

        startUpdater();
    }


    public String color(String message) {
        return facilitisAPI.strUtils.colored(message);
    }

    //OnDisable
    @Override
    public void onDisable() {
        facilitisAPI.messages.DisableMessage(this);
    }

    private void startUpdater() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::update, 20 * 60 * 3, 20 * 60 * 3);
    }

    public void update() {
        groupHandler.update();
    }

    public static ChatManager getInstance() {
        return instance;
    }

    public GroupHandler getGroupHandler() {
        return groupHandler;
    }
}