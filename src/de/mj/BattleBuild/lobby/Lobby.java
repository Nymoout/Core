/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.lobby;

import de.mj.BattleBuild.lobby.utils.Data;
import de.mj.BattleBuild.lobby.utils.HookManager;
import de.mj.BattleBuild.lobby.utils.ServerManager;
import de.mj.BattleBuild.lobby.utils.ServerType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Lobby extends JavaPlugin {


    private Lobby lobby;
    private ConsoleCommandSender sender;
    private ServerManager serverManager;
    private HookManager hookManager;

    private String prefix = new Data().getPrefix();

    @Override
    public synchronized void onEnable() {
        setSender(Bukkit.getConsoleSender());

        sender.sendMessage(prefix + "§eis starting...");
        serverManager = new ServerManager(this);
        hookManager = new HookManager(this);
        setLobby(this);

        sender.sendMessage(prefix + "§edetec server version...");
        sender.sendMessage(prefix + "§adetected server §6" + Bukkit.getServerName());
        sender.sendMessage(prefix + "§eload hooks...");
        hookManager.hook();
        sender.sendMessage(prefix + "§eload modules...");
        preInit();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        sender.sendMessage(prefix + "§awas successfully started!");
    }

    public void onDisable() {
        serverManager.stopServer();
    }

    private void preInit() {
        String server = Bukkit.getServerName();
        if (server.contains("Lobby")) {
            sender.sendMessage(prefix + "§eload modules for Server " + server + " ...");
            serverManager.init(ServerType.LOBBY);
        }
        if (server.equalsIgnoreCase("CityBuild"))
            serverManager.init(ServerType.CITY_BUILD);
        if (server.equalsIgnoreCase("BauServer"))
            serverManager.init(ServerType.BAU_SERVER);
        else
            serverManager.init(ServerType.DEFAULT);

    }

    public ConsoleCommandSender getSender() {
        return sender;
    }

    private void setSender(ConsoleCommandSender consoleCommandSender) {
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

    public ServerManager getServerManager() {
        return serverManager;
    }

    public HookManager getHookManager() {
        return hookManager;
    }
}
