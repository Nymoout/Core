/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.mysql;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MySQLLoader {

    private final CoreSpigot coreSpigot;
    File f = new File("plugins/BBLobby/", "MySQL.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
    private String host;
    private int port;
    private String user;
    private String pw;
    private String db;

    public MySQLLoader(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        loadMySQL();
    }

    public void loadConf() {
        coreSpigot.getSender().sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§dload config.yml");
        host = cfg.getString("host");
        port = cfg.getInt("port");
        user = cfg.getString("username");
        pw = cfg.getString("password");
        db = cfg.getString("database");
    }

    public void loadMySQL() {
        this.loadConf();
        coreSpigot.getSender().sendMessage(coreSpigot.getModuleManager().getData().getPrefix() + "§dconnect to MySQL");
        new AsyncMySQL(coreSpigot, host, port, user, pw, db);
    }
}
