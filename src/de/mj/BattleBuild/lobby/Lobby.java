/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby;

import de.mj.BattleBuild.lobby.commands.AFKCommand;
import de.mj.BattleBuild.lobby.commands.ServerCommand;
import de.mj.BattleBuild.lobby.commands.SetLocCommand;
import de.mj.BattleBuild.lobby.commands.SpawnCommand;
import de.mj.BattleBuild.lobby.listener.*;
import de.mj.BattleBuild.lobby.mySQL.AsyncMySQL;
import de.mj.BattleBuild.lobby.mySQL.MySQLLoader;
import de.mj.BattleBuild.lobby.mySQL.SettingsAPI;
import de.mj.BattleBuild.lobby.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public class Lobby extends JavaPlugin {

    private static Plugin plugin;
    private static Lobby lobby;
    private static Economy econ = null;
    String prefix = new Data().getPrefix();
    private ConsoleCommandSender sender;
    //Commands
    private AFKCommand afkCommand;
    private ServerCommand serverCommand;
    private SetLocCommand setLocCommand;
    private SpawnCommand spawnCommand;
    //Listener
    private AFKListener afkListener;
    private BukkitMinecraftCommandBlockListener commandBlockListener;
    private CancelListener cancelListener;
    private ChatListener chatListener;
    private CompassListener compassListener;
    private DoubleJumpListener doubleJumpListener;
    private JoinListener joinListener;
    private JumpPadListener jumpPadListener;
    private LobbySwitcherListener lobbySwitcherListener;
    private MinionListener minionListener;
    private QuitListener quitListener;
    private ServerListener serverListener;
    private SettingsListener settingsListener;
    private StopReloadRestartListener stopReloadRestartListener;
    private WaterJumpListener waterJumpListener;
    private YourProfileListener yourProfileListener;
    //mySQL
    private AsyncMySQL asyncMySQL;
    private AsyncMySQL.MySQL mySQL;
    private MySQLLoader mySQLLoader;
    private SettingsAPI settingsAPI;
    //Utlis
    private ActionbarTimer actionbarTimer;
    private ItemCreator itemCreator;
    private LocationsUtil locationsUtil;
    private Particle particle;
    private PlayerRealTime playerRealTime;
    private SchedulerSaver schedulerSaver;
    private ScoreboardManager scoreboardManager;
    private SetLocations setLocations;
    private TabList tabList;
    private Title title;
    private Data data;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        setSender(Bukkit.getConsoleSender());

        sender.sendMessage(prefix + "§ewird gestartet...");

        setLobby(this);

        pluginManager = Bukkit.getPluginManager();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        hook();

        init();

        sender.sendMessage(prefix + "§awurde erfolgreich gestartet!");
    }

    public void onDisable() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.kickPlayer("");
        }
        SchedulerSaver schedulerSaver = new SchedulerSaver();
        schedulerSaver.cancelSchedulers();
    }

    public void hook() {
        sender.sendMessage(prefix + "§2try to hook in Vault...");
        if (!setupEconomy()) {
            sender.sendMessage(String.format("§c[%s] - Vault-Dependecy wasn't found - disable Plugin!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            sender.sendMessage(prefix + "§2hooked into: Vault");
        }

        sender.sendMessage(prefix + "§etry to hook into TimoCloud...");
        if (getServer().getPluginManager().getPlugin("TimoCloud") != null) {
            sender.sendMessage(prefix + "§ehooked into: TimoCloud");
        } else {
            sender.sendMessage(String.format("§c[%s] - TimoCloud wasn't found - disable Plugin!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        sender.sendMessage(prefix + "§dtry to hook into LuckPerms...");
        if (getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            sender.sendMessage(prefix + "§dhooked into: LuckPerms");
        } else {
            sender.sendMessage(String.format("§c[%s] - LuckPerms wasn't found - disable Plugin!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        sender.sendMessage(prefix + "§6try to hook into FriendsAPIForPartyAndFriends...");
        if (getServer().getPluginManager().getPlugin("FriendsAPIForPartyAndFriends") != null) {
            sender.sendMessage(prefix + "§6hooked into: FriendsAPIForPartyAndFriends");
        } else {
            sender.sendMessage(String.format("§c[%s] - FriendsAPIForPartyAndFriends wasn't found - disable Plugin!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        sender.sendMessage(prefix + "§5try to hook into Clans-Spigot-Part...");
        if (getServer().getPluginManager().getPlugin("Clans-Spigot-Part") != null) {
            sender.sendMessage(prefix + "§5hooked into: Clans-Spigot-Part");
        } else {
            sender.sendMessage(String.format("§c[%s] - Clans-Spigot-Part wasn't found - disable Plugin!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        sender.sendMessage(prefix + "§atry to hook into NetworkManagerBridge...");
        if (getServer().getPluginManager().getPlugin("NetworkManagerBridge") != null) {
            sender.sendMessage(prefix + "§ahooked into: NetworkManagerBridge");
        } else {
            sender.sendMessage(String.format("§c[%s] - NetworkManagerBridge wasn't found - disable Plugin!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    public void init() {
        //Need to load at first
        sender.sendMessage(prefix + "§fload Data");
        data = new Data();

        //init commands
        sender.sendMessage(prefix + "§fload Commands");
        afkCommand = new AFKCommand(this);
        serverCommand = new ServerCommand(this);
        setLocCommand = new SetLocCommand(this);
        spawnCommand = new SpawnCommand(this);

        //init listener
        sender.sendMessage(prefix + "§fLoad Listener");
        afkListener = new AFKListener(this);
        commandBlockListener = new BukkitMinecraftCommandBlockListener(this);
        cancelListener = new CancelListener(this);
        chatListener = new ChatListener(this);
        compassListener = new CompassListener(this);
        doubleJumpListener = new DoubleJumpListener(this);
        joinListener = new JoinListener(this);
        jumpPadListener = new JumpPadListener(this);
        lobbySwitcherListener = new LobbySwitcherListener(this);
        minionListener = new MinionListener(this);
        quitListener = new QuitListener(this);
        serverListener = new ServerListener(this);
        settingsListener = new SettingsListener(this);
        stopReloadRestartListener = new StopReloadRestartListener(this);
        waterJumpListener = new WaterJumpListener(this);
        yourProfileListener = new YourProfileListener(this);

        //mySQL
        sender.sendMessage(prefix + "§fload MySQL");
        asyncMySQL = new AsyncMySQL(this);
        mySQL = new AsyncMySQL.MySQL();
        mySQLLoader = new MySQLLoader(this);
        settingsAPI = new SettingsAPI(this);

        //Utils
        sender.sendMessage(prefix + "§fload Utils");
        actionbarTimer = new ActionbarTimer(this);
        itemCreator = new ItemCreator();
        locationsUtil = new LocationsUtil();
        particle = new Particle();
        playerRealTime = new PlayerRealTime(this);
        schedulerSaver = new SchedulerSaver();
        scoreboardManager = new ScoreboardManager(this);
        setLocations = new SetLocations(this);
        tabList = new TabList();
        title = new Title(this);

        //load and connect to mysql
        sender.sendMessage(prefix + "§dget MySQL-conf and connect to MySQL");
        mySQLLoader.loadConf();
        mySQLLoader.loadMySQL();

        //init scheduler
        sender.sendMessage(prefix + "§3start scheduler");
        actionbarTimer.setActionBar();
        afkListener.AFKWorker();
        scoreboardManager.ScoreboardActu();

        //load locations
        sender.sendMessage(prefix + "§eget locations from conf");
        setLocations.saveLocs();

        //load tablist
        sender.sendMessage(prefix + "§2create tablist");
        tabList.loadTablist();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            System.out.println("Vault nicht gefunden");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            System.out.println("RegisteredServiceProvider Fehler");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public void removeMetadata(Entity entity, String meta) {
        if (entity.hasMetadata(meta))
            entity.removeMetadata(meta, this);
    }

    public void setMetadata(Entity entity, String meta, Object object) {
        removeMetadata(entity, meta);
        entity.setMetadata(meta, new FixedMetadataValue(this, object));
    }

    public ConsoleCommandSender getSender() {
        return sender;
    }

    public void setSender(ConsoleCommandSender consoleCommandSender) {
        this.sender = consoleCommandSender;
    }

    public void setListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public void setCommand(CommandExecutor commandExecutor, String command) {
        getCommand(command).setExecutor(commandExecutor);
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public Economy getEconomy() {
        return econ;
    }

    public AFKCommand getAfkCommand() {
        return afkCommand;
    }

    public SpawnCommand getSpawnCommand() {
        return spawnCommand;
    }

    public AFKListener getAfkListener() {
        return afkListener;
    }

    public BukkitMinecraftCommandBlockListener getCommandBlockListener() {
        return commandBlockListener;
    }

    public CancelListener getCancelListener() {
        return cancelListener;
    }

    public ChatListener getChatListener() {
        return chatListener;
    }

    public CompassListener getCompassListener() {
        return compassListener;
    }

    public DoubleJumpListener getDoubleJumpListener() {
        return doubleJumpListener;
    }

    public JoinListener getJoinListener() {
        return joinListener;
    }

    public JumpPadListener getJumpPadListener() {
        return jumpPadListener;
    }

    public LobbySwitcherListener getLobbySwitcherListener() {
        return lobbySwitcherListener;
    }

    public MinionListener getMinionListener() {
        return minionListener;
    }

    public QuitListener getQuitListener() {
        return quitListener;
    }

    public SettingsListener getSettingsListener() {
        return settingsListener;
    }

    public StopReloadRestartListener getStopReloadRestartListener() {
        return stopReloadRestartListener;
    }

    public WaterJumpListener getWaterJumpListener() {
        return waterJumpListener;
    }

    public YourProfileListener getYourProfileListener() {
        return yourProfileListener;
    }

    public AsyncMySQL getAsyncMySQL() {
        return asyncMySQL;
    }

    public AsyncMySQL.MySQL getMySQL() {
        return mySQL;
    }

    public MySQLLoader getMySQLLoader() {
        return mySQLLoader;
    }

    public SettingsAPI getSettingsAPI() {
        return settingsAPI;
    }

    public ActionbarTimer getActionbarTimer() {
        return actionbarTimer;
    }

    public ItemCreator getItemCreator() {
        return itemCreator;
    }

    public LocationsUtil getLocationsUtil() {
        return locationsUtil;
    }

    public Particle getParticle() {
        return particle;
    }

    public PlayerRealTime getPlayerRealTime() {
        return playerRealTime;
    }

    public SchedulerSaver getSchedulerSaver() {
        return schedulerSaver;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public SetLocations getSetLocations() {
        return setLocations;
    }

    public TabList getTabList() {
        return tabList;
    }

    public Title getTitle() {
        return title;
    }

    public Data getData() {
        return data;
    }
}
