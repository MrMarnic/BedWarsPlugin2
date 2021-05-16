package me.marnic.bedwars.core.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 15.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class ItemBuilder {
    private Material type;
    private String title;
    private List<String> lore;
    private int size;
    private List<EnchantmentLevelPair> enchantments;
    private PotionEffect effect;

    public ItemBuilder() {
        this.enchantments = new ArrayList<>();
        this.lore = new ArrayList<>();
        this.size = 1;
    }

    public ItemBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public ItemBuilder setType(Material type) {
        this.type = type;
        return this;
    }

    public ItemBuilder addEnchant(Enchantment e,int level) {
        this.enchantments.add(new EnchantmentLevelPair(e,level));
        return this;
    }

    public ItemBuilder setEffect(PotionEffect effect) {
        this.effect = effect;
        return this;
    }

    public ItemStack build() {
        if(effect == null) {
            ItemStack stack = new ItemStack(type);
            stack.setAmount(size);

            ItemMeta meta = stack.getItemMeta();
            if(!lore.isEmpty()) {
                meta.setLore(lore);
            }
            if(title != null) {
                meta.setDisplayName(title);
            }
            if(!enchantments.isEmpty()) {
                for(EnchantmentLevelPair pair : enchantments) {
                    meta.addEnchant(pair.getEnchantment(),pair.getLevel(),true);
                }
            }

            stack.setItemMeta(meta);

            return stack;
        } else {
            ItemStack stack = new ItemStack(type);
            stack.setAmount(size);

            PotionMeta meta = (PotionMeta) stack.getItemMeta();
            if(!lore.isEmpty()) {
                meta.setLore(lore);
            }
            if(title != null) {
                meta.setDisplayName(title);
            }
            if(!enchantments.isEmpty()) {
                for(EnchantmentLevelPair pair : enchantments) {
                    meta.addEnchant(pair.getEnchantment(),pair.getLevel(),true);
                }
            }

            meta.addCustomEffect(effect,true);

            stack.setItemMeta(meta);

            return stack;
        }
    }
}
