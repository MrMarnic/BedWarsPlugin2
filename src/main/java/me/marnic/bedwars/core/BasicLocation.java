package me.marnic.bedwars.core;

import me.marnic.bedwars.game.objects.BedWarsConfig;
import me.marnic.bedwars.game.objects.BedWarsLobby;
import me.marnic.bedwars.game.objects.BedWarsMap;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 14.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BasicLocation {
    private double x;
    private double y;
    private double z;

    /**
     * Used to store a location without needing to load the world it is in
     * @param x
     * @param y
     * @param z
     */
    public BasicLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Location getLoc(World world) {
        return new Location(world,x,y,z);
    }

    public static BasicLocation fromLoc(Location loc) {
        return new BasicLocation(loc.getX(),loc.getY(),loc.getZ());
    }

    public Map<String, Object> serialize() {
        Map<String,Object> result = new HashMap<>();
        result.put("x",x);
        result.put("y",y);
        result.put("z",z);
        return result;
    }

    public static BasicLocation deserialize(Map<String, Object> map) {
        double x = (double) map.get("x");
        double y = (double) map.get("y");
        double z = (double) map.get("z");

        return new BasicLocation(x,y,z);
    }

    public boolean equalsLoc(Location loc) {
        return loc.getX() == this.x && loc.getY() == this.y && loc.getZ() == this.z;
    }
}
