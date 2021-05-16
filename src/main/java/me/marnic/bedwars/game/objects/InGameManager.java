package me.marnic.bedwars.game.objects;

import me.marnic.bedwars.core.MiniGameCore;
import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.game.BedWarsMiniGame;
import me.marnic.bedwars.game.inventory.BedWarsInventories;
import me.marnic.bedwars.game.user.BedWarsUser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Copyright (c) 14.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class InGameManager {
    private MiniGameCore core;
    private BedWarsMiniGame miniGame;

    public InGameManager(MiniGameCore core, BedWarsMiniGame miniGame) {
        this.core = core;
        this.miniGame = miniGame;
    }

    public void handlePlayerLeave(Player player) {
        BedWarsUser user = (BedWarsUser) core.getUserManager().getUser(player.getUniqueId());
        user.getTeam().removeUser(user);

        checkTeams();
    }

    public void checkBedBreak(BlockBreakEvent e) {

        BedWarsUser user = (BedWarsUser) core.getUserManager().getUser(e.getPlayer().getUniqueId());

        Location loc = e.getBlock().getLocation();

        for(BedWarsTeam team : miniGame.getConfig().map.teams.values()) {
            if(team.isBed(loc)) {
                if(team.teamMembers.contains(user)) {
                    e.setCancelled(true);
                    LogUtil.playerInfo(e.getPlayer(),"You can't break your own bed!");
                } else {
                    team.setHasBed(false);
                }
            }
        }
    }

    public void checkTeams() {
        int size = 0;

        BedWarsTeam winner = null;

        for(BedWarsTeam team : miniGame.getConfig().map.teams.values()) {
            if(team.isAlive()) {
                winner = team;
                size++;
            }
        }

        if(size == 1) {
            winner.celebrateWin();
            miniGame.stopGame();
        }
    }

    public void handleDeath(Player entity) {
        BedWarsUser user = (BedWarsUser) core.getUserManager().getUser(entity.getUniqueId());

        user.kill();
        checkTeams();
    }

    public void handleRespawn(PlayerRespawnEvent e) {
        BedWarsUser user = (BedWarsUser) core.getUserManager().getUser(e.getPlayer().getUniqueId());

        user.respawn(e);
    }

    public void handleVillager(PlayerInteractEntityEvent e) {
        e.getPlayer().openInventory(BedWarsInventories.MAIN.getInventory());
    }
}
