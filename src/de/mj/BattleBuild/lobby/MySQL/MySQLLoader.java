package de.mj.BattleBuild.lobby.MySQL;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MySQLLoader {

    File f = new File("plugins/BBLobby/", "MySQL.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    Plugin plugin;
    String host;
    int port;
    String user;
    String pw;
    String db;

    public void loadConf() {
        host = cfg.getString("host");
        System.out.println(host);
        port = cfg.getInt("port");
        System.out.println(port);
        user = cfg.getString("username");
        System.out.println(user);
        pw = cfg.getString("password");
        db = cfg.getString("database");
        System.out.println(db);
    }

    public MySQLLoader(Plugin pl) {
        this.plugin = pl;
    }

    public void loadMySQL() {
        this.loadConf();
        new AsyncMySQL(plugin, host, port, user, pw, db);
    }
}
