package main.de.mj.bb.core;

import lombok.Getter;
import lombok.Setter;
import main.de.mj.bb.core.managers.HookManager;
import main.de.mj.bb.core.managers.ModuleManager;
import main.de.mj.bb.core.utils.Data;
import main.de.mj.bb.core.utils.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;


@Getter
@Setter
public class CoreSpigot extends JavaPlugin {

    /*
     * @author MJ
     * Created in 25.08.2018
     * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
     */

    private CoreSpigot coreSpigot;
    private ConsoleCommandSender sender;
    private ModuleManager moduleManager;
    private HookManager hookManager;

    private String prefix = new Data().getPrefix();

    @Override
    public void onEnable() {
        setSender(Bukkit.getConsoleSender());

        sender.sendMessage("§6  ____        _   _   _      ____        _ _     _         _____               ");
        sender.sendMessage("§6 |  _ \\      | | | | | |    |  _ \\      (_) |   | |       / ____|              ");
        sender.sendMessage("§6 | |_) | __ _| |_| |_| | ___| |_) |_   _ _| | __| |______| |     ___  _ __ ___ ");
        sender.sendMessage("§6 |  _ < / _` | __| __| |/ _ \\  _ <| | | | | |/ _` |______| |    / _ \\| '__/ _ \\");
        sender.sendMessage("§6 | |_) | (_| | |_| |_| |  __/ |_) | |_| | | | (_| |      | |___| (_) | | |  __/");
        sender.sendMessage("§6 |____/ \\__,_|\\__|\\__|_|\\___|____/ \\__,_|_|_|\\__,_|       \\_____\\___/|_|  \\___|");

        sender.sendMessage(prefix + "§eis starting...");
        hookManager = new HookManager(this);
        this.setCoreSpigot(this);

        sender.sendMessage(prefix + "§edetect server version...");
        sender.sendMessage(prefix + "§adetected server §6" + Bukkit.getServerName());
        preInit();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        infoScheduler();
        sender.sendMessage(prefix + "§awas successfully started!");
    }

    public void onDisable() {
        moduleManager.stopServer();
    }

    private void preInit() {
        String server = Bukkit.getServerName();
        sender.sendMessage(prefix + "§eload hooks and modules for Server " + server + " ...");
        if (server.contains("Lobby")) {
            moduleManager = new ModuleManager(this, ServerType.LOBBY);
            hookManager.hook(ServerType.LOBBY);
            moduleManager.init();
            return;
        }
        if (server.contains("BedWars")) {
            moduleManager = new ModuleManager(this, ServerType.BED_WARS);
            hookManager.hook(ServerType.BED_WARS);
            moduleManager.init();
            return;
        }
        if (server.equalsIgnoreCase("CityBuild")) {
            moduleManager = new ModuleManager(this, ServerType.CITY_BUILD);
            hookManager.hook(ServerType.CITY_BUILD);
            moduleManager.init();
            return;
        }
        if (server.equalsIgnoreCase("SkyPvP")) {
            moduleManager = new ModuleManager(this, ServerType.SKY_PVP);
            hookManager.hook(ServerType.SKY_PVP);
            moduleManager.init();
            return;
        }
        if (server.equalsIgnoreCase("BauServer")) {
            moduleManager = new ModuleManager(this, ServerType.BAU_SERVER);
            hookManager.hook(ServerType.BAU_SERVER);
            moduleManager.init();
            return;
        }
        moduleManager = new ModuleManager(this, ServerType.DEFAULT);
        hookManager.hook(ServerType.DEFAULT);
        moduleManager.init();
    }

    public void setListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public void setCommand(CommandExecutor commandExecutor, String command) {
        getCommand(command).setExecutor(commandExecutor);
    }

    private void infoScheduler() {
        new BukkitRunnable() {
            @Override
            public void run() {
                sender.sendMessage("§6  ____        _   _   _      ____        _ _     _         _____               ");
                sender.sendMessage("§6 |  _ \\      | | | | | |    |  _ \\      (_) |   | |       / ____|              ");
                sender.sendMessage("§6 | |_) | __ _| |_| |_| | ___| |_) |_   _ _| | __| |______| |     ___  _ __ ___ ");
                sender.sendMessage("§6 |  _ < / _` | __| __| |/ _ \\  _ <| | | | | |/ _` |______| |    / _ \\| '__/ _ \\");
                sender.sendMessage("§6 | |_) | (_| | |_| |_| |  __/ |_) | |_| | | | (_| |      | |___| (_) | | |  __/");
                sender.sendMessage("§6 |____/ \\__,_|\\__|\\__|_|\\___|____/ \\__,_|_|_|\\__,_|       \\_____\\___/|_|  \\___|");
                sender.sendMessage("§8§m---------------§8[§9ServerInfo§8]§8§m---------------§r");
                long cram = Runtime.getRuntime().freeMemory() / 1048576L;
                long mram = Runtime.getRuntime().maxMemory() / 1048576L;
                ArrayList<String> names = new ArrayList<>();
                for (Player all : Bukkit.getOnlinePlayers()) {
                    names.add(all.getName());
                }
                sender.sendMessage("§9§lCurrent RAM-Usage: §a" + cram + " MB §9 of §3" + mram + " MB");
                sender.sendMessage("§e§lPort: §6" + Bukkit.getPort());
                sender.sendMessage("§bServername: §3" + Bukkit.getServerName());
                sender.sendMessage("§5§lVersion: §b" + Bukkit.getBukkitVersion());
                sender.sendMessage("§6§lPlugin-Version: §e" + coreSpigot.getDescription().getVersion());
                sender.sendMessage("§8§m--------------------------------------------§r");
            }
        }.runTaskTimer(this, 0L, 20L * 60 * 5);
    }
}
