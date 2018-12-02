package main.de.mj.bb.core.mysql;

import main.de.mj.bb.core.CoreBungee;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BungeeSQLLoader {

    private final CoreBungee coreBungee;
    private Configuration configuration;
    private String host;
    private int port;
    private String user;
    private String pw;
    private String db;

    public BungeeSQLLoader(CoreBungee coreBungee) {
        this.coreBungee = coreBungee;
    }

    public void createConfig() {
        if (!coreBungee.getDataFolder().exists())
            coreBungee.getDataFolder().mkdir();

        File file = new File(coreBungee.getDataFolder(), "mysql.yml");


        if (!file.exists()) {
            try (InputStream in = coreBungee.getResourceAsStream("mysql.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadConfig() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(coreBungee.getDataFolder(), "mysql.yml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadConf() {
        host = configuration.getString("host");
        port = configuration.getInt("port");
        user = configuration.getString("username");
        pw = configuration.getString("password");
        db = configuration.getString("database");
    }

    public void loadMySQL() {
        this.loadConf();
        System.out.println("Starte MySQL Verbindung");
        new AsyncBungeeSQL(coreBungee, host, port, user, pw, db);
    }
}
