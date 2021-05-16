package me.marnic.bedwars.game.objects;

import me.marnic.bedwars.core.BasicLocation;
import org.bukkit.Location;

/**
 * Copyright (c) 14.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsBedBuilder {
    private BedWarsTeam team;
    private boolean isBuilding;

    public BedWarsBedBuilder() {

    }

    public void setLoc(Location loc) {
        if(team.bed1 == null) {
            team.bed1 = BasicLocation.fromLoc(loc);
        } else {
            team.bed2 = BasicLocation.fromLoc(loc);
            this.isBuilding = false;
        }
    }

    public void enable(BedWarsTeam team) {
        this.team = team;
        this.team.bed1 = null;
        this.team.bed2 = null;
        this.isBuilding = true;
    }

    public void disable() {
        this.isBuilding = false;
    }

    public boolean isBuilding() {
        return isBuilding;
    }
}
