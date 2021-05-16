package me.marnic.bedwars.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */


public class ProtectionManager implements Listener {

    private boolean breakBlock = true;
    private boolean allowMove = true;
    private boolean allowDamage = true;
    private boolean allowPlayerInteract = true;
    private boolean allowJoin = true;
    private boolean placeBlock = true;
    private String kickMessage;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(!breakBlock) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if(!allowMove) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(!allowDamage) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!allowPlayerInteract) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(!allowJoin) {
            e.getPlayer().kickPlayer(kickMessage);
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        if(!placeBlock) {
            e.setCancelled(true);
        }
    }

    public ProtectionManager setBreakBlock(boolean breakBlock) {
        this.breakBlock = breakBlock;
        return this;
    }

    public ProtectionManager setAllowMove(boolean allowMove) {
        this.allowMove = allowMove;
        return this;
    }

    public ProtectionManager setAllowDamage(boolean allowDamage) {
        this.allowDamage = allowDamage;
        return this;
    }

    public ProtectionManager setAllowPlayerInteract(boolean allowPlayerInteract) {
        this.allowPlayerInteract = allowPlayerInteract;
        return this;
    }

    public ProtectionManager setAllowJoin(boolean allowJoin, String kickMessage) {
        this.allowJoin = allowJoin;
        this.kickMessage = kickMessage;
        return this;
    }

    public ProtectionManager setPlaceBlock(boolean placeBlock) {
        this.placeBlock = placeBlock;
        return this;
    }

    public void reset() {
        this.allowJoin = true;
        this.allowPlayerInteract = true;
        this.allowDamage = true;
        this.breakBlock = true;
        this.allowMove = true;
        this.placeBlock = true;
    }
}
