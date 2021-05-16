package me.marnic.bedwars.game.listener;

import me.marnic.bedwars.core.GameState;
import me.marnic.bedwars.game.BedWarsMiniGame;
import me.marnic.bedwars.game.user.BedWarsUser;
import org.bukkit.GameMode;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Copyright (c) 14.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class InGameListener implements Listener {

    private BedWarsMiniGame game;

    public InGameListener(BedWarsMiniGame game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if(game.getState() == GameState.INGAME) {
            game.handlePlayerLeave(e.getPlayer());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(game.getState() == GameState.INGAME) {
            if(e.getBlock().getBlockData() instanceof Bed) {
                game.getManager().checkBedBreak(e);
            } else if(!game.isBedWarsBlock(e.getBlock().getType())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(game.getState() == GameState.INGAME) {
            game.getManager().handleDeath(e.getEntity());
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if(game.getState() == GameState.INGAME) {
            game.getManager().handleRespawn(e);
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        if(game.getState() == GameState.INGAME) {
            e.setCancelled(true);
            if(e.getRightClicked().getType() == EntityType.VILLAGER) {
                game.getManager().handleVillager(e);
            }
        }
    }
}
