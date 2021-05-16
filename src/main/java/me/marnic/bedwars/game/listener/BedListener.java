package me.marnic.bedwars.game.listener;

import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.game.BedWarsMiniGame;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Copyright (c) 14.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedListener implements Listener {

    private BedWarsMiniGame game;

    public BedListener(BedWarsMiniGame game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e) {
        if(game.getBedWarsBuilder().isBuilding()) {
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block block = e.getClickedBlock();

                if(block.getBlockData() instanceof Bed) {
                    if(game.getBedWarsBuilder().getBedBuilder().isBuilding()) {
                        game.getBedWarsBuilder().getBedBuilder().setLoc(block.getLocation());
                        LogUtil.playerInfo(e.getPlayer(),"Bed Loc set!");
                    }
                }
            }
        }
    }
}
