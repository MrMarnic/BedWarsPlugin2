package me.marnic.bedwars.game.objects.shop;

/**
 * Copyright (c) 16.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsShopResult {
    private boolean success;
    private String errorMessage;

    public BedWarsShopResult(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public BedWarsShopResult() {
        this.success = true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }
}
