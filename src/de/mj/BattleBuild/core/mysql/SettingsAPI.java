/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.mysql;


import de.mj.BattleBuild.core.Core;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class SettingsAPI {

    private final Core core;
    private AsyncMySQL amsql;

    public SettingsAPI(Core core) {
        this.core = core;
        amsql = core.getServerManager().getAsyncMySQL();
    }

    public void createPlayer(Player p) {
        UUID uuid = p.getUniqueId();
        AsyncMySQL.update("INSERT INTO LobbyConf (UUID, COLOR, WJUMP, PJUMP, SILENT, RIDE, DJUMP) SELECT '" + uuid + "', '1', '1', '1', '1', '1', '1' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM LobbyConf WHERE UUID = '" + uuid + "');");
    }

    public void createScorePlayer(Player p) {
        UUID uuid = p.getUniqueId();
        AsyncMySQL.update("INSERT INTO ScoreConf (UUID, FRIENDS, RANG, SERVER, CLAN, COINS, TIME, REALTIME, WEATHER) SELECT '" + uuid + "', '1', '1', '1', '1', '1', '1', '1', '1' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM ScoreConf WHERE UUID = '" + uuid + "');");
    }

    public void getColor(Player p) {
        UUID uuid = p.getUniqueId();
        amsql.query("SELECT * FROM LobbyConf WHERE UUID='" + uuid + "'", rs -> {
                    try {
                        if (rs.next()) {
                            short c = rs.getShort("COLOR");
                            core.getServerManager().getSettingsListener().getDesign().put(p, c);
                            core.getServerManager().getSettingsListener().ItemColToString(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getSilentState().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getSilentState().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getWaterJump().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getWaterJump().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getJumpPads().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getJumpPads().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getDoubleJump().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getDoubleJump().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getRideState().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getRideState().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void setColor(Player p, short i) {
        UUID uuid = p.getUniqueId();
        AsyncMySQL.update("UPDATE LobbyConf SET COLOR='" + i + "' WHERE UUID='" + uuid + "'");
    }

    public void setSilent(Player p, boolean silent) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = silent ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET SILENT='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setRide(Player p, boolean ride) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = ride ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET RIDE='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setWJUMP(Player p, boolean wjump) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = wjump ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET WJUMP='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setPJUMP(Player p, boolean pjump) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = pjump ? 1 : 0;
        AsyncMySQL.update("UPDATE LobbyConf SET PJUMP='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setDJUMP(Player p, boolean djump) {
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
                            core.getServerManager().getSettingsListener().getSfriends().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getSfriends().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getSrang().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getSrang().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getSserver().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getSserver().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getSclan().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getSclan().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getScoins().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getScoins().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getSrealtime().add(p);
                        } else if (b == 2) {
                            core.getServerManager().getSettingsListener().getSrealtime().remove(p);
                            core.getServerManager().getSettingsListener().getSday().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getSrealtime().remove(p);
                            core.getServerManager().getSettingsListener().getSday().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getStime().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getStime().remove(p);
                        }
                    } catch (SQLException e) {
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
                            core.getServerManager().getSettingsListener().getSweather().add(p);
                        } else {
                            core.getServerManager().getSettingsListener().getSweather().remove(p);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void setFriends(Player p, boolean friends) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = friends ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET FRIENDS='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setRang(Player p, boolean rang) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = rang ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET RANG='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setServer(Player p, boolean server) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = server ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET SERVER='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setClan(Player p, boolean clan) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = clan ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET CLAN='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setCoins(Player p, boolean coins) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = coins ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET COINS='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setRealTime(Player p, boolean realtime, boolean day) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = realtime ? 1 : (day ? 2 : 0);
        AsyncMySQL.update("UPDATE ScoreConf SET REALTIME='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setWeather(Player p, boolean weather) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = weather ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET WEATHER='" + b + "' WHERE UUID='" + uuid + "'");
    }

    public void setTime(Player p, boolean time) {
        UUID uuid = p.getUniqueId();
        int b = 0;
        b = time ? 1 : 0;
        AsyncMySQL.update("UPDATE ScoreConf SET TIME='" + b + "' WHERE UUID='" + uuid + "'");
    }
}
