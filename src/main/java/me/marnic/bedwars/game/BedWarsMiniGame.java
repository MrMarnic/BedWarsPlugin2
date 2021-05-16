package me.marnic.bedwars.game;

import me.marnic.bedwars.core.*;
import me.marnic.bedwars.core.util.LogUtil;
import me.marnic.bedwars.game.inventory.BedWarsInventories;
import me.marnic.bedwars.game.inventory.InventoryListener;
import me.marnic.bedwars.game.listener.BedListener;
import me.marnic.bedwars.game.listener.InGameListener;
import me.marnic.bedwars.game.listener.LobbyListener;
import me.marnic.bedwars.game.objects.*;
import me.marnic.bedwars.game.objects.shop.BedWarsShop;
import me.marnic.bedwars.game.user.BedWarsUser;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

/**
 * Copyright (c) 13.05.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class BedWarsMiniGame extends MiniGame {

    private BedWarsConfig config;
    private boolean configFinished = false;
    private BedWarsBuilder bedWarsBuilder;
    private BedWarsShop shop;
    private BukkitTask startingTask;
    private BukkitTask endingTask;
    private InGameManager manager;
    private SpawnerManager spawnerManager;

    public BedWarsMiniGame(MiniGameCore core) {
        super(core);
        this.bedWarsBuilder = new BedWarsBuilder();
        this.manager = new InGameManager(core,this);
        this.shop = new BedWarsShop();
        this.spawnerManager = new SpawnerManager(this);
    }

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(BedWarsLobby.class);
        ConfigurationSerialization.registerClass(BedWarsSpawner.class);
        ConfigurationSerialization.registerClass(BedWarsTeam.class);
        ConfigurationSerialization.registerClass(BedWarsMap.class);
        ConfigurationSerialization.registerClass(BedWarsConfig.class);

        Bukkit.getPluginManager().registerEvents(new BedListener(this),plugin);
        Bukkit.getPluginManager().registerEvents(new LobbyListener(this),plugin);
        Bukkit.getPluginManager().registerEvents(new InGameListener(this),plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(this),plugin);

        this.state = GameState.STARTING;
        core.enableConfig();
        core.getProtectionManager().setAllowJoin(false,"MiniGame is starting! Please wait!");

        prepare();

        if(configFinished) {
            Bukkit.getLogger().warning("MiniGame config loaded correctly!");

            LogUtil.consoleInfo("Loading worlds...");
            config.lobby.initWorld();
            config.map.initWorld();
            LogUtil.consoleInfo("Loaded worlds!");

            Bukkit.getLogger().warning("MiniGame state set to LOBBY!");
            this.state = GameState.LOBBY;
            core.getProtectionManager().reset();
            core.getProtectionManager().setAllowDamage(false).setBreakBlock(false).setAllowMove(true).setAllowPlayerInteract(false).setPlaceBlock(false);
            spawnerManager.setWorld(config.map.getWorld());
            spawnerManager.init(config.map);
        } else {
            core.getProtectionManager().reset();
            Bukkit.getLogger().warning("MiniGame could not be started!");
        }
    }

    @Override
    public void prepare() {
        config = plugin.getConfig().getObject("config",BedWarsConfig.class);

        if(config != null && !config.isFinished()) {
            Bukkit.getLogger().warning("BedWarsMap is not finished!");
            List<String> notFinished = config.getNotFinished();
            notFinished.forEach(LogUtil::consoleWarn);
            configFinished = false;
        } else if(config == null) {
            Bukkit.getLogger().warning("BedWarsMap config is null!");
            configFinished = false;
        } else if(config.isFinished()) {
            configFinished = true;
        }
    }

    @Override
    public void startGame() {
        startingTask.cancel();
        this.state = GameState.INGAME;

        core.getProtectionManager().reset();
        core.getProtectionManager().setAllowJoin(false,"Game is running!");

        Bukkit.getOnlinePlayers().forEach(p -> {
            core.getUserManager().addUser(new BedWarsUser(p.getUniqueId()));
        });

        for(MiniGameUser user : core.getUserManager().getUsers().values()) {
            BedWarsUser bwuser = (BedWarsUser) user;

            bwuser.getPlayer().setGameMode(GameMode.SURVIVAL);
            bwuser.getPlayer().getInventory().clear();

            BedWarsTeam team = getEmptyTeam();

            if(team != null) {
                bwuser.setTeam(team);
                team.teamMembers.add(bwuser);
            }
        }

        BedWarsInventories.init(this);

        spawnerManager.start();

        teleportPlayers();
    }

    public void handlePlayerJoin(Player player) {
        if(Bukkit.getOnlinePlayers().size() == config.map.teams.size()) {
            startStartingTask();
        }
    }

    public void handlePlayerLeave(Player player) {
        if(state == GameState.LOBBY) {
            if(startingTask != null) {
                startingTask.cancel();
                LogUtil.allPlayerInfo("Starting stopped. Not enough players");
            }
        } else if(state == GameState.INGAME) {
            manager.handlePlayerLeave(player);
        }
    }

    @Override
    public void teleportPlayers() {
        for(BedWarsTeam team : config.map.teams.values()) {
            team.teamMembers.forEach((player)-> {
                player.getPlayer().teleport(team.teamSpawnLocation.getLoc(config.map.getWorld()));
            });
        }
    }

    @Override
    public void stopGame() {
        this.startEndingTask();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, String cmd, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(cmd.equalsIgnoreCase("bw")) {
                if(args.length > 0) {
                    if(args[0].equalsIgnoreCase("stop")) {
                        stopGame();
                    }
                    if(args[0].equalsIgnoreCase("create")) {
                        bedWarsBuilder.start();
                        LogUtil.playerInfo(player,"BedWarsMap creation started");
                    }
                    if(args[0].equalsIgnoreCase("lobby")) {
                        if(args[1].equalsIgnoreCase("world")) {
                            String name = args[2];
                            bedWarsBuilder.config.lobby.setWorldName(name);
                            LogUtil.playerInfo(player,"Lobby world set");
                        }
                        if(args[1].equalsIgnoreCase("spawn")) {
                            Location loc = player.getLocation();
                            bedWarsBuilder.config.lobby.setSpawnLocation(BasicLocation.fromLoc(loc));
                            LogUtil.playerInfo(player,"Lobby spawn set");
                        }
                    }
                    if(args[0].equalsIgnoreCase("map")) {
                        if(args[1].equalsIgnoreCase("world")) {
                            String name = args[2];
                            bedWarsBuilder.config.map.setWorldName(name);
                            LogUtil.playerInfo(player,"Map world set");
                        }
                        if(args[1].equalsIgnoreCase("name")) {
                            String name = args[2];
                            bedWarsBuilder.config.map.setName(name);
                            LogUtil.playerInfo(player,"Map name set");
                        }
                        if(args[1].equalsIgnoreCase("spawner")) {
                            String action = args[2];

                            if(action.equalsIgnoreCase("add")) {
                                String material = args[3];
                                SpawnerType type = SpawnerType.valueOf(material);

                                Location loc = player.getLocation();

                                BedWarsSpawner spawner = new BedWarsSpawner(BasicLocation.fromLoc(loc),type);

                                bedWarsBuilder.config.map.spawners.add(spawner);

                                LogUtil.playerInfo(player,material + " spawner was added to map ");
                            }
                        }
                        if(args[1].equalsIgnoreCase("team")) {
                            if(args[2].equalsIgnoreCase("create")) {
                                String color = args[3];
                                BedWarsTeam team = new BedWarsTeam();
                                team.setTeamColor(ChatColor.valueOf(color));
                                bedWarsBuilder.config.map.teams.put(ChatColor.valueOf(color),team);
                                LogUtil.playerInfo(player,"Team " + color + " created");
                            } else {
                                String color = args[2];
                                ChatColor teamColor = ChatColor.valueOf(color);

                                if(bedWarsBuilder.config.map.hasTeam(teamColor)) {

                                    BedWarsTeam team = bedWarsBuilder.config.map.getTeam(teamColor);

                                    if(args[3].equalsIgnoreCase("setspawn")) {
                                        Location loc = player.getLocation();
                                        team.setTeamSpawnLocation(BasicLocation.fromLoc(loc));
                                        LogUtil.playerInfo(player,"Spawn for team " + team.teamColor.name() + " set");
                                    }
                                    if(args[3].equalsIgnoreCase("bed")) {
                                        bedWarsBuilder.getBedBuilder().enable(team);
                                        LogUtil.playerInfo(player,"Click on the bed blocks to add the bed");
                                    }
                                    if(args[3].equalsIgnoreCase("spawner")) {
                                        String action = args[4];

                                        if(action.equalsIgnoreCase("add")) {
                                            String material = args[5];
                                            SpawnerType type = SpawnerType.valueOf(material);

                                            Location loc = player.getLocation();

                                            BedWarsSpawner spawner = new BedWarsSpawner(BasicLocation.fromLoc(loc),type);

                                            team.spawners.add(spawner);

                                            LogUtil.playerInfo(player,material + " spawner was added to team " + teamColor.name());
                                        }
                                    }
                                } else {
                                    LogUtil.playerInfo(player,"Team " + teamColor.name() + " does not exist");
                                }
                            }
                        }
                        if(args[1].equalsIgnoreCase("finish")) {
                            if(bedWarsBuilder.isFinished()) {
                                config = bedWarsBuilder.config;
                                plugin.getConfig().set("config",config);
                                plugin.saveConfig();

                                LogUtil.playerInfo(player,"BedWarsMap finished and saved!");
                            } else {
                                LogUtil.playerInfo(player,"BedWarsMap is not finished!");
                                List<String> notFinished = bedWarsBuilder.getNotFinished();
                                notFinished.forEach(LogUtil::consoleWarn);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public BedWarsTeam getEmptyTeam() {
        for(BedWarsTeam team : config.map.teams.values()) {
            if(!team.isFull()) {
                return team;
            }
        }

        return null;
    }

    public BedWarsBuilder getBedWarsBuilder() {
        return bedWarsBuilder;
    }

    public BedWarsConfig getConfig() {
        return config;
    }

    public boolean isFull() {
        return Bukkit.getOnlinePlayers().size() > config.map.teams.size();
    }

    public void startStartingTask() {
        startingTask = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

            int secs = 10;

            @Override
            public void run() {
                if(secs == 10) {
                    LogUtil.allPlayerInfo("Starting in 10s...");
                } else if(secs == 5) {
                    LogUtil.allPlayerInfo("Starting in 5s...");
                } else if(secs == 3) {
                    LogUtil.allPlayerInfo("Starting in 3s...");
                } else if(secs == 2) {
                    LogUtil.allPlayerInfo("Starting in 2s...");
                } else if(secs == 1) {
                    LogUtil.allPlayerInfo("Starting in 1s...");
                } else if(secs == 0) {
                    LogUtil.allPlayerInfo("Starting game...");
                    startGame();
                }

                secs--;
            }
        }, 0, 20);
    }

    public void startEndingTask() {
        endingTask = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

            int secs = 10;

            @Override
            public void run() {
                if(secs == 10) {
                    LogUtil.allPlayerInfo("Stopping in 10s...");
                } else if(secs == 5) {
                    LogUtil.allPlayerInfo("Stopping in 5s...");
                } else if(secs == 3) {
                    LogUtil.allPlayerInfo("Stopping in 3s...");
                } else if(secs == 2) {
                    LogUtil.allPlayerInfo("Stopping in 2s...");
                } else if(secs == 1) {
                    LogUtil.allPlayerInfo("Stopping in 1s...");
                } else if(secs == 0) {
                    state = GameState.ENDED;
                    spawnerManager.stop();
                    LogUtil.allPlayerInfo("Stopping game...");
                    Bukkit.getOnlinePlayers().forEach(p-> {
                        p.getInventory().clear();
                        p.setGameMode(GameMode.SURVIVAL);
                        p.kickPlayer("Game ended!");
                    });
                    config.map.resetWorld();
                    config.lobby.resetWorld();
                    Bukkit.shutdown();
                }

                secs--;
            }
        }, 0, 20);
    }

    public boolean isBedWarsBlock(Material material) {
        return material == Material.SANDSTONE | material == Material.GLASS | material == Material.COBWEB | material == Material.END_STONE | material == Material.IRON_BLOCK;
    }

    public InGameManager getManager() {
        return manager;
    }

    public BedWarsShop getShop() {
        return shop;
    }
}
