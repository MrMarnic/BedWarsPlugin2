package me.marnic.bedwars.game.objects;

import me.marnic.bedwars.game.BedWarsMiniGame;
import me.marnic.bedwars.main.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 16.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class SpawnerManager {
    private List<BedWarsSpawner> spawners;
    private BukkitTask spawnerTask;
    private BedWarsMiniGame miniGame;
    private World world;

    public SpawnerManager(BedWarsMiniGame miniGame) {
        this.spawners = new ArrayList<>();
        this.miniGame = miniGame;
    }

    public void start() {
        this.spawnerTask = Bukkit.getScheduler().runTaskTimer(BedWars.INSTANCE, new Runnable() {
            @Override
            public void run() {
                spawners.forEach((spawner -> {
                    spawner.handleSec(world);
                }));
            }
        },0,20);
    }

    public void stop() {
        if(spawnerTask != null) {
            spawnerTask.cancel();
        }
    }

    public BukkitTask getSpawnerTask() {
        return spawnerTask;
    }

    public List<BedWarsSpawner> getSpawners() {
        return spawners;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void init(BedWarsMap map) {
        spawners.addAll(map.spawners);

        for(BedWarsTeam team : map.teams.values()) {
            spawners.addAll(team.spawners);
        }
    }
}
