package me.marnic.bedwars.core;

import me.marnic.bedwars.main.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class MiniGameCore {

    private final ProtectionManager protectionManager;
    private UserManager userManager;
    private JavaPlugin plugin;

    public MiniGameCore(JavaPlugin plugin) {
        this.protectionManager = new ProtectionManager();
        this.userManager = new UserManager();
        this.plugin = plugin;
    }

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(protectionManager, BedWars.INSTANCE);
    }

    public void onDisable() {

    }

    public void enableConfig() {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public ProtectionManager getProtectionManager() {
        return protectionManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
