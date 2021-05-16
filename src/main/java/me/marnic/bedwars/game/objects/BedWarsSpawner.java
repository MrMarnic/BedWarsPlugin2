package me.marnic.bedwars.game.objects;

import me.marnic.bedwars.core.BasicLocation;
import me.marnic.bedwars.core.util.ObjectsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */

public class BedWarsSpawner implements ConfigurationSerializable,IFinished {
    public BasicLocation spawnLocation;
    public SpawnerType spawnerType;

    private ItemStack drop;
    private int timeToSpawn;
    private int currentTime;

    public BedWarsSpawner() {
    }

    public BedWarsSpawner(BasicLocation spawnLocation, SpawnerType spawnerType) {
        this.spawnLocation = spawnLocation;
        this.spawnerType = spawnerType;

        if(spawnerType == SpawnerType.BRONZE) {
            drop = new ItemStack(Material.BRICK);
            timeToSpawn = 1;
        } else if(spawnerType == SpawnerType.IRON) {
            drop = new ItemStack(Material.IRON_INGOT);
            timeToSpawn = 5;
        } else if(spawnerType == SpawnerType.GOLD) {
            drop = new ItemStack(Material.GOLD_INGOT);
            timeToSpawn = 10;
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> result = new HashMap<>();
        result.put("spawnLocation",spawnLocation.serialize());
        result.put("spawnerType",spawnerType.name());
        return result;
    }

    public static BedWarsSpawner deserialize(Map<String, Object> map) {
        BasicLocation spawnLocation = BasicLocation.deserialize((Map<String, Object>) map.get("spawnLocation"));
        SpawnerType type = SpawnerType.valueOf((String) map.get("spawnerType"));

        return new BedWarsSpawner(spawnLocation,type);
    }

    public BedWarsSpawner setSpawnLocation(BasicLocation spawnLocation) {
        this.spawnLocation = spawnLocation;
        return this;
    }

    public BedWarsSpawner setSpawnerType(SpawnerType spawnerType) {
        this.spawnerType = spawnerType;
        return this;
    }

    @Override
    public boolean isFinished() {
        return ObjectsUtil.checkNonNullAndFinished(spawnerType,spawnerType);
    }

    @Override
    public List<String> getNotFinished() {
        List<String> notFinished = new ArrayList<>();

        if(spawnLocation == null) {
            notFinished.add("Spawn location is null");
        }
        if(spawnerType == null) {
            notFinished.add("Spawner type is null");
        }

        if(!notFinished.isEmpty()) {
            notFinished.add(0,"BedWarsSpawner:");
        }

        return notFinished;
    }

    public void handleSec(World world) {
        currentTime++;

        if(currentTime == timeToSpawn) {
            spawn(world);
            currentTime = 0;
        }
    }

    public void spawn(World world) {
        world.dropItem(spawnLocation.getLoc(world),drop);
    }
}
