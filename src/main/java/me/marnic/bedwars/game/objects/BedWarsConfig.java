package me.marnic.bedwars.game.objects;

import me.marnic.bedwars.core.util.ObjectsUtil;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */

public class BedWarsConfig implements ConfigurationSerializable,IFinished {
    public BedWarsMap map;
    public BedWarsLobby lobby;

    public BedWarsConfig() {
    }

    public BedWarsConfig(BedWarsMap map, BedWarsLobby lobby) {
        this.map = map;
        this.lobby = lobby;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> result = new HashMap<>();
        result.put("map",map);
        result.put("lobby",lobby);
        return result;
    }

    public static BedWarsConfig deserialize(Map<String, Object> map) {
        BedWarsMap bwmap = (BedWarsMap) map.get("map");
        BedWarsLobby lobby = (BedWarsLobby) map.get("lobby");

        return new BedWarsConfig(bwmap,lobby);
    }

    public BedWarsConfig setMap(BedWarsMap map) {
        this.map = map;
        return this;
    }

    public BedWarsConfig setLobby(BedWarsLobby lobby) {
        this.lobby = lobby;
        return this;
    }

    @Override
    public boolean isFinished() {
        return ObjectsUtil.checkNonNullAndFinished(map,lobby);
    }

    @Override
    public List<String> getNotFinished() {
        List<String> notFinished = new ArrayList<>();

        if(map == null) {
            notFinished.add("BedWarsMap is null");
        }else {
            notFinished.addAll(map.getNotFinished());
        }
        if(lobby == null) {
            notFinished.add("BedWars lobby is null");
        }else {
            notFinished.addAll(lobby.getNotFinished());
        }

        if(!notFinished.isEmpty()) {
            notFinished.add(0,"BedWarsConfig:");
        }
        return notFinished;
    }
}
