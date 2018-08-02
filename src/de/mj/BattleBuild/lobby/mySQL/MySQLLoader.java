package de.mj.BattleBuild.lobby.mySQL;

import de.mj.BattleBuild.lobby.Lobby;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class MySQLLoader {

    File f = new File("plugins/BBLobby/", "MySQL.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    private final Lobby lobby;
    private String host;
    private int port;
    private String user;
    private String pw;
    private String db;

    public MySQLLoader(Lobby lobby) {
        this.lobby = lobby;
        loadMySQL();
    }

    public void loadConf() {
        lobby.getSender().sendMessage(lobby.getData().getPrefix() + "§dload config.yml");
        host = cfg.getString("host");
        port = cfg.getInt("port");
        user = cfg.getString("username");
        pw = cfg.getString("password");
        db = cfg.getString("database");
    }

    public void loadMySQL() {
        this.loadConf();
        lobby.getSender().sendMessage(lobby.getData().getPrefix() + "§dconnect to MySQL");
        new AsyncMySQL(lobby, host, port, user, pw, db);
    }
}
