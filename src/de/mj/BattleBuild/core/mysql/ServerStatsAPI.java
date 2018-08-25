package de.mj.BattleBuild.core.mysql;

import de.mj.BattleBuild.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;

public class ServerStatsAPI {

    private final Core core;
    private AsyncMySQL amsql;
    private HashMap<Player, HashMap<String, Integer>> played = new HashMap<>();
    private HashMap<Player, String> maxServer = new HashMap<>();

    public ServerStatsAPI(Core core) {
        this.core = core;
        this.amsql = core.getServerManager().getAsyncMySQL();
    }

    public void createTable() {
        AsyncMySQL.update("CREATE TABLE IF NOT EXISTS serverstats (uuid VARCHAR(100),server VARCHAR(50),played INTEGER)");
    }

    public void createPlayer(Player player) {
        AsyncMySQL.update("INSERT INTO serverstats (uuid, server, played) SELECT '" + player.getUniqueId() + "', '" + Bukkit.getServerName() + "', '1' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM serverstats WHERE server = '" + Bukkit.getServerName() + "');");
    }

    public void updatePlayed(Player player, int i, String serverName) {
        AsyncMySQL.update("UPDATE serverstats SET played='" + i + "' WHERE uuid='" + player.getUniqueId() + "' AND server='" + serverName + "'");
    }

    public void getPlayed(Player player) {
        amsql.query("SELECT * FROM serverstats WHERE uuid='" + player.getUniqueId() + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    Integer played = resultSet.getInt("played");
                    String serverName = resultSet.getString("server");
                    HashMap<String, Integer> server = new HashMap<>();
                    server.put(serverName, played);
                    this.played.put(player, server);
                }
            } catch (SQLException sql) {
                sql.printStackTrace();
            }
        });
    }

    public void getMaxPlayed(Player player) {
        amsql.query("SELECT MAX(played),server,uuid FROM serverstats WHERE uuid='" + player.getUniqueId() + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    String maxServer = resultSet.getString("server");
                    this.maxServer.put(player, maxServer);
                }
            } catch (SQLException sql) {
                sql.printStackTrace();
            }
        });
    }

    public Integer getPlayedInt(Player player, String serverName) {
        return played.get(player).get(serverName);
    }

    public HashMap<Player, HashMap<String, Integer>> getPlayed() {
        return played;
    }

    public HashMap<Player, String> getMaxServer() {
        return maxServer;
    }
}
