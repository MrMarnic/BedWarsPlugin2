package me.marnic.bedwars.game.inventory;

import me.marnic.bedwars.core.util.ItemBuilder;
import me.marnic.bedwars.core.util.ItemUtil;
import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.game.BedWarsMiniGame;
import me.marnic.bedwars.game.objects.shop.BedWarsShopResult;
import me.marnic.bedwars.game.objects.shop.IShopOffers;
import me.marnic.bedwars.game.objects.shop.ShopOffer;
import org.bukkit.Material;
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
public class BuildingBlocksInventory extends BasicInventory implements IShopOffers {

    private HashMap<Integer,ShopOffer> shopOffers;

    public BuildingBlocksInventory(BedWarsMiniGame miniGame) {
        super(27, "BedWars Building Blocks", miniGame);
        this.shopOffers = new HashMap<>();

        List<ItemStack> contents = new ArrayList<>();

        ItemStack[] upper_row = row(placeHolder,9);
        ItemStack[] lower_row = row(placeHolder,9);

        lower_row[0] = new ItemBuilder().setType(Material.REDSTONE).setTitle("Back").build();
        lower_row[2] = price(Material.BRICK,2);
        lower_row[3] = price(Material.BRICK,10);
        lower_row[4] = price(Material.BRICK,7);
        lower_row[5] = price(Material.IRON_INGOT,2);
        lower_row[6] = price(Material.BRICK,5);

        ItemStack sandstone = new ItemBuilder().setType(Material.SANDSTONE).build();
        ItemStack endStone = new ItemBuilder().setType(Material.END_STONE).build();
        ItemStack glass = new ItemBuilder().setType(Material.GLASS).build();
        ItemStack ironBlock = new ItemBuilder().setType(Material.IRON_BLOCK).build();
        ItemStack cobweb = new ItemBuilder().setType(Material.COBWEB).build();

        contents.addAll(Arrays.asList(upper_row));
        contents.addAll(Arrays.asList(placeHolder.clone(),placeHolder.clone(),sandstone,endStone,glass,ironBlock,cobweb,placeHolder.clone(),placeHolder.clone()));
        contents.addAll(Arrays.asList(lower_row));

        ItemStack[] cons = new ItemStack[27];
        contents.toArray(cons);

        inventory.setContents(cons);

        this.shopOffers = new HashMap<>();
        shopOffers.put(11,new ShopOffer(sandstone.clone(),Material.BRICK,2));
        shopOffers.put(12,new ShopOffer(endStone.clone(),Material.BRICK,10));
        shopOffers.put(13,new ShopOffer(glass.clone(),Material.BRICK,7));
        shopOffers.put(14,new ShopOffer(ironBlock.clone(),Material.IRON_INGOT,2));
        shopOffers.put(15,new ShopOffer(cobweb.clone(),Material.BRICK,5));
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
