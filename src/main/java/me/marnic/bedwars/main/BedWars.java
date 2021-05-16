package me.marnic.bedwars.main;

import me.marnic.bedwars.core.MiniGameCore;
import me.marnic.bedwars.game.BedWarsMiniGame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWars extends JavaPlugin {

    public static MiniGameCore MINI_GAME_CORE;
    public static BedWars INSTANCE;
    public BedWarsMiniGame miniGame;

    @Override
    public void onEnable() {
        INSTANCE = this;
        MINI_GAME_CORE = new MiniGameCore(this);
        MINI_GAME_CORE.onEnable();
        miniGame = new BedWarsMiniGame(MINI_GAME_CORE);
        miniGame.onEnable();
    }

    @Override
    public void onDisable() {
        MINI_GAME_CORE.onDisable();
        miniGame.onDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return miniGame.onCommand(sender,command.getName(),args);
    }
}
