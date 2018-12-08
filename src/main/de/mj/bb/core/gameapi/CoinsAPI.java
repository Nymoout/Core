package main.de.mj.bb.core.gameapi;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.mysql.AsyncMySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class CoinsAPI {

    private final CoreSpigot coreSpigot;
    private HashMap<UUID, Integer> trys = new HashMap<>();

    public CoinsAPI(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public boolean PlayerExistCoins(UUID uuid) {
        try {
            ResultSet rs = coreSpigot.getModuleManager().getAsyncMySQL().getMySQL().query("SELECT * FROM Coins WHERE UUID='" + uuid.toString() + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void registerPlayer(UUID uuid) {
        if (!PlayerExistCoins(uuid)) {
            AsyncMySQL.update("INSERT INTO Coins (UUID, COINS) SELECT '" + uuid.toString() + "', '0' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM Coins WHERE UUID='" + uuid.toString() + "');");
        }
    }

    public int getCoins(UUID uuid) {
        if (PlayerExistCoins(uuid)) {
            ResultSet rs = coreSpigot.getModuleManager().getAsyncMySQL().getMySQL().query("SELECT * FROM Coins WHERE UUID='" + uuid.toString() + "'");
            try {
                if (rs.first()) {
                    int coins = rs.getInt("COINS");
                    trys.remove(uuid);
                    return coins;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            trys.put(uuid, trys.containsKey(uuid) ? trys.get(uuid) + 1 : 1);
            if (trys.get(uuid) >= 5) {
                System.err.println("Please Check the MySQL Configuration");
                return 0;
            }
            registerPlayer(uuid);
            return getCoins(uuid);
        }
        return 0;
    }


    public void setCoins(UUID uuid, int amount) {
        if (PlayerExistCoins(uuid)) {
            AsyncMySQL.update("UPDATE Coins SET COINS='" + amount + "'");
            trys.remove(uuid);
        } else {
            trys.put(uuid, trys.containsKey(uuid) ? trys.get(uuid) + 1 : 1);
            if (trys.get(uuid) >= 5) {
                System.err.println("Please Check the MySQL Configuration");
                return;
            }
            registerPlayer(uuid);
            setCoins(uuid, amount);
        }


    }
}
