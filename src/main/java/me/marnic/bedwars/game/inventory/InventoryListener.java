package me.marnic.bedwars.game.inventory;

import me.marnic.bedwars.core.GameState;
import me.marnic.bedwars.game.BedWarsMiniGame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Copyright (c) 15.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class InventoryListener implements Listener {

    private BedWarsMiniGame miniGame;

    public InventoryListener(BedWarsMiniGame miniGame) {
        this.miniGame = miniGame;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if(miniGame.getState() == GameState.INGAME) {
            Inventory inv = e.getClickedInventory();

            if(inv != null && e.getCurrentItem() != null) {
                boolean handle = BedWarsInventories.handleInvClick(e);

                if(handle) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
