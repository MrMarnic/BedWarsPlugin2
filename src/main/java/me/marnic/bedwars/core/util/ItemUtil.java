package me.marnic.bedwars.core.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Copyright (c) 15.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class ItemUtil {
    public static boolean isItem(ItemStack stack, String displayName) {
        ItemMeta meta = stack.getItemMeta();
        if(meta != null) {
            return meta.getDisplayName().equalsIgnoreCase(displayName);
        }
        return false;
    }
}
