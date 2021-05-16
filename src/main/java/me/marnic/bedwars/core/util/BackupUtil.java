package me.marnic.bedwars.core.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Copyright (c) 14.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BackupUtil {

    /**
     * Copies the world folder to create a backup if the backup folder doesn't exist
     * @param world name of world directory
     * @throws IOException
     */
    public static void backupWorld(String world) throws IOException {
        File worldDirFile = new File(world);
        File backupFile = new File(worldDirFile.getParent() + "/" + worldDirFile.getName() + "_copy");

        if(!backupFile.exists()) {
            copyDirectory(worldDirFile.getAbsolutePath(),backupFile.getAbsolutePath());
        }
    }

    /**
     * Copies the directory and all of its contents
     * @param sourceDirectoryLocation
     * @param destinationDirectoryLocation
     * @throws IOException
     */
    private static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation)
            throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation))
                .forEach(source -> {
                    Path destination = Paths.get(destinationDirectoryLocation, source.toString()
                            .substring(sourceDirectoryLocation.length()));
                    try {
                        Files.copy(source, destination);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * Deletes a file and all of its contents
     * @param f
     * @throws IOException
     */
    private static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                delete(c);
        }
        if (!f.delete())
            throw new FileNotFoundException("Failed to delete file: " + f);
    }

    /**
     * Deletes world directory and replaces it with the backup folder
     * @param world
     * @throws IOException
     */
    public static void resetWorld(World world) throws IOException {
        File worldFile = world.getWorldFolder();

        world.setKeepSpawnInMemory(false);
        Bukkit.unloadWorld(world,true);

        delete(world.getWorldFolder());

        File backupFile = new File(worldFile.getParent() + "/" + worldFile.getName() + "_copy");

        copyDirectory(backupFile.getAbsolutePath(),worldFile.getAbsolutePath());
    }
}
