package me.marnic.bedwars.game.inventory;

import me.marnic.bedwars.core.util.ItemBuilder;
import me.marnic.bedwars.core.util.ItemUtil;
import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.game.BedWarsMiniGame;
import me.marnic.bedwars.game.objects.shop.BedWarsShopResult;
import me.marnic.bedwars.game.objects.shop.IShopOffers;
import me.marnic.bedwars.game.objects.shop.ShopOffer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 15.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsMainInventory extends BasicInventory {

    public BedWarsMainInventory(BedWarsMiniGame miniGame) {
        super(27, "BedWars Main",miniGame);

        List<ItemStack> contents = new ArrayList<>();

        ItemStack[] upper_row = row(placeHolder,9);
        ItemStack[] lower_row = row(placeHolder,9);

        ItemStack blocks = new ItemBuilder().setType(Material.SANDSTONE).setTitle("Building Blocks").build();
        ItemStack specials = new ItemBuilder().setType(Material.STONE_PICKAXE).setTitle("Pickaxe Items").build();
        ItemStack armor = new ItemBuilder().setType(Material.LEATHER_CHESTPLATE).setTitle("Armor Items").build();
        ItemStack weapons = new ItemBuilder().setType(Material.STONE_SWORD).setTitle("Weapon Items").build();
        ItemStack bows = new ItemBuilder().setType(Material.BOW).setTitle("Bow Items").build();
        ItemStack magic = new ItemBuilder().setType(Material.POTION).setTitle("Magic Items").build();
        ItemStack extras = new ItemBuilder().setType(Material.ENDER_PEARL).setTitle("Extra Items").build();

        contents.addAll(Arrays.asList(upper_row));
        contents.addAll(Arrays.asList(placeHolder.clone(),blocks,specials,armor,weapons,bows,magic,extras,placeHolder.clone()));
        contents.addAll(Arrays.asList(lower_row));

        ItemStack[] cons = new ItemStack[27];
        contents.toArray(cons);

        inventory.setContents(cons);
    }

    @Override
    public void handleClick(InventoryClickEvent e) {

        if(ItemUtil.isItem(e.getCurrentItem(),"Building Blocks")) {
            e.getWhoClicked().openInventory(BedWarsInventories.BUILDING_BLOCKS.inventory);
        }
        if(ItemUtil.isItem(e.getCurrentItem(),"Pickaxe Items")) {
            e.getWhoClicked().openInventory(BedWarsInventories.PICKAXE_ITEMS.inventory);
        }
        if(ItemUtil.isItem(e.getCurrentItem(),"Armor Items")) {
            e.getWhoClicked().openInventory(BedWarsInventories.ARMOR_ITEMS.inventory);
        }
        if(ItemUtil.isItem(e.getCurrentItem(),"Weapon Items")) {
            e.getWhoClicked().openInventory(BedWarsInventories.WEAPON_ITEMS.inventory);
        }
        if(ItemUtil.isItem(e.getCurrentItem(),"Bow Items")) {
            e.getWhoClicked().openInventory(BedWarsInventories.BOW_ITEMS.inventory);
        }
        if(ItemUtil.isItem(e.getCurrentItem(),"Magic Items")) {
            e.getWhoClicked().openInventory(BedWarsInventories.MAGIC_ITEMS.inventory);
        }
        if(ItemUtil.isItem(e.getCurrentItem(),"Extra Items")) {
            e.getWhoClicked().openInventory(BedWarsInventories.EXTRAS_ITEMS.inventory);
        }
    }
}
