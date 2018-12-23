package main.de.mj.bb.core.sql;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class NickAPI {

    private final CoreSpigot coreSpigot;
    private AsyncMySQL mySQL;

    public NickAPI(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        this.mySQL = coreSpigot.getModuleManager().getAsyncMySQL();
    }

    public void getPlayerNick(Player player) {
        UUID uuid = player.getUniqueId();
        if (!checkPlayer(player)) return;
        mySQL.query("SELECT * FROM nicksystem WHERE uuid='" + player.getUniqueId() + "'", resultSet -> {
            try {
                if (resultSet.next()) {
                    coreSpigot.getModuleManager().getNickManager().disguise(player);
                }
            } catch (SQLException ignored) { }
        });
    }

    public void setPlayer(Player player, String nickName) {
        UUID uuid = player.getUniqueId();
        String query = "INSERT INTO nicksystem (UUID, NICKNAME) SELECT '" + uuid + "', '" + nickName + "' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM nicksystem WHERE UUID = '" + uuid + "');";
        AsyncMySQL.update(query);
    }

    public void deletePlayer(Player player) {
        String preparedStatement = "DELETE FROM nicksystem WHERE player='" + player.getUniqueId() + "'";
        AsyncMySQL.update(preparedStatement);
    }

    private boolean checkPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        String query = "SELECT UUID FROM nicksystem WHERE UUID='" + uuid + "'";
        try {
            Statement statement = mySQL.getMySQL().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
}
