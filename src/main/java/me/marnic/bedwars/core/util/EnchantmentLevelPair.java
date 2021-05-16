package me.marnic.bedwars.core.util;

import org.bukkit.enchantments.Enchantment;

/**
 * Copyright (c) 15.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class EnchantmentLevelPair {
    private Enchantment enchantment;
    private int level;

    public EnchantmentLevelPair(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }
}
