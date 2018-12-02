package main.de.mj.bb.core.mysql;

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
        AsyncBungeeSQL.update("INSERT INTO LobbyConf (UUID, COLOR, WJUMP, PJUMP, SILENT, RIDE, DJUMP) SELECT '" + uuid + "', '1', '1', '1', '1', '1', '1' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM LobbyConf WHERE UUID = '" + uuid + "');");
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
}
