package main.de.mj.bb.core.sql;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class SpawnLocationAPI {

    private final CoreSpigot coreSpigot;

    public SpawnLocationAPI(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void setSpawnLocation(UUID uuid, int x, int y, int z) {
        AsyncMySQL.update("INSERT INTO spawn_location (UUID, X, Y, Z) SELECT '" + uuid + "', '0', '0', '0' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM spawn_location WHERE UUID = '" + uuid + "');");
        AsyncMySQL.update("UPDATE spawn_location SET X='" + x + "' WHERE UUID='" + uuid + "'");
        AsyncMySQL.update("UPDATE spawn_location SET Y='" + y + "' WHERE UUID='" + uuid + "'");
        AsyncMySQL.update("UPDATE spawn_location SET Z='" + z + "' WHERE UUID='" + uuid + "'");
    }

    public Location getSpawnLocation(UUID uuid) {
        String query = "SELECT * FROM spawn_location WHERE UUID='" + uuid + "'";
        try {
            Statement statement = coreSpigot.getModuleManager().getAsyncMySQL().prepare(query);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return new Location(Bukkit.getPlayer(uuid).getWorld(), resultSet.getInt("X"), resultSet.getInt("Y"), resultSet.getInt("Z"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
