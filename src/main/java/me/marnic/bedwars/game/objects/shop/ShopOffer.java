package me.marnic.bedwars.game.objects.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright (c) 16.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class ShopOffer {
    private ItemStack product;
    private Material price;
    private int amount;

    public ShopOffer(ItemStack product, Material price, int amount) {
        this.product = product;
        this.price = price;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack getProduct() {
        return product;
    }

    public Material getPrice() {
        return price;
    }
}
