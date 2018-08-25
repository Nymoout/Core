/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.mysql;

import de.mj.BattleBuild.core.Core;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MySQLLoader {

    private final Core core;
    File f = new File("plugins/BBLobby/", "MySQL.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
    private String host;
    private int port;
    private String user;
    private String pw;
    private String db;

    public MySQLLoader(Core core) {
        this.core = core;
        loadMySQL();
    }

    public void loadConf() {
        core.getSender().sendMessage(core.getServerManager().getData().getPrefix() + "§dload config.yml");
        host = cfg.getString("host");
        port = cfg.getInt("port");
        user = cfg.getString("username");
        pw = cfg.getString("password");
        db = cfg.getString("database");
    }

    public void loadMySQL() {
        this.loadConf();
        core.getSender().sendMessage(core.getServerManager().getData().getPrefix() + "§dconnect to MySQL");
        new AsyncMySQL(core, host, port, user, pw, db);
    }
}
