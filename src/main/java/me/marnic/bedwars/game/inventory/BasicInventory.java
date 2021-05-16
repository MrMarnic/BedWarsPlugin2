package me.marnic.bedwars.game.inventory;

import me.marnic.bedwars.core.util.ItemBuilder;
import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.game.BedWarsMiniGame;
import me.marnic.bedwars.game.objects.shop.BedWarsShopResult;
import me.marnic.bedwars.game.objects.shop.ShopOffer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 15.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BasicInventory {

    protected Inventory inventory;
    protected ItemStack placeHolder;
    protected BedWarsMiniGame miniGame;
    private String title;

    public BasicInventory(int size, String title, BedWarsMiniGame miniGame) {
        this.miniGame = miniGame;
        this.title = title;
        this.inventory = Bukkit.createInventory(null,size,title);
        this.placeHolder = new ItemBuilder().setType(Material.GLASS_PANE).setTitle(" ").build();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ItemStack[] row(ItemStack stack, int row) {

        ItemStack[] items = new ItemStack[row];

        for(int i = 0; i<row;i++) {
            items[i] = stack.clone();
        }

        return items;
    }

    public ItemStack price(Material mat,int amount) {
        return new ItemBuilder().setType(mat).setSize(amount).build();
    }

    public boolean onClick(InventoryClickEvent e) {
        boolean handle = e.getView().getTitle().equalsIgnoreCase(title);

        if(handle) {
            handleClick(e);
        }

        return handle;
    }

    public void handleClick(InventoryClickEvent e) {

    }
}
