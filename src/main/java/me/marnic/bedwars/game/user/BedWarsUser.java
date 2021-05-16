package me.marnic.bedwars.game.user;

import me.marnic.bedwars.core.MiniGameUser;
import me.marnic.bedwars.game.objects.BedWarsTeam;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsUser extends MiniGameUser {

    private BedWarsTeam team;
    private boolean isAlive = true;

    public BedWarsUser(UUID uuid) {
        super(uuid);
    }

    public void setTeam(BedWarsTeam team) {
        this.team = team;
    }

    public BedWarsTeam getTeam() {
        return team;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void kill() {
        if(!team.hasBed()) {
            team.removeUser(this);
            isAlive = false;
        }
    }

    public void respawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(team.teamSpawnLocation.getLoc(e.getPlayer().getWorld()));
        if(isAlive) {
            getPlayer().setGameMode(GameMode.SURVIVAL);
        } else {
            getPlayer().setGameMode(GameMode.SPECTATOR);
        }
    }
}
