package me.marnic.bedwars.core.util;

import me.marnic.bedwars.main.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class LogUtil {
    public static void playerInfo(Player p,String msg) {
        p.sendMessage("[" + BedWars.INSTANCE.getName() + "]" + msg);
    }
    public static void allPlayerInfo(String msg) {
        Bukkit.getOnlinePlayers().forEach((player)-> {
            player.sendMessage("[" + BedWars.INSTANCE.getName() + "]" + msg);
        });
    }
    public static void consoleWarn(String msg) {
        Bukkit.getLogger().warning("[" + BedWars.INSTANCE.getName() + "]" + msg);
    }

    public static void consoleInfo(String msg) {
        Bukkit.getLogger().info("[" + BedWars.INSTANCE.getName() + "]" + msg);
    }
}
