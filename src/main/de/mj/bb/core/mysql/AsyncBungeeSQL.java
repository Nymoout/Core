package main.de.mj.bb.core.mysql;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncBungeeSQL {

    private static ExecutorService executor;
    private static MySQL sql;
    private Plugin plugin;

    public AsyncBungeeSQL(Plugin pl, String host, int port, String user, String pw, String db) {
        try {
            plugin = pl;
            sql = new MySQL(host, port, user, pw, db);
            executor = Executors.newCachedThreadPool();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public AsyncBungeeSQL(Plugin pl) {
        plugin = pl;
    }

    public static void update(String statement) {
        executor.execute(() -> sql.queryUpdate(statement));
    }

    public void update(PreparedStatement statement) {
        executor.execute(() -> sql.queryUpdate(statement));
        try {
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void query(PreparedStatement statement, Consumer<ResultSet> consumer) {
        executor.execute(() -> {
            ResultSet result = sql.query(statement);
            ProxyServer.getInstance().getScheduler().runAsync(plugin, () -> consumer.accept(result));
        });
    }

    public void query(String statement, Consumer<ResultSet> consumer) {
        executor.execute(() -> {
            ResultSet result = sql.query(statement);
            ProxyServer.getInstance().getScheduler().runAsync(plugin, () -> consumer.accept(result));
        });
    }

    public PreparedStatement prepare(String query) {
        try {
            return sql.getConnection().prepareStatement(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public MySQL getMySQL() {
        return sql;
    }

    public static class MySQL {
        private String host, user, password, database;
        private int port;

        private Connection conn;

        public MySQL() {

        }

        public MySQL(String host, int port, String user, String password, String database) throws Exception {
            this.host = host;
            this.port = port;
            this.user = user;
            this.password = password;
            this.database = database;

            this.openConnection();
        }

        public void queryUpdate(String query) {
            checkConnection();
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                queryUpdate(statement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void queryUpdate(PreparedStatement statement) {
            checkConnection();
            try {
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public ResultSet query(String query) {
            checkConnection();
            try {
                return query(conn.prepareStatement(query));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public ResultSet query(PreparedStatement statement) {
            checkConnection();
            try {
                return statement.executeQuery();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public Connection getConnection() {
            return this.conn;
        }

        private void checkConnection() {
            try {
                if (this.conn == null || !this.conn.isValid(10) || this.conn.isClosed())
                    openConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void openConnection() throws Exception {
            this.conn = DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true",
                    this.user, this.password);
        }

        public void closeConnection() {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this.conn = null;
            }
        }
    }
}
