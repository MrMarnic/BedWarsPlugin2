package me.marnic.bedwars.game.objects;

import me.marnic.bedwars.core.BasicLocation;
import me.marnic.bedwars.core.util.BackupUtil;
import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.core.util.ObjectsUtil;
import org.bukkit.*;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsLobby implements ConfigurationSerializable,IFinished {
    public String worldName;
    public BasicLocation spawnLocation;
    private World world;

    public BedWarsLobby() {
    }

    public BedWarsLobby(String worldName, BasicLocation spawnLocation) {
        this.worldName = worldName;
        this.spawnLocation = spawnLocation;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> result = new HashMap<>();
        result.put("worldName",worldName);
        result.put("spawnLocation",spawnLocation.serialize());
        return result;
    }

    public static BedWarsLobby deserialize(Map<String, Object> map) {
        String worldName = (String) map.get("worldName");
        BasicLocation spawnLocation = BasicLocation.deserialize((Map<String, Object>) map.get("spawnLocation"));

        return new BedWarsLobby(worldName,spawnLocation);
    }

    public BedWarsLobby setWorldName(String worldName) {
        this.worldName = worldName;
        return this;
    }

    public BedWarsLobby setSpawnLocation(BasicLocation spawnLocation) {
        this.spawnLocation = spawnLocation;
        return this;
    }

    @Override
    public boolean isFinished() {
        return ObjectsUtil.checkNonNullAndFinished(worldName,spawnLocation);
    }

    @Override
    public List<String> getNotFinished() {
        List<String> notFinished = new ArrayList<>();

        if(worldName == null) {
            notFinished.add("WorldName is null");
        }
        if(spawnLocation == null) {
            notFinished.add("Spawn location is null");
        }

        if(!notFinished.isEmpty()) {
            notFinished.add(0,"BedWarsLobby:");
        }
        return notFinished;
    }

    public void initWorld() {
        LogUtil.consoleInfo("Staring backup of lobby world...");
        try {
            BackupUtil.backupWorld(Bukkit.getWorldContainer().getAbsolutePath() + File.separator + worldName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.consoleInfo("Backup of lobby world finished!");

        this.world = Bukkit.getServer().createWorld(new WorldCreator(worldName));
        world.setAutoSave(false);
        world.setDifficulty(Difficulty.PEACEFUL);
    }

    public void resetWorld() {
        LogUtil.consoleInfo("Staring reset of lobby world...");
        try {
            BackupUtil.resetWorld(world);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.consoleInfo("Reset of lobby world finished!");
    }

    public World getWorld() {
        return world;
    }
}
