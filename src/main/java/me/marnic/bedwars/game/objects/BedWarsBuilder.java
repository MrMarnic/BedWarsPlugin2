package me.marnic.bedwars.game.objects;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsBuilder implements IFinished{
    public BedWarsConfig config;
    private BedWarsBedBuilder bedBuilder;
    private boolean isBuilding;

    public BedWarsBuilder() {
        this.bedBuilder = new BedWarsBedBuilder();
    }

    public void start() {
        this.config = new BedWarsConfig();
        config.map = new BedWarsMap();
        config.lobby = new BedWarsLobby();
        this.isBuilding = true;
    }

    @Override
    public boolean isFinished() {
        return config.isFinished();
    }

    @Override
    public List<String> getNotFinished() {

        List<String> notFinished = new ArrayList<>();

        if(config == null) {
            notFinished.add("BedWarsConfig is null");
        } else {
            notFinished.addAll(config.getNotFinished());
        }

        if(!notFinished.isEmpty()) {
            notFinished.add(0,"BedWarsBuilder:");
        }

        return notFinished;
    }

    public BedWarsConfig getConfig() {
        return config;
    }

    public BedWarsBedBuilder getBedBuilder() {
        return bedBuilder;
    }

    public boolean isBuilding() {
        return isBuilding;
    }
}
