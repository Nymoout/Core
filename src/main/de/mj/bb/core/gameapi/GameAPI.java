package main.de.mj.bb.core.gameapi;

import lombok.Data;
import main.de.mj.bb.core.CoreSpigot;
import org.jetbrains.annotations.NotNull;

@Data
public class GameAPI {

    private final CoreSpigot coreSpigot;
    private final MySQL mySQL;

    public GameAPI(@NotNull CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
        this.mySQL = new MySQL(coreSpigot);
    }

    public CoreSpigot getCoreSpigot() {
        return coreSpigot;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}
