package me.marnic.bedwars.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class UserManager {
    private HashMap<UUID,MiniGameUser> users;

    public UserManager() {
        this.users = new HashMap<>();
    }

    public void addUser(MiniGameUser user) {
        this.users.put(user.getUuid(),user);
    }

    public void removeUser(MiniGameUser user) {
        this.users.remove(user.getUuid());
    }

    public MiniGameUser getUser(UUID uuid) {
        return this.users.get(uuid);
    }

    public HashMap<UUID, MiniGameUser> getUsers() {
        return users;
    }
}
