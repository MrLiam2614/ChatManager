package me.mrliam2614.config;

import me.mrliam2614.ChatManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigVariable {
    public static String lang, reload, NoPermMSG, messageFormat, chatUnmuted, chatMuted, prefixUpdated, suffixUpdated, consoleError, socialSpy,
            commandAlert, cancelledCommand, sspyDisabled, sspyEnabled, MySqlHost, MySqlPort, MySqlDatabase, MySqlTablePrefix, MySqlUsername, MySqlPassword;
    public static boolean autoUpd, cmdAlertEnabled, MySqlEnable;

    public static ChatManager plugin;
    public static List<String> cmdAlert = new ArrayList<String>();

    public ConfigVariable(ChatManager plugin) {
        plugin.reloadConfig();
        FileConfiguration mConfig = plugin.MConfig.getConfig();

        messageFormat = plugin.getConfig().getString("chatDisplay");
        cmdAlert = plugin.getConfig().getStringList("commandAlert.commands");

        //LANG
        lang = plugin.getConfig().getString("lang");

        //MESSAGES
        NoPermMSG = plugin.color(mConfig.getString("message.NoPerm"));
        consoleError = plugin.color(mConfig.getString("message.consoleError"));
        reload = plugin.color(mConfig.getString("message.reload"));
        chatUnmuted = plugin.color(mConfig.getString("message.chatUnmuted"));
        chatMuted = plugin.color(mConfig.getString("message.chatMuted"));
        prefixUpdated = plugin.color(mConfig.getString("message.prefixUpdate"));
        suffixUpdated = plugin.color(mConfig.getString("message.suffixUpdate"));
        socialSpy = plugin.color(mConfig.getString("message.socialSpy"));
        sspyDisabled = plugin.color(mConfig.getString("message.socialSpyDisabled"));
        sspyEnabled = plugin.color(mConfig.getString("message.socialSpyEnabled"));
        commandAlert = plugin.color(mConfig.getString("message.commandAlert"));
        cancelledCommand = plugin.color(mConfig.getString("message.playerAlert"));

        //BOOLEAN
        autoUpd = plugin.getConfig().getBoolean("autodownload");
        cmdAlertEnabled = plugin.getConfig().getBoolean("commandAlert.enable");

        //MySql
        MySqlEnable = plugin.getConfig().getBoolean("mySql.enable");
        MySqlHost = plugin.getConfig().getString("mySql.host");
        MySqlPort = plugin.getConfig().getString("mySql.port");
        MySqlDatabase = plugin.getConfig().getString("mySql.database");
        MySqlTablePrefix = plugin.getConfig().getString("mySql.tablePrefix");
        MySqlUsername = plugin.getConfig().getString("mySql.username");
        MySqlPassword = plugin.getConfig().getString("mySql.password");


        return;
    }
}