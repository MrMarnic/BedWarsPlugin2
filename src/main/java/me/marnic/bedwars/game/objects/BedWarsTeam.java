package me.marnic.bedwars.game.objects;

import me.marnic.bedwars.core.BasicLocation;
import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.core.util.ObjectsUtil;
import me.marnic.bedwars.game.user.BedWarsUser;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */

public class BedWarsTeam implements ConfigurationSerializable,IFinished {
    public BasicLocation teamSpawnLocation;
    public ChatColor teamColor;
    public List<BedWarsSpawner> spawners;
    public BasicLocation bed1;
    public BasicLocation bed2;

    public List<BedWarsUser> teamMembers;
    private boolean hasBed = true;

    public BedWarsTeam() {
        this.teamMembers = new ArrayList<>();
        this.spawners = new ArrayList<>();
    }

    public BedWarsTeam(BasicLocation teamSpawnLocation, ChatColor teamColor, List<BedWarsSpawner> spawners, BasicLocation bed1, BasicLocation bed2) {
        this.teamSpawnLocation = teamSpawnLocation;
        this.teamColor = teamColor;
        this.spawners = spawners;
        this.bed1 = bed1;
        this.bed2 = bed2;
        this.teamMembers = new ArrayList<>();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> result = new HashMap<>();
        result.put("teamSpawnLocation",teamSpawnLocation.serialize());
        result.put("teamColor",teamColor.name());
        result.put("spawners",spawners);
        result.put("bed1",bed1.serialize());
        result.put("bed2",bed2.serialize());
        return result;
    }

    public static BedWarsTeam deserialize(Map<String, Object> map) {
        BasicLocation teamSpawnLocation = BasicLocation.deserialize((Map<String, Object>) map.get("teamSpawnLocation"));
        ChatColor teamColor = ChatColor.valueOf((String)map.get("teamColor"));
        List<BedWarsSpawner> spawners = (List<BedWarsSpawner>) map.get("spawners");
        BasicLocation bed1 = BasicLocation.deserialize((Map<String, Object>) map.get("bed1"));
        BasicLocation bed2 = BasicLocation.deserialize((Map<String, Object>) map.get("bed2"));

        return new BedWarsTeam(teamSpawnLocation,teamColor,spawners,bed1,bed2);
    }

    public BedWarsTeam setBed1(BasicLocation bed1) {
        this.bed1 = bed1;
        return this;
    }

    public BedWarsTeam setBed2(BasicLocation bed2) {
        this.bed2 = bed2;
        return this;
    }

    public BedWarsTeam addSpawners(BedWarsSpawner... spawners) {
        this.spawners.addAll(Arrays.asList(spawners));
        return this;
    }

    public BedWarsTeam setTeamColor(ChatColor teamColor) {
        this.teamColor = teamColor;
        return this;
    }

    public BedWarsTeam setTeamSpawnLocation(BasicLocation teamSpawnLocation) {
        this.teamSpawnLocation = teamSpawnLocation;
        return this;
    }

    @Override
    public boolean isFinished() {

        for(BedWarsSpawner spawner: spawners) {
            if(!spawner.isFinished()) {
                return false;
            }
        }

        return ObjectsUtil.checkNonNullAndFinished(teamSpawnLocation,teamColor,bed1,bed2) && !spawners.isEmpty();
    }

    @Override
    public List<String> getNotFinished() {
        List<String> notFinished = new ArrayList<>();

        if(teamSpawnLocation == null) {
            notFinished.add("Team spawn location is null");
        }
        if(teamColor == null) {
            notFinished.add("Team color is null");
        }
        if(spawners.isEmpty()) {
            notFinished.add("No spawner was added");
        } else {
            spawners.forEach((sp)-> {
                notFinished.addAll(sp.getNotFinished());
            });
        }

        if(bed1 == null) {
            notFinished.add("Bed1 location is null");
        }
        if(bed2 == null) {
            notFinished.add("Bed2 location is null");
        }
        if(!notFinished.isEmpty()) {
            if(teamColor != null) {
                notFinished.add(0,"BedWarsTeam " + teamColor.name() + ":");
            } else {
                notFinished.add(0,"BedWarsTeam [NO COLOR]:");
            }
        }

        return notFinished;
    }

    public boolean isFull() {
        return teamMembers.size() >= 1;
    }

    public boolean hasBed() {
        return hasBed;
    }

    public boolean isAlive() {
        for(BedWarsUser user : teamMembers) {
            if(user.isAlive()) {
                return true;
            }
        }

        return false;
    }

    public void removeUser(BedWarsUser user) {
        this.teamMembers.remove(user);
    }

    public void celebrateWin() {
        for (BedWarsUser teamMember : this.teamMembers) {
            LogUtil.playerInfo(teamMember.getPlayer(),"You have won the game!");
        }
    }

    public boolean isBed(Location loc) {
        return this.bed1.equalsLoc(loc) | this.bed2.equalsLoc(loc);
    }

    public void setHasBed(boolean hasBed) {
        this.hasBed = hasBed;

        if(!hasBed) {
            for(BedWarsUser user : teamMembers) {
                LogUtil.playerInfo(user.getPlayer(),"Your bed was destroyed!");
            }
        }
    }
}
