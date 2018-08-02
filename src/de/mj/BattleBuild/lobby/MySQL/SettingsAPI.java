package de.mj.BattleBuild.lobby.MySQL;


import de.mj.BattleBuild.lobby.listener.SettingsListener;
import de.mj.BattleBuild.lobby.main.Lobby;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class SettingsAPI {

    private final Lobby lobby;
    private AsyncMySQL amsql;
    private AsyncMySQL.MySQL msql;

    public SettingsAPI(Lobby lobby) {
        this.lobby = lobby;
        amsql = lobby.getAsyncMySQL();
        msql = lobby.getMySQL();
    }

    public void createPlayer(Player p) throws SQLException {
        UUID uuid = p.getUniqueId();
        AsyncMySQL.update("INSERT INTO LobbyConf (UUID, COLOR, WJUMP, PJUMP, SILENT, RIDE, DJUMP) SELECT '" + uuid + "', '1', '1', '1', '1', '1', '1' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM LobbyConf WHERE UUID = '" + uuid + "');");
    }

    public void createScorePlayer(Player p) throws SQLException {
        UUID uuid = p.getUniqueId();
        AsyncMySQL.update("INSERT INTO ScoreConf (UUID, FRIENDS, RANG, SERVER, CLAN, COINS, TIME, REALTIME, WEATHER) SELECT '" + uuid + "', '1', '1', '1', '1', '1', '1', '1', '1' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM ScoreConf WHERE UUID = '" + uuid + "');");
    }

    public void getColor(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        if (rs.next()) {
                            short c = rs.getShort("COLOR");
                            SettingsListener.design.put(p, c);
                            SettingsListener.ItemColToString(p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getSilent(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("SILENT"));
                        }
                        if ((b = rs.getInt("SILENT")) == 1) {
                            SettingsListener.silentstate.add(p);
                        } else {
                            SettingsListener.silentstate.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getWjump(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("WJUMP"));
                        }
                        if ((b = rs.getInt("WJUMP")) == 1) {
                            SettingsListener.waterjump.add(p);
                        } else {
                            SettingsListener.waterjump.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getJumPlate(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("PJUMP"));
                        }
                        if ((b = rs.getInt("PJUMP")) == 1) {
                            SettingsListener.jumppads.add(p);
                        } else {
                            SettingsListener.jumppads.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getDoubleJump(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("DJUMP"));
                        }
                        if ((b = rs.getInt("DJUMP")) == 1) {
                            SettingsListener.doppelsprung.add(p);
                        } else {
                            SettingsListener.doppelsprung.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getRide(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("RIDE"));
                        }
                        int b = rs.getInt("RIDE");
                        System.out.println("R" + b);
                        if (b == 1) {
                            SettingsListener.ridestate.add(p);
                        } else {
                            SettingsListener.ridestate.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void setColor(Player p, short i) {
        UUID uuid = p.getUniqueId();
        AsyncMySQL.update("UPDATE LobbyConf SET COLOR='" + i + "' WHERE UUID='" + uuid + "'");
    }

    public void setSilent(Player p, boolean silent) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = silent ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET SILENT='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setRide(Player p, boolean ride) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = ride ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET RIDE='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setWJUMP(Player p, boolean wjump) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = wjump ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET WJUMP='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setPJUMP(Player p, boolean pjump) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = pjump ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET PJUMP='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setDJUMP(Player p, boolean djump) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = djump ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET DJUMP='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void getFriends(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("FRIENDS"));
                        }
                        if ((b = rs.getInt("FRIENDS")) == 1) {
                            SettingsListener.sfriends.add(p);
                        } else {
                            SettingsListener.sfriends.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getRang(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("RANG"));
                        }
                        if ((b = rs.getInt("RANG")) == 1) {
                            SettingsListener.srang.add(p);
                        } else {
                            SettingsListener.srang.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getServer(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("SERVER"));
                        }
                        if ((b = rs.getInt("SERVER")) == 1) {
                            SettingsListener.sserver.add(p);
                        } else {
                            SettingsListener.sserver.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getClan(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("CLAN"));
                        }
                        if ((b = rs.getInt("CLAN")) == 1) {
                            SettingsListener.sclan.add(p);
                        } else {
                            SettingsListener.sclan.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getCoins(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("COINS"));
                        }
                        if ((b = rs.getInt("COINS")) == 1) {
                            SettingsListener.scoins.add(p);
                        } else {
                            SettingsListener.scoins.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getRealTime(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("REALTIME"));
                        }
                        if ((b = rs.getInt("REALTIME")) == 1) {
                            SettingsListener.srealtime.add(p);
                        } else if (b == 2) {
                            SettingsListener.srealtime.remove((Object)p);
                            SettingsListener.sday.add(p);
                        } else {
                            SettingsListener.srealtime.remove((Object)p);
                            SettingsListener.sday.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getTime(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("TIME"));
                        }
                        if ((b = rs.getInt("TIME")) == 1) {
                            SettingsListener.szeit.add(p);
                        } else {
                            SettingsListener.szeit.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void getWeather(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM ScoreConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        int b;
                        if (rs.next()) {
                            Integer.valueOf(rs.getInt("WEATHER"));
                        }
                        if ((b = rs.getInt("WEATHER")) == 1) {
                            SettingsListener.sweather.add(p);
                        } else {
                            SettingsListener.sweather.remove((Object)p);
                        }
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void setFriends(Player p, boolean friends) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = friends ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET FRIENDS='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setRang(Player p, boolean rang) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = rang ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET RANG='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setServer(Player p, boolean server) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = server ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET SERVER='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setClan(Player p, boolean clan) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = clan ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET CLAN='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setCoins(Player p, boolean coins) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = coins ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET COINS='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setRealTime(Player p, boolean realtime, boolean day) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = realtime ? 1 : (day ? 2 : 0);
        AsyncMySQL.update("UPDATE ScoreConf SET REALTIME='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setWeather(Player p, boolean weather) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = weather ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET WEATHER='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setTime(Player p, boolean time) throws SQLException {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = time ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET TIME='" + b + "' WHERE UUID='" + uuid + "'");
    }
}
