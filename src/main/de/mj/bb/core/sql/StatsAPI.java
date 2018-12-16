package main.de.mj.bb.core.sql;

import main.de.mj.bb.core.CoreSpigot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StatsAPI {

    private final AsyncMySQL asyncMySQL;

    public StatsAPI(CoreSpigot coreSpigot) {
        asyncMySQL = coreSpigot.getModuleManager().getAsyncMySQL();
    }

    public void createTable(String tableName) {
        AsyncMySQL.update("CREATE TABLE IF NOT EXIST '" + tableName + "'");
    }

    public void addColumn(String tableName, String column, ColumnType columnType, boolean first) {
        if (columnType.equals(ColumnType.VARCHAR)) {
            if (first)
                AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' VARCHAR(150) FIRST");
            else
                AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' VARCHAR(150)");
        } else if (columnType.equals(ColumnType.INTEGER)) {
            if (first)
                AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' INTEGER FIRST");
            else
                AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' INTEGER");
        } else if (columnType.equals(ColumnType.DATETIME)) {
            if (first)
                AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' DATETIME FIRST");
            else
                AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' DATETIME");
        } else if (columnType.equals(ColumnType.DOUBLE)) {
            if (first)
                AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' DOUBLE FIRST");
            else
                AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' DOUBLE");
        }
    }

    public void addColumnAfter(String tableName, String column, ColumnType columnType, String after) {
        if (columnType.equals(ColumnType.VARCHAR)) {
            AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' VARCHAR(150) AFTER '" + after + "'");
        } else if (columnType.equals(ColumnType.INTEGER)) {
            AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' INTEGER AFTER '" + after + "'");
        } else if (columnType.equals(ColumnType.DATETIME)) {
            AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' DATETIME AFTER '" + after + "'");
        } else if (columnType.equals(ColumnType.DOUBLE)) {
            AsyncMySQL.update("ALTER TABLE '" + tableName + "' ADD '" + column + "' DOUBLE AFTER '" + after + "'");
        }
    }

    public boolean checkPlayer(String table, UUID uuid) {
        String query = "SELECT UUID FROM '" + table + "' WHERE UUID='" + uuid + "'";
        try {
            PreparedStatement statement = asyncMySQL.prepare(query);
            ResultSet resultSet = statement.executeQuery();
            return resultSet != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public Object getStats(String table, String stats, UUID uuid) {
        Object result = null;
        PreparedStatement statement;
        ResultSet resultSet;
        try {
            statement = asyncMySQL.prepare("SELECT * FROM '" + table + "' WHERE UUID= '" + uuid + "'");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getObject(stats);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void updateStats(String table, String stats, UUID uuid) {
        AsyncMySQL.update("UPDATE '" + table + "' SET '" + stats + "' WHERE UUID='" + uuid + "'");
    }
}
