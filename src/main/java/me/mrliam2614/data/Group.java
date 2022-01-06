package me.mrliam2614.data;

public class Group {
    String prefix, suffix, groupName, chatColor;
    public Group(String prefix, String suffix, String chatColor, String groupName){
        this.groupName = groupName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.chatColor = chatColor;
    }

    public String getGroupName() {
        return groupName;
    }
    public String getPrefix() {
        return prefix;
    }
    public String getSuffix() {
        return suffix;
    }
    public String getChatColor(){ return chatColor; }
}
