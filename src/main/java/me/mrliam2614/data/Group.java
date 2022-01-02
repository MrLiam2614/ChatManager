package me.mrliam2614.data;

public class Group {
    String prefix, suffix, groupName;
    public Group(String prefix, String suffix, String groupName){
        this.groupName = groupName;
        this.prefix = prefix;
        this.suffix = suffix;
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
}
