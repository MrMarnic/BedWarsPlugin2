package me.marnic.bedwars.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class MiniGameUser {
    private UUID uuid;

    public MiniGameUser(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
