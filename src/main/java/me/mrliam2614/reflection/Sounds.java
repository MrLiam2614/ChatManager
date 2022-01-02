package me.mrliam2614.reflection;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {

    public static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    public static void pingPlayer(Player player){
        try {
            Sound outSound;

            if(getServerVersion().contains("1.8")){
                outSound = Sound.valueOf("ORB_PICKUP");
            }else{
                outSound = Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP");
            }

            player.playSound(player.getLocation(), outSound, 1F, 0.5F);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
