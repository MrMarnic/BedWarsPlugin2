package me.marnic.bedwars.core.util;

import me.marnic.bedwars.game.objects.IFinished;
import org.bukkit.Bukkit;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class ObjectsUtil {
    public static boolean checkNonNull(Object... objs) {
        for(Object o : objs) {
            if(o == null) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkNonNullAndFinished(Object... objs) {
        for(Object o : objs) {
            if(o == null) {
                return false;
            } else {
                if(o instanceof IFinished) {
                    if(!((IFinished)o).isFinished()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
