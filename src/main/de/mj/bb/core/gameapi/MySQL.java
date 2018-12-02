package main.de.mj.bb.core.gameapi;

import main.de.mj.bb.core.CoreSpigot;
import main.de.mj.bb.core.mysql.AsyncMySQL;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;

public class MySQL {

    private final AsyncMySQL asyncMySQL;

    public MySQL(@NotNull CoreSpigot coreSpigot) {
        this.asyncMySQL = coreSpigot.getModuleManager().getAsyncMySQL();
    }


    /**
     *
     * @param query Update something in the MySQL Database
     */
    public void SQLUpdate(String query) {
        AsyncMySQL.update(query);
    }

    /**
     * @param query String what you want to get form MySQL
     * @return ResultSet that can be readed
     */
    public ResultSet SQLQuary(String query) {
        return asyncMySQL.getMySQL().query(query);
    }
}
