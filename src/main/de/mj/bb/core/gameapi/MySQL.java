package main.de.mj.bb.core.gameapi;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.mysql.AsyncMySQL;
import main.de.mj.bb.core.mysql.ColumnType;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class MySQL {

    private final AsyncMySQL asyncMySQL;

    public MySQL(CoreSpigot coreSpigot) {
        this.asyncMySQL = coreSpigot.getModuleManager().getAsyncMySQL();
    }

    private boolean tableExist(String tableName) throws SQLException {
        DatabaseMetaData metaData = asyncMySQL.getMySQL().getConnection().getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        return resultSet.getRow() == 1;
    }

    /**
     * @param tableName the table that should be created
     * @param column    the columns you will add
     * @throws SQLException you'll need a try-catch-block
     */
    public void createTable(String tableName, Map<String, ColumnType> column) throws SQLException {
        String tableCreate = "CREATE TABLE IF NOT EXIST '" + tableName + "' (UUID VARCHAR(100));";
        Statement statement = asyncMySQL.getMySQL().getConnection().createStatement();
        statement.executeQuery(tableCreate);
        addColumn(tableName, column);
    }


    private void addColumn(String tableName, Map<String, ColumnType> column) throws SQLException {
        Iterator iterator = column.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ColumnType> entry = (Map.Entry) iterator.next();
            if (!columnExist(tableName, entry.getKey())) {
                String rowCreate = "ALTER TABLE '" + tableName + "' ADD '" + entry.getKey() + "' " + entry.getValue() + ";";
            }
            iterator.remove();
        }
    }


    private boolean columnExist(String tableName, String columnName) throws SQLException {
        DatabaseMetaData metaData = asyncMySQL.getMySQL().getConnection().getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        resultSet.next();
        return resultSet.getObject(columnName) != null;
    }

    /**
     * @param tableName The table where the value for the player will be inserted
     * @param column    The column where the value for the player will be inserted
     * @param value     The value that will be saved fot the player
     * @param uuid      player's uuid
     */
    public void insertIntoTable(String tableName, String column, String value, UUID uuid) {
        AsyncMySQL.update("UPDATE + '" + tableName + "SET '" + column + "'='" + value + "' WHERE UUID='" + uuid + "'");
    }

    /**
     * @param tableName the table where you will search
     * @param column    what you want to return
     * @param uuid      the player you are looking for
     * @return Retruns an object for the table
     */
    public Object getFromTable(String tableName, String column, UUID uuid) {
        String query = "SELECT '" + column + "' FROM '" + tableName + "WHERE UUID='" + uuid + "'";
        try {
            Statement statement = asyncMySQL.getMySQL().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.getObject(column);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add the player to the database
     *
     * @param tableName Where the player where create
     * @param uuid      The player' uuid
     * @param columns   Defaults you will set
     */
    public void createPlayer(String tableName, UUID uuid, Map<String, String> columns) {
        Iterator iterator = columns.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            AsyncMySQL.update("INSERT INTO '" + tableName + "' " + "(" + entry.getKey() + ") SELECT '" + uuid + "', '" + entry.getValue() + "' FROM DUAL WHERE NOT EXISTS (SELECT '*' FROM '" + tableName + "WHERE UUID='" + uuid + "');");
            iterator.remove();
        }
    }

    /**
     * Checks if the player exists in the database
     *
     * @param tableName In which table should the player exist?
     * @param uuid      The player's uuid you look for
     * @return Returns true if the player exists and false if not
     */
    public boolean checkPlayer(String tableName, UUID uuid) {
        String query = "SELECT UUID FROM '" + tableName + "' WHERE UUID='" + uuid + "'";
        try {
            Statement statement = asyncMySQL.getMySQL().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
}
