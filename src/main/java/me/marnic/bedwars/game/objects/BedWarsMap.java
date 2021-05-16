package me.marnic.bedwars.game.objects;

import me.marnic.bedwars.core.util.BackupUtil;
import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.core.util.ObjectsUtil;
import org.bukkit.*;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsMap implements ConfigurationSerializable,IFinished{
    public HashMap<ChatColor,BedWarsTeam> teams;
    public String worldName;
    public String name;
    private World world;
    public List<BedWarsSpawner> spawners;

    public BedWarsMap() {
        this.teams = new HashMap<>();
        this.spawners = new ArrayList<>();
    }

    public BedWarsMap(HashMap<ChatColor,BedWarsTeam> teams, String worldName, String name, List<BedWarsSpawner> spawners) {
        this.teams = teams;
        this.worldName = worldName;
        this.name = name;
        this.spawners = spawners;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> result = new HashMap<>();
        List<BedWarsTeam> teamsList = new ArrayList<>(teams.values());

        result.put("teams",teamsList);
        result.put("worldName",worldName);
        result.put("name",name);
        result.put("spawners",spawners);
        return result;
    }

    public static BedWarsMap deserialize(Map<String, Object> map) {
        List<BedWarsTeam> teams = (List<BedWarsTeam>) map.get("teams");

        String worldName = (String) map.get("worldName");
        String name = (String) map.get("name");
        List<BedWarsSpawner> spawners = (List<BedWarsSpawner>) map.get("spawners");

        HashMap<ChatColor,BedWarsTeam> teamsMap = new HashMap<>();

        for(BedWarsTeam team : teams) {
            teamsMap.put(team.teamColor,team);
        }

        for(int i = 0;i<teams.size();i++) {
            BedWarsTeam team = teams.remove(i);
            teamsMap.put(team.teamColor,team);
        }

        return new BedWarsMap(teamsMap,worldName,name,spawners);
    }

    public BedWarsMap addTeams(BedWarsTeam... teams) {
        for(BedWarsTeam team : teams) {
            this.teams.put(team.teamColor,team);
        }
        return this;
    }

    public BedWarsMap addSpawner(BedWarsSpawner spawner) {
        this.spawners.add(spawner);
        return this;
    }

    public BedWarsMap setWorldName(String worldName) {
        this.worldName = worldName;
        return this;
    }

    public BedWarsMap setName(String name) {
        this.name = name;
        return this;
    }

    public boolean hasTeam(ChatColor chatColor) {
        return this.teams.containsKey(chatColor);
    }

    public BedWarsTeam getTeam(ChatColor chatColor) {
        return this.teams.get(chatColor);
    }

    @Override
    public boolean isFinished() {
        AtomicBoolean teamsFinished = new AtomicBoolean(true);

        teams.forEach((color,team) -> {
            if(!team.isFinished()) {
                teamsFinished.set(false);
            }
        });

        if(!teamsFinished.get()) {
            return false;
        }

        for(BedWarsSpawner spawner : spawners) {
            if(!spawner.isFinished()) {
                return false;
            }
        }

        return ObjectsUtil.checkNonNullAndFinished(worldName) && !teams.isEmpty();
    }

    @Override
    public List<String> getNotFinished() {
        List<String> notFinished = new ArrayList<>();

        if(teams.isEmpty()) {
            notFinished.add("No team was created");
        } else {
            teams.forEach((color,team)-> {
                notFinished.addAll(team.getNotFinished());
            });
        }
        if(!spawners.isEmpty()) {
            spawners.forEach((spawner)-> {
                notFinished.addAll(spawner.getNotFinished());
            });
        }
        if(worldName == null) {
            notFinished.add("World name is null");
        }
        if(name == null) {
            notFinished.add("Name is null");
        }
        if(!notFinished.isEmpty()) {
            notFinished.add(0,"BedWarsMap:");
        }

        return notFinished;
    }

    public void initWorld() {
        LogUtil.consoleInfo("Staring backup of map world...");
        try {
            BackupUtil.backupWorld(Bukkit.getWorldContainer().getAbsolutePath() + File.separator + worldName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.consoleInfo("Backup of lobby map finished!");

        this.world = Bukkit.getServer().createWorld(new WorldCreator(worldName));
        world.setAutoSave(false);
        world.setDifficulty(Difficulty.PEACEFUL);
    }

    public void resetWorld() {
        LogUtil.consoleInfo("Staring reset of map world...");
        try {
            BackupUtil.resetWorld(world);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.consoleInfo("Reset of lobby map finished!");
    }

    public World getWorld() {
        return world;
    }
}
