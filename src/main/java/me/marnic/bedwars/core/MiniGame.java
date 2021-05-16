package me.marnic.bedwars.core;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class MiniGame {

    protected MiniGameCore core;
    protected GameState state;
    protected JavaPlugin plugin;

    public MiniGame(MiniGameCore core) {
        this.core = core;
        this.state = GameState.STARTING;
        this.plugin = core.getPlugin();
    }

    public void teleportPlayers() {

    }

    public void prepare() throws Exception{

    }

    public void startGame() {

    }

    public void stopGame() {

    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender,String cmd, String[] args) {
        return false;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
