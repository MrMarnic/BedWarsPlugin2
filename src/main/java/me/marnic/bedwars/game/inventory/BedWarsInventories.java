package me.marnic.bedwars.game.inventory;

import me.marnic.bedwars.game.BedWarsMiniGame;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Copyright (c) 15.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsInventories {

    public static BasicInventory MAIN;
    public static BasicInventory BUILDING_BLOCKS;
    public static BasicInventory PICKAXE_ITEMS;
    public static BasicInventory ARMOR_ITEMS;
    public static BasicInventory WEAPON_ITEMS;
    public static BasicInventory BOW_ITEMS;
    public static BasicInventory MAGIC_ITEMS;
    public static BasicInventory EXTRAS_ITEMS;


    public static void init(BedWarsMiniGame miniGame) {
        MAIN = new BedWarsMainInventory(miniGame);
        BUILDING_BLOCKS = new BuildingBlocksInventory(miniGame);
        PICKAXE_ITEMS = new PickaxeInventory(miniGame);
        ARMOR_ITEMS = new ArmorInventory(miniGame);
        WEAPON_ITEMS = new WeaponsInventory(miniGame);
        BOW_ITEMS = new BowInventory(miniGame);
        MAGIC_ITEMS = new MagicInventory(miniGame);
        EXTRAS_ITEMS = new ExtrasInventory(miniGame);
    }

    public static boolean handleInvClick(InventoryClickEvent e) {
        if(MAIN.onClick(e)) {
            return true;
        }
        if(BUILDING_BLOCKS.onClick(e)) {
            return true;
        }
        if(PICKAXE_ITEMS.onClick(e)) {
            return true;
        }
        if(ARMOR_ITEMS.onClick(e)) {
            return true;
        }
        if(WEAPON_ITEMS.onClick(e)) {
            return true;
        }
        if(BOW_ITEMS.onClick(e)) {
            return true;
        }
        if(MAGIC_ITEMS.onClick(e)) {
            return true;
        }
        if(EXTRAS_ITEMS.onClick(e)) {
            return true;
        }

        return false;
    }
}
