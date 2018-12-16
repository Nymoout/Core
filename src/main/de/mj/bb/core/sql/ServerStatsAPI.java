package main.de.mj.bb.core.sql;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.HashMap;

public class ServerStatsAPI {

    private final CoreSpigot coreSpigot;
    private AsyncMySQL amsql;
    private HashMap<Player, HashMap<String, Integer>> played = new HashMap<>();
    private HashMap<Player, String> maxServer = new HashMap<>();

    public ServerStatsAPI(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        this.amsql = coreSpigot.getModuleManager().getAsyncMySQL();
    }

    public void createTable() {
        AsyncMySQL.update("CREATE TABLE IF NOT EXISTS lb_serverstats (uuid VARCHAR(100),server VARCHAR(50),played INTEGER)");
    }

    public void createPlayer(Player player) {
        AsyncMySQL.update("INSERT INTO lb_serverstats (uuid, server, played) SELECT '" + player.getUniqueId() + "', '" + Bukkit.getServerName() + "', '1' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM lb_serverstats WHERE server= '" + Bukkit.getServerName() + "' AND uuid='" + player.getUniqueId() + "');");
    }

    public void updatePlayed(Player player, int i, String serverName) {
        AsyncMySQL.update("UPDATE lb_serverstats SET played='" + i + "' WHERE uuid='" + player.getUniqueId() + "' AND server='" + serverName + "'");
    }

    public void getPlayed(Player player) {
        amsql.query("SELECT * FROM lb_serverstats WHERE uuid='" + player.getUniqueId() + "' AND server='" + Bukkit.getServerName() + "'", resultSet -> {
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
        amsql.query("SELECT MAX(played),server FROM lb_serverstats WHERE uuid='" + player.getUniqueId() + "' GROUP BY played", resultSet -> {
            try {
                if (resultSet.last()) {
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
