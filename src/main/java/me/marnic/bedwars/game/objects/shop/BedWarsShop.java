package me.marnic.bedwars.game.objects.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 15.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsShop {

    public static final BedWarsShopResult SUCCESS = new BedWarsShopResult();
    public static final BedWarsShopResult NO_MONEY = new BedWarsShopResult("You don't have the needed materials");

    public BedWarsShopResult buyShopOffer(Player buyer,ShopOffer offer) {
        return buyItemStack(buyer,offer.getProduct(),offer.getPrice(),offer.getAmount());
    }

    public BedWarsShopResult buyItemStack(Player buyer, ItemStack toBuy, Material price, int amount) {
        if(pay(price,amount,buyer.getInventory())) {
            HashMap<Integer,ItemStack> items = buyer.getInventory().addItem(toBuy);
            for(ItemStack stack : items.values()) {
                buyer.getWorld().dropItem(buyer.getLocation(), stack);
            }

            return SUCCESS;
        } else {
            return NO_MONEY;
        }
    }

    private boolean pay(Material mat, int amount, Inventory inv) {

        int matInInvSize = 0;
        List<Integer> slots = new ArrayList<>();

        for(int i = 0;i<inv.getSize();i++) {
            ItemStack stack = inv.getItem(i);

            if(stack != null) {
                if(stack.getType() == mat) {
                    matInInvSize += stack.getAmount();
                    slots.add(i);
                }
            }
        }

        if(matInInvSize >= amount) {
            for(int slot : slots) {
                ItemStack stack = inv.getItem(slot);

                if(amount <= stack.getAmount()) {
                    if(amount < stack.getAmount()) {
                        stack.setAmount(stack.getAmount() - amount);
                    } else {
                        inv.setItem(slot,null);
                    }
                    return true;
                } else {
                    amount -= stack.getAmount();
                    inv.setItem(slot,null);
                }
            }
        }

        return false;
    }
}
