package me.marnic.bedwars.game.listener;

import me.marnic.bedwars.core.GameState;
import me.marnic.bedwars.core.MiniGameCore;
import me.marnic.bedwars.game.BedWarsMiniGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class LobbyListener implements Listener {

    private BedWarsMiniGame game;

    public LobbyListener(BedWarsMiniGame game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(game.getState() == GameState.LOBBY) {
            if(!game.isFull()) {
                e.getPlayer().teleport(game.getConfig().lobby.spawnLocation.getLoc(game.getConfig().lobby.getWorld()));
                game.handlePlayerJoin(e.getPlayer());
            } else {
                e.getPlayer().kickPlayer("Game is full!");
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if(game.getState() == GameState.LOBBY) {
            game.handlePlayerLeave(e.getPlayer());
        }
    }
}
