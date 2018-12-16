package main.de.mj.bb.core.sql;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class BungeeAPI {

    private final CoreBungee coreBungee;

    public BungeeAPI(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
    }

    public void createPlayer(ProxiedPlayer player) {
        UUID uuid = player.getUniqueId();
        AsyncBungeeSQL.update("INSERT INTO LobbyConf (UUID, COLOR, WJUMP, PJUMP, SILENT, RIDE, DJUMP, SPAWN, VALUE) SELECT '" + uuid + "', '1', '1', '1', '1', '1', '1', '0', 'none' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM LobbyConf WHERE UUID = '" + uuid + "');");
    }

    public boolean checkPlayer(ProxiedPlayer player) {
        UUID uuid = player.getUniqueId();
        String query = "SELECT UUID FROM LobbyConf WHERE UUID='" + uuid + "'";
        try {
            Statement statement = coreBungee.getModuleManager().getAsyncBungeeSQL().getMySQL().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public void getColor(ProxiedPlayer p) {
        UUID uuid = p.getUniqueId();
        String query = "SELECT UUID, COLOR FROM LobbyConf WHERE UUID='" + uuid + "'";
        try {
            Statement statement = coreBungee.getModuleManager().getAsyncBungeeSQL().getMySQL().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            coreBungee.getModuleManager().getBungeeTablist().getDesign().put(p, resultSet.getShort("COLOR"));
            coreBungee.getModuleManager().getBungeeTablist().ItemColToString(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isMaintenance() {
        String query = "SELECT STATE FROM maintenance_state WHERE SERVER='" + coreBungee.getModuleManager().getBungeeType() + "'";
        try {
            Statement statement = coreBungee.getModuleManager().getAsyncBungeeSQL().getMySQL().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt("STATE") == 1;
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
        return false;
    }

    public void setMaintenance(boolean maintenance) {
        AsyncBungeeSQL.update("INSERT INTO maintenance_state (STATE, SERVER) SELECT '0', '" + coreBungee.getModuleManager().getBungeeType() + "' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM maintenance_state WHERE SERVER='" + coreBungee.getModuleManager().getBungeeType() + "');");
        int b = maintenance ? 1 : 0;
        AsyncBungeeSQL.update("UPDATE maintenance_state SET STATE='" + b + "' WHERE SERVER='" + coreBungee.getModuleManager().getBungeeType() + "'");
    }
}
