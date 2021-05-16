package me.marnic.bedwars.game.inventory;

import me.marnic.bedwars.core.util.ItemBuilder;
import me.marnic.bedwars.core.util.ItemUtil;
import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.game.BedWarsMiniGame;
import me.marnic.bedwars.game.objects.shop.BedWarsShopResult;
import me.marnic.bedwars.game.objects.shop.IShopOffers;
import me.marnic.bedwars.game.objects.shop.ShopOffer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 16.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class WeaponsInventory extends BasicInventory implements IShopOffers {

    private HashMap<Integer,ShopOffer> shopOffers;

    public WeaponsInventory(BedWarsMiniGame miniGame) {
        super(27, "BedWars Weapon Inventory", miniGame);
        this.shopOffers = new HashMap<>();

        List<ItemStack> contents = new ArrayList<>();

        ItemStack[] upper_row = row(placeHolder,9);
        ItemStack[] lower_row = row(placeHolder,9);

        lower_row[0] = new ItemBuilder().setType(Material.REDSTONE).setTitle("Back").build();
        lower_row[2] = price(Material.BRICK,10);
        lower_row[3] = price(Material.IRON_INGOT,1);
        lower_row[4] = price(Material.IRON_INGOT,3);
        lower_row[5] = price(Material.IRON_INGOT,5);
        lower_row[6] = price(Material.GOLD_INGOT,3);

        ItemStack knockbackStick = new ItemBuilder().setType(Material.STICK).addEnchant(Enchantment.KNOCKBACK,1).build();
        ItemStack woodenSword = new ItemBuilder().setType(Material.WOODEN_SWORD).build();
        ItemStack stoneSword = new ItemBuilder().setType(Material.STONE_SWORD).addEnchant(Enchantment.DAMAGE_ALL,1).build();
        ItemStack ironSword = new ItemBuilder().setType(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL,1).build();
        ItemStack diamondSword = new ItemBuilder().setType(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL,1).build();

        contents.addAll(Arrays.asList(upper_row));
        contents.addAll(Arrays.asList(placeHolder.clone(),placeHolder.clone(),knockbackStick,woodenSword,stoneSword,ironSword,diamondSword,placeHolder.clone(),placeHolder.clone()));
        contents.addAll(Arrays.asList(lower_row));

        ItemStack[] cons = new ItemStack[27];
        contents.toArray(cons);

        inventory.setContents(cons);

        this.shopOffers = new HashMap<>();
        shopOffers.put(11,new ShopOffer(knockbackStick.clone(),Material.BRICK,10));
        shopOffers.put(12,new ShopOffer(woodenSword.clone(),Material.IRON_INGOT,1));
        shopOffers.put(13,new ShopOffer(stoneSword.clone(),Material.IRON_INGOT,3));
        shopOffers.put(14,new ShopOffer(ironSword.clone(),Material.IRON_INGOT,5));
        shopOffers.put(15,new ShopOffer(diamondSword.clone(),Material.GOLD_INGOT,3));
    }

    @Override
    public void handleClick(InventoryClickEvent e) {

        if(ItemUtil.isItem(e.getCurrentItem(),"Back")) {
            e.getWhoClicked().openInventory(BedWarsInventories.MAIN.inventory);
        }

        ShopOffer offer = getForSlot(e.getSlot());

        if(offer != null) {
            BedWarsShopResult result = miniGame.getShop().buyShopOffer((Player)e.getWhoClicked(),offer);
            if(!result.isSuccess()) {
                LogUtil.playerInfo((Player) e.getWhoClicked(),result.getErrorMessage());
            }
        }
    }

    @Override
    public ShopOffer getForSlot(int slot) {
        return shopOffers.get(slot);
    }
}
