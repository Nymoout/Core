package de.mj.BattleBuild.lobby.MySQL;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class MySQLLoader {

    File f = new File("plugins/BBLobby/", "MySQL.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    private final Plugin plugin;
    private String host;
    private int port;
    private String user;
    private String pw;
    private String db;

    public MySQLLoader(Plugin pl) {
        this.plugin = pl;
        loadMySQL();
    }

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

    public void loadMySQL() {
        this.loadConf();
        new AsyncMySQL(plugin, host, port, user, pw, db);
    }
}
