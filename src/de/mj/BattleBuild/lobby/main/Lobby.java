package de.mj.BattleBuild.lobby.main;

import de.mj.BattleBuild.lobby.MySQL.MySQLLoader;
import de.mj.BattleBuild.lobby.Variabeln.Var;
import de.mj.BattleBuild.lobby.commands.AFKCommand;
import de.mj.BattleBuild.lobby.commands.SpawnCommand;
import de.mj.BattleBuild.lobby.listener.*;
import de.mj.BattleBuild.lobby.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {

    private static Plugin plugin;
    private static Lobby lobby;
    private ConsoleCommandSender sender;
    private static Economy econ = null;
    private NewScoreboardManager scoreboardManager;
    String prefix = new Var().getPrefix();

    @Override
    public void onEnable() {
        setSender(Bukkit.getConsoleSender());

        sender.sendMessage(prefix + "§ewird gestartet...");

        setPlugin(this);
        setLobby(this);

        sender.sendMessage(prefix + "§3initialisiere MySQL-Verbindung...");
        MySQLLoader mySQLLoader = new MySQLLoader(getPlugin());
        mySQLLoader.loadConf();

        sender.sendMessage(prefix + "§6initialisiere Commands und Listener");
        initCommandAndListener();

        sender.sendMessage(prefix + "§9registriere BungeeCord-PluginChannel...");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        sender.sendMessage(prefix + "§dinitialisiere Scheduler...");
        initScheduler();

        sender.sendMessage(prefix + "§binitialisiere Locations...");
        SetLocations setLocations = new SetLocations();
        setLocations.saveLocs();

        sender.sendMessage(prefix + "§2Versuche Vault-Hook...");
        if (!setupEconomy()) {
            sender.sendMessage(String.format("§c[%s] - Es wurde keine Vault-Dependency gefunden ... Plugin wird deaktiviert!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            sender.sendMessage(prefix + "§2Erfolg!");
        }

        sender.sendMessage(prefix + "§binitialisiere Scoreboards...");
        TabList tabList = new TabList();
        tabList.loadTablist();
        this.scoreboardManager = new NewScoreboardManager(this);

        sender.sendMessage(prefix + "§awurde gestartet!");
    }

    public void onDisable() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.kickPlayer("");
        }
        SchedulerSaver schedulerSaver = new SchedulerSaver();
        schedulerSaver.cancelSchedulers();
    }

    public void initCommandAndListener() {
        //Commands
        getCommand("afk").setExecutor(new AFKCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());

        //Listener
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new AFKListener(), this);
        pluginManager.registerEvents(new BukkitMinecraftCommandBlockListener(), this);
        pluginManager.registerEvents(new CancelListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new CompassListener(), this);
        pluginManager.registerEvents(new DoubleJumpListener(), this);
        pluginManager.registerEvents(new JoinListener(), this);
        pluginManager.registerEvents(new JumpPadListener(), this);
        pluginManager.registerEvents(new LobbySwitcherListener(), this);
        pluginManager.registerEvents(new MinionListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);
        pluginManager.registerEvents(new SettingsListener(), this);
        pluginManager.registerEvents(new StopReloadRestartListener(), this);
        pluginManager.registerEvents(new WaterJumpListener(), this);
        pluginManager.registerEvents(new YourProfileListener(), this);
    }

    public void initScheduler() {
        AFKListener afkListener = new AFKListener(getPlugin());
        PlayerRealTime playerRealTime = new PlayerRealTime(getPlugin());
        ActionbarTimer actionbarTimer = new ActionbarTimer(getPlugin());
        afkListener.LocationTimer();
        playerRealTime.setPlayerRealTime();
        actionbarTimer.setActionBar();
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
            entity.removeMetadata(meta,this);
    }

    public void setMetadata(Entity entity, String meta, Object object) {
        removeMetadata(entity, meta);
        entity.setMetadata(meta, new FixedMetadataValue(this, object));
    }

    public NewScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static void setPlugin(Plugin lobby) {
        plugin = lobby;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public void setSender(ConsoleCommandSender consoleCommandSender) {
        this.sender = consoleCommandSender;
    }

    private void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public static Lobby getLobby() {
        return lobby;
    }
}
