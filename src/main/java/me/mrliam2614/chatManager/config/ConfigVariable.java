package me.mrliam2614.chatManager.config;

import me.mrliam2614.chatManager.chatManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigVariable {
    public static String lang, reload, NoPermMSG, messageFormat, chatUnmuted, chatMuted, prefixUpdated, suffixUpdated, consoleError, socialSpy,
            commandAlert, cancelledCommand, sspyDisabled, sspyEnabled, MySqlHost, MySqlPort, MySqlDatabase, MySqlTablePrefix, MySqlUsername, MySqlPassword;
    public static boolean autoUpd, cmdAlertEnabled, MySqlEnable;

    public static chatManager plugin;
    public static List<String> cmdAlert = new ArrayList<String>();

    public ConfigVariable(chatManager plugin) {
        plugin.reloadConfig();
        FileConfiguration mConfig = plugin.MConfig;

        messageFormat = plugin.getConfig().getString("chatDisplay");
        cmdAlert = plugin.getConfig().getStringList("commandAlert.commands");

        //LANG
        lang = plugin.getConfig().getString("lang");

        //MESSAGES
        NoPermMSG = plugin.cc(mConfig.getString("message.NoPerm"));
        consoleError = plugin.cc(mConfig.getString("message.consoleError"));
        reload = plugin.cc(mConfig.getString("message.reload"));
        chatUnmuted = plugin.cc(mConfig.getString("message.chatUnmuted"));
        chatMuted = plugin.cc(mConfig.getString("message.chatMuted"));
        prefixUpdated = plugin.cc(mConfig.getString("message.prefixUpdate"));
        suffixUpdated = plugin.cc(mConfig.getString("message.suffixUpdate"));
        socialSpy = plugin.cc(mConfig.getString("message.socialSpy"));
        sspyDisabled = plugin.cc(mConfig.getString("message.socialSpyEnabled"));
        sspyEnabled = plugin.cc(mConfig.getString("message.socialSpyDisabled"));
        commandAlert = plugin.cc(mConfig.getString("message.commandAlert"));
        cancelledCommand = plugin.cc(mConfig.getString("message.playerAlert"));

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